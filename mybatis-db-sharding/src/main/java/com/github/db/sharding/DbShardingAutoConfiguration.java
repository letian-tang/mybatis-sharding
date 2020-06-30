package com.github.db.sharding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.db.sharding.common.DbShardingUtils;
import com.github.db.sharding.strategy.ShardingStrategy;
import com.zaxxer.hikari.HikariDataSource;
import com.github.db.sharding.common.DbShardingProperties;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import com.zaxxer.hikari.HikariConfig;
import com.github.db.sharding.core.ShardingInterceptor;
import com.github.db.sharding.db.DataSourceAdvice;
import com.github.db.sharding.db.DynamicDataSource;


/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 14:44
 */
@Configuration
@ComponentScan("com.github.db.sharding")
@ConditionalOnProperty(prefix = "mybatis.db.sharding", name = "enabled",
        havingValue = "true")
public class DbShardingAutoConfiguration {


    private final DbShardingProperties shardingProperties;
    private final ShardingStrategy shardStrategy;
    private static final String EXPRESSION_TRANSACTIONAL =
            "@annotation(org.springframework.transaction.annotation.Transactional)";

    private static final String EXPRESSION_TRANSACTIONAL_TEMPLATE =
            "execution(* org.springframework.transaction.support.TransactionTemplate.execute(..))";

    private final String[] expressions =
            new String[] {EXPRESSION_TRANSACTIONAL, EXPRESSION_TRANSACTIONAL_TEMPLATE};


    public DbShardingAutoConfiguration(ShardingStrategy shardStrategy,
            DbShardingProperties shardingProperties) {
        this.shardingProperties = shardingProperties;
        this.shardStrategy = shardStrategy;
    }

    @Bean
    public ShardingInterceptor shardingPlugin() {
        return new ShardingInterceptor();
    }


    @Bean(destroyMethod = "close")
    public DynamicDataSource dataSource() {
        DynamicDataSource resolver = new DynamicDataSource();
        Map<Object, Object> dataSources = new HashMap<>();
        List<HikariConfig> hikariConfigs = shardingProperties.getDataSources();
        int i = 0;
        for (HikariConfig hikariConfig : hikariConfigs) {
            dataSources.put(DbShardingUtils.dbKey((long) i++), new HikariDataSource(hikariConfig));
        }
        resolver.setTargetDataSources(dataSources);
        return resolver;
    }

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor() {
        List<String> expressionList = shardingProperties.getMapperPointcuts();
        Assert.notEmpty(expressionList, "Mapper Pointcut is null");
        DataSourceAdvice interceptor = new DataSourceAdvice(shardStrategy, shardingProperties);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        expressionList.addAll(Arrays.asList(expressions));
        String expression = String.join(" || ", expressionList);
        pointcut.setExpression(expression);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }

}

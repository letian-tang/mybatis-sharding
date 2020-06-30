package com.github.db.sharding.db;


import com.github.db.sharding.common.DbShardingProperties;
import com.github.db.sharding.common.DbShardingUtils;
import com.github.db.sharding.strategy.ShardingStrategy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
public class DataSourceAdvice implements MethodInterceptor {

    private final ShardingStrategy shardStrategy;
    private final int dbNums;

    public DataSourceAdvice(ShardingStrategy shardStrategy,
            DbShardingProperties shardingProperties) {
        this.shardStrategy = shardStrategy;
        this.dbNums = shardingProperties.getDataSources().size();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Long dbShardingKey = shardStrategy.getShardingKey();
        Long dbIndex = dbShardingKey % dbNums;
        DataSourceHolder.setDataSource(DbShardingUtils.dbKey(dbIndex));
        Object object;
        try {
            object = invocation.proceed();
        } finally {
            DataSourceHolder.clear();
        }
        return object;
    }
}

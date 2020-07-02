    史上最简洁的分库分表中间件  
    基于Springboot+Mybatis的分库分表
    基于Mapper的切面  
    数据源默认使用HikariDataSource
```
@Component
public class ShardingStrategyImpl implements ShardingStrategy {

    @Override
    public Long getShardingKey() {
        // 例子：从ThreadLocal中获取当前卖家id，通过卖家id sharding
        return SellerIdHolder.getSellerId();
    }
}
```
mybatis-db-sharding 分库分表   
mybatis-db-example OrderServiceTest 单元测试

```$xslt
如果有事务，需要在开启事务之前确定数据源   
那么就需要对@Transactional 和 TransactionTemplate.execute做拦截    

private static final String EXPRESSION_TRANSACTIONAL =
            "@annotation(org.springframework.transaction.annotation.Transactional)";

private static final String EXPRESSION_TRANSACTIONAL_TEMPLATE =
        "execution(* org.springframework.transaction.support.TransactionTemplate.execute(..))";

private final String[] expressions =
        new String[] {EXPRESSION_TRANSACTIONAL, EXPRESSION_TRANSACTIONAL_TEMPLATE};

/**
 * 拦截器
 * 
 * @return defaultPointcutAdvisor
 */
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
```

```
#开启
mybatis.db.sharding.enabled=true
#Mapper切面，多个 逗号分隔 如 execution(* com.tdy..*Mapper.*(..)),execution(* com.tdy..*Mapper.*(..))
mybatis.db.sharding.mapper-pointcuts=execution(* com.tdy..*Mapper.*(..))
#数据库1
mybatis.db.sharding.data-sources[0].jdbc-url=jdbc:mysql://localhost:3306/sophon_0?serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&useAffectedRows=true
mybatis.db.sharding.data-sources[0].username=username
mybatis.db.sharding.data-sources[0].password=password
mybatis.db.sharding.data-sources[0].maximum-pool-size=10
mybatis.db.sharding.data-sources[0].idle-timeout=10000
#数据库2
mybatis.db.sharding.data-sources[1].jdbc-url=jdbc:mysql://localhost:3306/sophon_1?serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&useAffectedRows=true
mybatis.db.sharding.data-sources[1].username=username
mybatis.db.sharding.data-sources[1].password=password
mybatis.db.sharding.data-sources[1].maximum-pool-size=3
mybatis.db.sharding.data-sources[1].idle-timeout=10000
#表名t_order，nums分表数量4，不配置的表不进行分表
mybatis.db.sharding.tables.t_order.nums=4

```
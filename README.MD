史上最简洁的分库分表中间件  
基于Spring+Mybatis的分库分表  
数据源默认使用HikariDataSource
```
@Component
public class ShardingStrategyImpl implements ShardingStrategy {

    private Long shardingKey = 15L;

    @Override
    public Long getShardingKey() {
        // 例子：从ThreadLocal中获取当前卖家id，通过卖家id sharding
        return shardingKey;
    }
}
```
mybatis-db-sharding 分库分表   
mybatis-db-example OrderServiceTest 单元测试
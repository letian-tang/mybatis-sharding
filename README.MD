    史上最简洁的分库分表中间件  
    基于Springboot+Mybatis的分库分表
    基于Mapper的切面  
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

```
#开启
mybatis.db.sharding.enabled=true
#Mapper切面，多个 逗号分隔 如 execution(* com.tdy..*Mapper.*(..)),execution(* com.tdy..*Mapper.*(..))
mybatis.db.sharding.mapper-pointcuts=execution(* com.tdy..*Mapper.*(..))
#数据库1
mybatis.db.sharding.data-sources[0].jdbc-url=jdbc:mysql://localhost:3306/sophon_0?serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&useAffectedRows=true
mybatis.db.sharding.data-sources[0].username=root
mybatis.db.sharding.data-sources[0].password=qazWSX098
mybatis.db.sharding.data-sources[0].maximum-pool-size=10
mybatis.db.sharding.data-sources[0].idle-timeout=10000
#数据库2
mybatis.db.sharding.data-sources[1].jdbc-url=jdbc:mysql://localhost:3306/sophon_1?serverTimezone=Asia/Shanghai&allowMultiQueries=true&useSSL=false&zeroDateTimeBehavior=convertToNull&useAffectedRows=true
mybatis.db.sharding.data-sources[1].username=root
mybatis.db.sharding.data-sources[1].password=qazWSX098
mybatis.db.sharding.data-sources[1].maximum-pool-size=3
mybatis.db.sharding.data-sources[1].idle-timeout=10000
#表名t_order，nums分表数量4，不配置的表不进行分表
mybatis.db.sharding.tables.t_order.nums=4

```
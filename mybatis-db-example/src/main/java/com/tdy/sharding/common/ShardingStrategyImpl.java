package com.tdy.sharding.common;

import com.github.db.sharding.strategy.ShardingStrategy;
import org.springframework.stereotype.Component;


/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/3/7 12:20
 */
@Component
public class ShardingStrategyImpl implements ShardingStrategy {

    private Long shardingKey = 15L;

    @Override
    public Long getShardingKey() {
        return shardingKey;
    }
}

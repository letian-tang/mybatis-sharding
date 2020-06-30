package com.github.db.sharding.strategy;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 13:12
 */
public interface ShardingStrategy {

    Long getShardingKey();
}

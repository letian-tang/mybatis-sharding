package com.github.db.sharding.common;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
public abstract class DbShardingUtils {

    public static final String SPLIT_KEY = "_";
    public static final String DB_NAME_PREFIX = "db" + SPLIT_KEY;

    public static String dbKey(Long dbIndex) {
        return DB_NAME_PREFIX + dbIndex;
    }
}

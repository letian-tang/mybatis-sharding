package com.github.db.sharding.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;

import lombok.Data;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 14:44
 */
@Component
@ConfigurationProperties(prefix = "mybatis.db.sharding")
@Data
public class DbShardingProperties {
    private boolean enabled;
    private Map<String, Table> tables = new HashMap<>();
    private List<HikariConfig> dataSources;
    private List<String> mapperPointcuts;

    @Data
    public static class Table {
        private Integer nums;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }
}

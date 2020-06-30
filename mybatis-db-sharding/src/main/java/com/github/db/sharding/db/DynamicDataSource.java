package com.github.db.sharding.db;

import java.lang.reflect.Field;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    public Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSource();
    }

    @SuppressWarnings("unchecked")
    public Map<Object, Object> getTargetDataSources() {
        Field field;
        Map<Object, Object> targetDataSources = null;
        try {
            field = this.getClass().getSuperclass().getDeclaredField("targetDataSources");
            field.setAccessible(true);
            targetDataSources = (Map<Object, Object>) field.get(this);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
                | IllegalAccessException e) {
            log.error("e", e);
        }
        return targetDataSources;
    }

    @PreDestroy
    public void close() {
        Map<Object, Object> dataSources = getTargetDataSources();
        HikariDataSource bds;
        for (Map.Entry<Object, Object> entry : dataSources.entrySet()) {
            bds = (HikariDataSource) entry.getValue();
            if (null != bds) {
                bds.close();
            }
        }
    }

}

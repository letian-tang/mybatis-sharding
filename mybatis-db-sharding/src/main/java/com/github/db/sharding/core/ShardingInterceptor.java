package com.github.db.sharding.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 14:54
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare",
        args = {Connection.class, Integer.class})})
@Slf4j
public class ShardingInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        String sql = statementHandler.getBoundSql().getSql();
        SqlParserFactory cf = SqlParserFactory.getFactory();
        sql = cf.convert(sql);
        setFieldValue(statementHandler.getBoundSql(), "sql", sql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Object data, String fieldName) {
        Class<?> clazz = data.getClass();
        Field field = ReflectionUtils.findField(clazz, fieldName);
        if (null == field) {
            return null;
        }
        field.setAccessible(true);
        Object o = ReflectionUtils.getField(field, data);
        return (null == o ? null : (T) o);
    }

    @SuppressWarnings("unchecked")
    private void setFieldValue(Object data, String fieldName, Object value) {
        Class<?> clazz = data.getClass();
        Field field = ReflectionUtils.findField(clazz, fieldName);
        if (null == field) {
            return;
        }
        field.setAccessible(true);
        ReflectionUtils.setField(field, data, value);
    }
}

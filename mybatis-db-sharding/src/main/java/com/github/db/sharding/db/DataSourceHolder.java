package com.github.db.sharding.db;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
public abstract class DataSourceHolder {

    // 线程本地环境
    private static final ThreadLocal<String> dataSources = new ThreadLocal<>();

    // 设置数据源
    public static void setDataSource(String databaseName) {
        dataSources.set(databaseName);
    }

    // 获取数据源
    public static String getDataSource() {
        return dataSources.get();
    }

    // 清除数据源
    public static void clear() {
        dataSources.remove();
    }

}

package com.github.db.sharding.core;

import net.sf.jsqlparser.statement.Statement;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 12:32
 */
public interface SqlParser {
    /**
     * 
     * @param statement
     * @return
     */
    String parse(Statement statement);
}

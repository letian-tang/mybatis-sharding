package com.github.db.sharding.core;


import java.util.List;
import java.util.stream.Collectors;

import com.github.db.sharding.common.ApplicationContextUtils;
import org.springframework.util.Assert;


import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 14:44
 */
@Slf4j
public class SqlParserFactory {

    private static final SqlParserFactory factory = new SqlParserFactory();

    public static SqlParserFactory getFactory() {
        return factory;
    }

    private SqlParserFactory() {

    }

    public String convert(String sql) {
        SqlParser sqlParser =
                ApplicationContextUtils.getApplicationContext().getBean(SqlParser.class);
        List<Statement> statementList = null;
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            statementList = statements.getStatements();
        } catch (JSQLParserException e) {
            log.error("e", e);
        }
        Assert.notNull(statementList, "statementList is null");
        return statementList.stream().map(sqlParser::parse).collect(Collectors.joining(";\n"));
    }

}

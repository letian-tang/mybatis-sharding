package com.github.db.sharding.core;

import com.github.db.sharding.common.DbShardingProperties;
import com.github.db.sharding.strategy.ShardingStrategy;
import com.github.db.sharding.table.TablesNamesModifier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 */
@Component
public class DefaultSqlParser implements SqlParser {

    private final ObjectProvider<ShardingStrategy> shardStrategies;
    private final DbShardingProperties shardingProperties;

    public DefaultSqlParser(ObjectProvider<ShardingStrategy> shardStrategies,
            DbShardingProperties shardingProperties) {
        this.shardStrategies = shardStrategies;
        this.shardingProperties = shardingProperties;
    }

    @Override
    public String parse(Statement statement) {
        ShardingStrategy shardStrategy = shardStrategies.getIfAvailable();
        Assert.notNull(shardStrategy, "shardStrategy is null");
        TablesNamesFinder tablesNamesFinder =
                new TablesNamesModifier(shardStrategy, shardingProperties);
        statement.accept(tablesNamesFinder);
        StatementDeParser statementDeParser = new StatementDeParser(new StringBuilder());
        statement.accept(statementDeParser);
        return statementDeParser.getBuffer().toString();
    }
}

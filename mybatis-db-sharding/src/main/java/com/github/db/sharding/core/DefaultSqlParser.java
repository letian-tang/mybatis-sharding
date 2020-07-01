package com.github.db.sharding.core;

import com.github.db.sharding.common.DbShardingProperties;
import com.github.db.sharding.strategy.ShardingStrategy;
import net.sf.jsqlparser.schema.Table;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;
import net.sf.jsqlparser.util.deparser.StatementDeParser;

import java.util.Map;

import static com.github.db.sharding.common.DbShardingUtils.SPLIT_KEY;

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
        statement.accept(new TablesNamesFinder() {
            @Override
            public void visit(Table table) {
                final String tableName = table.getName();
                Map<String, DbShardingProperties.Table> tableMap = shardingProperties.getTables();
                tableMap.forEach((tabName, tablePro) -> {
                    if (tableName.equalsIgnoreCase(tabName)) {
                        String convertTableName = tableName + SPLIT_KEY
                                + shardStrategy.getShardingKey() % tablePro.getNums();
                        table.setName(convertTableName);
                        return;
                    }
                });
            }
        });
        return statement.toString();
    }
}

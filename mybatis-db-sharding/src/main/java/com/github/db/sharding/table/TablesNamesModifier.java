package com.github.db.sharding.table;


import java.util.Map;

import com.github.db.sharding.common.DbShardingUtils;
import com.github.db.sharding.common.DbShardingProperties;
import com.github.db.sharding.strategy.ShardingStrategy;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.util.TablesNamesFinder;

/**
 * 代码描述
 * <p>
 *
 * @author 阿汤
 * @since 2020/1/11 14:58
 */
public class TablesNamesModifier extends TablesNamesFinder {


    private final ShardingStrategy shardStrategy;

    private final DbShardingProperties shardingProperties;

    public TablesNamesModifier(ShardingStrategy shardStrategy,
            DbShardingProperties shardingProperties) {
        this.shardStrategy = shardStrategy;
        this.shardingProperties = shardingProperties;
    }

    @Override
    public void visit(Table table) {
        final String tableName = table.getName();
        Map<String, DbShardingProperties.Table> tableMap = shardingProperties.getTables();
        tableMap.forEach((tabName, tablePro) -> {
            if (tableName.equalsIgnoreCase(tabName)) {
                String convertTableName =
                        tableName + DbShardingUtils.SPLIT_KEY + shardStrategy.getShardingKey() % tablePro.getNums();
                table.setName(convertTableName);
                return;
            }
        });
    }
}

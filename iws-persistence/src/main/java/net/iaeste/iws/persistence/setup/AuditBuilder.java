/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-persistence) - net.iaeste.iws.persistence.setup.AuditBuilder
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */

package net.iaeste.iws.persistence.setup;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class provides interface to fetch current database schemaName
 * and create auditing tables according to configuration.
 *
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@SuppressWarnings("ReturnOfThis")
public class AuditBuilder {
    /**
     * With {@code Database} class {@code AuditBuilder} stores the structure of the database.
     */
    private class Database {
        private List<Schema> schemas = new ArrayList<>();

        /**
         * Builds SQL for created database.
         *
         * @return SQL code for creation of auditing tables.
         */
        public String getTablesSchema() {
            final StringBuffer tableSchema = new StringBuffer();
            try {
                for (final Schema schema : schemas) {
                    final String auditSchema = schema.getSchemaName() + "_AUDIT";
                    tableSchema.append(String.format("CREATE SCHEMA %s;\n", auditSchema));
                    for (final Table table : schema.getTables()) {
                        tableSchema.append(String.format("CREATE TABLE %s.%s (\n", auditSchema, table.getTableName()));
                        final StringBuffer tableCode = new StringBuffer();
                        for (final Column column : table.getColumns()) {
                            tableCode.append(String.format("%s,\n", column.generateFieldString()));
                        }
                        tableCode.delete(tableCode.length() - 2, tableCode.length());
                        tableCode.append("\n);\n");
                        tableSchema.append(tableCode);
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return tableSchema.toString();
        }

        public String getTriggersSchema() {
            final StringBuffer triggersSchema = new StringBuffer();
            for (final Schema schema : schemas) {
                final String auditSchema = schema.getSchemaName() + "_AUDIT";
                for (final Table table : schema.getTables()) {
                    StringBuffer commaSeparatedColumns = new StringBuffer();
                    StringBuffer commaSeparatedOldColumns = new StringBuffer();

                    for (Iterator<Column> iterator = table.getColumns().iterator(); iterator.hasNext(); ) {
                        final Column column = iterator.next();
                        commaSeparatedColumns.append("    ").append(column.getColumnName());
                        commaSeparatedOldColumns.append("    oldrow.").append(column.getColumnName());
                        if (iterator.hasNext()) {
                            commaSeparatedColumns.append(", \n");
                            commaSeparatedOldColumns.append(", \n");
                        }
                    }
                    final String createTriggerStatement = "SET SCHEMA %s;\n" +
                            "CREATE TRIGGER TRIGGER_AUDIT_%s_%s\n" +
                            "AFTER %s ON %s.%s\n" +
                            "REFERENCING OLD AS oldrow %s\n" +
                            "FOR EACH ROW\n" +
                            "BEGIN ATOMIC\n" +
                            "  INSERT INTO %s.%s\n" +
                            "  (\n" +
                            "    %s\n" +
                            "  )\n" +
                            "  VALUES\n" +
                            "  (\n" +
                            "    %s\n" +
                            "  );\n" +
                            "  %s\n" + // OPTIONAL
                            "END;\n";

                    final String additionalUpdateCode = "SET newrow.changed_on = CURRENT_TIMESTAMP;\n";
                    triggersSchema.append(String.format(
                            createTriggerStatement, schema.getSchemaName(), "UPDATE", table.getTableName(), "UPDATE", schema.getSchemaName(),
                            table.getTableName(), "NEW AS newrow", auditSchema, table.getTableName(), commaSeparatedColumns, commaSeparatedOldColumns,
                            additionalUpdateCode));
                    triggersSchema.append(String.format(
                            createTriggerStatement, schema.getSchemaName(), "DELETE", table.getTableName(), "DELETE", schema.getSchemaName(),
                            table.getTableName(), "", auditSchema, table.getTableName(), commaSeparatedColumns, commaSeparatedOldColumns, ""));
                }

            }
            return triggersSchema.toString();
        }

        public List<Schema> getSchemas() {
            return schemas;
        }

        public void addSchema(final Schema schema) {
            schemas.add(schema);
        }

        @Override
        public String toString() {
            return String.format("Database{%s}", schemas);
        }
    }

    private class Schema {
        private List<Table> tables = new ArrayList<>();
        private String catalogName;
        private String schemaName;

        public Schema(final ResultSet schema) throws SQLException {
            this.schemaName = schema.getString(1);
            this.catalogName = schema.getString(2);
        }

        public List<Table> getTables() {
            return tables;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public String getSchemaName() {
            return schemaName;
        }

        public void addTable(final Table table) {
            tables.add(table);
        }

        @Override
        public String toString() {
            return "Schema{" +
                    "catalog='" + catalogName + '\'' +
                    ", schema='" + schemaName + '\'' +
                    ", tables=" + tables +
                    '}';
        }
    }

    private class Table {
        private List<Column> columns = new ArrayList<>();
        private String schemaName;
        private String catalogName;
        final String tableName;

        Table(final ResultSet table) throws SQLException {
            this.schemaName = table.getString(1);
            this.catalogName = table.getString(2);
            this.tableName = table.getString(3);

        }

        public String getTableName() {
            return tableName;
        }

        public void addColumn(final Column column) {
            columns.add(column);
        }

        public List<Column> getColumns() {
            return columns;
        }

        @Override
        public String toString() {
            return "Table{" +
                    "schema='" + schemaName + '\'' +
                    ", catalog='" + catalogName + '\'' +
                    ", table='" + tableName + '\'' +
                    ", columns=" + columns +
                    '}';
        }
    }

    private class Column {
        private String columnName;
        private int dataType;
        private boolean nullable;
        private int decimalDigits;
        private int columnSize;
        private String typeName;
        private String schemaName;
        private String catalogName;
        private String tableName;

        Column(final ResultSet column) throws SQLException {
            schemaName = column.getString(1);    // "TABLE_SCHEM"
            catalogName = column.getString(2); //"TABLE_CATALOG"
            tableName = column.getString(2); //"TABLE_CATALOG"
            columnName = column.getString(4); // String => column name
            dataType = column.getInt(5); // int => SQL type from java.sql.Types
            typeName = column.getString(6); // String => Data source dependent type name,
            columnSize = column.getInt(7); // int => column size.
            decimalDigits = column.getInt(9); // int => the number of fractional digits. Null is returned for data types where
            nullable = column.getInt(11) == 1; // is NULL allowed.
        }

        @Override
        public String toString() {
            return "Column{" + columnName + ' ' + dataType + '(' + columnSize + ")}";
        }

        /**
         * Helper function to generate SQL line for creation field tableName.
         *
         * @return
         * @throws SQLException
         */
        private String generateFieldString() throws SQLException {
            // all %-x is for creating the line of constant length
            final StringBuilder result = new StringBuilder(2 + 24 + 10 + 8 + 8);
            result.append("  ");
            result.append(String.format("%-24s", columnName));
            result.append(String.format("%-10s", typeName));
            final String dataTypeString;
            switch (dataType) {
                case Types.CHAR:
                case Types.VARCHAR:
                case Types.VARBINARY:
                    dataTypeString = String.format("(%d)", columnSize);
                    break;
                case Types.DECIMAL:
                case Types.NUMERIC:
                    dataTypeString = String.format("(%d, %d)", columnSize, decimalDigits);
                    break;
                default:
                    dataTypeString = "";
            }
            result.append(String.format("%-8s", dataTypeString));
            if (nullable) {
                result.append("        ");
            } else {
                result.append("NOT NULL");
            }
            return result.toString();
        }

        public String getColumnName() {
            return columnName;
        }

        public String getTableName() {
            return tableName;
        }

        public String getCatalogName() {
            return catalogName;
        }

        public String getSchemaName() {
            return schemaName;
        }

    }

    /**
     * Include all columns/tables for auditing.
     */
    public enum IncludeMode {
        ALL, NONE
    }

    private IncludeMode includeMode;
    private String catalog;
    private String schema;
    /**
     * List of tables which should be included for auditing (only for {@code includeMode = NONE}.
     */
    private Set<String> includeTable;
    /**
     * List of tables which should be excluded from auditing (only for {@code includeMode = ALL}.
     */
    private Set<String> excludeTable;
    private Map<String, Set<String>> includeColumn;
    private Map<String, Set<String>> excludeColumn;
    private Map<String, IncludeMode> columnIncludeMode;

    public Map<String, Set<String>> getIncludeColumn() {
        return includeColumn;
    }

    public Map<String, Set<String>> getExcludeColumn() {
        return excludeColumn;
    }

    public void setIncludeColumn(final String tableName, final Set<String> includeColumn) {
        this.includeColumn.put(tableName, includeColumn);
        this.columnIncludeMode.put(tableName, IncludeMode.NONE);
    }

    public void setExcludeColumn(final String tableName, final Set<String> excludeColumn) {
        this.excludeColumn.put(tableName, excludeColumn);
        this.columnIncludeMode.put(tableName, IncludeMode.NONE);
    }


    private DataSource dataSource;

    public AuditBuilder(final DataSource dataSource) {
        this.includeMode = IncludeMode.NONE;
        this.dataSource = dataSource;
        this.catalog = "PUBLIC";
        this.schema = "PUBLIC";
        this.includeTable = Collections.emptySet();
        this.excludeTable = Collections.emptySet();
        this.includeColumn = Collections.emptyMap();
        this.excludeColumn = Collections.emptyMap();
        this.columnIncludeMode = Collections.emptyMap();
    }

    public AuditBuilder setCatalog(final String catalog) {
        this.catalog = catalog;
        return this;
    }

    public AuditBuilder setSchema(final String schema) {
        this.schema = schema;
        return this;
    }

    public AuditBuilder setIncludeMode(final IncludeMode includeMode) {
        this.includeMode = includeMode;
        return this;
    }

    public AuditBuilder setExcludeTable(final Set<String> excludeTable) {
        this.excludeTable = new HashSet<>();
        for (final String table : excludeTable) {
            this.excludeTable.add(table.toUpperCase());
        }
        return this;

    }

    public AuditBuilder setIncludeTable(final Set<String> includeTable) {
        this.includeTable = new HashSet<>();
        for (final String table : includeTable) {
            this.includeTable.add(table.toUpperCase());
        }
        return this;
    }


    /**
     * Execute SQL statements to create tables and triggers for auditing.
     */
    public void execute() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            final Statement statement = connection.createStatement();
            final String tablesCode = getDdlScript(true);
            final String triggersCode = getDdlScript(false);
            // TODO: debug only, remove after testing
            System.out.println(tablesCode);
            System.out.println(triggersCode);
            statement.execute(tablesCode);
            statement.execute(triggersCode);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Connects with database and creates auditing tables initialization script.
     *
     * @return valid CREATE TABLE statements
     */
    public String getTablesSQL() {
        return getDdlScript(true);
    }

    /**
     * Connects with database and creates trigger initialization script.
     *
     * @return valid CREATE TRIGGER statements
     */
    public String getTriggersSQL() {
        return getDdlScript(false);
    }

    /**
     * helper method created due to problems with executing creation of tables and triggers at once
     *
     * @param tablesSchema if true, tableName schemaName should be returned, otherwise triggers schemaName should be returned
     * @return schemaName of either tables or triggers
     */
    private String getDdlScript(final boolean tablesSchema) {
        final HashMap<String, ResultSet> tablesMap = new HashMap<String, ResultSet>();

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            final DatabaseMetaData metaData = connection.getMetaData();
            final Database database = fetchDatabaseSchema(metaData);
            return tablesSchema ? database.getTablesSchema() : database.getTriggersSchema();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private Database fetchDatabaseSchema(final DatabaseMetaData metaData) throws SQLException {
        final Database database = new Database();
        final ResultSet schemasSet = metaData.getSchemas(catalog, schema);
        while (schemasSet.next()) {
            final Schema schema = new Schema(schemasSet);
            final ResultSet tablesSet = metaData.getTables(schema.getCatalogName(), schema.getSchemaName(), null, null);
            // for each tableName we fetch db schemaName
            while (tablesSet.next()) {
                final Table table = new Table(tablesSet);
                if (isTableIncluded(table.getTableName())) {
                    final ResultSet columnsSet = metaData.getColumns(schema.getCatalogName(), schema.getSchemaName(), table.getTableName(), null);
                    while (columnsSet.next()) {
                        final Column column = new Column(columnsSet);
                        if (isColumnIncluded(table.getTableName(), column.getColumnName())) {
                            table.addColumn(column);
                        }
                    }
                    schema.addTable(table);
                }
            }
            database.addSchema(schema);
        }
        return database;
    }

    private boolean isColumnIncluded(final String tableName, final String columnName) {
        if (!columnIncludeMode.containsKey(tableName)) {
            // if inc/exl not specified then all columns should be audited
            return true;
        }
        switch (columnIncludeMode.get(tableName)) {
            case ALL:
                if (!excludeColumn.containsKey(tableName)) {
                    // if exl set for given key does not exist then all columns should be audited
                    return true;
                } else {
                    // audit column if it doesn't exist on the list
                    return !excludeColumn.get(tableName).contains(columnName);
                }
            case NONE:
                if (!includeColumn.containsKey(tableName)) {
                    // if inc set for given key does not exist then no columns should be audited
                    return false;
                } else {
                    // audit column if it exists on the list
                    return includeColumn.get(tableName).contains(columnName);
                }
        }
        return false;
    }

    private boolean isTableIncluded(final String table) {
        if ((this.includeMode == IncludeMode.ALL) && !excludeTable.contains(table)) {
            return true;
        }
        if ((this.includeMode == IncludeMode.NONE) && includeTable.contains(table)) {
            return true;
        }
        return false;
    }

}

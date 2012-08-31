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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michal Knapik / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
@SuppressWarnings("ReturnOfThis")
public class AuditBuilder {
    public enum TableIncludeMode {
        ALL, NONE
    }

    private TableIncludeMode tableIncludeMode;
    private String catalog;
    private String schema;
    private Set<String> includeList = Collections.emptySet();
    private Set<String> excludeList = Collections.emptySet();
    private DataSource dataSource;


    public AuditBuilder(final DataSource dataSource) {
        this.tableIncludeMode = TableIncludeMode.NONE;
        this.dataSource = dataSource;
        this.catalog = "PUBLIC";
        this.schema = "PUBLIC";

    }

    public AuditBuilder(final AuditBuilder auditBuilder) {
        this.dataSource = auditBuilder.dataSource;
    }

    public AuditBuilder setCatalog(final String catalog) {
        this.catalog = catalog;
        return this;
    }

    public AuditBuilder setSchema(final String schema) {
        this.schema = schema;
        return this;
    }

    public AuditBuilder setTableIncludeMode(final TableIncludeMode tableIncludeMode) {
        this.tableIncludeMode = tableIncludeMode;
        return this;
    }

    public AuditBuilder setExcludeList(final Set<String> excludeList) {
        this.excludeList = excludeList;
        return this;

    }

    public AuditBuilder setIncludeList(final Set<String> includeList) {
        this.includeList = new HashSet<>();
        for (final String table : includeList) {
            this.includeList.add(table.toUpperCase());
        }
        return this;
    }

    public String getDdlScript() {
        final HashMap<String, ResultSet> tablesMap = new HashMap<String, ResultSet>();
        final StringBuilder result = new StringBuilder();
        final StringBuilder tablesCode = new StringBuilder();
        final StringBuilder triggersCode = new StringBuilder();

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            final DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData);
            final ResultSet schemas = metaData.getSchemas(catalog, schema);
            while (schemas.next()) {
                final String tableSchema = schemas.getString(1);    // "TABLE_SCHEM"
                final String tableCatalog = schemas.getString(2); //"TABLE_CATALOG"

                final String auditSchema = tableSchema + "_audit";
                result.append(String.format("CREATE SCHEMA %s;\n", auditSchema));

                final ResultSet tables = metaData.getTables(tableCatalog, tableSchema, null, null);

                // for each table create triggers and audit table
                while (tables.next()) {
                    final String table = tables.getString(3);
                    if (isTableIncluded(table)) {
                        final StringBuffer tableCode = getTableDdl(metaData, tableSchema, tableCatalog, table, auditSchema);
                        tablesCode.append(tableCode);

                    }
                }
//                for (Annotation annotation : OfferEntity.class.getAnnotations()) {
//                    if (annotation instanceof Table) {
//                        System.out.println(((Table) annotation).name());
//                    }
//                    System.out.println();
//                }
            }
            return result.append(tablesCode).append(triggersCode).toString();
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
        return null;
    }

    final private StringBuffer getTableDdl(final DatabaseMetaData metaData,
                                           final String tableSchema,
                                           final String tableCatalog,
                                           final String table,
                                           final String auditSchema) throws SQLException {
        final StringBuffer tableCode = new StringBuffer();

        tableCode.append(String.format("CREATE TABLE %s.%s (\n", auditSchema, table));
        final ResultSet columns = metaData.getColumns(tableCatalog, tableSchema, table, null);
        while (columns.next()) {
            tableCode.append(String.format("%s,\n", generateFieldString(columns)));
        }
        tableCode.delete(tableCode.length() - 2, tableCode.length());
        tableCode.append("\n);\n");
        return tableCode;
    }

    private boolean isTableIncluded(final String table) {
        if ((this.tableIncludeMode == TableIncludeMode.ALL) && !excludeList.contains(table)) {
            return true;
        }
        if ((this.tableIncludeMode == TableIncludeMode.NONE) && includeList.contains(table)) {
            return true;
        }
        return false;
    }

    private String generateFieldString(final ResultSet column) throws SQLException {
        final String columnName = column.getString(4).toLowerCase(); // String => column name
        final int dataType = column.getInt(5); // int => SQL type from java.sql.Types
        final String typeName = column.getString(6); // String => Data source dependent type name,
        final int columnSize = column.getInt(7); // int => column size.
        final int decimalDigits = column.getInt(9); // int => the number of fractional digits. Null is returned for data types where
//        final int numPrecRadix = column.getInt(10); // int => Radix (typically either 10 or 2)
        final int nullable = column.getInt(11); // is NULL allowed.
//        final String remarks = column.getString(11); // String => comment describing column (may be <code>null</code>)
//        final String columnDefinition = column.getString(12); // String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be <code>null</code>)

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
        if (nullable == 0) {
            result.append("NOT NULL");
        } else {
            result.append("        ");
        }
        return result.toString();
        /**
         *      <UL>
         *      <LI> columnNoNulls - might not allow <code>NULL</code> values
         *      <LI> columnNullable - definitely allows <code>NULL</code> values
         *      <LI> columnNullableUnknown - nullability unknown
         *      </UL>
         */
        /**
         *  <LI><B>SQL_DATA_TYPE</B> int => unused
         *  <LI><B>SQL_DATETIME_SUB</B> int => unused
         *  <LI><B>CHAR_OCTET_LENGTH</B> int => for char types the
         *       maximum number of bytes in the column
         *  <LI><B>ORDINAL_POSITION</B> int => index of column in table
         *      (starting at 1)
         *  <LI><B>IS_NULLABLE</B> String  => ISO rules are used to determine the nullability for a column.
         *       <UL>
         *       <LI> YES           --- if the column can include NULLs
         *       <LI> NO            --- if the column cannot include NULLs
         *       <LI> empty string  --- if the nullability for the
         * column is unknown
         *       </UL>
         *  <LI><B>SCOPE_CATALOG</B> String => catalog of table that is the scope
         *      of a reference attribute (<code>null</code> if DATA_TYPE isn't REF)
         *  <LI><B>SCOPE_SCHEMA</B> String => schema of table that is the scope
         *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
         *  <LI><B>SCOPE_TABLE</B> String => table name that this the scope
         *      of a reference attribute (<code>null</code> if the DATA_TYPE isn't REF)
         *  <LI><B>SOURCE_DATA_TYPE</B> short => source type of a distinct type or user-generated
         *      Ref type, SQL type from java.sql.Types (<code>null</code> if DATA_TYPE
         *      isn't DISTINCT or user-generated REF)
         *   <LI><B>IS_AUTOINCREMENT</B> String  => Indicates whether this column is auto incremented
         *       <UL>
         *       <LI> YES           --- if the column is auto incremented
         *       <LI> NO            --- if the column is not auto incremented
         *       <LI> empty string  --- if it cannot be determined whether the column is auto incremented
         *       </UL>
         *   <LI><B>IS_GENERATEDCOLUMN</B> String  => Indicates whether this is a generated column
         *       <UL>
         *       <LI> YES           --- if this a generated column
         *       <LI> NO            --- if this not a generated column
         *       <LI> empty string  --- if it cannot be determined whether this is a generated column
         *       </UL>
         *  </OL>
         *
         * <p>The COLUMN_SIZE column specifies the column size for the given column.
         * For numeric data, this is the maximum precision.  For character data, this is the length in characters.
         * For datetime datatypes, this is the length in characters of the String representation (assuming the
         * maximum allowed precision of the fractional seconds component). For binary data, this is the length in bytes.  For the ROWID datatype,
         * this is the length in bytes. Null is returned for data types where the
         * column size is not applicable.
         *
         * @param catalog a catalog name; must match the catalog name as it
         *        is stored in the database; "" retrieves those without a catalog;
         *        <code>null</code> means that the catalog name should not be used to narrow
         *        the search
         * @param schemaPattern a schema name pattern; must match the schema name
         *        as it is stored in the database; "" retrieves those without a schema;
         *        <code>null</code> means that the schema name should not be used to narrow
         *        the search
         * @param tableNamePattern a table name pattern; must match the
         *        table name as it is stored in the database
         * @param columnNamePattern a column name pattern; must match the column
         *        name as it is stored in the database
         * @return <code>ResultSet</code> - each row is a column description
         * @exception SQLException if a database access error occurs
         * @see #getSearchStringEscape
         */
    }

    public void execute() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            final Statement statement = connection.createStatement();
            statement.execute(getDdlScript());
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

}

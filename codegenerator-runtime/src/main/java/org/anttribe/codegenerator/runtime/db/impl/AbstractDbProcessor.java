/*
 * 文  件   名: AbstractDbProcessor.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月27日
 */
package org.anttribe.codegenerator.runtime.db.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.anttribe.codegenerator.core.config.DbConfig;
import org.anttribe.codegenerator.core.exception.DbException;
import org.anttribe.codegenerator.runtime.db.DbProcessor;
import org.anttribe.codegenerator.runtime.db.metadata.DbColumn;
import org.anttribe.codegenerator.runtime.db.metadata.DbTable;
import org.anttribe.codegenerator.runtime.db.metadata.DbTableType;
import org.anttribe.codegenerator.runtime.db.metadata.JavaType;
import org.anttribe.codegenerator.runtime.db.metadata.JdbcType;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhaoyong
 * @version 2015年12月27日
 */
public class AbstractDbProcessor implements DbProcessor
{
    private static Logger logger = LoggerFactory.getLogger(AbstractDbProcessor.class);
    
    /**
     * 数据库连接对象
     */
    private ThreadLocal<Connection> dbConnection = new ThreadLocal<Connection>();
    
    @Override
    public DbTable processDbTableMetaData(DbConfig dbConfig, String tableName)
        throws DbException
    {
        if (null == dbConfig || StringUtils.isEmpty(tableName))
        {
            logger.warn("processing dbTable metadata, params dbConfig[{}] or tableName[{}] is null",
                dbConfig,
                tableName);
            throw new DbException("processing dbTable metadata, params dbConfig[" + dbConfig + "] or tableName["
                + tableName + "] is null");
        }
        
        if (null != dbConfig && !StringUtils.isEmpty(tableName))
        {
            DbTable dbTable = new DbTable();
            // 设置表的元数据
            dbTable.setTableName(tableName);
            try
            {
                Connection conn = this.getDbConnection(dbConfig);
                if (null != conn)
                {
                    // 数据库元数据
                    DatabaseMetaData dbmd = conn.getMetaData();
                    // 表数据
                    ResultSet tables = dbmd.getTables(null, null, tableName, null);
                    while (tables.next())
                    {
                        dbTable.setCatalog(tables.getString("TABLE_CAT"));
                        dbTable.setSchema(tables.getString("TABLE_SCHEM"));
                        dbTable.setTableName(tables.getString("TABLE_NAME"));
                        dbTable.setTableComment(tables.getString("REMARKS"));
                        dbTable.setType(DbTableType.valueOf(tables.getString("TABLE_TYPE")));
                    }
                    // 处理表结构, 列信息
                    List<DbColumn> dbColumns = this.processDbColumnsMetaData(conn, dbTable);
                    if (null != dbColumns)
                    {
                        dbTable.setColumns(dbColumns);
                        
                        // 获取表主键
                        
                    }
                }
            }
            catch (SQLException e)
            {
                logger.error("Processing database data get error, cause: {}", e);
                throw new DbException(e);
            }
            
            return dbTable;
        }
        return null;
    }
    
    /**
     * 处理数据库表结构信息
     * 
     * @param conn
     * @param tableName
     * @return List<DbColumn>
     */
    private List<DbColumn> processDbColumnsMetaData(Connection conn, DbTable dbTable)
        throws DbException
    {
        // 列数据
        List<DbColumn> dbColumns = null;
        if (null != conn)
        {
            try
            {
                dbColumns = new ArrayList<DbColumn>();
                // 数据库元数据
                DatabaseMetaData dbmd = conn.getMetaData();
                ResultSet columns =
                    dbmd.getColumns(dbTable.getCatalog(), dbTable.getSchema(), dbTable.getTableName(), null);
                while (columns.next())
                {
                    DbColumn dbColumn = new DbColumn();
                    dbColumn.setColumnName(columns.getString("COLUMN_NAME"));
                    dbColumn.setColumnComment(columns.getString("REMARKS"));
                    dbColumn.setJdbcType(JdbcType.valueOf(columns.getInt("DATA_TYPE")));
                    dbColumn.setPrecision(columns.getInt("COLUMN_SIZE"));
                    dbColumn.setScale(columns.getInt("DECIMAL_DIGITS"));
                    dbColumn.setNullable(columns.getInt("NULLABLE") == ResultSetMetaData.columnNullable);
                    dbColumn.setAutoIncrement("YES".equals(columns.getString("IS_AUTOINCREMENT")));
                    
                    // 类字段名
                    String columnName = dbColumn.getColumnName();
                    if (!StringUtils.isEmpty(columnName))
                    {
                        // 表名按照 "_" 分隔
                        String[] subColumnNames = columnName.split("_");
                        if (null != subColumnNames)
                        {
                            String fieldName = "";
                            for (String subColumnName : subColumnNames)
                            {
                                if (StringUtils.isEmpty(subColumnName))
                                {
                                    continue;
                                }
                                fieldName += subColumnName.substring(0, 1).toUpperCase() + subColumnName.substring(1);
                            }
                            if(!StringUtils.isEmpty(fieldName)){
                                fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                                dbColumn.setFieldName(fieldName);
                            }
                        }
                    }
                    // 根据jdbcType获取其java类型
                    dbColumn.setJavaType(JavaType.valueOfJdbcType(dbColumn.getJdbcType()));
                    dbColumns.add(dbColumn);
                }
                return dbColumns;
            }
            catch (SQLException e)
            {
                logger.error("Processing database data get error, cause: {}", e);
                throw new DbException(e);
            }
        }
        
        return dbColumns;
    }
    
    /**
     * 获取数据库连接对象
     * 
     * @param dbConfig
     * @return Connection
     * @throws SQLException
     */
    private Connection getDbConnection(DbConfig dbConfig)
        throws SQLException
    {
        Connection conn = dbConnection.get();
        if (null == conn)
        {
            DbUtils.loadDriver(dbConfig.getDriverClass());
            conn = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
            dbConnection.set(conn);
            
            conn = dbConnection.get();
        }
        return conn;
    }
}
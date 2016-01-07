/*
 * 文  件   名: DbTable.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月21日
 */
package org.anttribe.codegenerator.runtime.db.metadata;

import java.util.List;

/**
 * 数据库表抽象映射
 * 
 * @author zhaoyong
 * @version 2015年12月21日
 */
public class DbTable
{
    /**
     * 表名称
     */
    private String tableName;
    
    /**
     * 表注释
     */
    private String tableComment;
    
    /**
     * 数据库
     */
    private String schema;
    
    /**
     * 表目录
     */
    private String catalog;
    
    /**
     * 类型, 默认是表
     */
    DbTableType type = DbTableType.TABLE;
    
    /**
     * 数据库列集合
     */
    private List<DbColumn> columns;
    
    /**
     * 主键列
     */
    private List<DbColumn> primaryKeys;
    
    public String getTableName()
    {
        return tableName;
    }
    
    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }
    
    public String getTableComment()
    {
        return tableComment;
    }
    
    public void setTableComment(String tableComment)
    {
        this.tableComment = tableComment;
    }
    
    public String getSchema()
    {
        return schema;
    }
    
    public void setSchema(String schema)
    {
        this.schema = schema;
    }
    
    public String getCatalog()
    {
        return catalog;
    }
    
    public void setCatalog(String catalog)
    {
        this.catalog = catalog;
    }
    
    public DbTableType getType()
    {
        return type;
    }
    
    public void setType(DbTableType type)
    {
        this.type = type;
    }
    
    public List<DbColumn> getColumns()
    {
        return columns;
    }
    
    public void setColumns(List<DbColumn> columns)
    {
        this.columns = columns;
    }
    
    public List<DbColumn> getPrimaryKeys()
    {
        return primaryKeys;
    }
    
    public void setPrimaryKeys(List<DbColumn> primaryKeys)
    {
        this.primaryKeys = primaryKeys;
    }
    
}
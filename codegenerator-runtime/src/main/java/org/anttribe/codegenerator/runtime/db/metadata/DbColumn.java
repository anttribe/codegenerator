/*
 * 文  件   名: DbColumn.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月21日
 */
package org.anttribe.codegenerator.runtime.db.metadata;

/**
 * 数据库列映射
 * 
 * @author zhaoyong
 * @version 2015年12月21日
 */
public class DbColumn
{
    /**
     * 列名称
     */
    private String columnName;
    
    /**
     * 类字段名称
     */
    private String fieldName;
    
    /**
     * 列注释
     */
    private String columnComment;
    
    /**
     * 索引序号, 表中的列的索引（从 1 开始）
     */
    private int ordinal;
    
    /**
     * 对应jdbc类型
     */
    private JdbcType jdbcType;
    
    /**
     * 对应java类型
     */
    private JavaType javaType;
    
    /**
     * 获取指定列的指定列宽: 数值型数据，是指最大精度; 字符型数据，是指字符串长度; 日期时间的数据类型，是指 String 表示形式的字符串长度（假定为最大允许的小数秒组件）。 对于二进制型数据，是指字节长度; ROWID
     * 数据类型，是指字节长度; 其列大小不可用的数据类型，则返回 0
     */
    private int precision;
    
    /**
     * 列的小数点右边的位数。对于其标度不可用的数据类型
     */
    private int scale;
    
    /**
     * 是否为主键
     */
    private boolean primaryKey;
    
    /**
     * 是否外键列（参照其他表的键）
     */
    private boolean importedKey;
    
    /**
     * 是否外键列（被其他表参照的键）
     */
    private boolean exportedKey;
    
    /**
     * 值能否为空, 默认允许
     */
    private boolean nullable = true;
    
    /**
     * 是否为自增列
     */
    private boolean autoIncrement;
    
    public String getColumnName()
    {
        return columnName;
    }
    
    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }
    
    public String getFieldName()
    {
        return fieldName;
    }
    
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }
    
    public int getOrdinal()
    {
        return ordinal;
    }
    
    public void setOrdinal(int ordinal)
    {
        this.ordinal = ordinal;
    }
    
    public String getColumnComment()
    {
        return columnComment;
    }
    
    public void setColumnComment(String columnComment)
    {
        this.columnComment = columnComment;
    }
    
    public JdbcType getJdbcType()
    {
        return jdbcType;
    }
    
    public void setJdbcType(JdbcType jdbcType)
    {
        this.jdbcType = jdbcType;
    }
    
    public JavaType getJavaType()
    {
        return javaType;
    }
    
    public void setJavaType(JavaType javaType)
    {
        this.javaType = javaType;
    }
    
    public int getPrecision()
    {
        return precision;
    }
    
    public void setPrecision(int precision)
    {
        this.precision = precision;
    }
    
    public int getScale()
    {
        return scale;
    }
    
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    
    public boolean isPrimaryKey()
    {
        return primaryKey;
    }
    
    public void setPrimaryKey(boolean primaryKey)
    {
        this.primaryKey = primaryKey;
    }
    
    public boolean isImportedKey()
    {
        return importedKey;
    }
    
    public void setImportedKey(boolean importedKey)
    {
        this.importedKey = importedKey;
    }
    
    public boolean isExportedKey()
    {
        return exportedKey;
    }
    
    public void setExportedKey(boolean exportedKey)
    {
        this.exportedKey = exportedKey;
    }
    
    public boolean isNullable()
    {
        return nullable;
    }
    
    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }
    
    public boolean isAutoIncrement()
    {
        return autoIncrement;
    }
    
    public void setAutoIncrement(boolean autoIncrement)
    {
        this.autoIncrement = autoIncrement;
    }
}
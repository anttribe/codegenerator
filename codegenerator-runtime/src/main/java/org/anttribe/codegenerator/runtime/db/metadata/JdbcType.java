/*
 * 文  件   名: JdbcType.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月21日
 */
package org.anttribe.codegenerator.runtime.db.metadata;

/**
 * JDBC类型
 * 
 * @author zhaoyong
 * @version 2015年12月21日
 */
public enum JdbcType
{
    ARRAY(java.sql.Types.ARRAY), BIGINT(java.sql.Types.BIGINT), BINARY(java.sql.Types.BINARY), BIT(
        java.sql.Types.BIT), BLOB(java.sql.Types.BLOB), BOOLEAN(java.sql.Types.BOOLEAN), CHAR(
            java.sql.Types.CHAR), CLOB(java.sql.Types.CLOB), DATALINK(java.sql.Types.DATALINK), DATE(
                java.sql.Types.DATE), DECIMAL(java.sql.Types.DECIMAL), DISTINCT(java.sql.Types.DISTINCT), DOUBLE(
                    java.sql.Types.DOUBLE), FLOAT(java.sql.Types.FLOAT), INTEGER(java.sql.Types.INTEGER), JAVA_OBJECT(
                        java.sql.Types.JAVA_OBJECT), LONGNVARCHAR(java.sql.Types.LONGNVARCHAR), LONGVARBINARY(
                            java.sql.Types.LONGVARBINARY), LONGVARCHAR(java.sql.Types.LONGVARCHAR), NCHAR(
                                java.sql.Types.NCHAR), NCLOB(java.sql.Types.NCLOB), NULL(java.sql.Types.NULL), NUMERIC(
                                    java.sql.Types.NUMERIC), NVARCHAR(java.sql.Types.NVARCHAR), OTHER(
                                        java.sql.Types.OTHER), REAL(java.sql.Types.REAL), REF(
                                            java.sql.Types.REF), ROWID(java.sql.Types.ROWID), SMALLINT(
                                                java.sql.Types.SMALLINT), SQLXML(java.sql.Types.SQLXML), STRUCT(
                                                    java.sql.Types.STRUCT), TIME(java.sql.Types.TIME), TIMESTAMP(
                                                        java.sql.Types.TIMESTAMP), TINYINT(
                                                            java.sql.Types.TINYINT), VARBINARY(
                                                                java.sql.Types.VARBINARY), VARCHAR(
                                                                    java.sql.Types.VARCHAR);
                                                                    
    private int sqlType;
    
    /**
     * @param sqlType
     */
    private JdbcType(int sqlType)
    {
        this.sqlType = sqlType;
    }
    
    public int getSqlType()
    {
        return sqlType;
    }
    
    public static JdbcType valueOf(int sqlType)
    {
        JdbcType[] types = JdbcType.values();
        for (JdbcType jdbcType : types)
        {
            if (jdbcType.sqlType == sqlType)
            {
                return jdbcType;
            }
        }
        return null;
    }
}
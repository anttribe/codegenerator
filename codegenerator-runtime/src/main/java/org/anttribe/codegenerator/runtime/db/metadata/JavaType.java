/*
 * 文  件   名: JavaType.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月21日
 */
package org.anttribe.codegenerator.runtime.db.metadata;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaoyong
 * @version 2015年12月21日
 */
public enum JavaType
{
    String(new JdbcType[] {JdbcType.CHAR, JdbcType.VARCHAR, JdbcType.LONGVARCHAR}, java.lang.String.class), Boolean(
        new JdbcType[] {JdbcType.BIT},
        java.lang.Boolean.class), Integer(new JdbcType[] {JdbcType.TINYINT, JdbcType.SMALLINT, JdbcType.INTEGER},
            java.lang.Integer.class), Long(new JdbcType[] {JdbcType.BIGINT}, java.lang.Long.class), Float(
                new JdbcType[] {JdbcType.REAL, JdbcType.FLOAT}, java.lang.Float.class), Double(
                    new JdbcType[] {JdbcType.DOUBLE, JdbcType.NUMERIC}, java.lang.Double.class), BigDecimal(
                        new JdbcType[] {JdbcType.DECIMAL}, java.math.BigDecimal.class), Date(
                            new JdbcType[] {JdbcType.DATE}, java.util.Date.class), Time(new JdbcType[] {JdbcType.TIME},
                                java.sql.Time.class), Timestamp(new JdbcType[] {JdbcType.TIMESTAMP},
                                    java.sql.Timestamp.class), Object(new JdbcType[] {}, java.lang.Object.class), Bytes(
                                        new JdbcType[] {JdbcType.BINARY, JdbcType.VARBINARY}, null, "byte[]");
                                        
    private JdbcType[] jdbcTypes;
    
    private Class<?> type;
    
    private String typeName;
    
    private JavaType(JdbcType[] jdbcTypes, Class<?> type)
    {
        this.jdbcTypes = jdbcTypes;
        this.type = type;
        if (null != type)
        {
            this.typeName = type.getSimpleName();
        }
    }
    
    private JavaType(JdbcType[] jdbcTypes, Class<?> type, String typeName)
    {
        this.jdbcTypes = jdbcTypes;
        this.type = type;
        this.typeName = typeName;
        if (StringUtils.isEmpty(typeName) && null != type)
        {
            this.typeName = type.getSimpleName();
        }
    }
    
    public static JavaType valueOfJdbcType(JdbcType jdbcType)
    {
        JavaType tempJavaType = JavaType.Object;
        if (null != jdbcType)
        {
            JavaType[] javaTypes = JavaType.values();
            for (JavaType javaType : javaTypes)
            {
                JdbcType[] jdbcTypes = javaType.getJdbcTypes();
                if (!ArrayUtils.isEmpty(jdbcTypes) && ArrayUtils.contains(jdbcTypes, jdbcType))
                {
                    tempJavaType = javaType;
                    break;
                }
            }
        }
        return tempJavaType;
    }
    
    public JdbcType[] getJdbcTypes()
    {
        return jdbcTypes;
    }
    
    public Class<?> getType()
    {
        return type;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
}
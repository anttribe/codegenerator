/*
 * 文  件   名: DbConfig.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月11日
 */
package org.anttribe.codegenerator.core.config;

/**
 * 数据库配置信息
 * 
 * @author zhaoyong
 * @version 2015年12月11日
 */
public class DbConfig
{
    /**
     * 数据库方言
     */
    private String dialect;
    
    /**
     * 数据库driber
     */
    private String driverClass;
    
    /**
     * url
     */
    private String url;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    @Override
    public String toString()
    {
        return new StringBuffer().append('{')
            .append("dialect")
            .append(':')
            .append(dialect)
            .append(',')
            .append("driverClass")
            .append(':')
            .append(driverClass)
            .append(',')
            .append("url")
            .append(':')
            .append(url)
            .append(',')
            .append("username")
            .append(':')
            .append(username)
            .append('}')
            .toString();
    }
    
    public String getDialect()
    {
        return dialect;
    }
    
    public void setDialect(String dialect)
    {
        this.dialect = dialect;
    }
    
    public String getDriverClass()
    {
        return driverClass;
    }
    
    public void setDriverClass(String driverClass)
    {
        this.driverClass = driverClass;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
}
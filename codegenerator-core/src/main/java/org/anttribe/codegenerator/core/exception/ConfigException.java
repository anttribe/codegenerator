/*
 * 文  件   名: ConfigException.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月19日
 */
package org.anttribe.codegenerator.core.exception;

/**
 * 配置异常
 * 
 * @author zhaoyong
 * @version 2015年12月19日
 */
public class ConfigException extends Exception
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 7655868120876665088L;
    
    public ConfigException()
    {
    }
    
    public ConfigException(String message)
    {
        super(message);
    }
    
    public ConfigException(Throwable cause)
    {
        super(cause);
    }
    
    public ConfigException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
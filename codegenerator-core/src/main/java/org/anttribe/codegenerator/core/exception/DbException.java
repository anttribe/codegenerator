/*
 * 文  件   名: DbException.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月28日
 */
package org.anttribe.codegenerator.core.exception;

/**
 * 数据库操作异常
 * 
 * @author zhaoyong
 * @version 2015年12月28日
 */
public class DbException extends Exception
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 561808205305613099L;
    
    public DbException()
    {
    }
    
    public DbException(String message)
    {
        super(message);
    }
    
    public DbException(Throwable cause)
    {
        super(cause);
    }
    
    public DbException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}
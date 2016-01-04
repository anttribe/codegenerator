/*
 * 文  件   名: CodeGeneratorException.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月18日
 */
package org.anttribe.codegenerator.core.exception;

/**
 * @author zhaoyong
 * @version 2015年12月18日
 */
public class CodeGeneratorException extends Exception
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 563118270838776474L;
    
    public CodeGeneratorException()
    {
    }
    
    public CodeGeneratorException(String message)
    {
        super(message);
    }
    
    public CodeGeneratorException(Throwable cause)
    {
        super(cause);
    }
    
    public CodeGeneratorException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
}

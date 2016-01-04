/*
 * 文  件   名: Generator.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月8日
 */
package org.anttribe.codegenerator.runtime;

import org.anttribe.codegenerator.core.exception.CodeGeneratorException;

/**
 * @author zhaoyong
 * @version 2015年12月8日
 */
public interface Generator
{
    public void generate()
        throws CodeGeneratorException;
        
    public void generate(String projectName)
        throws CodeGeneratorException;
        
    public void generate(String projectName, String tableName)
        throws CodeGeneratorException;
}
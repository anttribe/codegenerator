/*
 * 文  件   名: CodeGeneratorTest.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月13日
 */
package org.anttribe.codegenerator.runtime;

import org.anttribe.codegenerator.core.exception.CodeGeneratorException;
import org.junit.Test;

/**
 * @author zhaoyong
 * @version 2015年12月13日
 */
public class CodeGeneratorTest
{
    
    @Test
    public void testGenerate()
    {
        try
        {
            Generator generator = new CodeGenerator();
            generator.generate();
        }
        catch (CodeGeneratorException e)
        {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGenerateString()
    {
        try
        {
            Generator generator = new CodeGenerator();
            generator.generate("openeshop");
        }
        catch (CodeGeneratorException e)
        {
            e.printStackTrace();
        }
    }
    
}
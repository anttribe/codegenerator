/*
 * 文  件   名: Generator.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月8日
 */
package org.anttribe.codegenerator.runtime;

import java.util.List;
import java.util.Map;

import org.anttribe.codegenerator.core.exception.CodeGeneratorException;
import org.anttribe.codegenerator.runtime.entity.GeneratorOutput;

/**
 * @author zhaoyong
 * @version 2015年12月8日
 */
public interface Generator
{
    /**
     * 生成配置文件中所有项目代码
     * 
     * @return List<Map<String, List<GeneratorOutput>>>
     * @throws CodeGeneratorException
     */
    public List<Map<String, List<GeneratorOutput>>> generate()
        throws CodeGeneratorException;
        
    /**
     * 生成指定项目代码
     * 
     * @param projectName 项目名
     * @return Map<String, List<GeneratorOutput>>
     * @throws CodeGeneratorException
     */
    public Map<String, List<GeneratorOutput>> generate(String projectName)
        throws CodeGeneratorException;
        
    /**
     * 生成指定项目中指定表的代码
     * 
     * @param projectName
     * @param tableName
     * @return List<GeneratorOutput>
     * @throws CodeGeneratorException
     */
    public List<GeneratorOutput> generate(String projectName, String tableName)
        throws CodeGeneratorException;
}
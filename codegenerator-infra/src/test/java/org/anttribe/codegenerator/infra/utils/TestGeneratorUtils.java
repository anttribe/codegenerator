/*
 * 文  件   名: TestGeneratorUtils.java
 * 版         本 : (Anttribe)codegenerator-infra. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月20日
 */
package org.anttribe.codegenerator.infra.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author zhaoyong
 * @version 2015年12月20日
 */
public class TestGeneratorUtils
{
    
    @Test
    public void testGenerateString()
    {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("modelName", "TEST");
        String generatorStr = GeneratorUtils.generateString(datas, "D:/workspace/j-workspace/research/codegenerator/codegenerator-runtime/target/test-classes//test/${modelName}Model.java");
        System.out.println(generatorStr);
    }
    
}

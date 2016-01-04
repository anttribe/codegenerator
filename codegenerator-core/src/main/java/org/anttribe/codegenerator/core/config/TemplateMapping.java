/*
 * 文  件   名: TemplateMapping.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月18日
 */
package org.anttribe.codegenerator.core.config;

import java.util.HashMap;
import java.util.Map;

import org.anttribe.codegenerator.core.constants.Keys;

/**
 * 模板映射
 * 
 * @author zhaoyong
 * @version 2015年12月18日
 */
public class TemplateMapping
{
    /**
     * 模板
     */
    private String template;
    
    /**
     * 包名
     */
    private String packageName;
    
    /**
     * 输出
     */
    private String output;
    
    /**
     * 解析模板数据
     * 
     * @return Map<String, Object>
     */
    public Map<String, Object> toTemplateDatas()
    {
        Map<String, Object> templateDatas = new HashMap<String, Object>();
        templateDatas.put(Keys.PACKAGENAME, this.getPackageName());
        return templateDatas;
    }
    
    public String getTemplate()
    {
        return template;
    }
    
    public void setTemplate(String template)
    {
        this.template = template;
    }
    
    public String getPackageName()
    {
        return packageName;
    }
    
    public void setPackageName(String packageName)
    {
        this.packageName = packageName;
    }
    
    public String getOutput()
    {
        return output;
    }
    
    public void setOutput(String output)
    {
        this.output = output;
    }
    
}
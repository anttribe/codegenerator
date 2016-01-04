/*
 * 文  件   名: TableMapping.java
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
 * 表映射
 * 
 * @author zhaoyong
 * @version 2015年12月18日
 */
public class TableMapping
{
    /**
     * 表名称
     */
    private String table;
    
    /**
     * 模型类名
     */
    private String model;
    
    /**
     * 模块
     */
    private String module;
    
    /**
     * 解析模板数据
     * 
     * @return Map<String, Object>
     */
    public Map<String, Object> toTemplateDatas()
    {
        Map<String, Object> templateDatas = new HashMap<String, Object>();
        templateDatas.put(Keys.MODELNAME, this.getModel());
        templateDatas.put(Keys.MODULENAME, this.getModule());
        templateDatas.put(Keys.TABLENAME, this.getTable());
        return templateDatas;
    }
    
    public String getTable()
    {
        return table;
    }
    
    public void setTable(String table)
    {
        this.table = table;
    }
    
    public String getModel()
    {
        return model;
    }
    
    public void setModel(String model)
    {
        this.model = model;
    }
    
    public String getModule()
    {
        return module;
    }
    
    public void setModule(String module)
    {
        this.module = module;
    }
}
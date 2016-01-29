/*
 * 文  件   名: GeneratorOutput.java
 * 版         本 : (Anttribe) codegenerator-runtime All rights reserved
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2016年1月29日
 */
package org.anttribe.codegenerator.runtime.entity;

import java.io.Serializable;

/**
 * 输出
 * 
 * @author zhaoyong
 * @version 2016年1月29日
 */
public class GeneratorOutput implements Serializable
{
    
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2186355427122264871L;
    
    /**
     * 项目
     */
    private String project;
    
    /**
     * 表
     */
    private String table;
    
    /**
     * 模板
     */
    private String template;
    
    /**
     * model对象名
     */
    private String model;
    
    /**
     * 输出文件
     */
    private String outputFilename;
    
    @Override
    public String toString()
    {
        return new StringBuffer().append('{')
            .append("project:")
            .append(this.getProject())
            .append(',')
            .append("table:")
            .append(this.getTable())
            .append(',')
            .append("template:")
            .append(this.getTemplate())
            .append(',')
            .append("model:")
            .append(this.getModel())
            .append(',')
            .append("outputFilename:")
            .append(this.getOutputFilename())
            .append('}')
            .toString();
    }
    
    public String getProject()
    {
        return project;
    }
    
    public void setProject(String project)
    {
        this.project = project;
    }
    
    public String getTable()
    {
        return table;
    }
    
    public void setTable(String table)
    {
        this.table = table;
    }
    
    public String getTemplate()
    {
        return template;
    }
    
    public void setTemplate(String template)
    {
        this.template = template;
    }
    
    public String getModel()
    {
        return model;
    }
    
    public void setModel(String model)
    {
        this.model = model;
    }
    
    public String getOutputFilename()
    {
        return outputFilename;
    }
    
    public void setOutputFilename(String outputFilename)
    {
        this.outputFilename = outputFilename;
    }
    
}
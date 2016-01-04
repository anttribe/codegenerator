/*
 * 文  件   名: ProjectConfigBuilder.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月13日
 */
package org.anttribe.codegenerator.runtime.builder;

import java.util.List;

import org.anttribe.codegenerator.core.config.ProjectConfig;

/**
 * 项目配置信息构造器
 * 
 * @author zhaoyong
 * @version 2015年12月13日
 */
public interface ProjectConfigBuilder
{
    /**
     * 构造配置信息
     */
    void build();
    
    /**
     * 根据项目名获取项目配置信息
     * 
     * @param projectName
     * @return ProjectConfig
     */
    ProjectConfig loopup(String projectName);
    
    /**
     * 获取所有的项目配置信息
     * 
     * @return List<ProjectConfig>
     */
    List<ProjectConfig> loopup();
}
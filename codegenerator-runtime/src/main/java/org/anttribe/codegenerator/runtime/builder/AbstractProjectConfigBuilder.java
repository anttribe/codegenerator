/*
 * 文  件   名: AbstractProjectConfigBuilder.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月13日
 */
package org.anttribe.codegenerator.runtime.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anttribe.codegenerator.core.config.ProjectConfig;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhaoyong
 * @version 2015年12月13日
 */
public abstract class AbstractProjectConfigBuilder implements ProjectConfigBuilder
{
    /**
     * 项目配置信息
     */
    protected Map<String, ProjectConfig> projectConfigs = new HashMap<String, ProjectConfig>();
    
    @Override
    public ProjectConfig loopup(String projectName)
    {
        if (StringUtils.isEmpty(projectName))
        {
            return null;
        }
        if (MapUtils.isEmpty(projectConfigs))
        {
            // 重新加载配置信息
            this.build();
        }
        
        return projectConfigs.get(projectName);
    }
    
    @Override
    public List<ProjectConfig> loopup()
    {
        List<ProjectConfig> configs = new ArrayList<ProjectConfig>();
        if (MapUtils.isEmpty(projectConfigs))
        {
            // 重新加载配置信息
            this.build();
        }
        if (!MapUtils.isEmpty(projectConfigs))
        {
            configs.addAll(projectConfigs.values());
        }
        return configs;
    }
}
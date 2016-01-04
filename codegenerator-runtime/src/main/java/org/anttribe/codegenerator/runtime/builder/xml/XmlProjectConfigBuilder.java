/*
 * 文  件   名: XmlProjectConfigBuilder.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月13日
 */
package org.anttribe.codegenerator.runtime.builder.xml;

import java.io.InputStream;
import java.util.List;

import org.anttribe.codegenerator.core.config.ProjectConfig;
import org.anttribe.codegenerator.runtime.builder.AbstractProjectConfigBuilder;
import org.anttribe.component.dataparser.DataParser;
import org.anttribe.component.dataparser.Parser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML配置项目信息，从XML中构造ProjectConfig
 * 
 * @author zhaoyong
 * @version 2015年12月13日
 */
public class XmlProjectConfigBuilder extends AbstractProjectConfigBuilder
{
    
    private static Logger logger = LoggerFactory.getLogger(XmlProjectConfigBuilder.class);
    
    /**
     * 默认配置文件
     */
    private static final String DEFAULT_CONFIG_FILE = "projects-config.xml";
    
    /**
     * dataParser
     */
    private static DataParser dataParser = DataParser.getDataParser(Parser.XML);
    
    /**
     * 配置文件
     */
    private String configFile = DEFAULT_CONFIG_FILE;
    
    @SuppressWarnings("unchecked")
    @Override
    public void build()
    {
        logger.debug("Building ProjectConfig from XML file [{}]", configFile);
        
        if (!StringUtils.isEmpty(configFile))
        {
            InputStream in = XmlProjectConfigBuilder.class.getClassLoader().getResourceAsStream(configFile);
            List<ProjectConfig> configs = (List<ProjectConfig>)dataParser.parseToObject(in, ProjectConfig.class);
            if (!CollectionUtils.isEmpty(configs))
            {
                for (ProjectConfig config : configs)
                {
                    if (StringUtils.isEmpty(config.getName()))
                    {
                        logger.warn("After parsering the XML config file, ProjectConfig [] has no project name.",
                            config);
                        continue;
                    }
                    this.projectConfigs.put(config.getName(), config);
                }
            }
        }
        
        logger.debug("After building ProjectConfig from XML file [{}], get projectConfigs [{}]",
            configFile,
            projectConfigs);
    }
    
    public void setConfigFile(String configFile)
    {
        this.configFile = configFile;
    }
    
}
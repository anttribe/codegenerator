/*
 * 文  件   名: ProjectConfig.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月11日
 */
package org.anttribe.codegenerator.core.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.anttribe.codegenerator.core.constants.Constants;
import org.anttribe.codegenerator.core.constants.Keys;
import org.anttribe.codegenerator.core.exception.ConfigException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目配置信息
 * 
 * @author zhaoyong
 * @version 2015年12月11日
 */
public class ProjectConfig
{
    
    private static Logger logger = LoggerFactory.getLogger(ProjectConfig.class);
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 编码
     */
    private String encoding;
    
    /**
     * 模板目录
     */
    private String templateDirectory;
    
    /**
     * 输出文件目录
     */
    private String outputDirectory;
    
    /**
     * 基础包名
     */
    private String basePackage;
    
    /**
     * 基础包名对应的目录
     */
    private String basePackageDirectory;
    
    /**
     * 表前缀
     */
    private String tablePrefix;
    
    /**
     * 标前缀
     */
    private List<String> tablePrefixs;
    
    /**
     * 表后缀
     */
    private String tableSuffix;
    
    /**
     * 表后缀
     */
    private List<String> tableSuffixs;
    
    /**
     * 项目版权信息
     */
    private Copyright copyright;
    
    /**
     * 数据库配置信息
     */
    private DbConfig dbConfig;
    
    /**
     * 模板映射集合
     */
    List<TemplateMapping> templateMappings = new ArrayList<TemplateMapping>();
    
    /**
     * 表映射集合
     */
    private Map<String, TableMapping> tableMappings = new HashMap<String, TableMapping>();
    
    /**
     * <默认构造器>
     */
    public ProjectConfig()
    {
    }
    
    /**
     * 解析模板数据
     * 
     * @return Map<String, Object>
     */
    public Map<String, Object> toTemplateDatas()
    {
        Map<String, Object> templateDatas = new HashMap<String, Object>();
        templateDatas.put(Keys.PROJECTNAME, this.getName());
        templateDatas.put(Keys.PROJECTDESCRIPTION, this.getDescription());
        templateDatas.put(Keys.BASEPACKAGE, this.getBasePackage());
        templateDatas.put(Keys.BASEPACKAGEDIRECTORY, this.getBasePackageDirectory());
        templateDatas.put(Keys.COPYRIGHT, this.getCopyright());
        return templateDatas;
    }
    
    @Override
    public String toString()
    {
        return new StringBuffer().append('{')
            .append("name")
            .append(':')
            .append(name)
            .append(',')
            .append("description")
            .append(':')
            .append(description)
            .append(',')
            .append("templateDirectory")
            .append(':')
            .append(templateDirectory)
            .append(',')
            .append("outputDirectory")
            .append(':')
            .append(outputDirectory)
            .append(',')
            .append("packageName")
            .append(':')
            .append(basePackage)
            .append(',')
            .append("dbConfig")
            .append(':')
            .append(dbConfig)
            .append('}')
            .toString();
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
        throws ConfigException
    {
        if (StringUtils.isEmpty(name))
        {
            throw new ConfigException("Configing project config, project name is not set.");
        }
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getBasePackage()
    {
        return basePackage;
    }
    
    public void setBasePackage(String basePackage)
    {
        this.basePackage = basePackage;
        if (!StringUtils.isEmpty(this.basePackage) && basePackage.indexOf(".") != -1)
        {
            this.basePackageDirectory = basePackage.replaceAll("\\.", "/");
        }
    }
    
    public String getBasePackageDirectory()
    {
        return basePackageDirectory;
    }
    
    public void setBasePackageDirectory(String basePackageDirectory)
    {
        this.basePackageDirectory = basePackageDirectory;
    }
    
    public String getEncoding()
    {
        return encoding;
    }
    
    public void setEncoding(String encoding)
    {
        if (StringUtils.isEmpty(encoding))
        {
            encoding = Constants.DEFAULT_ENCODING;
            logger.debug("configing project config, encoding is not set, then use the default {}",
                Constants.DEFAULT_ENCODING);
        }
        this.encoding = encoding;
    }
    
    public String getTemplateDirectory()
    {
        return templateDirectory;
    }
    
    public void setTemplateDirectory(String templateDirectory)
    {
        if (StringUtils.isEmpty(templateDirectory))
        {
            templateDirectory = Constants.DEFAULT_TEMPLATE_DIRECTORY + this.getName();
            logger.debug("Configing project config, templateDirectory is not set, then use the default {}",
                templateDirectory);
        }
        this.templateDirectory = templateDirectory;
    }
    
    public String getOutputDirectory()
    {
        return outputDirectory;
    }
    
    public void setOutputDirectory(String outputDirectory)
    {
        if (StringUtils.isEmpty(outputDirectory))
        {
            outputDirectory = this.getClass().getClassLoader().getResource("").getFile() + "/" + this.getName();
            logger.debug("Configing project config, outputDirectory is not set, then set to the default {}",
                outputDirectory);
        }
        this.outputDirectory = outputDirectory + "/" + this.getName();
    }
    
    public String getTablePrefix()
    {
        return tablePrefix;
    }
    
    public void setTablePrefix(String tablePrefix)
    {
        this.tablePrefix = tablePrefix;
        if (!StringUtils.isEmpty(tablePrefix))
        {
            tablePrefixs = new ArrayList<String>();
            String[] tablePrefixArray = StringUtils.split(tablePrefix, ",");
            if (!ArrayUtils.isEmpty(tablePrefixArray))
            {
                tablePrefixs = Arrays.asList(tablePrefixArray);
            }
        }
    }
    
    public String getTableSuffix()
    {
        return tableSuffix;
    }
    
    public void setTableSuffix(String tableSuffix)
    {
        this.tableSuffix = tableSuffix;
        if (!StringUtils.isEmpty(tableSuffix))
        {
            tableSuffixs = new ArrayList<String>();
            String[] tableSuffixArray = StringUtils.split(tableSuffix, ",");
            if (!ArrayUtils.isEmpty(tableSuffixArray))
            {
                tableSuffixs = Arrays.asList(tableSuffixArray);
            }
        }
    }
    
    public Copyright getCopyright()
    {
        return copyright;
    }
    
    public void setCopyright(Copyright copyright)
    {
        this.copyright = copyright;
    }
    
    public DbConfig getDbConfig()
    {
        return dbConfig;
    }
    
    public void setDbConfig(DbConfig dbConfig)
        throws ConfigException
    {
        if (null == dbConfig)
        {
            logger.error("Configing dbConfig, dbConfig is not configed");
            throw new ConfigException("Configing dbConfig, dbConfig is not configed.");
        }
        if (StringUtils.isEmpty(dbConfig.getDialect()) || StringUtils.isEmpty(dbConfig.getDriverClass())
            || StringUtils.isEmpty(dbConfig.getUrl()) || StringUtils.isEmpty(dbConfig.getUsername()))
        {
            logger.error("Configing dbConfig, dbConfig dialect, driverClass, url, username is not configed complete.");
            throw new ConfigException(
                "Configing dbConfig, dbConfig dialect, driverClass, url, username is not configed complete.");
        }
        this.dbConfig = dbConfig;
    }
    
    public List<TemplateMapping> getTemplateMappings()
    {
        return templateMappings;
    }
    
    public void setTemplateMappings(List<TemplateMapping> templateMappings)
        throws ConfigException
    {
        if (null != templateMappings && !templateMappings.isEmpty())
        {
            for (TemplateMapping templateMapping : templateMappings)
            {
                String template = templateMapping.getTemplate();
                if (StringUtils.isEmpty(template))
                {
                    logger.error("Configing TemplateMapping, attribute template is not set");
                    throw new ConfigException("Configing TemplateMapping, attribute template is not set.");
                }
                
                // 模板路径
                template = template.replace("\\", "/");
                if (!template.contains(".") || StringUtils.substringAfterLast(template, ".").contains("/"))
                {
                    template += Constants.DEFAULT_TEMPLATE_FILE_SUBFIX;
                }
                template = this.getTemplateDirectory() + "/" + template;
                URL templateURL = this.getClass().getClassLoader().getResource(template);
                if (null == templateURL)
                {
                    logger.error("Configing TemplateMapping, template file [{}] not exist.", template);
                    throw new ConfigException("Configing TemplateMapping, template file [" + template + "] not exist.");
                }
                templateMapping.setTemplate(templateURL.getFile());
                
                // 输出路径
                String output = templateMapping.getOutput();
                if (StringUtils.isEmpty(output))
                {
                    output = "${" + Keys.MODELNAME + "}" + Constants.DEFAULT_OUTPUT_FILE_SUBFIX;
                    
                    logger.debug(
                        "Configing TemplateMapping, attribute output is not set, then use ${MODELNAME} as output[{}].",
                        output);
                }
                output = output.replace("\\", "/");
                templateMapping.setOutput(output);
                
                this.templateMappings.add(templateMapping);
            }
        }
    }
    
    public Map<String, TableMapping> getTableMappings()
    {
        return tableMappings;
    }
    
    public void setTableMappings(List<TableMapping> tableMappings)
        throws ConfigException
    {
        if (null != tableMappings && !tableMappings.isEmpty())
        {
            for (TableMapping tableMapping : tableMappings)
            {
                if (null == tableMapping || StringUtils.isEmpty(tableMapping.getTable()))
                {
                    logger.error("Configing TableMapping, attribute table is not set");
                    throw new ConfigException("Configing TableMapping, attribute table is not set.");
                }
                
                String tableName = tableMapping.getTable();
                // 如果模型类名不配置，默认取表名
                String modelName = tableMapping.getModel();
                if (StringUtils.isEmpty(modelName))
                {
                    String oriTableName = tableName;
                    // 去除表前缀
                    if (!CollectionUtils.isEmpty(this.tablePrefixs))
                    {
                        for (String prefix : tablePrefixs)
                        {
                            if (tableName.startsWith(prefix))
                            {
                                tableName = tableName.replaceFirst(prefix, "");
                                break;
                            }
                        }
                    }
                    // 去除表后缀
                    if (!CollectionUtils.isEmpty(this.tableSuffixs))
                    {
                        for (String suffix : tableSuffixs)
                        {
                            if (tableName.endsWith(suffix))
                            {
                                tableName = tableName.substring(0, tableName.lastIndexOf(suffix));
                                break;
                            }
                        }
                    }
                    if (StringUtils.isEmpty(tableName))
                    {
                        continue;
                    }
                    // 表名按照 "_" 分隔
                    String[] subTableNames = tableName.split("_");
                    if (null == subTableNames)
                    {
                        continue;
                    }
                    
                    modelName = "";
                    for (String subTableName : subTableNames)
                    {
                        if (StringUtils.isEmpty(subTableName))
                        {
                            continue;
                        }
                        modelName += subTableName.substring(0, 1).toUpperCase() + subTableName.substring(1);
                    }
                    
                    logger.debug(
                        "Configing TableMapping, attribute model is not set, then use tableName[{}] to populate modelName[{}].",
                        oriTableName,
                        modelName);
                }
                tableMapping.setModel(modelName);
                this.tableMappings.put(tableMapping.getTable(), tableMapping);
            }
        }
    }
    
    public void setTableMappings(Map<String, TableMapping> tableMappings)
    {
        this.tableMappings = tableMappings;
    }
}
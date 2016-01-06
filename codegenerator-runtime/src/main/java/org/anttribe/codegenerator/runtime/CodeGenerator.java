/*
 * 文  件   名: CodeGenerator.java
 * 版         本 : (Anttribe)codegenerator-runtime. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月9日
 */
package org.anttribe.codegenerator.runtime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.anttribe.codegenerator.core.config.DbConfig;
import org.anttribe.codegenerator.core.config.ProjectConfig;
import org.anttribe.codegenerator.core.config.TableMapping;
import org.anttribe.codegenerator.core.config.TemplateMapping;
import org.anttribe.codegenerator.core.constants.Keys;
import org.anttribe.codegenerator.core.exception.CodeGeneratorException;
import org.anttribe.codegenerator.core.exception.DbException;
import org.anttribe.codegenerator.infra.utils.GeneratorUtils;
import org.anttribe.codegenerator.runtime.builder.ProjectConfigBuilder;
import org.anttribe.codegenerator.runtime.builder.xml.XmlProjectConfigBuilder;
import org.anttribe.codegenerator.runtime.db.DbDialect;
import org.anttribe.codegenerator.runtime.db.DbProcessor;
import org.anttribe.codegenerator.runtime.db.metadata.DbTable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author zhaoyong
 * @version 2015年12月9日
 */
public class CodeGenerator implements Generator
{
    
    private static Logger logger = LoggerFactory.getLogger(CodeGenerator.class);
    
    /**
     * 默认配置构造器
     */
    private static final ProjectConfigBuilder DEFAULT_PROJECTCONFIGBUILDER = new XmlProjectConfigBuilder();
    
    /**
     * 项目配置信息构造器
     */
    private ProjectConfigBuilder projectConfigBuilder = DEFAULT_PROJECTCONFIGBUILDER;
    
    /**
     * 模板配置
     */
    private Configuration templateConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    
    @Override
    public void generate()
        throws CodeGeneratorException
    {
        List<ProjectConfig> projectConfigs = projectConfigBuilder.loopup();
        if (!CollectionUtils.isEmpty(projectConfigs))
        {
            for (ProjectConfig projectConfig : projectConfigs)
            {
                this.generate(projectConfig);
            }
        }
    }
    
    @Override
    public void generate(String projectName)
        throws CodeGeneratorException
    {
        if (!StringUtils.isEmpty(projectName))
        {
            ProjectConfig projectConfig = projectConfigBuilder.loopup(projectName);
            if (null == projectConfig)
            {
                logger.warn("Can not find projectConfig for project {}", projectName);
                return;
            }
            this.generate(projectConfig);
        }
    }
    
    @Override
    public void generate(String projectName, String tableName)
        throws CodeGeneratorException
    {
        if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(tableName))
        {
            ProjectConfig projectConfig = projectConfigBuilder.loopup(projectName);
            if (null == projectConfig)
            {
                logger.warn("Can not find projectConfig for project {}", projectName);
                return;
            }
            
            Map<String, TableMapping> tableMappings = projectConfig.getTableMappings();
            if (MapUtils.isEmpty(tableMappings))
            {
                logger.warn("There is no tableMappings in this projectConfig.");
                throw new CodeGeneratorException("there is no tableMappings in this projectConfig.");
            }
            
            TableMapping tableMapping = tableMappings.get(tableName);
            if (null == tableMapping)
            {
                logger.warn("There is no tableMapping in this projectConfig with table {}", tableName);
                throw new CodeGeneratorException(
                    "there is no tableMapping in this projectConfig with table " + tableName);
            }
            
            this.generate(projectConfig, tableMapping);
        }
    }
    
    private void generate(ProjectConfig projectConfig)
        throws CodeGeneratorException
    {
        if (null != projectConfig)
        {
            logger.debug("Starting generator code for project [{}]", projectConfig.getName());
            
            Map<String, TableMapping> tableMappings = projectConfig.getTableMappings();
            if (MapUtils.isEmpty(tableMappings))
            {
                logger.warn("there is no tableMappings in this projectConfig.");
                throw new CodeGeneratorException("there is no tableMappings in this projectConfig.");
            }
            
            // 遍历每张表，生成代码
            for (TableMapping tableMapping : tableMappings.values())
            {
                this.generate(projectConfig, tableMapping);
            }
        }
    }
    
    private void generate(ProjectConfig projectConfig, TableMapping tableMapping)
        throws CodeGeneratorException
    {
        logger.debug("Start generating code for project [{}], table [{}]",
            projectConfig.getName(),
            tableMapping.getTable());
        List<TemplateMapping> templateMappings = projectConfig.getTemplateMappings();
        if (CollectionUtils.isEmpty(templateMappings))
        {
            logger.warn("There is no templateMappings in this projectConfig.");
            throw new CodeGeneratorException("there is no templateMappings in this projectConfig.");
        }
        
        // 设置参数
        Map<String, Object> templateDatas = projectConfig.toTemplateDatas();
        if (null == templateDatas)
        {
            templateDatas = new HashMap<String, Object>();
        }
        templateDatas.putAll(tableMapping.toTemplateDatas());
        
        // 根据TableMapping和DBConfig解析表结构数据
        DbConfig dbConfig = projectConfig.getDbConfig();
        if (null == dbConfig || StringUtils.isEmpty(dbConfig.getDialect()))
        {
            logger.error("Generating code, dbConfig is not configed.");
            throw new CodeGeneratorException("Generating code, dbConfig is not configed");
        }
        // 根据不同的数据库，采用不同的数据库处理类处理
        DbDialect dbDialect = DbDialect.valueOf(dbConfig.getDialect());
        if (null == dbDialect)
        {
            logger.error("Generating code, dbDialect[{}] is not supported.", dbConfig.getDialect());
            throw new CodeGeneratorException(
                "Generating code, dbDialect[" + dbConfig.getDialect() + "] is not supported.");
        }
        try
        {
            DbProcessor dbProcessor = dbDialect.getDbProcessor();
            DbTable dbTable = dbProcessor.processDbTableMetaData(dbConfig, tableMapping.getTable());
            if (null == dbTable)
            {
                logger.error("Can not get table metadata while using table name [{}]", tableMapping.getTable());
                throw new CodeGeneratorException(
                    "Can not get table metadata while using table name [" + tableMapping.getTable() + "]");
            }
            templateDatas.put(Keys.DBTABLE, dbTable);
            
            // 遍历模板，生成对应每一个模板的代码
            for (TemplateMapping templateMapping : templateMappings)
            {
                try
                {
                    this.generate(templateDatas, projectConfig, tableMapping, templateMapping);
                }
                catch (Exception e)
                {
                    logger.error("Generating code get error, cause: {}", e);
                    throw new CodeGeneratorException("Generating code get error", e);
                }
            }
        }
        catch (DbException e)
        {
            throw new CodeGeneratorException(e);
        }
    }
    
    private void generate(Map<String, Object> templateDatas, ProjectConfig projectConfig, TableMapping tableMapping,
        TemplateMapping templateMapping)
            throws IOException, TemplateException, CodeGeneratorException
    {
        templateDatas.putAll(templateMapping.toTemplateDatas());
        // 模板路径
        File templateFile = new File(templateMapping.getTemplate());
        // 设置模板参数
        this.templateConfig.setEncoding(Locale.getDefault(), projectConfig.getEncoding());
        this.templateConfig.setDirectoryForTemplateLoading(templateFile.getParentFile());
        
        // 获取模板
        Template template = templateConfig.getTemplate(templateFile.getName());
        // 获取输出
        String outputFilename = this.buildOutputFilename(templateDatas, projectConfig, tableMapping, templateMapping);
        // 设置输出
        if (!StringUtils.isEmpty(outputFilename))
        {
            File outputFile = new File(outputFilename);
            if (null != outputFile.getParentFile() && !outputFile.getParentFile().exists())
            {
                // 创建目录
                outputFile.getParentFile().mkdirs();
            }
            
            String fileName = outputFile.getName();
            templateDatas.put(Keys.FILENAME, fileName);
            templateDatas.put(Keys.CLASSNAME, fileName.substring(0, fileName.indexOf(".")));
            
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outputFile)));
            template.process(templateDatas, writer);
            writer.close();
            logger.debug("Generating code successful, output file {}", outputFile.getPath());
        }
    }
    
    /**
     * 构造完整文件输出
     * 
     * @param templateDatas
     * @param projectConfig
     * @param templateMapping
     * @return String
     */
    private String buildOutputFilename(Map<String, Object> templateDatas, ProjectConfig projectConfig,
        TableMapping tableMapping, TemplateMapping templateMapping)
    {
        StringBuffer outputFilename = new StringBuffer();
        outputFilename.append(projectConfig.getOutputDirectory());
        
        // 构造包名
        StringBuffer packageName = new StringBuffer();
        if (!StringUtils.isEmpty(projectConfig.getBasePackage()))
        {
            packageName.append(projectConfig.getBasePackage());
        }
        if (!StringUtils.isEmpty(tableMapping.getModule()))
        {
            packageName.append(".").append(tableMapping.getModule());
        }
        if (!StringUtils.isEmpty(templateMapping.getPackageName()))
        {
            packageName.append(".").append(templateMapping.getPackageName());
        }
        templateDatas.put(Keys.PACKAGENAME, packageName.toString());
        outputFilename.append("/").append(packageName.toString().replaceAll("\\.", "/"));
        
        String output = templateMapping.getOutput();
        if (!StringUtils.isEmpty(output))
        {
            outputFilename.append("/").append(output);
            // 输出文件名中包含变量，处理变量
            if (outputFilename.indexOf("${") != -1)
            {
                output = GeneratorUtils.generateString(templateDatas, outputFilename.toString());
            }
        }
        
        return output;
    }
    
    public void setProjectConfigBuilder(ProjectConfigBuilder projectConfigBuilder)
    {
        this.projectConfigBuilder = projectConfigBuilder;
    }
    
}
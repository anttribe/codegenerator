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
import java.util.ArrayList;
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
import org.anttribe.codegenerator.runtime.entity.GeneratorOutput;
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
    public List<Map<String, List<GeneratorOutput>>> generate()
        throws CodeGeneratorException
    {
        List<ProjectConfig> projectConfigs = projectConfigBuilder.loopup();
        if (!CollectionUtils.isEmpty(projectConfigs))
        {
            List<Map<String, List<GeneratorOutput>>> projectsOutputs =
                new ArrayList<Map<String, List<GeneratorOutput>>>();
            for (ProjectConfig projectConfig : projectConfigs)
            {
                Map<String, List<GeneratorOutput>> projectOutputs = this.generate(projectConfig);
                if (null != projectOutputs)
                {
                    projectsOutputs.add(projectOutputs);
                }
            }
            
            return projectsOutputs;
        }
        return null;
    }
    
    @Override
    public Map<String, List<GeneratorOutput>> generate(String projectName)
        throws CodeGeneratorException
    {
        if (!StringUtils.isEmpty(projectName))
        {
            ProjectConfig projectConfig = projectConfigBuilder.loopup(projectName);
            if (null == projectConfig)
            {
                logger.warn("Can not find projectConfig for project {}", projectName);
                return null;
            }
            Map<String, List<GeneratorOutput>> projectOutputs = this.generate(projectConfig);
            
            return projectOutputs;
        }
        return null;
    }
    
    @Override
    public List<GeneratorOutput> generate(String projectName, String tableName)
        throws CodeGeneratorException
    {
        List<GeneratorOutput> tableOutputs = null;
        if (!StringUtils.isEmpty(projectName) && !StringUtils.isEmpty(tableName))
        {
            ProjectConfig projectConfig = projectConfigBuilder.loopup(projectName);
            if (null == projectConfig)
            {
                logger.warn("Can not find projectConfig for project {}", projectName);
                return null;
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
            
            tableOutputs = this.generate(projectConfig, tableMapping);
        }
        
        return tableOutputs;
    }
    
    /**
     * 根据项目配置生成对应项目的代码
     * 
     * @param projectConfig
     * @return
     * @throws CodeGeneratorException
     */
    private Map<String, List<GeneratorOutput>> generate(ProjectConfig projectConfig)
        throws CodeGeneratorException
    {
        if (null != projectConfig)
        {
            logger.debug("Starting generator code for project [{}]", projectConfig.getName());
            
            Map<String, TableMapping> tableMappings = projectConfig.getTableMappings();
            if (MapUtils.isEmpty(tableMappings))
            {
                logger.warn("There is no tableMappings in this projectConfig.");
                throw new CodeGeneratorException("There is no tableMappings in this projectConfig.");
            }
            
            Map<String, List<GeneratorOutput>> projectOutputs = new HashMap<String, List<GeneratorOutput>>();
            // 遍历每张表，生成代码
            for (TableMapping tableMapping : tableMappings.values())
            {
                List<GeneratorOutput> tableOutputs = this.generate(projectConfig, tableMapping);
                projectOutputs.put(tableMapping.getTable(), tableOutputs);
            }
            
            return projectOutputs;
        }
        return null;
    }
    
    /**
     * 根据项目配置和表映射生成对应表的代码
     * 
     * @param projectConfig
     * @param tableMapping
     * @return List<GeneratorOutput>
     * @throws CodeGeneratorException
     */
    private List<GeneratorOutput> generate(ProjectConfig projectConfig, TableMapping tableMapping)
        throws CodeGeneratorException
    {
        logger.debug("Starting generating code for project [{}], table [{}]",
            projectConfig.getName(),
            tableMapping.getTable());
        List<TemplateMapping> templateMappings = projectConfig.getTemplateMappings();
        if (CollectionUtils.isEmpty(templateMappings))
        {
            logger.warn("There is no templateMappings in this projectConfig.");
            throw new CodeGeneratorException("There is no templateMappings in this projectConfig.");
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
            
            List<GeneratorOutput> tableOutputs = new ArrayList<GeneratorOutput>();
            // 遍历模板，生成对应每一个模板的代码
            for (TemplateMapping templateMapping : templateMappings)
            {
                try
                {
                    GeneratorOutput output = this.generate(templateDatas, projectConfig, tableMapping, templateMapping);
                    if (null != output)
                    {
                        tableOutputs.add(output);
                    }
                }
                catch (Exception e)
                {
                    logger.error("Generating code get error, cause: {}", e);
                    throw new CodeGeneratorException("Generating code get error", e);
                }
            }
            return tableOutputs;
        }
        catch (DbException e)
        {
            throw new CodeGeneratorException(e);
        }
    }
    
    /**
     * 根据模板数据生成对应文件
     * 
     * @param templateDatas 模板数据
     * @param projectConfig 项目配置
     * @param tableMapping 表映射
     * @param templateMapping 模板映射
     * @return GeneratorOutput
     * @throws IOException
     * @throws TemplateException
     * @throws CodeGeneratorException
     */
    private GeneratorOutput generate(Map<String, Object> templateDatas, ProjectConfig projectConfig,
        TableMapping tableMapping, TemplateMapping templateMapping)
            throws IOException, TemplateException, CodeGeneratorException
    {
        GeneratorOutput generatorOutput = null;
        
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
            
            // 代码生成器输出
            generatorOutput = new GeneratorOutput();
            generatorOutput.setProject(projectConfig.getName());
            generatorOutput.setTable(tableMapping.getTable());
            generatorOutput.setTemplate(templateMapping.getTemplate());
            generatorOutput.setModel(tableMapping.getModel());
            generatorOutput.setOutputFilename(outputFilename);
        }
        return generatorOutput;
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
        StringBuffer packageNameStrB = new StringBuffer();
        if (!StringUtils.isEmpty(projectConfig.getBasePackage()))
        {
            packageNameStrB.append(projectConfig.getBasePackage());
        }
        if (!StringUtils.isEmpty(tableMapping.getModule()))
        {
            packageNameStrB.append(".").append(tableMapping.getModule());
        }
        if (!StringUtils.isEmpty(templateMapping.getPackageName()))
        {
            packageNameStrB.append(".").append(templateMapping.getPackageName());
        }
        // 去除包名可能多余的"."
        String packageName = packageNameStrB.toString();
        if (!StringUtils.isEmpty(packageName))
        {
            if (packageName.startsWith("."))
            {
                packageName = packageName.substring(packageName.indexOf(".") + 1);
            }
            if (!StringUtils.isEmpty(packageName))
            {
                outputFilename.append("/").append(packageName.replaceAll("\\.", "/"));
            }
        }
        templateDatas.put(Keys.PACKAGENAME, packageName);
        
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
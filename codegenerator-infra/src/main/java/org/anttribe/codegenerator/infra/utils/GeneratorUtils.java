/*
 * 文  件   名: GeneratorUtils.java
 * 版         本 : (Anttribe)codegenerator-infra. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月20日
 */
package org.anttribe.codegenerator.infra.utils;

import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @author zhaoyong
 * @version 2015年12月20日
 */
public class GeneratorUtils
{
    
    /**
     * 默认临时模板名称
     */
    private static final String DEFAULT_TEMP_TEMPLATE_NAME = "_default_temp_template";
    
    /**
     * 默认编码
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    private static Logger logger = LoggerFactory.getLogger(GeneratorUtils.class);
    
    /**
     * 根据给定数据解析模板字符串
     * 
     * @param datas Map<String, Object>
     * @param templateStr String
     * @return String
     */
    public static String generateString(Map<String, Object> datas, String templateStr)
    {
        if (!StringUtils.isEmpty(templateStr) && !MapUtils.isEmpty(datas))
        {
            if (!templateStr.contains("${"))
            {
                return templateStr;
            }
            
            Configuration templateConfig = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            // TemplateLoader
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate(DEFAULT_TEMP_TEMPLATE_NAME, templateStr);
            templateConfig.setTemplateLoader(templateLoader);
            templateConfig.setDefaultEncoding(DEFAULT_ENCODING);
            StringWriter writer = new StringWriter();
            try
            {
                Template template = templateConfig.getTemplate(DEFAULT_TEMP_TEMPLATE_NAME);
                template.process(datas, writer);
            }
            catch (Exception e)
            {
                logger.error("generating string get error, cause: {}", e);
            }
            return writer.toString();
        }
        return templateStr;
    }
    
}
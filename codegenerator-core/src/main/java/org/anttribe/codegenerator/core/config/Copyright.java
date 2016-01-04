/*
 * 文  件   名: Copyright.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月18日
 */
package org.anttribe.codegenerator.core.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaoyong
 * @version 2015年12月18日
 */
public class Copyright
{
    /**
     * 默认作者
     */
    private static final String DEFAULT_AUTHOR_NAME = System.getProperty("user.name");
    
    /**
     * 默认版本号
     */
    private static final String DEFAULT_VERSION_NO = "1.0";
    
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 作者
     */
    private String author = DEFAULT_AUTHOR_NAME;
    
    /**
     * 作者邮箱
     */
    private String authorEmail = "";
    
    /**
     * 版权描述
     */
    private String description = "";
    
    /**
     * 创建日期
     */
    private String createDate = dateFormat.format(new Date());
    
    /**
     * 版本
     */
    private String version = DEFAULT_VERSION_NO;
    
    public static DateFormat getDateFormat()
    {
        return dateFormat;
    }
    
    public static void setDateFormat(DateFormat dateFormat)
    {
        Copyright.dateFormat = dateFormat;
    }
    
    public String getAuthor()
    {
        if (StringUtils.isEmpty(author))
        {
            return DEFAULT_AUTHOR_NAME;
        }
        return author;
    }
    
    public void setAuthor(String author)
    {
        this.author = author;
    }
    
    public String getAuthorEmail()
    {
        return authorEmail;
    }
    
    public void setAuthorEmail(String authorEmail)
    {
        this.authorEmail = authorEmail;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getCreateDate()
    {
        return createDate;
    }
    
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }
    
    public String getVersion()
    {
        if (StringUtils.isEmpty(version))
        {
            return DEFAULT_VERSION_NO;
        }
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
}
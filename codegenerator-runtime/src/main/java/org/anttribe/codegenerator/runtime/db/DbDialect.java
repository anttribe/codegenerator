/*
 * 文  件   名: DbDialect.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月11日
 */
package org.anttribe.codegenerator.runtime.db;

import org.anttribe.codegenerator.runtime.db.impl.MySqlDbProcessor;

/**
 * 数据库方言
 * 
 * @author zhaoyong
 * @version 2015年12月11日
 */
public enum DbDialect
{
    MYSQL(new MySqlDbProcessor());
    
    /**
     * 数据库处理器
     */
    private DbProcessor dbProcessor;
    
    private DbDialect(DbProcessor dbProcessor)
    {
        this.dbProcessor = dbProcessor;
    }
    
    /**
     * @return the dbProcessor
     */
    public DbProcessor getDbProcessor()
    {
        return dbProcessor;
    }
}
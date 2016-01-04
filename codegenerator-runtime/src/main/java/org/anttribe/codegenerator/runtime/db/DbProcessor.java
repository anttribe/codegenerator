/*
 * 文  件   名: DbProcessor.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月11日
 */
package org.anttribe.codegenerator.runtime.db;

import org.anttribe.codegenerator.core.config.DbConfig;
import org.anttribe.codegenerator.core.exception.DbException;
import org.anttribe.codegenerator.runtime.db.metadata.DbTable;

/**
 * 数据库处理器
 * 
 * @author zhaoyong
 * @version 2015年12月11日
 */
public interface DbProcessor
{
    /**
     * 从数据库中根据表名解析出表元数据
     * 
     * @param tableName String
     * @return DbTable
     */
    DbTable processDbTableMetaData(DbConfig dbConfig, String tableName)
        throws DbException;
}
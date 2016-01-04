/*
 * 文  件   名: DbTableType.java
 * 版         本 : (Anttribe)codegenerator-core. All rights reserved.
 * 描         述 : <描述>
 * 修   改  人: zhaoyong
 * 修改时间: 2015年12月27日
 */
package org.anttribe.codegenerator.runtime.db.metadata;

/**
 * 数据库表类型
 * 
 * @author zhaoyong
 * @version 2015年12月27日
 */
public enum DbTableType
{
    /** 表 */
    TABLE,
    /** 视图 */
    VIEW,
    /** 系统表 */
    SYSTEM_TABLE,
    /** 全局临时表 */
    GLOBAL_TEMPORARY,
    /** 局部临时表 */
    LOCAL_TEMPORARY,
    /** 别名 */
    ALIAS,
    /** 同义词 */
    SYNONYM;
}
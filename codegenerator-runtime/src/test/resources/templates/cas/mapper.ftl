<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${PACKAGENAME}.${MODELNAME}">
	<cache readOnly="true" />
	<resultMap id="${MODELNAME}-resultMap" type="${PACKAGENAME}.${MODELNAME}">
	    <#list DBTABLE.columns as column>
		<result property="${column.fieldName?uncap_first}" column="${column.columnName}" />
	    </#list>
	</resultMap>
	
	<!-- select -->
	<sql id="select">
        <![CDATA[
            SELECT <#list DBTABLE.columns as column>t.${column.columnName}<#if column_index != (DBTABLE.columns?size - 1)>, </#if></#list>
              FROM ${DBTABLE.tableName} t
        ]]>
	</sql>

	<!-- where -->
	<sql id="where">
		<where>
		    <#list DBTABLE.columns as column>
		    <if test="null != ${column.fieldName} and ${column.fieldName} != ''">
				AND t.${column.columnName} = ${"#{"?xml}${column.fieldName}${"}"?xml}
			</if>
		    </#list>
		</where>
	</sql>
	
	<!-- orderBy -->
	<sql id="orderBy">
	    <![CDATA[]]>
	</sql>
	
	<!-- limit -->
	<sql id="limit">
	    <if test="null != pagination">
			limit ${"#{"?xml}pagination.offset${"}"?xml}, ${"#{"?xml}pagination.pagesize${"}"?xml}
		</if>
	</sql>
	
	<!-- insert: 插入单条数据 -->
	<insert id="insert" parameterType="${PACKAGENAME}.${MODELNAME}">
	    <#list DBTABLE.primaryKeys as primaryKey>
		<#if primaryKey.autoIncrement>
		<selectKey keyProperty="${primaryKey.columnName}" resultType="${primaryKey.javaType}" order="AFTER">
		    <![CDATA[ SELECT LAST_INSERT_ID() ]]>
		</selectKey>
		</#if>
		</#list>
		<![CDATA[
		    INSERT INTO ${DBTABLE.tableName}(<#list DBTABLE.columns as column><#if !column.primaryKey || (column.primaryKey && !column.autoIncrement)>${column.columnName}<#if column_index != (DBTABLE.columns?size - 1)>, </#if></#if></#list>)
		        VALUES(<#list DBTABLE.columns as column><#if !column.primaryKey || (column.primaryKey && !column.autoIncrement)>${"#{"?xml}${column.fieldName}${"}"?xml}<#if  column_index != (DBTABLE.columns?size - 1)>, </#if></#if></#list>)
		]]>
	</insert>
	
	<!-- batchInsert: 批量插入数据 -->
	<insert id="batchInsert" parameterType="java.util.List">
		<![CDATA[
		    INSERT INTO ${DBTABLE.tableName}(<#list DBTABLE.columns as column>${column.columnName}<#if column_index != (DBTABLE.columns?size - 1)>, </#if></#list>)
		        VALUES
		]]>
		<foreach collection="list" item="item" open="(" close=")" separator=", ">
		    <#list DBTABLE.columns as column>
			${"#{"?xml}${column.fieldName}${"}"?xml}<#if  column_index != (DBTABLE.columns?size - 1)>, </#if>
			</#list>
		</foreach>
	</insert>

	<!-- update: 更新单条数据 -->
	<update id="update" parameterType="${PACKAGENAME}.${MODELNAME}">
        <![CDATA[
            UPDATE ${DBTABLE.tableName} t
        ]]>
		<set>
		    <#list DBTABLE.columns as column>
			<#if !column.primaryKey || (column.primaryKey && !column.autoIncrement)>
		    <if test="null != ${column.fieldName} and ${column.fieldName} != ''">
				t.${column.columnName} = ${"#{"?xml}${column.fieldName}${"}"?xml}
			</if>
			</#if>
		    </#list>
		</set>
		<include refid="where"></include>
	</update>
	
	<!-- batchUpdate: 批量更新数据 -->
	<update id="batchUpdate" parameterType="java.util.List">
		<![CDATA[
            UPDATE ${DBTABLE.tableName} t
        ]]>
		<trim prefix="set" suffixOverrides=",">
            <#list DBTABLE.columns as column>
			<#if !column.primaryKey>
			<trim prefix="${column.columnName} = case <#list DBTABLE.primaryKeys as primaryKey>${primaryKey.columnName}</#list>" suffix="end,">
                <foreach collection="list" item="item">
                    <if test="null != ${column.fieldName} and ${column.fieldName} != ''">
                        when ${"#{"?xml}item.<#list DBTABLE.primaryKeys as primaryKey>${primaryKey.fieldName}</#list>${"}"?xml} then ${"#{"?xml}${column.fieldName}${"}"?xml}
                    </if>
                </foreach>
            </trim>
			</#if>
		    </#list>
        </trim>
		<![CDATA[
            WHERE <#list DBTABLE.primaryKeys as primaryKey>${primaryKey.columnName}</#list> IN 
        ]]>
        <foreach collection="list" separator=", " item="item" open="(" close=")">
            ${"#{"?xml}item.<#list DBTABLE.primaryKeys as primaryKey>${primaryKey.fieldName}</#list>${"}"?xml}
        </foreach>
	</update>

	<!-- delete: 删除单条数据 -->
	<delete id="delete" parameterType="${PACKAGENAME}.${MODELNAME}">
	    <![CDATA[
            DELETE FROM ${DBTABLE.tableName} t
        ]]>
		<include refid="where"></include>
	</delete>
	
	<!-- queryAll: 查询所有数据 -->
	<select id="queryAll" resultMap="${MODELNAME}-resultMap">
		<include refid="select"></include>
		<include refid="orderBy"></include>
	</select>

	<!-- queryById: 根据id查询单条数据 -->
	<select id="queryById" resultMap="${MODELNAME}-resultMap">
		<include refid="select"></include>
		<include refid="where"></include>
	</select>

	<!-- queryByCriteria: 根据条件查询数据 -->
	<select id="queryByCriteria" resultMap="${MODELNAME}-resultMap"
		parameterType="Map">
		<include refid="select"></include>
		<include refid="where"></include>
		<include refid="orderBy"></include>
	</select>
	
	<!-- queryDataCount: 根据条件查询数据条数 -->
	<select id="queryDataCount" resultMap="int" parameterType="Map">
		<![CDATA[
            SELECT COUNT(1)
              FROM ${DBTABLE.tableName} t
        ]]>
		<include refid="where"></include>
	</select>
	
	<!-- queryByPagination: 根据分页条件查询数据 -->
	<select id="queryByPagination" resultMap="${MODELNAME}-resultMap"
		parameterType="Map">
		<include refid="select"></include>
		<include refid="where"></include>
		<include refid="orderBy"></include>
		<include refid="limit"></include>
	</select>
	
</mapper>
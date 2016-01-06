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
            SELECT <#list DBTABLE.columns as column><#if column_index != 0>, </#if>t.${column.columnName}</#list>
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
	
	<!-- insert: 插入单条数据 -->
	<insert id="insert" parameterType="${PACKAGENAME}.${MODELNAME}">
		<![CDATA[
		    INSERT INTO ${DBTABLE.tableName}(<#list DBTABLE.columns as column><#if column_index != 0>, </#if>${column.columnName}</#list>)
		        VALUES(<#list DBTABLE.columns as column><#if column_index != 0>, </#if>${"#{"?xml}${column.fieldName}${"}"?xml}</#list>)
		]]>
	</insert>

	<!-- update: 更新单条数据 -->
	<update id="update" parameterType="${PACKAGENAME}.${MODELNAME}">
        <![CDATA[
            UPDATE ${DBTABLE.tableName} t
        ]]>
		<set>
		    <#list DBTABLE.columns as column>
		    <if test="null != ${column.fieldName} and ${column.fieldName} != ''">
				t.${column.columnName} = ${"#{"?xml}${column.fieldName}${"}"?xml}
			</if>
		    </#list>
		</set>
		<![CDATA[
            WHERE id = 
        ]]>
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
	</select>
	
</mapper>
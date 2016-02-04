<#include "common/copyright.ftl">
package ${PACKAGENAME};

import org.anttribe.opengadget.core.domain.MybatisAbstractEntity;

/**
 * ${DBTABLE.tableComment}
 * 
 * @author ${COPYRIGHT.author}
 * @version ${COPYRIGHT.version}
 */
public class ${MODELNAME} extends MybatisAbstractEntity
{

	<#list DBTABLE.columns as column>
	/**
	 * ${column.columnComment}
	 */
	private ${column.javaType.typeName} ${column.fieldName?uncap_first};
	
	</#list>
	/**
	 * <默认构造器>
	 */
	public ${MODELNAME}() 
	{
	}
	
	/**
	 * 将当前对象转换为参数列表
	 */
	public Map<String, Object> toCriteria()
	{
	    Map<String, Object> criteria = new HashMap<String, Object>();
		<#list DBTABLE.columns as column>
		criteria.put("${column.fieldName?uncap_first}", this.get${column.fieldName?cap_first});
		</#list>
		return criteria;
	}
	
	@Override
	public String toString()
	{
		StringBuilder strB = new StringBuilder();
		strB.append("${MODELNAME}").append("{")
		<#list DBTABLE.columns as column>
		    .append("${column.fieldName?uncap_first}=").append(this.get${column.fieldName?cap_first}())
		    <#if column_index != (DBTABLE.columns?size - 1)>
		    .append(',')
		    </#if>
		</#list>
		    .append("}");
		return strB.toString();
	}
	
	<#list DBTABLE.columns as column>
	public ${column.javaType.typeName} get${column.fieldName?cap_first}()
	{
		return this.${column.fieldName?uncap_first};
	}
	
	public void set${column.fieldName?cap_first}(${column.javaType.typeName} ${column.fieldName?uncap_first})
	{
		this.${column.fieldName?uncap_first} = ${column.fieldName?uncap_first};
	}
	
	</#list>
}
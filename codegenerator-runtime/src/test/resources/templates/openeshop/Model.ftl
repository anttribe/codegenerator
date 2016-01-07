<#include "common/copyright.ftl">
package ${PACKAGENAME};

import org.anttribe.opengadget.core.domain.MybatisAbstractEntity;

/**
 *
 * @author ${COPYRIGHT.author}
 * @version ${COPYRIGHT.version}
 */
public class ${CLASSNAME} extends MybatisAbstractEntity
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
	public ${CLASSNAME}() 
	{
	}
	
	@Override
	public String toString()
	{
		StringBuilder strB = new StringBuilder();
		strB.append("${CLASSNAME}").append("{")
		<#list DBTABLE.columns as column>
		<#if column_index != 0>
		    .append(',')
		</#if>
		    .append("${column.fieldName?uncap_first}=").append(this.get${column.fieldName?cap_first}())
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
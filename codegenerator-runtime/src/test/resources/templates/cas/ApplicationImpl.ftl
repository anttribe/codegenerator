<#include "common/copyright.ftl">
package ${PACKAGENAME};

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ${COPYRIGHT.author}
 * @version ${COPYRIGHT.version}
 */
public class ${CLASSNAME} implements ${CLASSNAME?replace("Impl", "")}
{

    private static Logger logger = LoggerFactory.getLogger(${CLASSNAME}.class);
	
    @Override
    public List<${MODELNAME}> list${MODELNAME}s(Map<String, Object> criteria)
	{
	    logger.debug("listing ${MODELNAME}s refer to criteria, param: [{}]", criteria);
		
		if(null == criteria)
		{
		    criteria = new HashMap<String, Object>();
		}
        return ${MODELNAME}.find(${MODELNAME}.class, criteria);
	}
	
	@Override
    public Pagination list${MODELNAME}s(Map<String, Object> criteria, Pagination pagination)
	{
	    logger.debug("listing ${MODELNAME}s refer to criteria and pagination, param: criteria[{}], pagination[{}]", criteria, pagination);
		
		if(null == criteria)
		{
		    criteria = new HashMap<String, Object>();
		}
		List<${MODELNAME}> temp${MODELNAME}s = ${MODELNAME}.find(${MODELNAME}.class, criteria, pagination);
		int totalCount = ${MODELNAME}.count(entityClass, criteria);
		if(null == pagination)
		{
		    pagination = new Pagination();
		}
        pagination.setTotalRecords(totalCount);
        pagination.setDatas(temp${MODELNAME}s);
		
        return pagination;
	}
	
	@Override
    public ${MODELNAME} find${MODELNAME}(Map<String, Object> criteria)
	{
	    logger.debug("find ${MODELNAME} refer to criteria, param: criteria[{}]", criteria);
		
		if(null == criteria)
		{
		    // 参数错误 
		    return null;
		}
		
		List<${MODELNAME}> temp${MODELNAME}s = ${MODELNAME}.find(${MODELNAME}.class, criteria);
		if(!CollectionUtils.isEmpty(temp${MODELNAME}s))
		{
		    return temp${MODELNAME}s.get(0);
		}
        return null;
	}
	
	@Override
    public void persistent${MODELNAME}(${MODELNAME} ${MODELNAME?uncap_first})
	{
	    logger.debug("persistenting ${MODELNAME} to DB, param: ${MODELNAME?uncap_first}[{}]", ${MODELNAME?uncap_first});
        
		// TODO: 参数校验
        
		Map<String, Object> criteria = new HashMap<String, Object>();
		<#list DBTABLE.primaryKeys as column>
		criteria.put("${column.fieldName?uncap_first}", this.get${column.fieldName?cap_first}());
		</#list>
        List<${MODELNAME}> temp${MODELNAME}s = ${MODELNAME}.find(${MODELNAME}.class, criteria);
        if (CollectionUtils.isEmpty(temp${MODELNAME}s))
        {
            ${MODELNAME?uncap_first}.save();
            logger.debug("${MODELNAME?uncap_first} not exist in DB, then save new ${MODELNAME?uncap_first} to DB, ${MODELNAME?uncap_first}: {}", ${MODELNAME?uncap_first});
            return;
        }
        ${MODELNAME?uncap_first}.update();
        logger.debug("${MODELNAME?uncap_first} exist in DB, then update ${MODELNAME?uncap_first} info, ${MODELNAME?uncap_first}: {}", ${MODELNAME?uncap_first});
	}
    
    /**
     * 删除数据
     * 
     * @param ${MODELNAME?uncap_first} ${MODELNAME}
     */
    public void delete${MODELNAME}(${MODELNAME} ${MODELNAME?uncap_first})
	{
	    logger.debug("deleting ${MODELNAME?uncap_first} from DB, param: ${MODELNAME?uncap_first}[{}]", ${MODELNAME?uncap_first});
        
        if (null != ${MODELNAME?uncap_first})
        {
            ${MODELNAME?uncap_first}.remove();
        }
	}

}
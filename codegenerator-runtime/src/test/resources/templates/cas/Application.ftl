<#include "common/copyright.ftl">
package ${PACKAGENAME};

import java.util.*;

/**
 * ${MODELNAME}逻辑层处理接口
 * 
 * @author ${COPYRIGHT.author}
 * @version ${COPYRIGHT.version}
 */
public interface ${CLASSNAME}
{

    List<${MODELNAME}> list${MODELNAME}s(Map<String, Object> criteria);
	
	/**
     * 持久化数据
     * 
     * @param ${MODELNAME?uncap_first} ${MODELNAME}
     */
    void persistent${MODELNAME}(${MODELNAME} ${MODELNAME?uncap_first});
    
    /**
     * 删除数据
     * 
     * @param ${MODELNAME?uncap_first} ${MODELNAME}
     */
    void delete${MODELNAME}(${MODELNAME} ${MODELNAME?uncap_first});

}
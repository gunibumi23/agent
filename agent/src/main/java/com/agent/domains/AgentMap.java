package com.agent.domains;

import java.util.HashMap;

import org.apache.ibatis.type.Alias;
import org.springframework.jdbc.support.JdbcUtils;

import com.agent.common.util.CommonUtil;


@Alias("agentMap")
public class AgentMap extends HashMap<String, Object>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * MyBatis resultType에 사용  CamelCase 로 변환 해준다.
	 */
	public Object put(String key , Object value){
		if(!CommonUtil.isCamelCase(key)){
			key = JdbcUtils.convertUnderscoreNameToPropertyName(key);
		}
		return super.put(key, value);
	}
}


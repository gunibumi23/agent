package com.agent.domains.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	/**
	 * 사용자 리스트
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> selectUserList(Map<String,Object> params);
	
	/**
	 * 사용자 정보
	 * @param params
	 * @return
	 */
	Map<String,Object> selectUserInfo(Map<String,Object> params);
}

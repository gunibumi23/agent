package com.agent.domains.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agent.common.util.ObjectUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"users"})
public class UserService implements UserDetailsService {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final CacheManager cacheManager;
	
	private final UserMapper userMapper;
	/**
	 * 사용자 인증
	 */
	@Override
	public UserDetails loadUserByUsername(String userCd) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Map<String,Object> user = this.selectUserInfo(userCd);
		UserDetailData userDetails = null;
		if(user != null) {
			userDetails = ObjectUtil.objectToClass(user, UserDetailData.class);
			log.info("userDetails : {}",userDetails);
		}else {
			throw new UsernameNotFoundException("NO_USER_EXIST");
		}
		return userDetails;
	}
	
	/**
	 * 사용자 리스트
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> selectUserList(Map<String,Object> params){		
		return userMapper.selectUserList(params);
	}
	
	/**
	 * 사용자 정보
	 * @param userCd
	 * @return
	 */
	@Cacheable(key = "#userCd")
	public Map<String,Object> selectUserInfo(String userCd){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userCd", userCd);
		return selectUserInfo(params);
	}
	
	/**
	 * 사용자 ㅓㅈㅇ보
	 * @param params
	 * @return
	 */
	@Cacheable(key = "#params.userCd")
	public Map<String,Object> selectUserInfo(Map<String,Object> params){
		return userMapper.selectUserInfo(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@CachePut(key = "#params.userCd")
	public int mergeUserInfo(Map<String,Object> params){
		return userMapper.mergeUserInfo(params);
	}
}

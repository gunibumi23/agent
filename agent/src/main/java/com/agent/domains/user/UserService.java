package com.agent.domains.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class UserService implements UserDetailsService {
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String userCd) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Map<String,Object> user = this.selectUserInfo(userCd);
		UserDetailData userDetails = null;
		if(user != null) {
			userDetails = ObjectUtil.objectToClass(user, UserDetailData.class);
			log.info("userDetails : {}",userDetails);
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
	public Map<String,Object> selectUserInfo(String userCd){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("userCd", userCd);
		return selectUserInfo(params);
	}
	
	public Map<String,Object> selectUserInfo(Map<String,Object> params){
		return userMapper.selectUserInfo(params);
	}
}

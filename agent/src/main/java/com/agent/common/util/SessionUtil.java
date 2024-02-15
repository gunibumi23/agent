package com.agent.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.agent.domains.user.UserDetailData;

public class SessionUtil {
	
	/**
	 * 로그인 세션 사용자
	 * @return
	 */
    public static UserDetailData getUserInfo() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	UserDetailData user = (UserDetailData) authentication.getPrincipal();    	    	
    	return user;    	
    }

}

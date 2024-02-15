package com.agent.common.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

	
	public static boolean isAjax(HttpServletRequest req) {
		String xHeader = req.getHeader("x-requested-with");
		if("XMLHttpRequest".equalsIgnoreCase(xHeader)){
			return true;
		}
		return false;
	}
}

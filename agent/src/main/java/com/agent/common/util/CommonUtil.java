package com.agent.common.util;

public class CommonUtil {

	 /**
	  * null 치환
	  * @param val
	  * @return
	  */
	 public static String nvl(Object val){
		 return CommonUtil.nvl(val,"");
	 }

	 /**
	  * null 치환
	  * @param val
	  * @param repVal
	  * @return
	  */
	 public static String nvl(Object val,String repVal){
		String result = "";
		if(val != null)
		{
			result = String.valueOf(val);
			if(result.length() == 0)
			{
				result = repVal;
			}
		}
		else
		{
			result = repVal;
		}
	    return result;
	 }

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isCamelCase(String value){
		boolean result = true;
		if(value != null){
			String camelCasePattern = "([a-z]+[A-Z]+\\w+)+"; // 3rd edit, getting better
			result = value.matches(camelCasePattern);
		}
		return result;
	}
}
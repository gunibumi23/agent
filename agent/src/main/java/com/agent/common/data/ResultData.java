package com.agent.common.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.agent.common.enums.ApiResultEnum;
import com.agent.common.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;


@Data
@JsonInclude(Include.NON_NULL)
public class ResultData {
	
	private ApiResultEnum code;
	
	private String message;
	
	private Map<String,?> data;
	
	private List<?> list;	
	
	private Map<String,Object> page;
	
	public ResultData(ApiResultEnum code) {
		this.code = code;
	}
	
	public ResultData(ApiResultEnum code,String message) {
		this.code = code;
		this.message = message;
	}
	
	public ResultData(ApiResultEnum code,Map<String, ?> data) {
		this.code = code;
		this.data = data;
	}
	
	public ResultData(ApiResultEnum code,List<?> list) {
		this.code = code;
		this.list = list;
	}
	
	public ResultData(ApiResultEnum code,List<?> list,Map<String,Object> page) {
		this.code = code;
		this.list = list;
		this.page = setPageData(page);
	}
	
	protected Map<String,Object> setPageData(Map<String,Object> params) {
		Map<String,Object> page = new HashMap<String,Object>();
		page.put("currentPage"	,Integer.parseInt(CommonUtil.nvl(params.get("currentPage"),"0")));
		page.put("pageSize"		,Integer.parseInt(CommonUtil.nvl(params.get("pageSize"),"0")));
		page.put("totalElements",Integer.parseInt(CommonUtil.nvl(params.get("totalElements"),"0")));
		page.put("totalPages"	,Integer.parseInt(CommonUtil.nvl(params.get("totalPages"),"0")));
		return page;
	}
}

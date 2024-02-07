package com.agent.domains.errors;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.agent.common.data.ResultData;
import com.agent.common.enums.ApiResultEnum;
import com.agent.common.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/error")
@Slf4j
public class ErrorsController extends AbstractErrorController {

	
	public ErrorsController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
	}
	
	/**
	 * view 에러핸들러
	 * @param req
	 * @param res
	 * @param model
	 * @return
	 */
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String html(HttpServletRequest req, HttpServletResponse res,Model model) {   	
    	ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
    	Map<String, Object>  error = getErrorAttributes(req, options);
    	model.addAllAttributes(error);
		return "/errors/error";
    }
    
    /**
     * ajax 에러 핸들러
     * @param req
     * @param res
     * @return
     */
    @GetMapping
    public ResponseEntity<Object> json(HttpServletRequest req,HttpServletResponse res) {
    	ErrorAttributeOptions options = ErrorAttributeOptions.defaults();
    	Map<String, Object>  error = getErrorAttributes(req, options);
		/*
		 * try { log.info("error : {}",ObjectUtil.toJson(error)); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
        return ResponseEntity.status(getStatus(req)).body(new ResultData(ApiResultEnum.ERROR,CommonUtil.nvl(error.get("error"))));
    }
    
}

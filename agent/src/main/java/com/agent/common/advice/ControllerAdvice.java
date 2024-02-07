package com.agent.common.advice;

import java.util.Iterator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.agent.common.data.ResultData;
import com.agent.common.enums.ApiResultEnum;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ControllerAdvice {
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData> processValidationError(MethodArgumentNotValidException e) {		
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        ResultData errorEntity = new ResultData(ApiResultEnum.ERROR,fieldError.getDefaultMessage());
        return ResponseEntity.badRequest().body(errorEntity);           
    }

	@ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ResultData> noResourceFoundException(NoResourceFoundException e) {	
        return ResponseEntity.notFound().build();           
    }
	
    /**
     * 
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResponseEntity<ResultData> exHandler(Exception e) {
    	log.error("ERROR",e);
        ResultData errorEntity = new ResultData(ApiResultEnum.ERROR,e.getMessage());
        return new ResponseEntity<ResultData>(errorEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

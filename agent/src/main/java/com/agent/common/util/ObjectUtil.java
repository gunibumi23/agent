package com.agent.common.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectUtil {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
	 /**
	  * obj to map
	  * @param <T>
	  * @param obj
	  * @param type
	  * @return
	  */
	 public static <T> T objectToClass(Object obj,Class<T> type) {
		 return objectMapper.convertValue(obj, type);
	 }
	 
	 /**
	  * map to object
	  * @param <T>
	  * @param map
	  * @param obj
	  * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	  */
	 public static <T> T mapToObject(Map<String,Object> map,Object obj) throws IllegalAccessException, InvocationTargetException {
		 BeanUtils.populate(obj, map);
		 return (T) obj;
	 }
	 
	 /**
	  * 
	  * @param object
	  * @return
	  * @throws JsonProcessingException
	  */
	 public static String toJson(Object object) throws JsonProcessingException {
		 return objectMapper.writeValueAsString(object);
    }

	 /**
	  * 
	  * @param <T>
	  * @param jsonStr
	  * @param cls
	  * @return
	  * @throws JsonMappingException
	  * @throws JsonProcessingException
	  */
	 public static <T> T fromJson(String jsonStr, Class<T> cls) throws JsonMappingException, JsonProcessingException {
    	return objectMapper.readValue(jsonStr, cls);
	 }

	 /**
	  * 
	  * @param object
	  * @return
	  * @throws JsonProcessingException
	  */
	 public static String toListJson(Object object) throws JsonProcessingException {
    	return objectMapper.writeValueAsString(object);
	 }

	 /**
	  * 
	  * @param jsonStr
	  * @return
	  * @throws JsonMappingException
	  * @throws JsonProcessingException
	  */
	 public static Map<String, Object> fromJsonToMap(Object obj) throws IOException {
    	return fromJson(obj, new TypeReference<HashMap<String, Object>>() {});
	 }
	 

	 /**
	  * 
	  * @param jsonStr
	  * @return
	  * @throws JsonMappingException
	  * @throws JsonProcessingException
	  */
	 public static List<Map<String, Object>> fromJsonToList(Object obj) throws IOException {
    	return fromJson(obj, new TypeReference<List<Map<String, Object>>>() {});
	 }

	 /**
	  * 
	  * @param <T>
	  * @param jsonStr
	  * @param typeReference
	  * @return
	  * @throws JsonMappingException
	  * @throws JsonProcessingException
	  */
	 public static <T> T fromJson(Object obj, TypeReference<T> typeReference) throws IOException {
		if (obj instanceof File) {
			return objectMapper.readValue((File) obj, typeReference);
		} 
		return objectMapper.readValue(String.valueOf(obj), typeReference);
	 }
	 
	 /**
	  * 
	  * @param req
	  * @return
	  */
	 public static Map<String, Object> getParameterMap(HttpServletRequest req){
		 Map<String,Object> params = new HashMap<>();
		 Map<String,String[]> reqParams = req.getParameterMap();
		 Iterator<String> iter = reqParams.keySet().iterator();
		 while(iter.hasNext()) {
			 String key = iter.next();
			 String[] values = reqParams.get(key);
			 Object val = new Object();
			 if(values != null) {
				if(values.length > 1) {
					val = Arrays.asList(values);
				}else {
					val = values[0];
				}
			 }
			 params.put(key, val);
		 }
		 return params;
	 }
	 
	 public static String toPrettyJson(Object object) {
        try {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

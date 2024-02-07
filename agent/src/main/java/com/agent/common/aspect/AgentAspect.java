package com.agent.common.aspect;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.agent.common.util.ObjectUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class AgentAspect {

	/**
	 * 컨트롤러 관련 로그 및 파라미터 핸들링 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "execution(* com.agent.domains..*Controller.*(..))")
	public Object object(ProceedingJoinPoint pjp) throws Throwable {	
				
		String reqId = UUID.randomUUID().toString();
		
		MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
		Method method = methodSignature.getMethod();
		
		Object[] objs = pjp.getArgs();		
		
		
		if(objs.length > 0) {
			int idx = 0;
			for(Object obj : objs) {
				if (obj instanceof HashMap) {
					Map<String, Object> map = (Map<String, Object>) ObjectUtil.objectToClass(obj, Map.class);					
					if(map.containsKey("_")) {
						map.remove("_");
						objs[idx] = map;
					}
				}
				idx++;
			}
		}
		long startAt = System.currentTimeMillis();
		log.info("- REQUEST  : {} => {}.{} = {} ", reqId, method.getDeclaringClass().getSimpleName(),pjp.getSignature().getName(), objs);
		Object result = pjp.proceed(objs);
		long endAt = System.currentTimeMillis();
		log.info("- RESPONSE : {} => {}.{} = {} ({}ms)", reqId, method.getDeclaringClass().getSimpleName(),pjp.getSignature().getName(), "SUCCESS", endAt - startAt);
		return result;
	}
}

package com.agent.common.interceptor;

import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.agent.common.util.CommonUtil;
import com.agent.common.util.ObjectUtil;
import com.agent.common.util.SessionUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 공통 파라메터 정의
 * @author kgb
 *
 */
@Intercepts({ 
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query" , args = { MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class })})
@Slf4j
@Component
public class ParamInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invoc) throws Throwable {
    	
    	String methodName = invoc.getMethod().getName();        
        Object params = invoc.getArgs()[1];
        
        if (params != null) {
            if (params instanceof Map) {
                Map<String, Object> newParams = ObjectUtil.objectToClass(params, Map.class);
                if ("query".equalsIgnoreCase(methodName)) {
                    // 페이징 관련
                    if (newParams.containsKey("pageSize") && newParams.containsKey("pageNumber")) {
                        int pageSize = Integer.parseInt(CommonUtil.nvl(newParams.get("pageSize"), "0"));
                        int pageNumber = Integer.parseInt(CommonUtil.nvl(newParams.get("pageNumber"), "0"));
                        newParams.put("pageStart", pageSize * pageNumber);
                    }
                }else {
	                if (!newParams.containsKey("loginUserCd")) {
	                	newParams.put("loginUserCd", SessionUtil.getUserInfo().getUserCd());
	                }
                }
                invoc.getArgs()[1] = newParams;
            }
        }
        return invoc.proceed();
    }

    @Override
    public Object plugin(Object paramObject) {
        // TODO Auto-generated method stub
        return Plugin.wrap(paramObject, this);
    }

    @Override
    public void setProperties(Properties paramProperties) {
        // TODO Auto-generated method stub
    }
}


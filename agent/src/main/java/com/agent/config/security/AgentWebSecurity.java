package com.agent.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.agent.common.data.ResultData;
import com.agent.common.enums.ApiResultEnum;
import com.agent.common.enums.LoginFailureEnum;
import com.agent.common.enums.ResCodeEnum;
import com.agent.common.util.ObjectUtil;
import com.agent.common.util.RequestUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableCaching
@Slf4j
@RequiredArgsConstructor
public class AgentWebSecurity {
	
	private final String LOGIN_OUT   = "/logout";
	private final String LOGIN_FORM  = "/login/form";
	private final String LOGIN_CHECK = "/login/check";
	private final String[] SKIP_URL = new String[]{"/vendor/**"
												  ,"/css/**"
												  ,"/js/**"					
												  ,"/errors/**"
												  ,"/login/check"};
	

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	
    	
        http
        		.sessionManagement(session -> session
        			.maximumSessions(1)
        			.maxSessionsPreventsLogin(false)        			
        		)
        		.csrf(AbstractHttpConfigurer::disable)       		
				/*
				 * .sessionManagement((sessionManagement) ->
				 * sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
				 */
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(SKIP_URL).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage(LOGIN_FORM)
                        .usernameParameter("userCd")
                        .passwordParameter("userPw")
                        .loginProcessingUrl(LOGIN_CHECK)          
                        .successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                		.logoutUrl(LOGIN_OUT)
                		.logoutSuccessHandler(new LogoutSuccesHandler())
                		.permitAll()
                )
                .exceptionHandling(
                		exception -> exception.authenticationEntryPoint(new LoginAuthenticationEntryPoint(LOGIN_FORM))
                ) 
                .rememberMe(Customizer.withDefaults());

        return http.build();
    }
    
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
    	return new BCryptPasswordEncoder(11); 
    }
    
    /**
     * 로그인 성공 
     */
    class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    	
		@Override
		public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,Authentication auth) throws IOException, ServletException {
			log.info(auth.toString());
			Map<String,Object> rsltMap = new HashMap<String,Object>();
			rsltMap.put("code"    , ResCodeEnum.SUCCESS);		
			rsltMap.put("redirect", "/login/success");				
            res.getOutputStream().println(ObjectUtil.toJson(rsltMap));
		}
    }
    
    /**
     * 로그인 실패
     */
    class LoginFailureHandler implements AuthenticationFailureHandler {
		@Override
		public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res,
				AuthenticationException excp) throws IOException, ServletException {
			excp.printStackTrace();
			Map<String,Object> rsltMap = new HashMap<String,Object>();
			String excpNm = excp.getClass().getSimpleName();
			String excpCd = LoginFailureEnum.valueOf(excpNm).getValue();			
			rsltMap.put("code", excpCd);			
            res.getOutputStream().println(ObjectUtil.toJson(rsltMap));
		}
    }
    
    /**
     * 로그아웃 성공
     */
    class LogoutSuccesHandler implements LogoutSuccessHandler {

		@Override
		public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse res,
				Authentication auth) throws IOException, ServletException {			
			if (auth != null && auth.isAuthenticated()) {
				if(req != null) {
					req.getSession().invalidate();
				}
	        } 
	        res.sendRedirect("/login/form");
		}
    }
    
    class LoginAuthenticationEntryPoint implements AuthenticationEntryPoint {
    	
    	private String fwUrl;
    	
    	LoginAuthenticationEntryPoint(String fwUrl) {
    		this.fwUrl = fwUrl;
    	}

		@Override
		public void commence(HttpServletRequest req, HttpServletResponse res,AuthenticationException excp) throws IOException, ServletException {			
			if(RequestUtil.isAjax(req)) {
				ResultData rslt = new ResultData(ApiResultEnum.ERROR,"SESSION_EXPIRED");
				res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		        res.setStatus(HttpStatus.UNAUTHORIZED.value());
		        res.setCharacterEncoding("UTF-8");
		        res.getWriter().write(ObjectUtil.toJson(ObjectUtil.toJson(rslt)));
			}else {
				RequestDispatcher dispt = req.getRequestDispatcher(fwUrl);
				dispt.forward(req, res);
			}
		}
    }
}

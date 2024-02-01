package com.agent.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;

import com.agent.common.enums.LoginFailureEnum;
import com.agent.common.enums.ResCodeEnum;
import com.agent.common.util.ObjectUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class AgentWebSecurity {
	
	private final String[] SKIP_URL = new String[]{"/vendor/**"
												  ,"/css/**"
												  ,"/js/**"												  
												  ,"/login/check"};

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
        http
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
                        .loginPage("/login/form")
                        .usernameParameter("userCd")
                        .passwordParameter("userPw")
                        .loginProcessingUrl("/login/check")          
                        .successHandler(new LoginSuccessHandler())
                        .failureHandler(new LoginFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                		.logoutUrl("/logout")
                		.logoutSuccessHandler(new LogoutSuccesHandler())
                		.permitAll()
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
}

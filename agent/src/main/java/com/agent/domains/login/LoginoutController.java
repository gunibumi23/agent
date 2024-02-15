package com.agent.domains.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
@RequestMapping("/login")
public class LoginoutController {
	
	@GetMapping("/form")
	public String login(Model model,HttpServletRequest req) {
		log.info("{}",req.getHeaders("x-requested-with"));
		return "login";
	}
	
	@GetMapping("/{rslt}")
	public String loginHandle(@PathVariable String rslt,Model model) {		
		if("expired".equalsIgnoreCase(rslt)) {			
			return "/erros/error";
		}else {
			return "/domains/tables";
		} 
	}
}

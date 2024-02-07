package com.agent.domains.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class LoginoutController {
	
	@GetMapping("/login/form")
	public String login(Model model,HttpServletRequest req) {
		log.info("{}",req.getHeaders("x-requested-with"));
		return "login";
	}
	
	@GetMapping("/login/{rslt}")
	public ModelAndView loginHandle(@PathVariable String rslt,Model model,HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		if("expired".equalsIgnoreCase(rslt)) {			
			mv.setViewName("/login");
		}else {
			mv.setViewName("/domains/tables");
		}
		return mv; 
	}
}

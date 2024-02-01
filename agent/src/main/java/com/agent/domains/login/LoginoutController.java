package com.agent.domains.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class LoginoutController {
	
	@GetMapping("/login/form")
	public String login(Model model) {		
		return "login";
	}
	
	@GetMapping("/login/{rslt}")
	public String loginHandle(@PathVariable String rslt,Model model) {
		log.info("{} ===============================> ",rslt);
		return "/domains/tables";
	}
}

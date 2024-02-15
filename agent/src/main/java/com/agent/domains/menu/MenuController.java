package com.agent.domains.menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
	
	private final MenuService menuService;
	
	
	@GetMapping("/form")
	public String form(Model mode) {
		
		return "/menuForm";
	}
}

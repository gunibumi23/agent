package com.agent.domains.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	
	@GetMapping("/form")
	public String form(@RequestParam Map<String,Object> params) {
		return "/users/userForm";
	}
	
	@GetMapping("/list")
	public Map<String,Object> list(@RequestParam Map<String,Object> params) {
		Map<String,Object> res = new HashMap<String,Object>();
		List<Map<String,Object>> list = userService.selectUserList(params);
		res.put("list", list);
		return res;
	}
	
	
}

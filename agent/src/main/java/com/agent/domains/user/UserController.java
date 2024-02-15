package com.agent.domains.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agent.common.data.ResultData;
import com.agent.common.enums.ApiResultEnum;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	private int idx = 0;
	
	
	@GetMapping("/form")
	public String form(@RequestParam Map<String,Object> params,Model model) {
		return "/domains/users/userForm";
	}
	
	/**
	 * 사용자 리스트
	 * @param params
	 * @return
	 */
	@GetMapping("/list")	
	public ResponseEntity<ResultData> list(@RequestParam Map<String,Object> params) {		
		List<Map<String,Object>> list = userService.selectUserList(params);
		return ResponseEntity.ok(new ResultData(ApiResultEnum.SUCCESS,list));
	}
	
	/**
	 * 사용자 정보
	 * @param userCd
	 * @return
	 */
	@GetMapping("/{userCd}")
	public ResponseEntity<ResultData> info(@PathVariable String userCd) {		
		Map<String,Object> user = userService.selectUserInfo(userCd);
		return ResponseEntity.ok(new ResultData(ApiResultEnum.SUCCESS,user));
	}
	
	
	
	@PutMapping
	public ResponseEntity<ResultData> test() {
		Map<String,Object> user = null;
		int edIdx = idx + 10000;
		for(int i=idx;i<edIdx;i++) {
			user = new HashMap<String,Object>();
			user.put("userCd" , "testUser" + i);
			user.put("userNm" , "유저_" + i);
			user.put("userPw" , "$2a$11$3aacVZKjRadCAGpaUTSnk.A74qLrQIynhEk28qK6k4MyzK4z7b9Au_");			
			user.put("userSts", "NORMAL");
			userService.mergeUserInfo(user);
			idx = i;
		}
		return ResponseEntity.ok(new ResultData(ApiResultEnum.SUCCESS));
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping	
	public ResponseEntity modify(@RequestBody Map<String,Object> params) {
		return ResponseEntity.ok("");
	}
}

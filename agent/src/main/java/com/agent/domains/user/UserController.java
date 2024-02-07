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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.agent.common.data.ResultData;
import com.agent.common.enums.ApiResultEnum;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	
	@GetMapping("/form")
	public String form(@RequestParam Map<String,Object> params,Model model) {
		
		List<Map<String,Object>> authBtns = new ArrayList<Map<String,Object>>();
		Map<String,Object> btnMap = new HashMap<String,Object>();
		btnMap.put("btnCd"  ,"users.save");
		btnMap.put("btnNm"  ,"저장");
		btnMap.put("btnType","SAVE");
		authBtns.add(btnMap);
		
		btnMap = new HashMap<String,Object>();
		btnMap.put("btnCd"  ,"users.new");
		btnMap.put("btnNm"  ,"신규");
		btnMap.put("btnType","NEW");
		authBtns.add(btnMap);
		
		btnMap = new HashMap<String,Object>();
		btnMap.put("btnCd"  ,"users.init");
		btnMap.put("btnNm"  ,"취소");
		btnMap.put("btnType","INIT");
		authBtns.add(btnMap);
		
		btnMap = new HashMap<String,Object>();
		btnMap.put("btnCd"  ,"users.delete");
		btnMap.put("btnNm"  ,"삭제");
		btnMap.put("btnType","DEL");
		authBtns.add(btnMap);
		
		model.addAttribute("authBtns",authBtns);
		return "/users/userForm";
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
	
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	@PutMapping	
	public ResponseEntity modify(@RequestBody Map<String,Object> params) {
		return ResponseEntity.ok("");
	}
}

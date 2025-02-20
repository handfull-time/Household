package com.utime.household.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.UserReqVo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {
	
	final UserService userService;
	
	@GetMapping("Login.html")
    public String loginPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/Login";
    }
	
	@GetMapping("JoinUser.html")
    public String joinUserPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/JoinUser";
    }
	
	@ResponseBody
	@PostMapping("JoinUser.json")
    public ResponseEntity<?> login( HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserReqVo reqVo) {
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	
    	final ReturnBasic result = userService.joinUser(reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", result.getMessage()));
    	}
    	
    	return ResponseEntity.ok().body(result);
    }

}

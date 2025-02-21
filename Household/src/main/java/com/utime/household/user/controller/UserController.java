package com.utime.household.user.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.FindUserIdResVo;
import com.utime.household.user.vo.UserReqVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("User")
public class UserController {
	
	final UserService userService;
	
	/**
	 * 로그인 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("Login.html")
    public String loginPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/Login2";
    }
	
	/**
	 * 회원 가입 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("JoinUser.html")
    public String joinUserPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/JoinUser2";
    }
	
	/**
	 * 회원 가입
	 * @param request
	 * @param reqVo
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@PostMapping("JoinUser.json")
    public ResponseEntity<?> login( HttpServletRequest request, @ModelAttribute UserReqVo reqVo) throws IOException {
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	
    	final ReturnBasic result = userService.joinUser(reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", result.getMessage()));
    	}
    	
    	return ResponseEntity.ok().body(result);
    }
	
	/**
	 * id 중복인지 검사
	 * @param id
	 * @return
	 */
	@ResponseBody
	@GetMapping("CheckId.json")
    public ResponseEntity<ReturnBasic> checkId( @RequestParam("id") String id) {
    	
    	final ReturnBasic result = userService.checkId(id);
    	
    	return ResponseEntity.ok().body(result);
    }
	
	/**
	 * id 찾기 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("FindUserId.html")
    public String findUserIdPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/FindUserId";
    }
	
	@PostMapping("FindUserId.layer")
    public String findUserId( HttpServletRequest request, ModelMap model, UserReqVo reqVo ) {
		
		reqVo.setSessionId( request.getRequestedSessionId() );
		
		final FindUserIdResVo result = userService.findUserId(reqVo);
		
		model.addAttribute("item", result);
    	
    	return "User/FindUserIdLayer";
    }

	/**
	 * pw 찾기 화면
	 * @param request
	 * @param model
	 * @return
	 */
	@GetMapping("FindUserPw.html")
    public String findUserPwPage( HttpServletRequest request, ModelMap model ) {
		
		model.addAttribute("genToken", userService.getNewGenToken(request.getRequestedSessionId()) );
		
        return "User/FindUserPw";
    }
	
	/**
	 * 비번 찾기
	 * @param request
	 * @param reqVo
	 * @return
	 */
	@PostMapping("FindUserPw.layer")
    public String findUserPw( HttpServletRequest request, ModelMap model, UserReqVo reqVo ) {
		
		reqVo.setSessionId( request.getRequestedSessionId() );
		
		final ReturnBasic result = userService.findUserPw(reqVo);
		
		model.addAttribute("item", result);
    	
    	return "User/FindUserPwLayer";
    }

	/**
	 * 비번 변경
	 * @param request
	 * @param reqVo
	 * @return
	 */
	@ResponseBody
	@PostMapping("ConvertUserPw.json")
    public ResponseEntity<ReturnBasic> convertUserPw( HttpServletRequest request, UserReqVo reqVo ) {
		
		reqVo.setSessionId( request.getRequestedSessionId() );
		
		final ReturnBasic result = userService.convertUserPw(reqVo);
    	
    	return ResponseEntity.ok().body(result);
    }
}

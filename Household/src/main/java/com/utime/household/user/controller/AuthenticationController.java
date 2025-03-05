package com.utime.household.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.UserReqVo;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("Auth")
public class AuthenticationController {
	
	final UserService userService;
	
    @PostMapping("Login.json")
    public ResponseEntity<?> login( HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReqVo reqVo) throws Exception {
    	
    	reqVo.setSessionId( HouseholdUtils.getRemoteAddress( request ) );
    	
    	final ReturnBasic result = userService.procLogin(request, response, reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ""));
    	}
    	
    	String url;
    	final Object obj = request.getSession().getAttribute(HouseholdDefine.KeyBeforeUri);
    	if( obj != null ) {
    		request.getSession().removeAttribute(HouseholdDefine.KeyBeforeUri);
    		url = (String)obj;
    	}else {
    		url = request.getContextPath() + "/Home.html";
    	}
    	
    	result.setMessage(url);
    	
    	return ResponseEntity.ok().body(result);
    }
    
    @PostMapping("Refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response, 
    		@CookieValue(HouseholdDefine.KeyRefreshToken) String refreshToken) {
    	
    	final ReturnBasic result = userService.refreshAccessToken(request, response, refreshToken);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    	}

    	return ResponseEntity.ok().body(result);
    }

    
    /**
	 * 회원 가입
	 * @param request
	 * @param reqVo
	 * @return
     * @throws Exception 
	 */
	@PostMapping("JoinUser.json")
    public ResponseEntity<?> login( HttpServletRequest request, @ModelAttribute UserReqVo reqVo) throws Exception {
    	
    	reqVo.setSessionId( HouseholdUtils.getRemoteAddress( request ) );
    	
    	final ReturnBasic result = userService.joinUser(reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", result.getMessage()));
    	}
    	
    	return ResponseEntity.ok().body(result);
    }
    
	/**
	 * 비번 변경
	 * @param request
	 * @param reqVo
	 * @return
	 */
	@PostMapping("ConvertUserPw.json")
    public ResponseEntity<ReturnBasic> convertUserPw( HttpServletRequest request, UserReqVo reqVo )throws Exception {
		
		reqVo.setSessionId( HouseholdUtils.getRemoteAddress( request ) );
		
		final ReturnBasic result = userService.convertUserPw(reqVo);
    	
    	return ResponseEntity.ok().body(result);
    }
    
    /**
     * 로그아웃
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	@GetMapping("Logout.json")
    public ResponseEntity<?> userLogout( HttpServletRequest request, HttpServletResponse response )throws Exception {
		
		userService.logout(request, response);
    	
    	return ResponseEntity.ok().body(new ReturnBasic());
    }
}


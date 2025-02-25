package com.utime.household.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.common.jwt.JwtProvider;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.TokenPairVo;
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
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	
    	final TokenPairVo result = userService.procLogin(reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    	}
    	
    	{
    		// access token
        	final Cookie cookie = new Cookie("accessToken", result.getAccessToken());
        	cookie.setHttpOnly(true); // JavaScript에서 접근 불가능 (XSS 방지)
        	//cookie.setSecure(true);
        	cookie.setPath(request.getContextPath()); 
        	cookie.setMaxAge( (int)(JwtProvider.ACCESS_EXPIRATION_TIME / 1000L));
        	
        	response.addCookie(cookie);
    	}
    	
    	{
    		// refresh token
        	final Cookie cookie = new Cookie("refreshToken", result.getRefreshToken());
        	cookie.setHttpOnly(true); // JavaScript에서 접근 불가능 (XSS 방지)
        	//cookie.setSecure(true);
        	cookie.setPath(request.getContextPath() + "/Auth/Refresh");
        	cookie.setMaxAge( (int)(JwtProvider.REFRESH_EXPIRATION_TIME / 1000L));
        	
        	response.addCookie(cookie);
    	}
    	
    	String url;
    	final Object obj = request.getSession().getAttribute(HouseholdDefine.KeyBeforeUri);
    	if( obj != null ) {
    		request.getSession().removeAttribute(HouseholdDefine.KeyBeforeUri);
    		url = (String)obj;
    	}else {
    		url = "/Home.html";
    	}
    	
    	result.setMessage(url);
    	
    	return ResponseEntity.ok().body(result);
    }
    
    @PostMapping("Refresh")
    public ResponseEntity<?> refreshAccessToken(@CookieValue("refreshToken") String refreshToken) {
    	
    	final ReturnBasic result = userService.refreshAccessToken(refreshToken);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", result.getMessage()));
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
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	
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
		
		reqVo.setSessionId( request.getRequestedSessionId() );
		
		final ReturnBasic result = userService.convertUserPw(reqVo);
    	
    	return ResponseEntity.ok().body(result);
    }
    
    
    
}


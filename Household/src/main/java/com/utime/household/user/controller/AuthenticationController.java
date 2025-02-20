package com.utime.household.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.LoginReqVo;

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
    public ResponseEntity<?> login( HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReqVo reqVo) {
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	
    	final ReturnBasic result = userService.procLogin(reqVo);
    	
    	if( result.isError() ) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
    	}
    	
    	// JWT를 HttpOnly 쿠키로 설정
    	final Cookie cookie = new Cookie("token", result.getMessage());
    	cookie.setHttpOnly(true); // JavaScript에서 접근 불가능 (XSS 방지)
    	//cookie.setSecure(true); // HTTPS에서만 전송 (보안 강화)
    	cookie.setPath("/"); // 모든 경로에서 접근 가능
    	cookie.setMaxAge(60 * 60 * 24); // 1일 동안 유지
    	
    	response.addCookie(cookie); // 쿠키 추가
    	
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
}

//
//
//@PostMapping("/login")
//public ResponseEntity<?> login(@RequestBody @Valid Map<String, String> loginRequest, HttpServletResponse response) {
//    String username = loginRequest.get("id");
//    String password = loginRequest.get("pw");
//
//    Optional<User> userOpt = userRepository.findByUsername(username);
//    if (userOpt.isPresent() && passwordEncoder.matches(password, userOpt.get().getPassword())) {
//        String token = jwtUtil.generateToken(username, userOpt.get().getRole().name());
//
//        // JWT를 HttpOnly 쿠키로 설정
//        Cookie cookie = new Cookie("token", token);
//        cookie.setHttpOnly(true); // JavaScript에서 접근 불가능 (XSS 방지)
//        cookie.setSecure(true); // HTTPS에서만 전송 (보안 강화)
//        cookie.setPath("/"); // 모든 경로에서 접근 가능
//        cookie.setMaxAge(60 * 60 * 24); // 1일 동안 유지
//
//        response.addCookie(cookie); // 쿠키 추가
//
//        return ResponseEntity.ok().body(Map.of("message", "로그인 성공!"));
//    } else {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
//    }
//}

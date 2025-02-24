package com.utime.household.user.controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.UserReqVo;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("Auth")
public class AuthenticationController {
	
	final UserService userService;
	
	private KeyPair keyPair;
	
	private final String KeyEncAlgorithm = "RSA";
	
	@PostConstruct
	public void Controller() throws NoSuchAlgorithmException {
		this.keyPair = this.generateRSAKeyPair();
	}
	
	private KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(KeyEncAlgorithm);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    @GetMapping("PublicKey")
    public ResponseEntity<String> createPublicKey( HttpServletRequest request, HttpServletResponse response) {
    	
    	final String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
    	
    	return ResponseEntity.ok().body(
    			"-----BEGIN PUBLIC KEY-----\n" + publicKey + "\n-----END PUBLIC KEY-----"
    		);
    }

    private String decryptPassword(String encryptedPassword) throws Exception {
    	
    	final Cipher cipher = Cipher.getInstance(KeyEncAlgorithm);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, keyPair.getPrivate());
        
        final byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        
        return new String(decryptedBytes);
    }
    
    @PostMapping("Login.json")
    public ResponseEntity<?> login( HttpServletRequest request, HttpServletResponse response, @RequestBody LoginReqVo reqVo) throws Exception {
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	reqVo.setPw( this.decryptPassword(reqVo.getPw()) );
    	
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
    
    /**
	 * 회원 가입
	 * @param request
	 * @param reqVo
	 * @return
     * @throws Exception 
	 */
	@ResponseBody
	@PostMapping("JoinUser.json")
    public ResponseEntity<?> login( HttpServletRequest request, @ModelAttribute UserReqVo reqVo) throws Exception {
    	
    	reqVo.setSessionId( request.getRequestedSessionId() );
    	reqVo.setPw( this.decryptPassword(reqVo.getPw()) );
    	
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
	@ResponseBody
	@PostMapping("ConvertUserPw.json")
    public ResponseEntity<ReturnBasic> convertUserPw( HttpServletRequest request, UserReqVo reqVo )throws Exception {
		
		reqVo.setSessionId( request.getRequestedSessionId() );
		reqVo.setPw( this.decryptPassword(reqVo.getPw()) );
		
		final ReturnBasic result = userService.convertUserPw(reqVo);
    	
    	return ResponseEntity.ok().body(result);
    }
    
    
    
}


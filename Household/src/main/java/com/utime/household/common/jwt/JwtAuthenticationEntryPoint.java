package com.utime.household.common.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.utime.household.common.vo.HouseholdDefine;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("jwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	
        log.warn( "Url:{}\tStatus:{}\tMessage:{}", request.getRequestURL(), response.getStatus(), authException.getMessage() );
        
        final String requestUri = request.getRequestURI();
        
        if( requestUri.lastIndexOf(".html") > -1 ) {
        	// 이전 페이지 정보 저장
        	request.getSession().setAttribute(HouseholdDefine.KeyBeforeUri, requestUri);
        	
            // 인증되지 않은 사용자가 보호된 페이지에 접근하면 로그인 페이지로 리디렉트
        	response.sendRedirect(request.getContextPath() + "/User/Login.html");
        }
    }

}
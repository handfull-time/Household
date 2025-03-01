package com.utime.household.common.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.utime.household.common.vo.HouseholdDefine;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>인증 실패 시 401 처리 (로그인 페이지 이동 또는 JSON 응답)</P>
 * 인증되지 않은 사용자(401 Unauthorized)가 보호된 리소스에 접근했을 때 실행되는 핸들러.<br/>
 * <br/>
 * 일반적으로 로그인 페이지로 리디렉트하거나, JSON 응답을 반환하는 역할.<br/>
 * JwtAuthenticationFilter에서 response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized") 등을 호출하면 authenticationEntryPoint가 실행됨.<br/>
 * 예외 발생 시 commence() 메서드가 호출되며, 클라이언트에게 로그인 페이지로 리디렉트하거나, 401 JSON 응답을 반환하는 등의 처리를 수행.
 */
@Slf4j
@Component("jwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	
    	final int status = response.getStatus();
        log.warn( "Url:{}\tStatus:{}\tMessage:{}", request.getRequestURL(), status, authException.getMessage() );
        
        final String requestUri = request.getRequestURI();
        
        if( requestUri.lastIndexOf(".html") > -1 && status == HttpServletResponse.SC_UNAUTHORIZED) {
        	// 이전 페이지 정보 저장
        	request.getSession().setAttribute(HouseholdDefine.KeyBeforeUri, requestUri);
        	
            // 인증되지 않은 사용자가 보호된 페이지에 접근하면 로그인 페이지로 리디렉트
        	response.sendRedirect(request.getContextPath() + "/User/Login.html");
        }
    }

}
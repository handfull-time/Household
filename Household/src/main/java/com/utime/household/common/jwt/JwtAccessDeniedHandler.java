package com.utime.household.common.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * AccessDeniedHandler는 Spring Security에서 사용자가 인증(로그인)은 되었지만, 특정 리소스에 대한 접근 권한이 없는 경우 실행되는 핸들러입니다.
 * ➡ 즉, "403 Forbidden" (접근 거부) 응답을 처리하는 역할을 합니다.
 */
@Slf4j
@Component("JwtAccessDenied")
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    	log.warn("접근 권한이 없는 사용자입니다( AccessDeniedHandler )");
    	log.warn( "요청 URL : " + request.getRequestURI() );
    	
    	response.setStatus( HttpServletResponse.SC_FORBIDDEN );
    	
    	// 403 발생 시 특정 페이지로 이동
    	response.sendRedirect( request.getContextPath() + "/Error/AccessDenied.html?url=" + request.getRequestURI());
    }
}

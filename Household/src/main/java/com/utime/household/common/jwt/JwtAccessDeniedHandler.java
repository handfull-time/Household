package com.utime.household.common.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.utime.household.common.vo.ApiResponse;
import com.utime.household.common.vo.ApiResponseType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 권한 없는 페이지 접근 처리
 */
@Slf4j
@Component("JwtAccessDenied")
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    	//아마 안쓸거 같지만 일단 냅두자
    	log.info("접근 권한이 없는 사용자입니다( AccessDeniedHandler )");
    	log.info( "요청 URL : " + request.getRequestURI() );
    	ApiResponse.error(response, ApiResponseType.ACCESS_DENINE);
    }
}

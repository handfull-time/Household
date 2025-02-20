package com.utime.household.common.jwt;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.utime.household.common.vo.ApiResponse;
import com.utime.household.common.vo.ApiResponseType;
import com.utime.household.common.vo.HouseholdDefine;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("jwtAuthenticationEntryPoint")
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
    	
        log.info( authException.getMessage() + " " + request.getRequestURL());
        
        final String contextPath = request.getContextPath();
        final String requestUri = request.getRequestURI();
        final String beforeUri = requestUri.substring(contextPath.length());
        
		request.getSession().setAttribute(HouseholdDefine.KeyBeforeUri, beforeUri);
        
        // 인증되지 않은 사용자가 보호된 페이지에 접근하면 로그인 페이지로 리디렉트
        response.sendRedirect(request.getContextPath() + "/User/Login.html");
    	
//    	int status = response.getStatus();
//    	switch ( status ) {
//		case 200: return;
//		case 401,403 : 
//			ApiResponse.error(response, ApiResponseType.REQ_ACCESS);
//			break;
//		case 404 :
//			ApiResponse.error(response, ApiResponseType.NOT_FOUND_RESPONSE);
//			break;
//		case 405 :
//			ApiResponse.error(response, ApiResponseType.METHOD_NOT_ALLOWED_RESPONSE);
//			break;
//		case 500 :
//			ApiResponse.error(response, ApiResponseType.SC_INTERNAL_SERVER_ERROR);
//			break;
//		}

    }
//    
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
//        log.info("[CustomAuthenticationEntryPointHandler] :: 토근 정보가 만료되었거나 존재하지 않음");
//
//        response.setStatus(ApiExceptionEnum.ACCESS_DENIED.getStatus().value());
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json; charset=UTF-8");
//
//        JsonObject returnJson = new JsonObject();
//        returnJson.addProperty("errorCode", ApiExceptionEnum.ACCESS_DENIED.getCode());
//        returnJson.addProperty("errorMsg", ApiExceptionEnum.ACCESS_DENIED.getMessage());
//
//        PrintWriter out = response.getWriter();
//        out.print(returnJson);
//    }
}
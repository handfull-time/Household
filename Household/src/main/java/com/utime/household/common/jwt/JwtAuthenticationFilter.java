package com.utime.household.common.jwt;

import java.io.IOException;
import java.util.Collections;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.WhiteAddressList;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.vo.UserVo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <P>JWT 토큰 검증 및 사용자 인증 수행</P>
 * 클라이언트의 요청을 가로채 JWT 토큰을 검증하고, 유효한 경우 사용자를 인증(SecurityContext에 저장).<br/>
 * 만약 토큰이 없거나, 만료되었거나, 검증에 실패하면 요청을 거부하거나, 다른 처리를 수행 (예: Refresh Token 사용).<br/>
 * <br/>
 * Authorization 헤더에서 JWT Access Token 추출.<br/>
 * JWT 토큰을 검증하여 유효하면 SecurityContext에 사용자 정보 저장.<br/>
 * 토큰이 없거나 유효하지 않으면, 401 Unauthorized 처리 또는 Refresh Token으로 새 Access Token 발급 시도.<br/>
 * 요청을 계속 진행(filterChain.doFilter(request, response))하거나, 예외를 발생시켜 Spring Security의 예외 처리 흐름을 타게 만듦.
 */
@Slf4j
@Order(1) // 필터의 실행 순서 지정
@RequiredArgsConstructor
@Component("JwtAuthentication")
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtUtil;
    
    private final UserDao userDao;
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    	 
    	final String path = request.getRequestURI().substring(request.getContextPath().length());

    	return WhiteAddressList.whiteListPaths.stream().anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	final String userToken = jwtUtil.getAuthToken( request );
    
    	if( userToken == null) {
    		filterChain.doFilter(request, response);
    		return;
    	}
    	
		log.info(userToken);
        if (! jwtUtil.validateToken(userToken)) {
        	// 401 Unauthorized 응답
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            return;
        }
        	
        final String userId = jwtUtil.getUsernameFromToken(userToken);
        
        //token 검증 완료 후 SecurityContextHolder 내 인증 정보가 없는 경우 저장
        if( HouseholdUtils.isNotEmpty( userId ) && SecurityContextHolder.getContext().getAuthentication() == null) {
        	log.info("Authentication 설정");
        	
        	final UserVo user = userDao.getUserFromIdDetail(userId);
        	
        	final Authentication authToken = new UsernamePasswordAuthenticationToken(user, 
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()))
            );
        	
        	SecurityContextHolder.getContext().setAuthentication( authToken );
        }

        filterChain.doFilter(request, response);
    }
}

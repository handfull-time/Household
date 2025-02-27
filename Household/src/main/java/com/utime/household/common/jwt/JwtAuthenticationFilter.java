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

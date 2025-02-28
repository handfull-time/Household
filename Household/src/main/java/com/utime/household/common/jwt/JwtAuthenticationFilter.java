package com.utime.household.common.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.WhiteAddressList;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.vo.UserVo;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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
    		log.warn(request.getRequestURI() + "\tUserToken is null.");
    		
    		if( ! this.refreshTokenAndContinue(request, response, filterChain) ) {
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
        		filterChain.doFilter(request, response);
        		return;
    		}
    	}
    	
		log.info(userToken);
        if (! jwtUtil.validateToken(userToken)) {
        	// 401 Unauthorized 응답
    		log.warn(request.getRequestURI() + "\tUserToken Unauthorized.");
        	response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            return;
        }

        this.authenticateUser( userToken );

        filterChain.doFilter(request, response);
    }
    
    private boolean refreshTokenAndContinue(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
    		throws ServletException, IOException {
    	
    	final Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;

        final String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(HouseholdDefine.KeyRefreshToken))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
        
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            log.warn("RefreshToken invalid or expired. Redirecting to login.");
            response.sendRedirect(request.getContextPath() + "/User/Login.html");
            return false;
        }
        
        final String id = jwtUtil.getUsernameFromToken(refreshToken);
		if( HouseholdUtils.isEmpty(id)) {
			log.warn("ID 추출 실패");
            response.sendRedirect(request.getContextPath() + "/User/Login.html");
            return false;
		}
		
		final UserVo user = userDao.getUserFromId( id );
		if( user == null ) {
			log.warn("회원 없음");
            response.sendRedirect(request.getContextPath() + "/User/Login.html");
            return false;
		}
		
		final String newAccessToken = jwtUtil.generateAccessToken(user);
        
        log.info("RefreshToken valid. Issuing new AccessToken.");

        Cookie accessTokenCookie = new Cookie(HouseholdDefine.KeyAccessToken, newAccessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath(request.getContextPath());
        accessTokenCookie.setMaxAge((int) (JwtProvider.ACCESS_EXPIRATION_TIME / 1000L));
        response.addCookie(accessTokenCookie);

        doFilterInternal(request, response, filterChain);
        return true;
    }
    
    private void authenticateUser(String token) {
    	
        final String userId = jwtUtil.getUsernameFromToken(token);
        
        if (HouseholdUtils.isNotEmpty(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.warn("Setting Authentication for user: {}", userId);
            
            final UserVo user = userDao.getUserFromIdDetail(userId);
            
            final Authentication authToken = new UsernamePasswordAuthenticationToken(user, null,
                    Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
            
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}

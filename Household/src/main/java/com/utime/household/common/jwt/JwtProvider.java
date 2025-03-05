package com.utime.household.common.jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.utime.household.common.util.CacheIntervalMap;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ResUserVo;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.vo.UserVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

	private static final long ONE_SECOND = 1000L;
    private static final long ACCESS_EXPIRATION_TIME = 15L * 60L * ONE_SECOND; // 15분
    private static final long PAGING_EXPIRATION_TIME = 1L * 24L * 60L * 60L * ONE_SECOND; // 1일
    private static final long REFRESH_EXPIRATION_TIME = 7L * 24L * 60L * 60L * ONE_SECOND; // 7일
    
	/** 엑세스 토큰의 쿠키 이름 */
    private static final String KeyAccessToken = "accessToken";
	
	/** 페이지 갱신 토큰의 쿠키 이름 */
    private static final String KeyPagingToken = "pagingToken";

	private final UserDao userDao;
    
    private final CacheIntervalMap<String, UserVo> intervalMap = new CacheIntervalMap<>(ACCESS_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;
    
    private final MacAlgorithm macAlgo = Jwts.SIG.HS256;
    
    private final String RefreshAddress = "/Auth/Refresh";
    
    /**
     * JWT 키 상수 관리
     */
    private static class JWT_KEY {
        static final String AUTHORIZATION = "Authorization";
        static final String TOKEN_PREFIX = "Bearer ";
        static final String IP = "ReqIp";
        static final String AGENT = "ReqAgent";
    }
    
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    /**
     * 해더 값에서 인증 토큰 추출
     * @param request
     * @return
     */
    private String getAuthToken( HttpServletRequest request ) {

    	return Optional.ofNullable(request.getHeader(JWT_KEY.AUTHORIZATION))
                .filter(header -> header.startsWith(JWT_KEY.TOKEN_PREFIX))
                .map(header -> header.substring(JWT_KEY.TOKEN_PREFIX.length()))
                .orElseGet(() -> getCookieValue(request, JwtProvider.KeyAccessToken));
    }
    
    /**
     * 특정 이름의 쿠키 값을 가져오기
     */
    private String getCookieValue(HttpServletRequest request, String key) {
        if (request.getCookies() == null) return null;
        
        return Arrays.stream(request.getCookies())
                .filter(cookie -> key.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
    
//    /**
//     * token 유효성 검증
//     *
//     * @param token JWT
//     * @return token 검증 결과
//     */
//    private boolean validateToken(String token) {
//        return Optional.ofNullable(this.getAllClaimsFromToken(token)).isPresent();
//    }
	
    /**
     * JWT에서 모든 Claims 정보 가져오기
     */
    private Claims getAllClaimsFromToken(String token) {
    	
    	if( token == null || token.length() < 1 ) {
    		log.warn("토큰 값 없음");
    		return null;
    	}
    	
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Token 생성 및 쿠키 추가
     */
    private Cookie createTokenAndCookie(String cookieName, UserVo user, Map<String, Object> claims, long expirationTime, String domain, String path) {
        
    	final String token = (user != null)? this.generateToken(user, claims, expirationTime):null;
        
    	final Cookie cookie = new Cookie(cookieName, token);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        cookie.setDomain(domain);
        cookie.setMaxAge((int) (expirationTime / ONE_SECOND));
        
        return cookie;
    }
    
    /**
     * JWT 생성
     */
    private String generateToken(UserVo user, Map<String, Object> claims, long expirationTime) {
        
        return Jwts.builder()
                .id(user.getId())
                .subject(String.valueOf(user.getUserNo()))
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(this.key, this.macAlgo)
                .compact();
    }
    
    /**
     * IP 및 User-Agent 정보 검증
     */
    private boolean validateRequestIpAndAgent(HttpServletRequest request, Claims claims) {
    	
    	final String ip = claims.get(JWT_KEY.IP, String.class);
		final String reqIp = HouseholdUtils.getRemoteAddress( request );
		if( ! reqIp.equals( ip ) ) {
			log.warn("ip가 서로 다르다. TokenIp:{}, ReqIp:{}", ip, reqIp);
            return false;
		}
		
		final String agent = claims.get(JWT_KEY.AGENT, String.class);
		final String reqAgent = request.getHeader("user-agent");
		if( ! reqAgent.equals( agent ) ) {
			log.warn("USER-AGENT가 서로 다르다. Token User-agent:{}, Req User-agent:{}", agent, reqAgent);
            return false;
		}
		
    	return true;
    }

    /**
     * JWT에서 사용자 정보 추출
     */
    private UserVo extractUserFromClaims(Claims claims) {
    	
    	final String id = claims.getId();
    	
    	UserVo user = this.intervalMap.get(id);
    	
    	if( user == null ) {
    		user = this.userDao.getUserFromIdDetail(id);
    		this.intervalMap.put(id, user);
    	}
        
        return user;
    }

//    /**
//     * 토큰 만료 일자 조회
//     *
//     * @param token JWT
//     * @return 만료 일자
//     */
//    public Date getExpirationDateFromToken(final String token) {
//        return this.getClaimFromToken(token, Claims::getExpiration);
//    }

    /**
     * 요청으로부터 IP 및 User-Agent 정보 가져오기
     */
    private Map<String, Object> createPagingClaims(HttpServletRequest request) {
        
    	final Map<String, Object> claims = new HashMap<>();
        
    	claims.put(JWT_KEY.IP, HouseholdUtils.getRemoteAddress(request));
        claims.put(JWT_KEY.AGENT, request.getHeader("user-agent"));
        
        log.info("요청 정보 : " + claims);
        
        return claims;
    }

    /**
     * <P>로그인</P>
     * 로그인에 필요한 토큰 생성
     * @param request
     * @param response
     * @param user
     * @return
     */
	public ReturnBasic procLogin(HttpServletRequest request, HttpServletResponse response, UserVo user) {
		
		this.intervalMap.put(user.getId(), userDao.getUserFromIdDetail(user.getId()) );
		
        final String domain = request.getServerName();
        final String contextPath = request.getContextPath();
        
        final Map<String, Object> claims = this.createPagingClaims(request);

        response.addCookie( this.createTokenAndCookie( JwtProvider.KeyAccessToken, 
        		user, new HashMap<>(), 
        		ACCESS_EXPIRATION_TIME, 
        		domain, contextPath));
        
        response.addCookie( this.createTokenAndCookie( JwtProvider.KeyPagingToken, 
        		user, new HashMap<>(claims), 
        		PAGING_EXPIRATION_TIME, 
        		domain, contextPath));
        
        response.addCookie( this.createTokenAndCookie( HouseholdDefine.KeyRefreshToken, 
        		user, new HashMap<>(claims), 
        		REFRESH_EXPIRATION_TIME, 
        		domain, contextPath + RefreshAddress));

        return new ReturnBasic();

	}
	
	/**
	 * <P>로그인</P>
     * 로그인에 필요한 토큰 생성
	 * @param request
	 * @param response
	 * @param refreshToken
	 * @return
	 */
	public ReturnBasic procRefresh(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
		
        final Claims claims = this.getAllClaimsFromToken(refreshToken);
        if( claims == null ) {
        	return new ReturnBasic("E", "토큰으로부터 데이터 추출 실패");
        }
        
        if ( ! this.validateRequestIpAndAgent(request, claims)) { 
        	return new ReturnBasic("E", "유효성 검사 실패");
        }

        final UserVo user = this.extractUserFromClaims(claims);

        return this.procLogin(request, response, user);
	}
	
	/**
	 * 페이징 토큰을 이용해 Access token 작업을 생성한다.
	 * @param request
	 * @param response
	 * @param pagingToken
	 * @return
	 */
	public ResUserVo procPagingToken(HttpServletRequest request, HttpServletResponse response) {
		
		boolean isPagingToken = false;
		String userToken = this.getAuthToken(request);
		
		if( userToken == null ) {
			isPagingToken = true;
		    userToken = this.getCookieValue(request, JwtProvider.KeyPagingToken);
		    
		    if( userToken == null ) {
		    	log.warn("PagingToken invalid or expired. Redirecting to login.");
		        return new ResUserVo("E", "토큰 만료");
		    }
		}
    	
        final Claims claims = this.getAllClaimsFromToken(userToken);
        if( claims == null ) {
        	return new ResUserVo("E", "토큰으로부터 데이터 추출 실패");
        }
        
        if( isPagingToken ) {
	        if ( ! this.validateRequestIpAndAgent(request, claims)) { 
	        	return new ResUserVo("E", "유효성 검사 실패");
	        }
        }

        final ResUserVo result = new ResUserVo();
        final UserVo user = this.extractUserFromClaims(claims);
        result.setUser(user);
        
        final String domain = request.getServerName();
        final String contextPath = request.getContextPath();

        response.addCookie( this.createTokenAndCookie( JwtProvider.KeyAccessToken, 
        		user, new HashMap<>(), 
        		ACCESS_EXPIRATION_TIME, 
        		domain, contextPath));
        
        response.addCookie( this.createTokenAndCookie( JwtProvider.KeyPagingToken, 
        		user, this.createPagingClaims(request), 
        		PAGING_EXPIRATION_TIME, 
        		domain, contextPath));

        return result;
	}

	/**
	 * 로그 아웃 처리
	 * @param request
	 * @param response
	 */
	public void procLogout(HttpServletRequest request, HttpServletResponse response) {
		
		final String userToken = this.getAuthToken(request);
		
		if( userToken != null ) {
	        final Claims claims = this.getAllClaimsFromToken(userToken);
	        if( claims != null ) {
	        	this.intervalMap.remove( claims.getId() );
	        }
		}

		final String domain = request.getServerName();
        final String contextPath = request.getContextPath();
        
		response.addCookie( this.createTokenAndCookie( JwtProvider.KeyAccessToken, 
        		null, null, 
        		ONE_SECOND, 
        		domain, contextPath));
        
        response.addCookie( this.createTokenAndCookie( JwtProvider.KeyPagingToken, 
        		null, null, 
        		ONE_SECOND, 
        		domain, contextPath));
        
        response.addCookie( this.createTokenAndCookie( HouseholdDefine.KeyRefreshToken, 
        		null, null, 
        		ONE_SECOND, 
        		domain, contextPath + RefreshAddress));
		
	}

}
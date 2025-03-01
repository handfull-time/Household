package com.utime.household.common.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.user.vo.UserVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

    // jwt 만료 시간 1시간
//    private static final long JWT_TOKEN_VALID = (long) 1000 * 60 * 30;
    
//    public static final long ACCESS_EXPIRATION_TIME = 15 * 60 * 1000; // 15분
    public static final long ACCESS_EXPIRATION_TIME = 1 * 60 * 1000; // 1분
    public static final long REFRESH_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // 7일
    

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;
    
    private final String KeyHaderAuthorization = "Authorization";
    
    private final String KeyTokenStarter = "Bearer ";
    
    private final String KeyRole = "roles";
    
    private final int LenTokenStarter = KeyTokenStarter.length();
    
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    /**
     * 해더 값에서 인증 토큰 추출
     * @param request
     * @return
     */
    public String getAuthToken( HttpServletRequest request ) {

    	String result = null;
        final String headerToken = request.getHeader(KeyHaderAuthorization);

        if (headerToken != null && headerToken.startsWith(KeyTokenStarter)) {
        	log.info(headerToken);
        	result = headerToken.substring(LenTokenStarter);
        }else {
        	final Cookie[] cookies = request.getCookies();
        	if( cookies != null ) {
        		for( Cookie cookie : cookies ) {
        			if( HouseholdDefine.KeyAccessToken.equals( cookie.getName() ) ){
        				result = cookie.getValue();
        				break;
        			}
        		}
        	}
        }
        
        return result;
    }

    /**
     * token Username 조회
     *
     * @param token JWT
     * @return token Username
     */
    public String getUsernameFromToken(final String token) {
        return this.getClaimFromToken(token, Claims::getId);
    }

    /**
     * token 사용자 속성 정보 조회
     *
     * @param token JWT
     * @param claimsResolver Get Function With Target Claim
     * @param <T> Target Claim
     * @return 사용자 속성 정보
     */
    private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        // token 유효성 검증
        if(! this.validateToken(token) )
            return null;

        final Claims claims = this.getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    
    /**
     * token 사용자 모든 속성 정보 조회
     *
     * @param token JWT
     * @return All Claims
     */
    private Claims getAllClaimsFromToken(final String token) {
    	
    	return Jwts.parser()
    			.verifyWith(key)
    			.build()
    			.parseSignedClaims(token)
    			.getPayload();
    }

    /**
     * 토큰 만료 일자 조회
     *
     * @param token JWT
     * @return 만료 일자
     */
    public Date getExpirationDateFromToken(final String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

//    /**
//     * access token 생성
//     *
//     * @param id token 생성 id
//     * @return access token
//     */
//    public String generateAccessToken(final String id) {
//        return this.generateAccessToken(id, new HashMap<>());
//    }
    
    public String generateAccessToken(UserVo user) {
    	final Map<String, Object> claims = new HashMap<>();
    	claims.put(KeyRole, user.getRole());
    	
		return this.generateAccessToken(user, claims);
	}

    /**
     * access token 생성
     *
     * @param id token 생성 id
     * @param claims token 생성 claims
     * @return access token
     */
    private String generateAccessToken(final UserVo user, final Map<String, Object> claims) {
        return this.doGenerateAccessToken(user, claims);
    }
    
    /**
     * JWT access token 생성
     *
     * @param id token 생성 id
     * @param claims token 생성 claims
     * @return access token
     */
    private String doGenerateAccessToken(final UserVo user, final Map<String, Object> claims) {
    	
    	final long now = System.currentTimeMillis();
    	
    	return Jwts.builder()
    			.id(user.getId())
                .subject("" + user.getUserNo())
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + ACCESS_EXPIRATION_TIME))
                .signWith(this.key, Jwts.SIG.HS256)
                .compact(); 
    }

    /**
     * refresh token 생성
     *
     * @param id token 생성 id
     * @return refresh token
     */
    public String generateRefreshToken(final String id) {
        return this.doGenerateRefreshToken(id);
    }
    
    /**
     * 토큰에서 역할(role) 추출
     * @param token
     * @return
     */
    public String getRole(String token) {
        return this.getAllClaimsFromToken(token).get(KeyRole, String.class);
    }

    /**
     * refresh token 생성
     * 
     * @param id token 생성 id
     * @return refresh token
     */
    private String doGenerateRefreshToken(final String id) {
    	
    	final long now = System.currentTimeMillis();
    	
        return Jwts.builder()
                .id(id)
//                .subject(id)
                .issuedAt(new Date(now))
                .expiration(new Date(now + REFRESH_EXPIRATION_TIME)) 
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * token 검증
     *
     * @param token JWT
     * @return token 검증 결과
     */
    public boolean validateToken(final String token) {
    	Exception error = null;
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        } catch (SecurityException e) {
            log.warn("Invalid JWT signature: {}", e.getMessage()); error = e;
        } catch (MalformedJwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage()); error = e;
        } catch (ExpiredJwtException e) {
            log.warn("JWT token is expired: {}", e.getMessage()); error = e;
        } catch (UnsupportedJwtException e) {
            log.warn("JWT token is unsupported: {}", e.getMessage()); error = e;
        } catch (IllegalArgumentException e) {
            log.warn("JWT claims string is empty: {}", e.getMessage()); error = e;
        }

        return error == null;
    }

}
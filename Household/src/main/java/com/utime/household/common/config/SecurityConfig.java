package com.utime.household.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Resource(name="JwtAuthentication")
	private jakarta.servlet.Filter jwtAuthFilter;
	
	@Resource(name="jwtAuthenticationEntryPoint")
	private AuthenticationEntryPoint authenticationEntryPoint;
	
	@Resource(name="JwtAccessDenied")
	private AccessDeniedHandler accessDeniedHandler;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

    	final MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);
    	
    	// white list (Spring Security 체크 제외 목록)
        final MvcRequestMatcher[] permitAllWhiteList = {
//        	mvc.pattern("/Home.html"),
        	mvc.pattern("/Auth/**"),
        	mvc.pattern("/User/**"),
            mvc.pattern("/View/**"),
            mvc.pattern("/register"),
            mvc.pattern("/token-refresh"),
            mvc.pattern("/favicon.ico"),
            mvc.pattern("/error"),
            mvc.pattern("/js/**"),
            mvc.pattern("/css/**"),
            mvc.pattern("/html/**"),
            mvc.pattern("/images/**")
        };
	
	
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(permitAllWhiteList).permitAll()
                .anyRequest().authenticated()
            );
        
        // Spring Security는 기본적으로 로그인 폼을 제공하지만, JWT를 사용하면 세션 기반 인증이 필요 없기 때문에 로그인 폼을 비활성화해야 합니다.
        http.formLogin(AbstractHttpConfigurer::disable);
        // Spring Security는 기본적으로 /logout URL을 제공하여 세션 기반 로그아웃을 처리하지만, JWT 기반 인증에서는 서버가 세션을 관리하지 않기 때문에 필요하지 않습니다.
        http.logout(AbstractHttpConfigurer::disable);
        // CSRF(Cross-Site Request Forgery) 공격은 웹 브라우저에서 세션을 유지하는 상태에서 발생하는 공격입니다. JWT는 세션을 사용하지 않고, 요청마다 토큰을 포함해야 하므로 CSRF 보호가 필요하지 않습니다.
        http.csrf(AbstractHttpConfigurer::disable);

        // session 비활성화
        // SessionCreationPolicy의 옵션들
        // - ALWAYS → 항상 세션 생성
        // - IF_REQUIRED → 필요할 때만 세션 생성 (기본값)
        // - NEVER → 세션을 만들지는 않지만, 기존 세션이 있다면 사용
        // - STATELESS → 세션을 전혀 생성하지 않음 (JWT 사용 시 필수)
        http.sessionManagement(session -> session
        		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	);

        // before filter
        http.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        // exception handler
        http.exceptionHandling(conf -> conf
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .accessDeniedHandler(this.accessDeniedHandler)
            );
        
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//            .username("user")
//            .password("password") // 기본 패스워드 설정
//            .roles("USER")
//            .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}

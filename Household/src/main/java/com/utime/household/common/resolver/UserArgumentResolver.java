package com.utime.household.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.utime.household.common.jwt.JwtProvider;
import com.utime.household.user.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component("UserArgument")
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	private final JwtProvider jwtUtil;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserVo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		UserVo result = null;
		
		final String userToken = jwtUtil.getAuthToken( webRequest.getNativeRequest(HttpServletRequest.class) );
		if( userToken != null ) {
			log.info(userToken);
		}
		
		return result;
	}

}

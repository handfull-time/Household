package com.utime.household.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.utime.household.user.vo.UserVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("UserArgument")
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(UserVo.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		final Object result;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        	result = authentication.getPrincipal();
        }else {
        	result = null;
        }
        
        return result;
		
	}

}

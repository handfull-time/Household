package com.utime.household.common.interceptor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.utime.household.user.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("ViewInterceptor")
class ViewHandlerInterceptor implements AsyncHandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView view)
			throws Exception {
		
		if( ! (handler instanceof HandlerMethod) )
			return;
		
		if( req.getServletPath().lastIndexOf(".html") < 1 )
			return;

		final ModelMap model = view.getModelMap();
		model.addAttribute("CurrentTime", System.currentTimeMillis());
		
		final String uri = req.getRequestURI().substring(1);
		model.addAttribute("currentURI", uri.substring(uri.indexOf("/")) );
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserVo) {
        	model.addAttribute("user", authentication.getPrincipal() );
        }
	}
}

package com.utime.household.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("ViewInterceptor")
class ViewHandlerInterceptor implements AsyncHandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView view)
			throws Exception {
		
		if( handler instanceof HandlerMethod ) {
			final String path = req.getServletPath();
			if( path.lastIndexOf(".html") > 0 ){
				view.getModelMap().addAttribute("CurrentTime", System.currentTimeMillis());
			}
		}
		
	}
}

package com.utime.household.common.interceptor;


import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller 로그 처리
 */
@Slf4j
@Component("LogInterceptor")
class LogHandlerInterceptor implements AsyncHandlerInterceptor {

	private final WebLogProcessor logProcessor = new WebLogProcessor(); 
	
	
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView view)
			throws Exception {
		
		if( handler instanceof HandlerMethod ) {
			log.info( logProcessor.urlParamLogPost(req, res, (HandlerMethod)handler, view) );
		}
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		if( handler instanceof HandlerMethod )
			log.info( logProcessor.urlParamLogPre(req, (HandlerMethod)handler));
		
		return true;
	}
}
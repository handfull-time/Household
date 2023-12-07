package com.utime.household.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
class CustomErrorController implements ErrorController {
	
	@GetMapping(value = {"error"})
	public String goMain(HttpServletRequest request, ModelMap model, Exception e) {
		
		final ReturnBasic res = new ReturnBasic();
		
		final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            res.setCode( status.toString() );
        }else {
        	res.setCode( "Unknown" );
        }
        
        final Object msg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (msg != null) {
        	res.setMessage(msg.toString());
        }
        
        if( HouseholdUtils.isEmpty( res.getMessage() )) {
        	res.setMessage( HouseholdUtils.exceptionToStr(e) );
        }
        
		model.addAttribute("res", res);
		
		return "common/error";
	}
	
}

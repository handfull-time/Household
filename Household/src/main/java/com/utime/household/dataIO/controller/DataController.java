package com.utime.household.dataIO.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.utime.household.dataIO.service.DataService;
import com.utime.household.dataIO.vo.OuputReqVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Data")
public class DataController {
	
	private final DataService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Data/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String dataEmptyMain(ModelMap model, OuputReqVO reqVo) {
		
		if( reqVo.getBegin().length() < 1 && reqVo.getEnd().length() < 1 ) {
			final Calendar calendar = Calendar.getInstance(Locale.KOREA);
			
	        // 월의 첫 날짜 계산
	        calendar.set(Calendar.DAY_OF_MONTH, 1);
	        Date firstDayOfMonth = calendar.getTime();

	        // 월의 마지막 날짜 계산
	        calendar.add(Calendar.MONTH, 1);
	        calendar.add(Calendar.DATE, -1);
	        Date lastDayOfMonth = calendar.getTime();
	        
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			reqVo.setBegin( sdf.format(firstDayOfMonth) );
			reqVo.setEnd( sdf.format(lastDayOfMonth) );
		}
		
		model.addAttribute("list", service.getHouseholdDataList(reqVo));
		
		return "data/bankcard/bankcard";
	}


}

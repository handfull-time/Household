package com.utime.household.dataIO.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.service.StoreService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Data")
public class DataController {
	
	private final DataIOService dataService;
	
	private final BankCardService bcService;
	
	private final StoreService stService;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Data/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String dataEmptyMain(ModelMap model, OuputReqVO reqVo) {
		
		if( HouseholdUtils.isEmpty(reqVo.getBegin()) && HouseholdUtils.isEmpty(reqVo.getEnd()) ) {
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
		
		model.addAttribute("reqVo", reqVo);
		model.addAttribute("bankCards", bcService.getBankCardList());
		model.addAttribute("categories", stService.getCategoryList());
		model.addAttribute("stores", stService.getStoreList());
		model.addAttribute("result", dataService.getHouseholdDataList(reqVo));
		
		return "data/dataMain";
	}


}

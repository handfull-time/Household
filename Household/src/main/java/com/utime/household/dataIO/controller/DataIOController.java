package com.utime.household.dataIO.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.config.service.BankCardService;
import com.utime.household.config.service.StoreService;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.root.vo.HouseholdDataListResVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("IO")
public class DataIOController {
	
	private final DataIOService service;
	private final StoreService stService;
	private final BankCardService bcService;
	
	@RequestMapping(value = {"Upload.html"})
	public String rootUploadShow(ModelMap model) {
		model.addAttribute("bankCard", bcService.getBankCardList());//EInputBankCard.values()
		return "data/upload";
	}

//	@ResponseBody
//	@RequestMapping(value = {"Upload.json"}, method = RequestMethod.POST)
//	public HouseholdDataListResVO rootUploadFile(@RequestParam("bankCard") EInputBankCard bankCard, @RequestParam("uploadFile") MultipartFile file) {
//		HouseholdDataListResVO result = service.upload(bankCard, file );
//		return result;
//	}
	
	@RequestMapping(value = {"UploadResult.html"}, method = RequestMethod.POST)
	public String rootUploadFile(ModelMap model, @RequestParam("bankCard") long bankCardNo, @RequestParam("uploadFile") MultipartFile file) {
		
		final HouseholdDataListResVO res = service.upload(bankCardNo, file );
		if( res.isError() ) {
			model.addAttribute("res", res);
			return "common/error";
		}
		
		model.addAttribute("list", res.getList());
		model.addAttribute("bankCard", res.getBcVo());
		model.addAttribute("listCategory", stService.getCategoryList());
		
		return "data/uploadResult";
	}

}

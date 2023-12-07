package com.utime.household.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.config.service.StoreService;
import com.utime.household.root.service.HouseholdService;
import com.utime.household.root.vo.EInputBankCard;
import com.utime.household.root.vo.HouseholdDataListResVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HouseholdController {
	
	private final HouseholdService service;
	private final StoreService stService;
	
	@RequestMapping(value = {"/"})
	public String goMain() {
		return "redirect:/Home.html";
	}
	
	@RequestMapping(value = {"Home.html"})
	public String rootMain() {
		return "root/home";
	}
	
	@RequestMapping(value = {"List.html"})
	public String rootList() {
		return "root/list";
	}
	
	@RequestMapping(value = {"Upload.html"})
	public String rootUploadShow(ModelMap model) {
		model.addAttribute("bankCard", EInputBankCard.values());
		return "data/upload";
	}

//	@ResponseBody
//	@RequestMapping(value = {"Upload.json"}, method = RequestMethod.POST)
//	public HouseholdDataListResVO rootUploadFile(@RequestParam("bankCard") EInputBankCard bankCard, @RequestParam("uploadFile") MultipartFile file) {
//		HouseholdDataListResVO result = service.upload(bankCard, file );
//		return result;
//	}
	
	@RequestMapping(value = {"UploadResult.html"}, method = RequestMethod.POST)
	public String rootUploadFile(ModelMap model, @RequestParam("bankCard") EInputBankCard bankCard, @RequestParam("uploadFile") MultipartFile file) {
		
		final HouseholdDataListResVO res = service.upload(bankCard, file );
		if( res.isError() ) {
			model.addAttribute("res", res);
			return "common/error";
		}
		
		model.addAttribute("list", res.getList());
		model.addAttribute("bankCard", bankCard);
		model.addAttribute("listCategory", stService.getCategoryList());
		
		return "data/uploadResult";
	}

}

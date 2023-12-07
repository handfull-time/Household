package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.service.StoreService;
import com.utime.household.config.vo.StoreVO;

import lombok.RequiredArgsConstructor;

/**
 * 사용처 처리
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("Config/Store")
public class StoreController {
	
	private final StoreService service;
	
	@RequestMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/Store/Home.html";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.GET)
	public String categoryMain(ModelMap model) {
		
		model.addAttribute("listStore",  service.getStoreList() );
		model.addAttribute("listCategory",  service.getCategoryList() );
		
		return "config/store/store";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.POST, params = {"no", "name"})
	public String categoryMain(ModelMap model, StoreVO vo) {
		
		ReturnBasic res = service.saveStore(vo);
		model.addAttribute("res", res);

		return this.categoryMain(model);
	}
}

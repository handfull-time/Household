package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String storeMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
		
		model.addAttribute("listStore",  service.getStoreList() );
		model.addAttribute("listCategory",  service.getCategoryList() );
		
		if( res != null )
			model.addAttribute("res", res );
		
		return "config/store/store";
	}
	
	@RequestMapping(value="Save.html", method = RequestMethod.POST, params = {"no", "name"})
	public String storeMain(RedirectAttributes redirectAttributes, StoreVO vo) {
		
		final ReturnBasic res = service.saveStore(vo);
		
		redirectAttributes.addFlashAttribute("res", res);

		return "redirect:/Config/Store/Home.html";
	}
	
	@ResponseBody
	@RequestMapping(value="Remove.json", method = RequestMethod.POST, params = {"no"})
	public ReturnBasic storeRemove(StoreVO vo) {
		
		final ReturnBasic res = service.deleteStore(vo);

		return res;
	}
}

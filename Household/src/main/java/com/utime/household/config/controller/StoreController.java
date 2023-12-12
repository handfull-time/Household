package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.service.StoreService;
import com.utime.household.config.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 사용처 처리
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("Config/Store")
public class StoreController {
	
	private final StoreService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/Store/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String storeMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
		
		model.addAttribute("listStore",  service.getStoreList() );
		model.addAttribute("listCategory",  service.getCategoryList() );
		
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.info(gson.toJson( service.getCategoryList() ));
		log.info(gson.toJson( service.getStoreList() ));
		
		
		if( res != null )
			model.addAttribute("res", res );
		
		return "config/store/store";
	}
	
	@PostMapping(value="Save.html", params = {"no", "name"})
	public String storeMain(RedirectAttributes redirectAttributes, StoreVO vo) {
		
		final ReturnBasic res = service.saveStore(vo);
		
		redirectAttributes.addFlashAttribute("res", res);

		return "redirect:/Config/Store/Home.html";
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json", params = {"no"})
	public ReturnBasic storeRemove(StoreVO vo) {
		
		final ReturnBasic res = service.deleteStore(vo);

		return res;
	}
}

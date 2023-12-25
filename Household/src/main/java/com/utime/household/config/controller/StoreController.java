package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
//	@GetMapping(value="Home.html")
//	public String storeMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
//		
//		model.addAttribute("listStore",  service.getStoreList() );
//		model.addAttribute("listCategory",  service.getCategoryList() );
//		
//		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		log.info(gson.toJson( service.getCategoryList() ));
//		log.info(gson.toJson( service.getStoreList() ));
//		
//		
//		if( res != null )
//			model.addAttribute("res", res );
//		
//		return "config/store/store";
//	}
//	
//	@PostMapping(value="Save.html", params = {"no", "name"})
//	public String storeMain(RedirectAttributes redirectAttributes, StoreVO vo) {
//		
//		final ReturnBasic res = service.saveStore(vo);
//		
//		redirectAttributes.addFlashAttribute("res", res);
//
//		return "redirect:/Config/Store/Home.html";
//	}
	
	@GetMapping(value="Home.html")
	public String bankCardMain(ModelMap model) {
		
		model.addAttribute("listCategory",  service.getCategoryList() );
		
		return "config/store/store";
	}
	
	@PostMapping(value="List.list")
	public String listBankCard(ModelMap model, @RequestParam(value = "categoryNo", defaultValue = "-1") long categoryNo) {
		
		model.addAttribute("listStore",  service.getStoreList(categoryNo) );

		return "config/store/storeList";
	}
	
	
	@PostMapping(value="Detail.layer")
	public String DetailBankCardLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		model.addAttribute("listCategory",  service.getCategoryList() );
		model.addAttribute("data",  service.getStore(no) );

		return "config/store/storeItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="Save.json")
	public ReturnBasic saveBankCard(StoreVO vo) {
		
		final ReturnBasic res = service.saveStore(vo);

		return res;
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json")
	public ReturnBasic storeRemove(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteStore(no);

		return res;
	}
}

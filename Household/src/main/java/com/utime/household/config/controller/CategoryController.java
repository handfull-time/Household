package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.service.CategoryService;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.ECategoryType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Config/Category")
public class CategoryController {
	
	private final CategoryService service;
	
	@RequestMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/Category/Home.html";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.GET)
	public String categoryMain(ModelMap model) {
		
		model.addAttribute("list",  service.getCategoryList() );
		model.addAttribute("cTypes",  ECategoryType.values() );
		
		return "config/category/category";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.POST, params = {"no", "name"})
	public String categoryMain(ModelMap model, CategoryVO vo) {
		
		ReturnBasic res = service.saveCategory(vo);
		model.addAttribute("res", res);

		return this.categoryMain(model);
	}
}

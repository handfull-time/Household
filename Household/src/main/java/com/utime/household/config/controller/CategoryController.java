package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String categoryMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
		
		model.addAttribute("list",  service.getCategoryList() );
		model.addAttribute("cTypes",  ECategoryType.values() );
		
		if( res != null )
			model.addAttribute("res", res );
		
		return "config/category/category";
	}
	
	@RequestMapping(value="Save.html", method = RequestMethod.POST, params = {"no", "name"})
	public String categorySave(RedirectAttributes redirectAttributes, CategoryVO vo) {
		
		final ReturnBasic res = service.saveCategory(vo);
		
		redirectAttributes.addFlashAttribute("res", res);

		return "redirect:/Config/Category/Home.html";
	}
	
	@ResponseBody
	@RequestMapping(value="Remove.json", method = RequestMethod.POST, params = {"no"})
	public ReturnBasic categoryRemove(CategoryVO vo) {
		
		final ReturnBasic res = service.deleteCategory(vo);

		return res;
	}
}

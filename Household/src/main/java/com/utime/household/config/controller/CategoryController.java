package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/Category/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String categoryMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
		
		model.addAttribute("list",  service.getCategoryList() );
		model.addAttribute("cTypes",  ECategoryType.values() );
		
		if( res != null )
			model.addAttribute("res", res );
		
		return "config/category/category";
	}
	
	@PostMapping(value="Save.html", params = {"no", "name"})
	public String categorySave(RedirectAttributes redirectAttributes, CategoryVO vo) {
		
		final ReturnBasic res = service.saveCategory(vo);
		
		redirectAttributes.addFlashAttribute("res", res);

		return "redirect:/Config/Category/Home.html";
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json", params = {"no"})
	public ReturnBasic categoryRemove(CategoryVO vo) {
		
		final ReturnBasic res = service.deleteCategory(vo);

		return res;
	}
}

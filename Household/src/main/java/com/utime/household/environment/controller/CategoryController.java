package com.utime.household.environment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.service.CategoryService;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Env/Category")
public class CategoryController {
	
	private final CategoryService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Env/Category/Home.html";
	}
	
//	@GetMapping(value="Home.html")
//	public String categoryMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
//		
//		model.addAttribute("list",  service.getCategoryList() );
//		model.addAttribute("cTypes",  ECategoryType.values() );
//		
//		if( res != null )
//			model.addAttribute("res", res );
//		
//		return "config/category/category";
//	}
//	
//	@PostMapping(value="Save.html", params = {"no", "name"})
//	public String categorySave(RedirectAttributes redirectAttributes, CategoryVO vo) {
//		
//		final ReturnBasic res = service.saveCategory(vo);
//		
//		redirectAttributes.addFlashAttribute("res", res);
//
//		return "redirect:/Config/Category/Home.html";
//	}
	
	@GetMapping(value="Home.html")
	public String CategoryMain(ModelMap model) {
		
		model.addAttribute("cTypes",  ECategoryType.values() );
		
		return "Environment/category/category";
	}
	
	@PostMapping(value="List.list")
	public String listCategory(ModelMap model, @RequestParam(value = "cType", required = false) ECategoryType cType) {
		
		model.addAttribute("list",  service.getCategoryOwnerList(cType) );

//		return "Environment/category/categoryList";
		return "Environment/category/categoryTree";
	}
	
	
	@PostMapping(value="Detail.layer")
	public String DetailCategoryLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		model.addAttribute("cTypes",  ECategoryType.values() );
		model.addAttribute("data",  service.getCategory(no) );

		return "Environment/category/categoryItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="Save.json")
	public ReturnBasic saveCategory(CategoryVO vo) {
		
		final ReturnBasic res = service.saveCategory(vo);

		return res;
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json")
	public ReturnBasic removeCategory(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteCategory(no);

		return res;
	}
	
	
	@PostMapping(value="SubDetail.layer")
	public String DetailSubCategoryLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		model.addAttribute("data",  service.getSubCategory(no) );

		return "Environment/category/categorySubItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="SubSave.json")
	public ReturnBasic saveSubCategory(CategorySubVO vo) {
		
		final ReturnBasic res = service.saveSubCategory(vo);

		return res;
	}
	
	@ResponseBody
	@PostMapping(value="SubRemove.json")
	public ReturnBasic removeSubCategory(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteSubCategory(no);

		return res;
	}
	
}

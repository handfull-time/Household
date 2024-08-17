package com.utime.household.environment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.service.CategoryService;
import com.utime.household.environment.service.StoreService;
import com.utime.household.environment.vo.CategoryOwnerList;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Env/Category")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	private final StoreService storeService;
	
	
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
		
		model.addAttribute("list",  categoryService.getCategoryOwnerListOfList(cType) );

		return "Environment/category/categoryList";
	}
	
	
	@PostMapping(value="Detail.layer")
	public String DetailCategoryLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		model.addAttribute("cTypes",  ECategoryType.values() );
		model.addAttribute("categoryData", categoryService.getCategory(no) );

		return "Environment/category/categoryItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="Save.json")
	public ReturnBasic saveCategory(CategoryVO vo) {
		
		final ReturnBasic res = categoryService.saveCategory(vo);

		return res;
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json")
	public ReturnBasic removeCategory(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = categoryService.deleteCategory(no);

		return res;
	}
	
	
	@PostMapping(value="SubDetail.layer")
	public String DetailSubCategoryLayer(ModelMap model, @RequestParam(value = "ownerNo") long ownerNo, @RequestParam(value = "subNo") long subNo) {
		
		final CategorySubVO category = categoryService.getSubCategory(subNo);
		if( category.getOwner() == null ) {
			final CategoryVO owner = new CategoryVO();
			owner.setNo(ownerNo);
			category.setOwner(owner);
		}
		
		model.addAttribute("subData",  category );
		model.addAttribute("storeDataList",  storeService.getStoreList(category) );

		return "Environment/category/categorySubItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="SubSave.json")
	public ReturnBasic saveSubCategory(@RequestBody CategorySubVO vo) {
		
		final ReturnBasic res = categoryService.saveSubCategory(vo);

		return res;
	}
	
	@ResponseBody
	@PostMapping(value="SubRemove.json")
	public ReturnBasic removeSubCategory(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = categoryService.deleteSubCategory(no);

		return res;
	}
	
	@ResponseBody
	@GetMapping(value="Test.json")
	public CategoryOwnerList saveSubCategory() {
		
		return categoryService.getCategoryOwnerListOfList(null);
	}
	
	
}

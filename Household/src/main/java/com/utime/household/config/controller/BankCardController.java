package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.service.BankCardService;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.EBankCard;
import com.utime.household.dataIO.vo.InputBankCardList;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Config/BankCard")
public class BankCardController {
	
	private final BankCardService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/BankCard/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String bankCardMain(ModelMap model) {
		
		model.addAttribute("BankCard", EBankCard.values() );
		
		return "config/bankcard/bankcard";
	}
	
//	@GetMapping(value="Home.html")
//	public String bankCardMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
//		
//		model.addAttribute("BankCard", EBankCard.values() );
//		model.addAttribute("InputBC", EInputBankCard.values() );
//		
//		if( res != null )
//			model.addAttribute("res", res );
//		
//		return "config/bankcard/bankcard";
//	}
//	
//	@PostMapping(value="Save.html", params = {"no", "name", "bc", "inputBC"})
//	public String bankCardSave(RedirectAttributes redirectAttributes, BankCardVO vo) {
//		
//		final ReturnBasic res = service.saveBankCard(vo);
//		
//		redirectAttributes.addFlashAttribute("res", res);
//
//		return "redirect:/Config/BankCard/Home.html";
//	}
	
	@PostMapping(value="List.list")
	public String listBankCard(ModelMap model, @RequestParam(value = "bc", required = false) EBankCard bc) {
		
		model.addAttribute("list",  service.getBankCardList(bc) );

		return "config/bankcard/bankcardList";
	}
	
	
	@PostMapping(value="Detail.layer")
	public String DetailBankCardLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		model.addAttribute("BankCard", EBankCard.values() );
		model.addAttribute("InputBC", InputBankCardList.valuse() );
		model.addAttribute("data",  service.getBankCard(no) );

		return "config/bankcard/bankcardItem";
	}
	
	
	@ResponseBody
	@PostMapping(value="Save.json")
	public ReturnBasic saveBankCard(BankCardVO vo) {
		
		final ReturnBasic res = service.saveBankCard(vo);

		return res;
	}

	@ResponseBody
	@PostMapping(value="Remove.json")
	public ReturnBasic bankCardRemove(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteBankCard(no);

		return res;
	}
}

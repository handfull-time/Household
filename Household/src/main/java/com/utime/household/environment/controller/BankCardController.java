package com.utime.household.environment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.dataIO.vo.InputBankCardList;
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.EAccountType;
import com.utime.household.environment.vo.EBankCard;
import com.utime.household.environment.vo.ECardType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Env/BankCard")
public class BankCardController {
	
	private final BankCardService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Env/BankCard/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String bankCardMain(ModelMap model) {
		
		model.addAttribute("BankCard", EBankCard.values() );
		
		return "Environment/bankcard/bankcard";
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

		return "Environment/bankcard/bankcardList";
	}
	
	
	@PostMapping(value="Detail.layer")
	public String DetailBankCardLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		final BankCardVO item = service.getBankCard(no);
		
		if( item.getBc() == EBankCard.Bank ) {
			model.addAttribute("BankKinds", service.getBankKind() );
			model.addAttribute("BankCards", EBankCard.values() );
			model.addAttribute("AccountTypes", EAccountType.values() );
		}else {
			model.addAttribute("CardCompanies", service.getCardCompany() );
			model.addAttribute("CardTypes", ECardType.values() );
			model.addAttribute("Banks", service.getBankCardList(EBankCard.Bank) );
		}
		
		model.addAttribute("data",  item );

		return "Environment/bankcard/bankcardItem";
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

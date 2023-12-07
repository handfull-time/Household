package com.utime.household.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.service.BankCardService;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.EBankCard;
import com.utime.household.root.vo.EInputBankCard;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Config/BankCard")
public class BankCardController {
	
	private final BankCardService service;
	
	@RequestMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/BankCard/Home.html";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.GET)
	public String bankCardMain(ModelMap model) {
		
		model.addAttribute("BankCard", EBankCard.values() );
		model.addAttribute("InputBC", EInputBankCard.values() );
		model.addAttribute("list",  service.getBankCardList() );
		
		return "config/bankcard/bankcard";
	}
	
	@RequestMapping(value="Home.html", method = RequestMethod.POST, params = {"no", "name", "bc", "inputBC"})
	public String bankCardMain(ModelMap model, BankCardVO vo) {
		
		ReturnBasic res = service.saveBankCard(vo);
		model.addAttribute("res", res);

		return this.bankCardMain(model);
	}
}

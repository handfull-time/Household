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
import com.utime.household.config.service.BankCardService;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.EBankCard;
import com.utime.household.dataIO.vo.EInputBankCard;

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
	public String bankCardMain(ModelMap model, @ModelAttribute(value = "res") ReturnBasic res) {
		
		model.addAttribute("BankCard", EBankCard.values() );
		model.addAttribute("InputBC", EInputBankCard.values() );
		model.addAttribute("list",  service.getBankCardList() );
		
		if( res != null )
			model.addAttribute("res", res );
		
		return "config/bankcard/bankcard";
	}
	
	@PostMapping(value="Save.html", params = {"no", "name", "bc", "inputBC"})
	public String bankCardSave(RedirectAttributes redirectAttributes, BankCardVO vo) {
		
		final ReturnBasic res = service.saveBankCard(vo);
		
		redirectAttributes.addFlashAttribute("res", res);

		return "redirect:/Config/BankCard/Home.html";
	}
	
	@ResponseBody
	@PostMapping(value="Remove.json", params = {"no"})
	public ReturnBasic bankCardRemove(BankCardVO vo) {
		
		final ReturnBasic res = service.deleteBankCard(vo);

		return res;
	}
}

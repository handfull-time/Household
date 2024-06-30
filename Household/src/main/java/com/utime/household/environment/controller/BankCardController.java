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
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
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
	public String bankCardMain(ModelMap model, @RequestParam(name = "BcType", defaultValue = "Bank") EBankCard bc) {
		
		final String result;
		if( bc == EBankCard.Bank ) {
			result = "bankMain";
		}else {
			result = "cardMain";
		}
		
		return "Environment/bankcard/" + result;
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
	
	@PostMapping(value="ListBank.list")
	public String listBank(ModelMap model) {
		
		model.addAttribute("list",  service.getBankCardList(EBankCard.Bank) );
		
		return "Environment/bankcard/bankList";
	}
	
	@PostMapping(value="ListCard.list")
	public String listCard(ModelMap model) {
		
		model.addAttribute("list",  service.getBankCardList(EBankCard.Card) );

		return "Environment/bankcard/cardList";
	}

	
	@PostMapping(value="DetailBank.layer")
	public String DetailBankLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		final BankCardVO item = service.getBank(no);
		
		model.addAttribute("Companies", service.getBankCompanies() );
		model.addAttribute("BankCards", EBankCard.values() );
		model.addAttribute("AccountTypes", EAccountType.values() );
		
		model.addAttribute("data",  item );
		
		return "Environment/bankcard/bankItem";
	}
	
	@PostMapping(value="DetailCard.layer")
	public String DetailCardLayer(ModelMap model, @RequestParam(value = "no") long no) {
		
		final BankCardVO item = service.getCard(no);
		
		model.addAttribute("Companies", service.getCardCompanies() );
		//model.addAttribute("CardTypes", ECardType.values() );
		model.addAttribute("Banks", service.getBankCardList(EBankCard.Bank) );
		
		model.addAttribute("data",  item );

		return "Environment/bankcard/cardItem";
	}
	
	@ResponseBody
	@PostMapping(value="Save.json")
	public ReturnBasic saveBankCard(@RequestBody BankCardVO vo) {
		
		final ReturnBasic res = service.saveBankCard(vo);

		return res;
	}

	@ResponseBody
	@PostMapping(value="Remove.json")
	public ReturnBasic bankCardRemove(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteBankCard(no);

		return res;
	}
	
	@PostMapping(value="DetailCardItem.layer")
	public String DetailCardItemLayer(ModelMap model, @RequestParam(value = "cardNo") long cardNo, @RequestParam(value = "no") long no) {
		
		final CardItemVO item = service.getCardItem(cardNo, no);
		
		model.addAttribute("CardTypes", ECardType.values() );
		
		model.addAttribute("data",  item );

		return "Environment/bankcard/cardItemOfItem";
	}
	
	@ResponseBody
	@PostMapping(value="SaveCardItem.json")
	public ReturnBasic saveCardItem(@RequestBody CardItemVO vo) {
		
		final ReturnBasic res = service.saveCardItem(vo);

		return res;
	}

	@ResponseBody
	@PostMapping(value="RemoveCardItem.json")
	public ReturnBasic cardItemRemove(@RequestParam(value = "no") long no) {
		
		final ReturnBasic res = service.deleteCardItem(no);

		return res;
	}
}

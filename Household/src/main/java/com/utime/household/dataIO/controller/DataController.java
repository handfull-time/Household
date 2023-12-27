package com.utime.household.dataIO.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.utime.household.dataIO.service.DataIOService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("Data")
public class DataController {
	
	private final DataIOService service;
	
	@GetMapping(value = {"", "/"})
	public String goMain() {
		return "redirect:/Config/BankCard/Home.html";
	}
	
	@GetMapping(value="Home.html")
	public String dataMain(ModelMap model) {
		
		return "data/bankcard/bankcard";
	}


}

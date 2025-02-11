package com.utime.household.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HouseholdController {
	
	@RequestMapping(value = {"/"})
	public String goMain() {
		return "redirect:/Home.html";
	}
	
	@RequestMapping(value = {"Home.html"})
	public String rootMain() {
		return "Root/Home";
	}
	
	@RequestMapping(value = {"List.html"})
	public String rootList() {
		return "Root/list";
	}

}

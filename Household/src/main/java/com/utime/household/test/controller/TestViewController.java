package com.utime.household.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("Test")
public class TestViewController {
	
	@GetMapping("Tailwind.html")
	public String showTailwind(){
		return "test/ViewTest";
	}

}

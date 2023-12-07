package com.utime.household.root.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utime.household.common.vo.ReturnBasic;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("Data")
@RequiredArgsConstructor
public class DataInOutController {

	@ResponseBody
	@RequestMapping(value = "InputData.json")
	public ReturnBasic inputData(@RequestParam("data") String data) {
		final ReturnBasic result = new ReturnBasic();
		
		result.setMessage(data);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "List.json")
	public String inputList(@RequestParam("data") String data) {
		String s = "{"
				+ "  \"data\": ["
				+ "    ["
				+ "      \"Tiger Nixon\","
				+ "      \"System Architect\","
				+ "      \"Edinburgh\","
				+ "      \"5421\","
				+ "      \"2011/04/25\","
				+ "      \"$320,800\""
				+ "    ],"
				+ "    ["
				+ "      \"Garrett Winters\","
				+ "      \"Accountant\","
				+ "      \"Tokyo\","
				+ "      \"8422\","
				+ "      \"2011/07/25\","
				+ "      \"$170,750\""
				+ "    ] }";
		return s;
	}
}

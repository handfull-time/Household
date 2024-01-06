package com.utime.household.dataIO.controller;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdReqDataVO;
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.service.StoreService;
import com.utime.household.environment.vo.ECategoryType;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("IO")
public class DataIOController {
	
	private final DataIOService service;
	private final StoreService stService;
	private final BankCardService bcService;
	
	@GetMapping(value = {"Upload.html"})
	public String rootUploadShow(ModelMap model) {
		model.addAttribute("bankCards", bcService.getBankCardList());
		return "dataIO/upload";
	}

//	@ResponseBody
//	@RequestMapping(value = {"Upload.json"}, method = RequestMethod.POST)
//	public HouseholdDataListResVO rootUploadFile(@RequestParam("bankCard") EInputBankCard bankCard, @RequestParam("uploadFile") MultipartFile file) {
//		HouseholdDataListResVO result = service.upload(bankCard, file );
//		return result;
//	}
	
	@PostMapping(value = {"UploadResult.html"})
	public String rootUploadFile(ModelMap model, @RequestParam("bankCard") long bankCardNo, @RequestParam("uploadFile") MultipartFile file) {
		
		final HouseholdDataListResVO res = service.analyzeData(bankCardNo, file );
		if( res.isError() ) {
			model.addAttribute("res", res);
			return "common/error";
		}

		model.addAttribute("list", res.getList());
		model.addAttribute("bankCard", res.getBcVo());
		model.addAttribute("listCategoryType", ECategoryType.values() );
		model.addAttribute("listCategory", stService.getCategoryList());
		model.addAttribute("listStore", stService.getStoreList());
		
		return "dataIO/uploadResult";
	}

	class GsonDateConverter implements JsonSerializer<Date>, JsonDeserializer<Date> {
		
		private final String FORMAT = "yyyy.MM.dd HH:mm";
		private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT);

		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			
			try {
				return json == null ? null : simpleDateFormat.parse(json.getAsString());
			} catch (ParseException e) {
				throw new JsonParseException(e);
			}
		}

		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(simpleDateFormat.format(src));
		}

	}
			
	@PostMapping(value = {"DoSaveData.html"})
	public String doSaveData(RedirectAttributes redirectAttributes, @RequestParam("jsonData") String json ) {
		
		final Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Date.class, new GsonDateConverter()).create();
		
		final HouseholdReqDataVO vo = gson.fromJson(json, HouseholdReqDataVO.class);
		
		final HouseholdResDataVO res = service.saveCompareData( vo );
		
		if( res.isError() ) {
			return "error";
		}
		
		redirectAttributes.addFlashAttribute("res", res);
		
		return "redirect:/IO/DoSaveResult.html";
	}
	
	@GetMapping(value = {"DoSaveResult.html"})
	public String doSaveResult( ModelMap model, @ModelAttribute(value = "res") HouseholdResDataVO res ) {
		
		model.addAttribute("res", res);
//		return "redirect:/Data/Home.html?bcNo="+vo.getBcNo()+"&begin="+res.getBegin()+"&end="+res.getEnd();
		return "dataIO/saveResult";
	}
	

}

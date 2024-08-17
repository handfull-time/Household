package com.utime.household.dataIO.controller;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.utime.household.common.util.CacheIntervalMap;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.HouseholdReqDataVO;
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.service.CategoryService;
import com.utime.household.environment.service.StoreService;
import com.utime.household.environment.vo.ECategoryType;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("IO")
public class DataIOController {
	
	private final DataIOService service;
	private final StoreService stService;
	private final BankCardService bcService;
	private final CategoryService ctService;
	private final CacheIntervalMap<String, HouseholdDataListResVO> cacheMap = new CacheIntervalMap<>(1L, TimeUnit.HOURS);
	
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
	
	/**
	 * 데이터 분석.
	 * 기존에 화면에 다 갖고 있는 구조였는데( uploadResult4 , uploadDataModal3 파일임) 너무 화면이 복잡해 져서 백엔드에서 갖고 있는 구조로 변경하려 함.
	 * @param request
	 * @param model
	 * @param bankCardNo
	 * @param file
	 * @return
	 */
//	@PostMapping(value = {"UploadResult.html"})
//	public String rootUploadFile(ModelMap model,  @RequestParam("bankCard") long bankCardNo, @RequestParam("uploadFile") MultipartFile file) {
//		
//		final HouseholdDataListResVO res = service.analyzeData(bankCardNo, file );
//		if( res.isError() ) {
//			model.addAttribute("res", res);
//			return "common/error";
//		}
//		
//		model.addAttribute("list", res.getList());
//		model.addAttribute("bankCard", res.getBcVo());
//		model.addAttribute("listCategoryType", ECategoryType.values() );
//		model.addAttribute("listCategory", ctService.getCategoryList());
//		model.addAttribute("listCategorySub", ctService.getSubCategoryList(-1L) );
//		model.addAttribute("listStore", stService.getStoreList());
//		
//		return "dataIO/uploadResult";
//	}
	
	@PostMapping(value = {"UploadResult.html"})
	public String rootUploadFile(HttpServletRequest request,
			ModelMap model,  @RequestParam("bankCard") long bankCardNo, @RequestParam("uploadFile") MultipartFile file) {
		
		final String sessionId = request.getSession().getId();
		
		final HouseholdDataListResVO res = service.analyzeData(bankCardNo, file );
		if( res.isError() ) {
			model.addAttribute("res", res);
			return "common/error";
		}
		
		cacheMap.remove(sessionId);
		cacheMap.put(sessionId, res);

		model.addAttribute("list", res.getList());
		model.addAttribute("bankCard", res.getBcVo());
//		model.addAttribute("listCategoryType", ECategoryType.values() );
//		model.addAttribute("listCategory", ctService.getCategoryList());
//		model.addAttribute("listCategorySub", ctService.getSubCategoryList(-1L) );
//		model.addAttribute("listStore", stService.getStoreList());
		
		return "dataIO/uploadResult";
	}
	
	@PostMapping(value = {"DetailLayer.layer"})
	public String detailLayer(HttpServletRequest request,  ModelMap model, @RequestParam("no") int index) {
		
		final String sessionId = request.getSession().getId();
		if( sessionId == null ) {
			model.addAttribute("res", new ReturnBasic("EDOC0C1", "유효시간 만료. 다시 시도하세요."));
			return "common/error";
		}
		
		final HouseholdDataListResVO listRes = cacheMap.get(sessionId);
		if( listRes == null ) {
			model.addAttribute("res", new ReturnBasic("EDOC0C2", "유효시간 만료. 다시 시도하세요."));
			return "common/error";
		}
		final HouseholdDataVO dataVo = listRes.getList().get(index);
		
		model.addAttribute("data", dataVo );
		model.addAttribute("listCategoryType", ECategoryType.values() );
		model.addAttribute("listCategory", ctService.getCategoryList());
		model.addAttribute("listCategorySub", ctService.getSubCategoryList(-1L) );
		model.addAttribute("listStore", stService.getStoreList());

		return "dataIO/uploadDataModal";
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
	
	@ResponseBody
	@PostMapping(value = {"DoSaveItem.json"})
	public ReturnBasic doSaveData(HttpServletRequest request, @RequestBody HouseholdDataVO data ) {
		
		final String sessionId = request.getSession().getId();
		if( sessionId == null ) {
			return new ReturnBasic("EDOC0W1", "유효시간 만료. 다시 시도하세요.");
		}
		
		final HouseholdDataListResVO listRes = cacheMap.get(sessionId);
		if( listRes == null ) {
			return new ReturnBasic("EDOC0W2", "유효시간 만료. 다시 시도하세요.");
		}
		
		
		int no = (int) (data.getNo() );
		final HouseholdDataVO dataVo = listRes.getList().get( no );
		dataVo.setIo( data.getIo() );
		dataVo.setDscr( data.getDscr() );
		dataVo.setIncluded( data.isIncluded() );
		dataVo.setUseAmount( data.getUseAmount() );
		dataVo.setOriginAmount( data.getOriginAmount() );
		dataVo.getStore().setNo( data.getStore().getNo() );
		dataVo.getCategorySub().setNo( data.getCategorySub().getNo() );
		dataVo.getCategoryOwner().setNo( data.getCategoryOwner().getNo() );
		
		return new ReturnBasic(); 
	}
			
	@PostMapping(value = {"DoSaveData.html"})
	public String doSaveData(HttpServletRequest request, RedirectAttributes redirectAttributes, ModelMap model, @RequestParam("jsonData") String json ) {
		
		final String sessionId = request.getSession().getId();
		if( sessionId == null ) {
			model.addAttribute("res", new ReturnBasic("EDOC0T1", "유효시간 만료. 다시 시도하세요."));
			return "common/error";
		}
		
		final HouseholdDataListResVO listRes = cacheMap.get(sessionId);
		if( listRes == null ) {
			model.addAttribute("res", new ReturnBasic("EDOC0T2", "유효시간 만료. 다시 시도하세요."));
			return "common/error";
		}
		
		final Gson gson = new GsonBuilder().setPrettyPrinting()
				.registerTypeAdapter(Date.class, new GsonDateConverter()).create();
		
		final HouseholdReqDataVO vo = gson.fromJson(json, HouseholdReqDataVO.class);
		final List<HouseholdDataVO> reqList = vo.getList();
		final Set<Long> reqSet = new HashSet<>();
		for( HouseholdDataVO itm : reqList ) {
			reqSet.add( itm.getNo() );
		}
		
		final List<HouseholdDataVO> list = listRes.getList();
		for( int index = list.size()-1 ; index>=0 ; index-- ) {
			if( ! reqSet.contains( Long.valueOf(index) )) {
				list.remove(index);
			}
		}
		
		final HouseholdResDataVO res = service.saveCompareData( listRes );
		
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

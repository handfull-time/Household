package com.utime.household.root.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.root.service.BankCardExtractDataService;
import com.utime.household.root.service.HouseholdService;
import com.utime.household.root.vo.EInputBankCard;
import com.utime.household.root.vo.HouseholdDataListResVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class HouseholdServiceImpl implements HouseholdService{
	
	private final ApplicationContext ctx;
	
	@Override
	public HouseholdDataListResVO upload(EInputBankCard bankCard, MultipartFile file) {
		
		log.info(file.getOriginalFilename());
		
		final BankCardExtractDataService service = ctx.getBean(bankCard.getBean(), BankCardExtractDataService.class);
		
		HouseholdDataListResVO result;
		try {
			result = service.extractData( file );
		} catch (Exception e) {
			log.error("", e);
			result = new HouseholdDataListResVO();
			result.setCode("EHS0A1");
			result.setMessage(e.getMessage());
		}
		
		return result;
	}
	

}

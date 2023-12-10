package com.utime.household.dataIO.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.config.dao.BankCardDao;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.dataIO.service.DataIOService;
import com.utime.household.root.vo.HouseholdDataListResVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class DataIOServiceImpl implements DataIOService{
	
	private final ApplicationContext ctx;
	
	private final BankCardDao bankCardDao;
	
	@Override
	public HouseholdDataListResVO upload(long bankCardNo, MultipartFile file) {
		
		log.info(file.getOriginalFilename());
		
		HouseholdDataListResVO result;

		final BankCardVO bcVo = bankCardDao.getBankCard(bankCardNo);
		if( bcVo == null ) {
			result = new HouseholdDataListResVO();
			
			result.setCodeMessage("EHS0A1", "은행/카드 정보 찾을 수 없습니다.");
			
			return result;
		}
		
		final BankCardExtractDataService service = ctx.getBean(bcVo.getInputBC().getBean(), BankCardExtractDataService.class);
		
		try {
			result = service.extractData( bcVo, file );
			result.setBcVo( bcVo );
		} catch (Exception e) {
			log.error("", e);
			result = new HouseholdDataListResVO();
			result.setCodeMessage("EHS0A2", e.getMessage());
		}
		
		return result;
	}
	

}

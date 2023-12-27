package com.utime.household.dataIO.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.environment.vo.BankCardVO;

public interface BankCardExtractDataService {

	/**
	 * 파일 분석
	 * @param file
	 * @return
	 * @throws Exception
	 */
	HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception;

}

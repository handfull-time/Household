package com.utime.household.dataIO.service.impl;

import org.springframework.web.multipart.MultipartFile;

import com.utime.household.root.vo.HouseholdDataListResVO;

public interface BankCardExtractDataService {

	/**
	 * 파일 분석
	 * @param file
	 * @return
	 * @throws Exception
	 */
	HouseholdDataListResVO extractData(MultipartFile file) throws Exception;

}

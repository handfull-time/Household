package com.utime.household.dataIO.service;

import org.springframework.web.multipart.MultipartFile;

import com.utime.household.dataIO.vo.HouseholdDataListResVO;

/**
 * 자료 입출력 서비스
 */
public interface DataIOService {
	
	/**
	 * 데이터 분석
	 * @param bankCardNo
	 * @param file
	 * @return
	 */
	HouseholdDataListResVO upload(long bankCardNo, MultipartFile file);
}

package com.utime.household.dataIO.service;

import org.springframework.web.multipart.MultipartFile;

import com.utime.household.root.vo.EInputBankCard;
import com.utime.household.root.vo.HouseholdDataListResVO;

/**
 * 자료 입출력 서비스
 */
public interface DataIOService {
	
	HouseholdDataListResVO upload(EInputBankCard bankCard, MultipartFile file);
}

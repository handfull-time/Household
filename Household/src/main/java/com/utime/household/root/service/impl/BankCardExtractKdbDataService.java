package com.utime.household.root.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.root.service.BankCardExtractDataService;
import com.utime.household.root.vo.HouseholdDataListResVO;
import com.utime.household.root.vo.InputBankCardDefine;

@Service(InputBankCardDefine.NameKdb)
class BankCardExtractKdbDataService implements BankCardExtractDataService{

	@Override
	public HouseholdDataListResVO extractData(MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

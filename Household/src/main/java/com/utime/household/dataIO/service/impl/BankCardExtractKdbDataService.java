package com.utime.household.dataIO.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.config.vo.BankCardVO;
import com.utime.household.root.vo.HouseholdDataListResVO;
import com.utime.household.root.vo.InputBankCardDefine;

@Service(InputBankCardDefine.NameKdb)
class BankCardExtractKdbDataService implements BankCardExtractDataService{

	@Override
	public HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

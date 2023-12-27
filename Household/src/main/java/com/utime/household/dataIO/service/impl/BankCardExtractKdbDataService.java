package com.utime.household.dataIO.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.InputBankCardDefine;
import com.utime.household.environment.vo.BankCardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(InputBankCardDefine.NameKdb)
class BankCardExtractKdbDataService implements BankCardExtractDataService{

	@Override
	public HouseholdDataListResVO extractData(BankCardVO bcVo, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

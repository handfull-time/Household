package com.utime.household.dataIO.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.utime.household.dataIO.dao.DataIODao;
import com.utime.household.dataIO.service.DataService;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.dao.BankCardDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class DataServiceImpl implements DataService{

	@Override
	public HouseholdDataListResVO getHouseholdDataList(OuputReqVO reqVo) {
		
		return null;
	}

}

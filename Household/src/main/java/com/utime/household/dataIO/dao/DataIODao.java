package com.utime.household.dataIO.dao;

import java.util.List;

import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.vo.BankCardVO;

public interface DataIODao {

	int insertHouseholdData(BankCardVO bcVo, List<HouseholdDataVO> vo ) throws Exception;
	
	/**
	 * 데이터 조회
	 * @param bankCardNo
	 * @param file
	 * @return
	 */
	HouseholdDataListResVO getHouseholdDataList(OuputReqVO reqVo);
	
	
}

package com.utime.household.dataIO.dao;

import java.util.Date;
import java.util.List;

import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;

public interface DataIODao {

	/**
	 * 데이터 다수 추가. 내부에서 중복 검사 시행
	 * @param bcVo
	 * @param list
	 * @param minDate
	 * @param maxDate
	 * @return
	 * @throws Exception
	 */
	int addHouseholdData(List<HouseholdDataVO> list, Date minDate, Date maxDate ) throws Exception;
	
	/**
	 * 데이터 추가
	 * @param vo
	 * @return
	 */
	int addHouseholdData( HouseholdDataVO vo ) throws Exception;
	
	/**
	 * 데이터 조회
	 * @param bankCardNo
	 * @param file
	 * @return
	 */
	List<HouseholdDataVO> getHouseholdDataList(OuputReqVO reqVo);
	
	
}

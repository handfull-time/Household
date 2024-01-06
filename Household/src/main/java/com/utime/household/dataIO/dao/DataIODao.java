package com.utime.household.dataIO.dao;

import java.util.Date;
import java.util.List;

import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;

public interface DataIODao {

	/**
	 * 데이터 다수 추가. 내부에서 중복 검사 시행
	 * @param outResult 결과 데이터
	 * @param list 입력 데이터
	 * @param beginDate 시작일
	 * @param endDate 종료일
	 * @return
	 * @throws Exception
	 */
	int addHouseholdData(HouseholdResDataVO outResult, List<HouseholdDataVO> list, Date beginDate, Date endDate ) throws Exception;
	
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

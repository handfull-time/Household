package com.utime.household.dataIO.service;

import org.springframework.web.multipart.MultipartFile;

import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdReqDataVO;
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;

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
	HouseholdDataListResVO analyzeData(long bankCardNo, MultipartFile file);

	/**
	 * 비교 후 데이터 저장
	 * @param vo
	 * @return
	 */
	HouseholdResDataVO saveCompareData(HouseholdReqDataVO vo);
	
	/**
	 * 비교 없이 바로 데이터 저장
	 * @param vo
	 * @return
	 */
	HouseholdResDataVO saveDirectData(HouseholdReqDataVO vo);
	
	/**
	 * 데이터 조회
	 * @param bankCardNo
	 * @param file
	 * @return
	 */
	HouseholdDataListResVO getHouseholdDataList(OuputReqVO reqVo);
}

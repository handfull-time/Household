package com.utime.household.dataIO.mapper;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;

/**
 * 데이터 관리 Mapper
 */
@Mapper
public interface DataIOMapper {
	
	/**
	 * 가계부 메인 데이터 생성
	 * @return
	 */
	int createRecord();
	
	/**
	 * 가계부 임시 데이터 생성
	 * @return
	 */
	int createRecordTemp();
	
	/**
	 * 모든 데이터 삭제
	 * @return
	 */
	int deleteRecordTemp();
	
	//Map<String, Date> selectMaxMain
	
	/**
	 * 임시 데이터 추가
	 * @param vo
	 * @return
	 */
	int insertHouseholdTempData( HouseholdDataVO vo ) throws SQLException;
	
	/**
	 * 원본 데이터와 임시 데이터의 동일 데이터 조회
	 * @param reqVo
	 * @return
	 */
	List<HouseholdDataVO> selectHouseholdSameDataList(@Param("begin") Date beginDate, @Param("end") Date endDate);
	
	/**
	 * 원본 데이터와 임시 데이터의 다른 데이터 조회
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<HouseholdDataVO> selectHouseholdDeferentDataList(@Param("begin") Date beginDate, @Param("end") Date endDate);
	
	/**
	 * 원본 데이터에 없는 임시 데이터 추가.
	 * @param reqVo
	 * @return
	 */
	int insertHouseholdTempToOriginData(@Param("begin") Date beginDate, @Param("end") Date endDate);
	
	/**
	 * 데이터 추가
	 * @param vo
	 * @return
	 */
	int insertHouseholdData( HouseholdDataVO vo ) throws SQLException;

	/**
	 * 자료 조회
	 * @param reqVo
	 * @return
	 */
	List<HouseholdDataVO> selectHouseholdDataList(OuputReqVO reqVo);
	
	
}

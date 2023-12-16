package com.utime.household.dataIO.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.utime.household.dataIO.vo.HouseholdDataVO;

/**
 * 데이터 관리 Mapper
 */
@Mapper
public interface DataIOMapper {
	
	/**
	 * 데이터 추가
	 * @param vo
	 * @return
	 */
	int insertHouseholdData( HouseholdDataVO vo ) throws SQLException;
	
	
}

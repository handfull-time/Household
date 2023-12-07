package com.utime.household.root.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 최초 필수 테이블 관련 Mapper
 */
@Mapper
public interface HouseholdMapper {
	
	
	/**
	 * 가계부 메인 데이터 생성
	 * @return
	 */
	public int createRecord();




}

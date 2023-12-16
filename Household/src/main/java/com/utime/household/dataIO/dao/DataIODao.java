package com.utime.household.dataIO.dao;

import java.util.List;

import com.utime.household.dataIO.vo.HouseholdDataVO;

public interface DataIODao {

	int insertHouseholdData( List<HouseholdDataVO> vo ) throws Exception;
}

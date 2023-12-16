package com.utime.household.dataIO.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.dataIO.dao.DataIODao;
import com.utime.household.dataIO.mapper.DataIOMapper;
import com.utime.household.dataIO.vo.HouseholdDataVO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class DataIODaoImpl implements DataIODao{

	final DataIOMapper mapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertHouseholdData(List<HouseholdDataVO> list) throws Exception {

		int result = 0;
		if( list == null || list.size() < 1 ) {
			return result;
		}

		for( HouseholdDataVO item : list) {
			result += mapper.insertHouseholdData(item);
		}

		return result;
	}
}

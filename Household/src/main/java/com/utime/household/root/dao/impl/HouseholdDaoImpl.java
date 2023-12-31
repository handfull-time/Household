package com.utime.household.root.dao.impl;

import org.springframework.stereotype.Repository;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.root.dao.HouseholdDao;
import com.utime.household.root.mapper.HouseholdMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class HouseholdDaoImpl implements HouseholdDao {
	
	private final CommonMapper common;
	
	private final HouseholdMapper household;
	
	
	@Override
	public void init() {
		common.dropTable("HH_CATEGORY");
		common.dropTable("HH_RECORD");
		common.dropTable("HH_BANK_CARD");
		common.dropTable("HH_USAGE_STORE");
		
		System.exit(0);
	}
}

package com.utime.household.root.dao.impl;

import org.springframework.stereotype.Repository;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.root.dao.HouseholdDao;
import com.utime.household.root.mapper.HouseholdMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class HouseholdDaoImpl implements HouseholdDao {
	
	private final CommonMapper common;
	
	private final HouseholdMapper household;
	
	@PostConstruct
	private void construct() {
		try {
			
			// 가계부 메인 데이터
			if( ! common.existTable("HH_RECORD") ) {
				log.info("HH_RECORD 생성");
				household.createRecord();
				common.createIndex("HH_RECORD_REG_DATE_INDX", "HH_RECORD", "DEAL_DATE");
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	@Override
	public void init() {
		common.dropTable("HH_CATEGORY");
		common.dropTable("HH_RECORD");
		common.dropTable("HH_BANK_CARD");
		common.dropTable("HH_USAGE_STORE");
		
		System.exit(0);
	}
}

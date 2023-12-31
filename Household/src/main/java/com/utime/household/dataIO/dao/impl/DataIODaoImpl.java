package com.utime.household.dataIO.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.dataIO.dao.DataIODao;
import com.utime.household.dataIO.mapper.DataIOMapper;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.mapper.StoreMapper;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.StoreVO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class DataIODaoImpl implements DataIODao{

	private final CommonMapper common;
	
	private final DataIOMapper mapper;
	
	private final StoreMapper storeMapper;
	
	@PostConstruct
	private void construct() {
		try {
			
			// 가계부 메인 데이터
			if( ! common.existTable("HH_RECORD") ) {
				log.info("HH_RECORD 생성");
				mapper.createRecord();
				common.createIndex("HH_RECORD_REG_DATE_INDX", "HH_RECORD", "DEAL_DATE");
			}
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int insertHouseholdData(BankCardVO bcVo, List<HouseholdDataVO> list) throws Exception {

		int result = 0;
		if( list == null || list.size() < 1 ) {
			return result;
		}
		
		// Store 추가할때 Store 이름과 저장할 이름 번호
		final Map<String, Long> mapStore = new HashMap<>();
		for( HouseholdDataVO item : list) {
			final StoreVO store = item.getStore();
			if( store.isNew() ) {
				final String storeName = store.getName();
				if( mapStore.containsKey(storeName) ) {
					log.info(storeName + " 동일 이름");
					store.setNo( mapStore.get(storeName) );
				}else {
					log.info(storeName + " 새로 추가");
					if( storeMapper.insertStore(store) > 0 ) {
						mapStore.put(storeName, Long.valueOf(store.getNo()));
					}
				}
			}
		}

		for( HouseholdDataVO item : list) {
			item.setBcVo(bcVo);
			result += mapper.insertHouseholdData(item);
		}

		return result;
	}
	
	@Override
	public List<HouseholdDataVO> getHouseholdDataList(OuputReqVO reqVo) {
		
		if( HouseholdUtils.isNotEmpty(reqVo.getBegin()) && HouseholdUtils.isNotEmpty(reqVo.getEnd()) ) {
			final String begin = reqVo.getBegin();
			if( begin.length() == 8) {
				reqVo.setBegin( begin.substring(0, 4) + "-" + begin.substring(4, 6) + "-" + begin.substring(6, 8) );
			}
	        
			final String end = reqVo.getEnd();
			if( end.length() == 8 ) {
				reqVo.setEnd( end.substring(0, 4) + "-" + end.substring(4, 6) + "-" + end.substring(6, 8) );
			}
		}
		
		return mapper.getHouseholdDataList( reqVo );
	}
}

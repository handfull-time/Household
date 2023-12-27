package com.utime.household.dataIO.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.dataIO.dao.DataIODao;
import com.utime.household.dataIO.mapper.DataIOMapper;
import com.utime.household.dataIO.vo.HouseholdDataListResVO;
import com.utime.household.dataIO.vo.HouseholdDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.mapper.StoreMapper;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class DataIODaoImpl implements DataIODao{

	final DataIOMapper mapper;
	
	final StoreMapper storeMapper;

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
	public HouseholdDataListResVO getHouseholdDataList(OuputReqVO reqVo) {
		HouseholdDataListResVO result = null;
		List<HouseholdDataVO> list = mapper.getHouseholdDataList( reqVo );
		return null;
	}
}

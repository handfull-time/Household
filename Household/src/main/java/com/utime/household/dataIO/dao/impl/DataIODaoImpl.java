package com.utime.household.dataIO.dao.impl;

import java.util.Date;
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
import com.utime.household.dataIO.vo.HouseholdResDataVO;
import com.utime.household.dataIO.vo.OuputReqVO;
import com.utime.household.environment.mapper.CategoryMapper;
import com.utime.household.environment.mapper.StoreMapper;
import com.utime.household.environment.vo.CategoryVO;
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
	
	private final CategoryMapper categoryMapper;
	
	@PostConstruct
	private void construct() {
		try {
			
			// 가계부 메인 데이터
			final String tableName = "HH_RECORD";
			if( ! common.existTable(tableName) ) {
				log.info("HH_RECORD 생성");
				mapper.createRecord();
				common.createIndex("HH_RECORD_DEAL_DATE_INDX", tableName, "DEAL_DATE");
				common.createIndex("HH_RECORD_BANK_CARD_NO_INDX", tableName, "BANK_CARD_NO");
				common.createIndex("HH_RECORD_CATEGORY_NO_INDX", tableName, "CATEGORY_NO");
				common.createIndex("HH_RECORD_STORE_NO_INDX", tableName, "STORE_NO");
			}
			
			if( ! common.existTable("HH_RECORD_TEMP") ) {
				log.info("HH_RECORD_TEMP 생성");
				mapper.createRecordTemp();
				common.createIndex("HH_RECORD_TEMP_DEAL_DATE_INDX", "HH_RECORD_TEMP", "DEAL_DATE");
			}
			
			
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	private void genericHouseList(final Map<Long, CategoryVO> cMap, final Map<Long, StoreVO> sMap, List<HouseholdDataVO> list) {
		for( HouseholdDataVO item : list ) {
			final StoreVO store = sMap.get( item.getStore().getNo() );
			if( store != null ) {
				item.getStore().setName(store.getName());
			}else {
				item.getStore().setName("");
			}
			final CategoryVO category = cMap.get( item.getCategory().getNo() );
			if( category != null ) {
				item.setCategory(category);
			}else {
				item.setCategory(category);
			}
		}
	}
	

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addHouseholdData(HouseholdResDataVO outResult, List<HouseholdDataVO> list, Date beginDate, Date endDate) throws Exception {

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
		
		// 임시 테이블 클리어.
		mapper.deleteRecordTemp();
		
		int addCnt = 0;
		// 임시 테이블에 추가.
		for( HouseholdDataVO item : list) {
			addCnt += mapper.insertHouseholdTempData(item);
		}
		log.info("임시 추가 갯수 " + addCnt);
		
		// 동일 데이터 비교
		final List<HouseholdDataVO> sameList = mapper.selectHouseholdSameDataList(beginDate, endDate);
//		log.info("동일 데이터 " + sameList);
		outResult.setExList(sameList);
		
		final List<HouseholdDataVO> deferentList = mapper.selectHouseholdDeferentDataList(beginDate, endDate);
//		log.info("추가될 데이터 " + deferentList);
		outResult.setAddList(deferentList);
		
		
		result = mapper.insertHouseholdTempToOriginData(beginDate, endDate);
		log.info("추가된 데이터 " + result);
		
		// 임시 테이블 클리어.
		mapper.deleteRecordTemp();
		
		final Map<Long, CategoryVO> cMap = new HashMap<>();
		final Map<Long, StoreVO> sMap = new HashMap<>();
		{
			final List<CategoryVO> cList = categoryMapper.getCategoryList(null);
			for( CategoryVO item : cList ) {
				cMap.put(item.getNo(), item);
			}
			
			final List<StoreVO> sList = storeMapper.getStoreList(-1L);
			for( StoreVO item : sList ) {
				sMap.put(item.getNo(), item);
			}
		}
		
		this.genericHouseList(cMap, sMap, sameList);
		
		this.genericHouseList(cMap, sMap, deferentList);

		return result;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addHouseholdData(HouseholdDataVO vo) throws Exception {
		return mapper.insertHouseholdData(vo);
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
		
		return mapper.selectHouseholdDataList( reqVo );
	}
}

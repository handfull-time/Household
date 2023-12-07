package com.utime.household.config.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.config.dao.StoreDao;
import com.utime.household.config.mapper.StoreMapper;
import com.utime.household.config.vo.StoreVO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class StoreDaoImpl implements StoreDao{

	private final CommonMapper common;
	
	private final StoreMapper mapper;
	
	@PostConstruct
	private void construct() {
		try {
			
			//사용처 매칭
			if( ! common.existTable("HH_USAGE_STORE") ) {
				log.info("USAGE_STORE 생성");
				mapper.createMatchUsageStore();
			}

		}catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public List<StoreVO> getStoreList() {
		return mapper.getStoreList();
	}

	@Override
	@Transactional( rollbackFor = Exception.class )
	public int saveStore(StoreVO vo) throws Exception {
		
		int result;
		if( vo.getNo() < 0L ) {
			
			if( mapper.sameCheckStore(vo) ) {
				throw new Exception("동일 정보 있다.");
			}
			
			result = mapper.insertStore(vo);
		}else {
			result = mapper.updateStore(vo);
		}
		
		return result;
	}
	
	private StoreVO getSplit(String name, String key) {
		StoreVO result = null;
		if( name.indexOf(key) > 0 ){
			String [] split = name.split(key);
			for( String item : split ) {
				if( HouseholdUtils.isNotEmpty(item) ) {
					result = mapper.getStore( name );
	
					if( result != null ) {
						return result;
					}
				}
			}
		}
		
		return result;
		
	}
	
	@Override
	public StoreVO getStore(String name) {
		
		StoreVO result;
		if( HouseholdUtils.isEmpty(name) ) {
			result = new StoreVO();
			return result;
		}
		
		result = mapper.getStore( name );
		if( result != null ) {
			return result;
		}
		
		result = this.getSplit(name, " ");
		if( result != null ) {
			return result;
		}
		
		result = this.getSplit(name, ",");
		if( result != null ) {
			return result;
		}
		
		result = new StoreVO();
		return result;
	}
}

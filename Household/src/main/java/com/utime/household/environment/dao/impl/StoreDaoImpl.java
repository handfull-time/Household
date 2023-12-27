package com.utime.household.environment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.environment.dao.StoreDao;
import com.utime.household.environment.mapper.StoreMapper;
import com.utime.household.environment.vo.StoreVO;

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
	public List<StoreVO> getStoreList(long categoryNo) {
		return mapper.getStoreList(categoryNo);
	}

	@Override
	public List<StoreVO> getStoreList() {
		return mapper.getStoreList(-1L);
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
	
	@Override
	public StoreVO getStore(long no) {
		
		return mapper.getStore(no);
	}
	
	@Override
	@Transactional( rollbackFor = Exception.class )
	public int deleteStore(long no) throws Exception {
		return mapper.deleteStore(no);
	}
}

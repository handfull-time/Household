package com.utime.household.environment.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.dao.StoreDao;
import com.utime.household.environment.service.StoreService;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class StoreServiceImpl implements StoreService {

	private final StoreDao dao;
	
	@Override
	public List<StoreVO> getStoreList() {
		return dao.getStoreList();
	}
	

	@Override
	public List<StoreVO> getStoreList(long categoryNo) {
		return dao.getStoreList(categoryNo);
	}
	
	@Override
	public List<StoreVO> getStoreList(CategorySubVO category) {
		return dao.getStoreList(category);
	}

	@Override
	public ReturnBasic saveStore(StoreVO vo) {
		final ReturnBasic result = new ReturnBasic();
		
		if( HouseholdUtils.isEmpty( vo.getName() )) {
			result.setCodeMessage("ESS0U1", "이름은 필수");
			return result;
		}
		
		vo.setName( vo.getName().trim() );
		
		try {
			this.dao.saveStore(vo);
		} catch (Exception e) {
			result.setCodeMessage("ESS0U2", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public ReturnBasic deleteStore(long no) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteStore(no);
		} catch (Exception e) {
			result.setCodeMessage("ESS0Y1", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public StoreVO getStore(long no) {
		final StoreVO result;
		
		if( no < 0L ) {
			result = new StoreVO();
			result.setNo(no);
		}else {
			result = this.dao.getStore(no); 
		}
		
		return result;
	}

}

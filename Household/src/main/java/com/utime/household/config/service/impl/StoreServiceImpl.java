package com.utime.household.config.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.dao.CategoryDao;
import com.utime.household.config.dao.StoreDao;
import com.utime.household.config.service.StoreService;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.StoreVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class StoreServiceImpl implements StoreService {

	private final StoreDao dao;
	
	private final CategoryDao cd;

	@Override
	public List<StoreVO> getStoreList() {
		return dao.getStoreList();
	}
	
	@Override
	public List<CategoryVO> getCategoryList() {
		
		return cd.getCategoryList(null);
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
}

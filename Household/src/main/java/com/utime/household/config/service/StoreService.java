package com.utime.household.config.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.StoreVO;

public interface StoreService {
	
	List<StoreVO> getStoreList();
	
	List<CategoryVO> getCategoryList();
	
	ReturnBasic saveStore(StoreVO vo);

	
}

package com.utime.household.config.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.StoreVO;

public interface StoreService {
	
	List<StoreVO> getStoreList();
	
	List<CategoryVO> getCategoryList();

	/**
	 * 저장
	 * @param vo
	 * @return
	 */
	ReturnBasic saveStore(StoreVO vo);

	/**
	 * 삭제
	 * @param vo
	 * @return
	 */
	ReturnBasic deleteStore(StoreVO vo);
}

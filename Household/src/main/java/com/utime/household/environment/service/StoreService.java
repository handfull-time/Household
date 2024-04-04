package com.utime.household.environment.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.StoreVO;

public interface StoreService {
	
	
	List<StoreVO> getStoreList();
	
	List<StoreVO> getStoreList(long categoryNo);

	List<StoreVO> getStoreList(CategorySubVO category);

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
	ReturnBasic deleteStore(long no);

	/**
	 * 상세 정보
	 * @param no
	 * @return
	 */
	StoreVO getStore(long no);

}

package com.utime.household.config.dao;

import java.util.List;

import com.utime.household.config.vo.StoreVO;

public interface StoreDao {

	/**
	 * 목록 조회
	 * @return
	 */
	List<StoreVO> getStoreList();
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int saveStore(StoreVO vo)throws Exception;
	
	
	/**
	 * EInputBankCard 일치하는 정보 조회
	 * @param inputBC
	 * @return
	 */
	StoreVO getStore( String name );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteStore(StoreVO vo)throws Exception;
	
}


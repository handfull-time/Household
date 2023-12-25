package com.utime.household.config.dao;

import java.util.List;

import com.utime.household.config.vo.StoreVO;

public interface StoreDao {

	/**
	 * 목록 조회
	 * @return
	 */
	List<StoreVO> getStoreList(long categoryNo);

	/**
	 * 목록 전체 조회
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
	 * 상세 정보
	 * @param no
	 * @return
	 */
	StoreVO getStore(long no);
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteStore(long no)throws Exception;

	
}


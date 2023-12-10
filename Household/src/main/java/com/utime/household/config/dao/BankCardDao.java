package com.utime.household.config.dao;

import java.util.List;

import com.utime.household.config.vo.BankCardVO;

public interface BankCardDao {

	/**
	 * 목록 조회
	 * @return
	 */
	List<BankCardVO> getBankCardList();
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int saveBankCard(BankCardVO vo)throws Exception;
	
	/**
	 * 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getBankCard( long no );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteBankCard(BankCardVO vo)throws Exception;

}


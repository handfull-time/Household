package com.utime.household.config.dao;

import java.util.List;

import com.utime.household.config.vo.BankCardVO;
import com.utime.household.root.vo.EInputBankCard;

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
	 * EInputBankCard 일치하는 정보 조회
	 * @param inputBC
	 * @return
	 */
	BankCardVO getBankCard( EInputBankCard inputBC );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteBankCard(BankCardVO vo)throws Exception;
}


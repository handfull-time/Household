package com.utime.household.environment.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.EBankCard;

public interface BankCardService {

	/**
	 * 전체 내역 조회
	 * @return
	 */
	List<BankCardVO> getBankCardList();
	
	/**
	 * 내역 조회
	 * @return
	 */
	List<BankCardVO> getBankCardList(EBankCard bc);

	/**
	 * 저장
	 * @param vo
	 * @return
	 */
	ReturnBasic saveBankCard(BankCardVO vo);
	
	/**
	 * 삭제
	 * @param no
	 * @return
	 */
	ReturnBasic deleteBankCard(long no);

	/**
	 * 상세 정보 얻기
	 * @param vo
	 * @return
	 */
	BankCardVO getBankCard(long no);

}

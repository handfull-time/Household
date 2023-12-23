package com.utime.household.config.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.EBankCard;

public interface BankCardService {

	List<BankCardVO> getBankCardList();
	
	List<BankCardVO> getBankCardList(EBankCard bc);

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

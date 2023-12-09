package com.utime.household.config.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.vo.BankCardVO;

public interface BankCardService {

	List<BankCardVO> getBankCardList();
	
	ReturnBasic saveBankCard(BankCardVO vo);
	
	ReturnBasic deleteBankCard(BankCardVO vo);
}

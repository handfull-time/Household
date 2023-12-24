package com.utime.household.config.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.dao.BankCardDao;
import com.utime.household.config.service.BankCardService;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.EBankCard;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class BankCardServiceImpl implements BankCardService {

	private final BankCardDao dao;

	@Override
	public List<BankCardVO> getBankCardList() {
		return dao.getBankCardList(null);
	}
	
	@Override
	public List<BankCardVO> getBankCardList(EBankCard bc) {
		return dao.getBankCardList(bc);
	}

	@Override
	public ReturnBasic saveBankCard(BankCardVO vo) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.saveBankCard(vo);
		} catch (Exception e) {
			result.setCodeMessage("EPS0P1", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public ReturnBasic deleteBankCard(long no) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteBankCard(no);
		} catch (Exception e) {
			result.setCodeMessage("EPS0S1", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getBankCard(long no) {
		BankCardVO result;
		
		if( no < 0L ) {
			result = new BankCardVO();
			result.setNo(no);
			result.setBc(EBankCard.Bank);
			result.setInputBC(null);
		}else {
			result = this.dao.getBankCard(no);
		}
		
		return result;
	}
	
}

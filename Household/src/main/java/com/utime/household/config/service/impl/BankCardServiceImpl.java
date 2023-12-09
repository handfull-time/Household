package com.utime.household.config.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.dao.BankCardDao;
import com.utime.household.config.service.BankCardService;
import com.utime.household.config.vo.BankCardVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class BankCardServiceImpl implements BankCardService {

	private final BankCardDao dao;

	@Override
	public List<BankCardVO> getBankCardList() {
		return dao.getBankCardList();
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
	public ReturnBasic deleteBankCard(BankCardVO vo) {
		final ReturnBasic result = new ReturnBasic();
		try {
			this.dao.deleteBankCard(vo);
		} catch (Exception e) {
			result.setCodeMessage("EPS0S1", e.getMessage());
		}
		
		return result;
	}
}

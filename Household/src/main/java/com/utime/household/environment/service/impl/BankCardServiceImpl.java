package com.utime.household.environment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.service.BankCardService;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.BankVO;
import com.utime.household.environment.vo.CardVO;
import com.utime.household.environment.vo.CompanyVO;
import com.utime.household.environment.vo.EAccountType;
import com.utime.household.environment.vo.EBankCard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
			log.error("", e);
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
			log.error("", e);
			result.setCodeMessage("EPS0S1", e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getBankCard(long no) {
		final BankCardVO result;
		
		if( no < 0L ) {
			result = new BankCardVO();
			result.setNo(no);
			
			if( no == -1L ) {
				// 은행 추가.
				result.setBc(EBankCard.Bank);
				final BankVO bank = new BankVO();
				result.setBank(bank);
				bank.setAccountType(EAccountType.Ordinary);
				
			}else {
				// 카드 추가.
				result.setBc(EBankCard.Card);
				final CardVO card = new CardVO();
				result.setCard(card);
				final BankCardVO b = new BankCardVO();
				b.setNo(-1);
				card.setBank(b);
			}
			
		}else {
			result = this.dao.getBankCard(no);
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getBank(long no) {
		final BankCardVO result;
		if( no < 0L ) {
			result = new BankCardVO();
			result.setNo(no);
			result.setBc(EBankCard.Bank);
			
			final BankVO bank = new BankVO();
			bank.setNo(no);
			
			final List<CompanyVO> banks = getBankCompanies();
			if( banks != null && ! banks.isEmpty() ) {
				bank.setBankCompay(banks.get(0));
			}else {
				final CompanyVO company = new CompanyVO();
				company.setNo(no);
				company.setName("미정");
				bank.setBankCompay(company);
			}
			bank.setAccountType(EAccountType.Ordinary);
			
			result.setBank( bank );
			
		}else {
			result = this.dao.getBankCard(no);
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getCard(long no) {
		final BankCardVO result;
		if( no < 0L ) {
			result = new BankCardVO();
			result.setNo(no);
			result.setBc(EBankCard.Card);
			
			final CardVO card = new CardVO();
			card.setNo(no);
			
			final List<CompanyVO> cards = this.getCardCompanies();
			if( cards != null && ! cards.isEmpty() ) {
				card.setCardCompany(cards.get(0));
			}else {
				final CompanyVO company = new CompanyVO();
				company.setNo(no);
				company.setName("미정");
				card.setCardCompany(company);
			}
			card.setCards(new ArrayList<>());
			
			result.setCard(card);
		}else {
			result = this.dao.getBankCard(no);
		}
		
		return result;
	}
	
	@Override
	public List<CompanyVO> getBankCompanies() {
		return this.dao.getBankCompanyList();
	}
	
	@Override
	public List<CompanyVO> getCardCompanies() {
		return this.dao.getCardCompanyList();
	}
	
}

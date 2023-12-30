package com.utime.household.environment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.mapper.BankCardMapper;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.EBankCard;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class BankCardDaoImpl implements BankCardDao{

	private final CommonMapper common;
	
	private final BankCardMapper mapper;
	
	@PostConstruct
	private void construct() {
		try {
			
			if( ! common.existTable("HH_BANK_CARD") ) {
				log.info("HH_BANK_CARD 생성");
				mapper.createBankCard();
			}

		}catch (Exception e) {
			log.error("", e);
		}
	}

	@Override
	public List<BankCardVO> getBankCardList(EBankCard bc) {
		return mapper.getBankCardList(bc);
	}

	@Override
	@Transactional( rollbackFor = Exception.class )
	public int saveBankCard(BankCardVO vo) throws Exception {
		
		int result;
		if( vo.getNo() < 0L ) {
			
			if( mapper.sameCheckBankCard(vo) ) {
				throw new Exception("동일 정보 있다.");
			}
			
			result = mapper.insertBankCard(vo);
		}else {
			result = mapper.updateBankCard(vo);
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getSimpleBankCard(long no) {
		return mapper.getSimpleBankCard( no );
	}
	
	@Override
	public BankCardVO getBankCard(long no) {
		
		return mapper.getBankCard( no );
	}
	
	@Override
	public int deleteBankCard(long no) throws Exception {
		
		return mapper.deleteBankCard(no);
	}
}

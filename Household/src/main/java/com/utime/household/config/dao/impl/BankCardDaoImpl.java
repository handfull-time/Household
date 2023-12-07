package com.utime.household.config.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.config.dao.BankCardDao;
import com.utime.household.config.mapper.BankCardMapper;
import com.utime.household.config.vo.BankCardVO;
import com.utime.household.root.vo.EInputBankCard;

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
	public List<BankCardVO> getBankCardList() {
		return mapper.getBankCardList();
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
	public BankCardVO getBankCard(EInputBankCard inputBC) {
		
		return mapper.getBankCard( inputBC );
	}
}

package com.utime.household.environment.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.dataIO.vo.InputBankCardDefine;
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.mapper.BankCardMapper;
import com.utime.household.environment.mapper.InitBankCardMapper;
import com.utime.household.environment.mapper.SequenceMapper;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.BankVO;
import com.utime.household.environment.vo.CompanyVO;
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
	
	private final InitBankCardMapper initMapper;
	
	private final SequenceMapper sequenceMapper;
	
	private final String KeyBankCardSeq = "BankCardNo";

	
	private synchronized long getNextValueSequence() {
		sequenceMapper.updateIncrementValue(KeyBankCardSeq);
		
		return sequenceMapper.selectCurrentValue(KeyBankCardSeq);
	}
	
	@PostConstruct
	private void construct() {
		
		try {
			
			if( ! common.existTable("SEQUENCE_TABLE") ) {
				log.info("SEQUENCE_TABLE 생성");
				sequenceMapper.createSequence();
				sequenceMapper.registSequence(KeyBankCardSeq, 0L);
			}
			
			
			if( ! common.existTable("HH_BANK_KIND") ) {
				log.info("HH_BANK_KIND 생성");
				initMapper.createBankKind();
				
				mapper.insertBankKind("KB국민은행", InputBankCardDefine.NameKbBank);
				mapper.insertBankKind("하나은행", null);
				mapper.insertBankKind("신한은행", null);
				mapper.insertBankKind("우리은행", InputBankCardDefine.NameWooriBank);
				mapper.insertBankKind("IBK기업은행", null);
				mapper.insertBankKind("NH농협은행", null);
				mapper.insertBankKind("KDB산업은행", InputBankCardDefine.NameKdb);
				mapper.insertBankKind("SC제일은행", null);
				mapper.insertBankKind("BNK부산은행", null);
				mapper.insertBankKind("DGB대구은행", null);
				mapper.insertBankKind("수협은행", null);
				mapper.insertBankKind("카카오뱅크", InputBankCardDefine.NameKakaoBank);
				mapper.insertBankKind("BNK경남은행", null);
				mapper.insertBankKind("한국씨티은행", null);
				mapper.insertBankKind("광주은행", null);
				mapper.insertBankKind("토스뱅크", null);
				mapper.insertBankKind("전북은행", null);
				mapper.insertBankKind("케이뱅크", null);
				mapper.insertBankKind("제주은행", null);
				mapper.insertBankKind("우체국", null);
				mapper.insertBankKind("새마을금고", null);
				mapper.insertBankKind("저축은행", null);
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		try {
			
			if( ! common.existTable("HH_CARD_KIND") ) {
				log.info("HH_CARD_KIND 생성");
				initMapper.createCardKind();
				
				mapper.insertCardKind("KB국민카드", null);
				mapper.insertCardKind("신한카드", InputBankCardDefine.NameShinhanCard);
				mapper.insertCardKind("하나카드", null);
				mapper.insertCardKind("롯데카드", null);
				mapper.insertCardKind("BC카드", null);
				mapper.insertCardKind("NH농협카드", null);
				mapper.insertCardKind("삼성카드", InputBankCardDefine.NameSamsung);
				mapper.insertCardKind("현대카드", null);
				mapper.insertCardKind("우리카드", null);
				mapper.insertCardKind("제일은행카드", null);
				mapper.insertCardKind("이음-인천", InputBankCardDefine.NameIncheonEum);
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		
		try {
			
			if( ! common.existTable("HH_BANK_CARD") ) {
				log.info("HH_BANK_CARD 생성");
				initMapper.createBankCard();
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		try {
			
			if( ! common.existTable("HH_BANK") ) {
				log.info("HH_BANK 생성");
				initMapper.createBank();
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		try {
			
			if( ! common.existTable("HH_CARD") ) {
				log.info("HH_CARD 생성");
				initMapper.createCard();
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		try {
			
			if( ! common.existTable("HH_CARD_ITEM") ) {
				log.info("HH_CARD_ITEM 생성");
				initMapper.createCardItem();
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
	}
	
	@Override
	public List<CompanyVO> getBankKind(){
		return this.mapper.selectBankKind();
	}
	
	@Override
	public List<CompanyVO> getCardKind(){
		return this.mapper.selectCardKind();
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
			
			long seq = this.getNextValueSequence();

			if( result > 0 && vo.getNo() > 0L ) {
				if( vo.getBc() == EBankCard.Bank ) {
					final BankVO bvo = vo.getBank();
					bvo.setNo(seq);
					result += mapper.insertBank(bvo);
				}else if( vo.getBc() == EBankCard.Card ) {
					final CardVO cvo = vo.getCard();
					cvo.setNo(seq);
					result += mapper.insertCard(cvo);
				}else {
					
				}
			}
			
		}else {
			result = mapper.updateBankCard(vo);
			if( vo.getBc() == EBankCard.Bank ) {
				result += mapper.updateBank(vo.getBank());
			}else if( vo.getBc() == EBankCard.Card ) {
				result += mapper.updateCard(vo.getCard());
			}else {
				
			}
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

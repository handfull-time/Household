package com.utime.household.environment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.dataIO.vo.InputBankCardDefine;
import com.utime.household.environment.dao.BankCardDao;
import com.utime.household.environment.mapper.BankCardMapper;
import com.utime.household.environment.mapper.InitBankCardMapper;
import com.utime.household.environment.mapper.SequenceMapper;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
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
				
				mapper.insertBankCompany("KB국민은행", InputBankCardDefine.NameKbBank);
				mapper.insertBankCompany("하나은행", null);
				mapper.insertBankCompany("신한은행", null);
				mapper.insertBankCompany("우리은행", InputBankCardDefine.NameWooriBank);
				mapper.insertBankCompany("IBK기업은행", null);
				mapper.insertBankCompany("NH농협은행", null);
				mapper.insertBankCompany("KDB산업은행", InputBankCardDefine.NameKdb);
				mapper.insertBankCompany("SC제일은행", null);
				mapper.insertBankCompany("BNK부산은행", null);
				mapper.insertBankCompany("DGB대구은행", null);
				mapper.insertBankCompany("수협은행", null);
				mapper.insertBankCompany("카카오뱅크", InputBankCardDefine.NameKakaoBank);
				mapper.insertBankCompany("BNK경남은행", null);
				mapper.insertBankCompany("한국씨티은행", null);
				mapper.insertBankCompany("광주은행", null);
				mapper.insertBankCompany("토스뱅크", null);
				mapper.insertBankCompany("전북은행", null);
				mapper.insertBankCompany("케이뱅크", null);
				mapper.insertBankCompany("제주은행", null);
				mapper.insertBankCompany("우체국", null);
				mapper.insertBankCompany("새마을금고", null);
				mapper.insertBankCompany("저축은행", null);
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
		try {
			
			if( ! common.existTable("HH_CARD_KIND") ) {
				log.info("HH_CARD_KIND 생성");
				initMapper.createCardKind();
				
				mapper.insertCardCompany("KB국민카드", null);
				mapper.insertCardCompany("신한카드", InputBankCardDefine.NameShinhanCard);
				mapper.insertCardCompany("하나카드", null);
				mapper.insertCardCompany("롯데카드", null);
				mapper.insertCardCompany("BC카드", null);
				mapper.insertCardCompany("NH농협카드", null);
				mapper.insertCardCompany("삼성카드", InputBankCardDefine.NameSamsung);
				mapper.insertCardCompany("현대카드", null);
				mapper.insertCardCompany("우리카드", null);
				mapper.insertCardCompany("제일은행카드", null);
				mapper.insertCardCompany("이음-인천", InputBankCardDefine.NameIncheonEum);
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
	public List<CompanyVO> getBankCompanyList(){
		return this.mapper.selectBankCompanyList();
	}
	
	@Override
	public List<CompanyVO> getCardCompanyList(){
		return this.mapper.selectCardCompanyList();
	}
	

	@Override
	public List<BankCardVO> getBankCardList(EBankCard bc) {
		return mapper.selectBankCardList(bc);
	}

	@Override
	@Transactional( rollbackFor = Exception.class )
	public int saveBankCard(BankCardVO reqVo) throws Exception {
		
		int result;
		List<CardItemVO> cardItemDbList = null;
		final EBankCard bc = reqVo.getBc();
		if( reqVo.getNo() < 0L ) {
			
			if( mapper.sameCheckBankCard(reqVo) ) {
				throw new Exception("동일 정보 있다.");
			}
			
			// 새로운 고유 번호 획득
			final long seq = this.getNextValueSequence();
			
			if( bc == EBankCard.Bank ) {
				reqVo.getBank().setNo(seq);
			}else if( bc == EBankCard.Card ) {
				reqVo.getCard().setNo(seq);
			}
			
			result = mapper.insertBankCard(reqVo);

			if( result > 0 && reqVo.getNo() > 0L ) {
				if( bc == EBankCard.Bank ) {
					result += mapper.insertBank(reqVo.getBank());
				}else if( bc == EBankCard.Card ) {
					cardItemDbList = new ArrayList<>();
					result += mapper.insertCard(reqVo.getCard());
					
					reqVo.getCard().getCards().forEach( item -> item.setCardNo(seq) );
				}else {
					
				}
			}
			
		}else {
			result = mapper.updateBankCard(reqVo);
			if( bc == EBankCard.Bank ) {
				result += mapper.updateBank(reqVo.getBank());
			}else if( bc == EBankCard.Card ) {
				result += mapper.updateCard(reqVo.getCard());
				final long seq = reqVo.getCard().getNo();
				reqVo.getCard().getCards().forEach( item -> item.setCardNo(seq) );
				cardItemDbList = mapper.selectCardItemList(seq);
			}else {
				
			}
		}
		
		if( bc == EBankCard.Card  ) {
			final List<CardItemVO> cardItemReqList = reqVo.getCard().getCards();
		
			final Map<Long, CardItemVO> dbMap = cardItemDbList.stream()
	                 .collect(Collectors.toMap(card -> card.getNo(), card -> card));
			final Map<Long, CardItemVO> rqMap = cardItemReqList.stream()
	                 .collect(Collectors.toMap(CardItemVO::getNo, card -> card));
			
			
			// dbList에만 있는 항목 처리
			cardItemDbList.forEach( dbEntry -> {
				final CardItemVO uiEntry = rqMap.get(dbEntry.getNo());
				if( uiEntry == null ) {
					mapper.deleteCardItem( dbEntry.getNo() );
				} else if( ! uiEntry.equals( dbEntry ) ) {
					mapper.updateCardItem( uiEntry );
				}
			});

			// uiList에만 있는 항목 처리
			cardItemReqList.forEach( uiEntry -> {
				if( !dbMap.containsKey(uiEntry.getNo()) ) {
					mapper.insertCardItem(uiEntry);
				}
			});
		}
		
		return result;
	}
	
	@Override
	public BankCardVO getSimpleBankCard(long no) {
		return mapper.getSimpleBankCard( no );
	}
	
	@Override
	public BankCardVO getBankCard(long no) {
		
		return mapper.selectBankCard( no );
	}
	
	@Override
	@Transactional( rollbackFor = Exception.class )
	public int deleteBankCard(long no) throws Exception {
		int result = 0;
		final BankCardVO dbItem = this.getBankCard( no );
		if( dbItem == null ) {
			return result;
		}
		
		if( dbItem.getBc() == EBankCard.Bank ) {
			result += mapper.deleteBank(dbItem.getBank().getNo());
		}else if( dbItem.getBc() == EBankCard.Card ) {
			final long cardNo = dbItem.getCard().getNo();
			result += mapper.deleteAllCardItem(cardNo);
			result += mapper.deleteCard(cardNo);
		}
		
		result += mapper.deleteBankCard(no);
		
		return result;
	}
}

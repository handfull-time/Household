package com.utime.household.environment.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.EBankCard;
import com.utime.household.environment.vo.CompanyVO;

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
	 * @param no
	 * @param defaultBc
	 * @return
	 */
	BankCardVO getBankCard(long no);

	/**
	 * 은행사 목록
	 * @return
	 */
	List<CompanyVO> getBankCompanies();

	/**
	 * 카드사 목록
	 * @return
	 */
	List<CompanyVO> getCardCompanies();

	/**
	 * 은행 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getBank(long no);	

	/**
	 * 카드 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getCard(long no);

	/**
	 * 카드 아이템 정보 조회
	 * @param no
	 * @return
	 */
	CardItemVO getCardItem(long cardNo, long no);

	/**
	 * 카드 아이템 저장
	 * @param vo
	 * @return
	 */
	ReturnBasic saveCardItem(CardItemVO vo);

	/**
	 * 카드 아이템 삭제
	 * @param no
	 * @return
	 */
	ReturnBasic deleteCardItem(long no);	
}

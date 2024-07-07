package com.utime.household.environment.dao;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.CompanyVO;
import com.utime.household.environment.vo.EBankCard;

public interface BankCardDao {
	
	/**
	 * 은행 사 조회
	 * @param name
	 * @return
	 */
	public List<CompanyVO> getBankCompanyList();
	
	/**
	 * 카드 사 조회
	 * @param name
	 * @return
	 */
	public List<CompanyVO> getCardCompanyList();

	/**
	 * 목록 조회
	 * @return
	 */
	List<BankCardVO> getBankCardList(EBankCard bc);
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int saveBankCard(BankCardVO vo)throws Exception;
	
	/**
	 * 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getBankCard( long no );
	
	/**
	 * 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getSimpleBankCard( long no );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteBankCard(long no)throws Exception;
	
	/**
	 * 카드 아이템 정보 조회
	 * @param no
	 * @return
	 */
	CardItemVO getCardItem(long no);

	/**
	 * 카드 아이템 저장
	 * @param vo
	 * @return
	 */
	int saveCardItem(CardItemVO vo)throws Exception;

	/**
	 * 카드 아이템 삭제
	 * @param no
	 * @return
	 */
	int deleteCardItem(long no)throws Exception;

	/**
	 * 이 은행 번호를 사용중인 카드 조회
	 * @param no
	 * @return
	 */
	List<BankCardVO> getCardFromBankNo(long no);

}


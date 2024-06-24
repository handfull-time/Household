package com.utime.household.environment.dao;

import java.util.List;

import com.utime.household.environment.vo.BankCardVO;
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
}


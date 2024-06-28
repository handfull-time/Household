package com.utime.household.environment.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * 카드 은행 Mapper
 */
@Mapper
public interface InitBankCardMapper {
	
	
	/**
	 * 은행/카드 정보 생성
	 * @return
	 */
	public int createBankCard();
	
	/**
	 * 카드 사 종류
	 * @return
	 */
	public int createCardCompany();
	
	/**
	 * 은행 사 종류
	 * @return
	 */
	public int createBankCompany();
	
	/**
	 * 은행 정보 생성
	 * @return
	 */
	public int createBank();
	
	/**
	 * 카드 정보 생성
	 * @return
	 */
	public int createCard();
	
	/**
	 * 카드 하위 실제 카드 정보 생성
	 * @return
	 */
	public int createCardItem();

	
}

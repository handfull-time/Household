package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 카드 정보
 */
@Setter
@Getter
@ToString
public class CardInforVO {
	
	/** 카드 업체 */
	ECardCompany cardCompany;
	
	/**
	 * 카드 종류
	 */
	ECardType cardType;
	
	/**
	 * 연계 은행 정보
	 */
	BankCardVO bank;
	
	/**
	 * 매월 출금일자.
	 */
	int withdrawalDate;
	
}

package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class BankCardVO extends BasicItemVO{

	/** 
	 * 은행 카드 구분
	 */
	EBankCard bc;
	
	/**
	 * 은행 정보
	 */
	BankVO bank;
	
	/**
	 * 카드 정보
	 */
	CardVO card;
	
	/**
	 * 소유자 성명
	 */
	String ownerName;
	
	/**
	 * 비고
	 */
	String dscr;
}

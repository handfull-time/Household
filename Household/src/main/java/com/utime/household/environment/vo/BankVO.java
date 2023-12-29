package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 은행 정보
 */
@Setter
@Getter
@ToString
public class BankVO {

	/**
	 * 은행 종류
	 */
	EBankKind bankKind;
	
	/**
	 * 계좌 종류
	 */
	EAccountType accountType;
	
	/**
	 * 계좌 번호
	 */
	String accountNumber;
	
	/**
	 * 예금 주
	 */
	String accountHolder;
	
}

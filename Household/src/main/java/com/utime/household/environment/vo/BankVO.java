package com.utime.household.environment.vo;

import java.util.Date;

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
	
	/** 고유 번호 */
	long no;
	
	/**
	 * 은행 종류
	 */
	CompanyVO bankCompay;
	
	/**
	 * 계좌 종류
	 */
	EAccountType accountType;
	
	/**
	 * 계좌 번호
	 */
	String accountNumber;
	
	/**
	 * . 예금/적금 일 경우.
	 */
	Date maturity;
	
	/**
	 * 이율. 예금/적금 일 경우.
	 */
	float rate;
	
	/**
	 * 과세 여부. false:비과세
	 */
	boolean taxable;
	
}

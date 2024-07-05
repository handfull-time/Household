package com.utime.household.environment.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 은행 정보
 */
@Setter
@Getter
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
	 * 예금 주
	 */
	String accountHolder;
	
	/**
	 * . 예금/적금 일 경우.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date maturity;
	
	/**
	 * 이율. 예금/적금 일 경우.
	 */
	float rate;
	
	/**
	 * 과세 여부. false:비과세
	 */
	boolean taxable;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

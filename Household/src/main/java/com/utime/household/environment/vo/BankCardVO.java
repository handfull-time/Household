package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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
	 * 비고
	 */
	String dscr;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}
}

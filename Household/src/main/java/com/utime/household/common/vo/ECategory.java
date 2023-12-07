package com.utime.household.common.vo;

import lombok.Getter;

/**
 * 카테고리 종류
 */
@Getter
public enum ECategory {

	InOut( 1, "입출금 구분")
	, BankCard( 2, "은행/카드")
	, Usage(3, "사용처");

	final int value;
	final String dscr;
	
	private ECategory(int v, String s) {
		this.value = v;
		this.dscr = s;
	}
}

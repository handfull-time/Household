package com.utime.household.root.vo;

import lombok.Data;

@Data
public class SearchVO {
	/**
	 *  검색 날짜 yyyymmdd
	 */
	String startDate, endDate;
	/**
	 * 금액
	 */
	int amount;
	/** 
	 * 입금/ 출금 구분 
	 * 0 : 전체
	 * I : 입금
	 * O : 출금 
	 */
	char inOut = 0;
	/**
	 * 입출금 수단.
	 * 이체, 카드, 현찰 등...
	 */
	int paySource = 0;
	/**
	 * 항목
	 * 적금, 급여, 식비, 주유, 통신 등...
	 */
	int payTarget = 0;
	/** 
	 * 검색어
	 */
	String keyword;
}

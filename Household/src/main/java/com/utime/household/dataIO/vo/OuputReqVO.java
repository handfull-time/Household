package com.utime.household.dataIO.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OuputReqVO {
	/**
	 * 시작 날짜와 종료 날짜.
	 */
	String begin, end;
	
	/**
	 * 은행 카드 번호
	 */
	long bcNo = -1L;
	
	/** 
	 * 입출금 항목 번호 
	 */
	long categoryNo = -1L;
	
	/** 
	 * 분류 고유 번호 
	 */
	long storeNo = -1L;

	/**
	 * 비교 금액. 0이면 안함.
	 */
	int amount;
	
	/**
	 * amount 가 0이 아닐때 
	 * true : 이상 금액
	 * false : 이하 금액
	 */
	boolean amountThan;
	
	/**
	 * true : 최신 날짜 순
	 * false : 과거 날짜 순
	 */
	boolean dateOrder;
	
	/**
	 * 내용
	 */
	String content;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}
}

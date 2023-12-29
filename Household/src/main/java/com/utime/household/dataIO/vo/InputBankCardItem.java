package com.utime.household.dataIO.vo;

import com.utime.household.environment.vo.EBankCard;

import lombok.Getter;
import lombok.ToString;

/**
 * 분석 가능한 은행 or 카드 정보 
 */
@Getter
@ToString
public class InputBankCardItem {
	
	/**
	 * 이름
	 */
	private final String name;
	
	/**
	 * 은행 or 카드
	 */
	private final EBankCard bc;
	
	/**
	 * 분석 모듈 bean 이름
	 */
	private final String bean;
	
	/**
	 * 비고
	 */
	private final String dscr;
	
	public InputBankCardItem(String name) {
		this(name, null, null, null);
	}
	
	public InputBankCardItem(String name, EBankCard bc, String bean, String dscr) {
		this.name = name;
		this.bc = bc;
		this.bean = bean;
		this.dscr = dscr;
	}
}

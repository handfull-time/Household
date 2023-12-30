package com.utime.household.dataIO.vo;

import com.utime.household.environment.vo.EBankCard;
import com.utime.household.environment.vo.EBankKind;
import com.utime.household.environment.vo.ECardCompany;

import lombok.Getter;
import lombok.ToString;

/**
 * 분석 가능한 은행 or 카드 정보 
 */
@Getter
@ToString
public class InputBankCardItem {
	
	/**
	 * 은행 or 카드
	 */
	private final EBankCard bc;
	
	private final EBankKind bk;
	
	private final ECardCompany ck;
	
	/**
	 * 분석 모듈 bean 이름
	 */
	private final String bean;
	
	public InputBankCardItem(EBankKind bk, String bean) {
		this(bk, null, null);
	}

	public InputBankCardItem(ECardCompany ck, String bean) {
		this(null, ck, null);
	}
	
	public InputBankCardItem(EBankKind bk, ECardCompany ck, String bean) {
		this.bc = (bk == null)? EBankCard.Card:EBankCard.Bank;
		this.bk = bk;
		this.ck = ck;
		this.bean = bean;
	}
}

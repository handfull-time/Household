package com.utime.household.dataIO.vo;

import com.utime.household.config.vo.EBankCard;

/**
 * 입력 가능한 은행 / 카드
 */
@Deprecated
public enum EInputBankCard {
	
    KbBank("국민은행", EBankCard.Bank, InputBankCardDefine.NameKbBank),
	Kdb("산업은행", EBankCard.Bank, InputBankCardDefine.NameKdb),
    Samsung("삼성카드", EBankCard.Card, InputBankCardDefine.NameSamsung),
    Shinhan("신한카드", EBankCard.Card, InputBankCardDefine.NameShinhan),
    IncheonEum("인천-이음카드", EBankCard.Card, InputBankCardDefine.NameIncheonEum);
	
	private final String dscr;
	
	private final EBankCard bc;
	
	private final String bean; 
	
	private EInputBankCard(String d, EBankCard bc, String b) {
		this.dscr = d;
		this.bc = bc;
		this.bean = b;
	}
	
	public String getDscr() {
		return dscr;
	}
	
	public String getBean() {
		return bean;
	}
}

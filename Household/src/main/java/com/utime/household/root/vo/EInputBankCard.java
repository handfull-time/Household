package com.utime.household.root.vo;

/**
 * 입력 가능한 은행 / 카드
 */
public enum EInputBankCard {
	
	Kdb("산업은행", InputBankCardDefine.NameKdb),
    Samsung("삼성카드", InputBankCardDefine.NameSamsung),
    KbBank("국민은행", InputBankCardDefine.NameKbBank),
    Shinhan("신한카드", InputBankCardDefine.NameShinhan),
    IncheonEum("인천-이음카드", InputBankCardDefine.NameIncheonEum);
	
	private final String dscr;
	
	private final String bean; 
	
	private EInputBankCard(String d, String b) {
		this.dscr = d;
		this.bean = b;
	}
	
	public String getDscr() {
		return dscr;
	}
	
	public String getBean() {
		return bean;
	}
}

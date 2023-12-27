package com.utime.household.environment.vo;

/**
 * 은행 카드 구분
 */
public enum EBankCard {
	Bank("B", "은행"),
	Card("C", "카드"),
	Other("O", "기타");
	
	private final String code;
	
	private final String dscr;
	
	private EBankCard(String c, String d) {
		this.code = c;
		this.dscr = d;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDscr() {
		return dscr;
	}
}

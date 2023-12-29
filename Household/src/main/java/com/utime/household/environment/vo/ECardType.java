package com.utime.household.environment.vo;

/**
 * 카드 종류
 */
public enum ECardType {
	Credit("C", "신용카드")
	,Check("H", "체크카드")
	,Prepaid("P", "선불카드")
	,Gift("G", "기프트카드")
	,Point("I", "포인트카드")
	,Other("O", "기타");

	private final String code;
	
	private final String dscr;
	
	private ECardType(String c, String d) {
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

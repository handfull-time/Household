package com.utime.household.config.vo;

/**
 * 카테고리 타입
 */
public enum ECategoryType {
	Income("I", "수입")
	, Saving("S", "저축")
	, Expense("E", "지출");
	
	final String code;
	final String dscr;
	
	private ECategoryType(String c, String s) {
		this.code = c;
		this.dscr = s;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDscr() {
		return dscr;
	}
}

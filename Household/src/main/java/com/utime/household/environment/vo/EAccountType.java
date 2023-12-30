package com.utime.household.environment.vo;

/**
 * 계좌 종류
 */
public enum EAccountType {
	Ordinary("O", "수시 입출금")
	,Term("T", "정기예금")
	,Regular("R", "정기적금")
	,Free("F", "자유적금");
	
	private final String code;
	
	private final String dscr;
	
	private EAccountType(String c, String d) {
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

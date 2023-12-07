package com.utime.household.root.vo;

public enum EInOut {
	In("입금"),
	Out("출금");
	
	private String dscr;
	
	private EInOut(String d) {
		this.dscr = d;
	}
	
	public String getDscr() {
		return dscr;
	}
}

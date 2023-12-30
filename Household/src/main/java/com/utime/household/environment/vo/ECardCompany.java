package com.utime.household.environment.vo;

/**
 * 카드 종류
 */
public enum ECardCompany {
	KB("KB국민카드")
	,Shinhan("신한카드")
	,Hana("하나카드")
	,Lotte("롯데카드")
	,BC("BC카드")
	,NH("NH농협카드")
	,Samsung("삼성카드")
	,Hyundai("현대카드")
	,Woori("우리카드")
	,SC("제일은행카드")
	,KakaoPay("카카오페이")
	,KakaoBank("카카오뱅크")
	,TossBank("토스뱅크")
	,EumIncheon("이음-인천")
	;
	
	private final String dscr;
	
	private ECardCompany(String s) {
		this.dscr = s;
	}
	
	public String getDscr() {
		return dscr;
	}
}

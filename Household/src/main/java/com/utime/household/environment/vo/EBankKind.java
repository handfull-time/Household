package com.utime.household.environment.vo;

/**
 * 은행 종류
 */
public enum EBankKind {
	KB("KB국민은행")
	,Hana("하나은행")
	,Shinhan("신한은행")
	,Woori("우리은행")
	,Ibk("IBK기업은행")
	,Nonghyup("NH농협은행")
	,Kdb("KDB산업은행")
	,Sc("SC제일은행")
	,Busan("BNK부산은행")
	,Daegu("DGB대구은행")
	,Suhyup("수협은행")
	,Kakao("카카오뱅크")
	,Gyeongnam("BNK경남은행")
	,City("한국씨티은행")
	,Gwangju("광주은행")
	,Toss("토스뱅크")
	,Jeonbuk("전북은행")
	,KBank("케이뱅크")
	,Jeju("제주은행")
	,EPost("우체국")
	,Kfcc("새마을금고")
	,Saving("저축은행")
	,Other("기타");
	
	private final String dscr;
	
	private EBankKind(String s) {
		this.dscr = s;
	}
	
	public String getDscr() {
		return dscr;
	}
}

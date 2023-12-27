package com.utime.household.dataIO.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OuputReqVO {
	/**
	 * 시작 날짜와 종료 날짜.
	 */
	String begin, end;
	
	/**
	 * 은행 카드 번호
	 */
	long bcNo;
}

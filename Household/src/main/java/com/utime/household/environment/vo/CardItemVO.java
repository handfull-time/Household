package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 카드 정보
 */
@Setter
@Getter
@ToString
public class CardItemVO extends BasicItemVO{
	
	/** 사용 여부 */
	boolean enabled;
	
	/**
	 * 상위 부모 번호
	 */
	long cardNo;
	
	/**
	 * 카드 종류
	 */
	ECardType cardType;
	
	/**
	 * 카드 번호
	 */
	String cardNumber;
	
	/** 파일에서 처리되는 이름 */
	String innerFileCardName;
	
	/**
	 * 청구서에 기록되는 카드 번호
	 */
	String innerFileCardNumber;
}

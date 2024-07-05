package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 카드 정보
 */
@Setter
@Getter
public class CardItemVO extends BasicItemVO{
	
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
	
	/**
	 * 비고
	 */
	String dscr;
	
	@Override
	public boolean equals(Object obj) {
		final CardItemVO src = (CardItemVO)obj;
		if( src.no != this.no)
			return false;
		if( ! HouseholdUtils.StringEquals(src.name, this.name) )
			return false;
		if( src.enabled != this.enabled)
			return false;
		if( src.cardNo != this.cardNo)
			return false;
		if( src.cardType != this.cardType)
			return false;
		if( ! HouseholdUtils.StringEquals(src.cardNumber, this.cardNumber) )
			return false;
		if( ! HouseholdUtils.StringEquals(src.innerFileCardName, this.innerFileCardName) )
			return false;
		if( ! HouseholdUtils.StringEquals(src.innerFileCardNumber, this.innerFileCardNumber) )
			return false;
		if( ! HouseholdUtils.StringEquals(src.dscr, this.dscr) )
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

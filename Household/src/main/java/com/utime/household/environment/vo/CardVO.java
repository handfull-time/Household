package com.utime.household.environment.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 카드 정보
 */
@Setter
@Getter
public class CardVO{
	
	/** 고유 번호 */
	long no;
	
	/** 카드 업체 */
	CompanyVO cardCompany;
	
	/**
	 * 출금 은행 정보
	 */
	BankCardVO bank;
	
	/**
	 * 매월 출금일자.
	 */
	int withdrawalDate;
	
	/**
	 * 관련 카드 목록
	 */
	List<CardItemVO> cards;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

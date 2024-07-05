package com.utime.household.dataIO.vo;

import java.util.Date;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.StoreVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HouseholdDataVO {

	/** 고유 번호 */
	long no;
	
	/** 작성일자 */
	Date regDate;
	
	/** 거래 일자 */
//	@DateTimeFormat(pattern = "yyyy.MM.dd hh:mm")
	Date dealDate;
	
	/** 원래 금액 */
	int originAmount;

	/** 실제 지불 금액 */
	int useAmount;
	
	/** 포함 여부 true:포함, false:미포함 */
	boolean included = true;
	
	/** 입출금 여부 */
	EInOut io;
	
	/** 은행 카드 번호 */
	BankCardVO bcVo;
	
	/** 카드 아이템 */
	CardItemVO carditem;

	/** 입출금 대분류 항목 */
	CategoryVO categoryOwner;
	
	/** 입출금 소분류 항목 */
	CategorySubVO categorySub;
	
	/** 사용처 */
	StoreVO store;
	
	/** 비고 */
	String dscr;
	
	/**
	 * 데이터고유 해쉬
	 */
	int hash;
	
	/** 원 출처 번호 */
	long ownerNo;

	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

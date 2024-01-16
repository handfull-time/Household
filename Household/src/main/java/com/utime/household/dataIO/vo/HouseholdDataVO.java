package com.utime.household.dataIO.vo;

import java.util.Date;

import com.utime.household.environment.vo.BankCardVO;
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
	
	/** 거래 금액 */
	int amount;
	
	/** 포함 여부 true:포함, false:미포함 */
	boolean included = true;
	
	/** 입출금 여부 */
	EInOut io;
	
	/** 은행 카드 번호 */
	BankCardVO bcVo;

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
		StringBuilder builder = new StringBuilder();
		builder.append("[no=").append(no).append(", ");
		if (regDate != null)
			builder.append("regDate=").append(regDate).append(", ");
		if (dealDate != null)
			builder.append("dealDate=").append(dealDate).append(", ");
		builder.append("amount=").append(amount).append(", included=").append(included).append(", ");
		if (io != null)
			builder.append("io=").append(io).append(", ");
		if (bcVo != null)
			builder.append("bcVo=").append(bcVo).append(", ");
		if (categoryOwner != null)
			builder.append("categoryOwner=").append(categoryOwner).append(", ");
		if (categorySub != null)
			builder.append("categorySub=").append(categorySub).append(", ");
		if (store != null)
			builder.append("store=").append(store).append(", ");
		if (dscr != null)
			builder.append("dscr=").append(dscr).append(", ");
		builder.append("hash=").append(hash).append(", ownerNo=").append(ownerNo).append("]\n");
		return builder.toString();
	}
	
	
}

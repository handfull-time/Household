package com.utime.household.dataIO.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.utime.household.config.vo.BankCardVO;
import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.StoreVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class HouseholdDataVO {

	/** 고유 번호 */
	long no;
	
	/** 작성일자 */
	Date regDate;
	
	/** 거래 일자 */
	@DateTimeFormat(pattern = "yyyy.MM.dd hh:mm")
	Date dealDate;
	
	/** 거래 금액 */
	int amount;
	
	/** 포함 여부 true:포함, false:미포함 */
	boolean included = true;
	
	/** 입출금 여부 */
	EInOut io;
	
	/** 은행 카드 번호 */
	BankCardVO bcVo;

	/** 입출금 항목 */
	CategoryVO category;
	
	/** 사용처 */
	StoreVO store;
	
	/** 비고 */
	String dscr;
	
	/** 원 출처 번호 */
	long ownerNo;
}

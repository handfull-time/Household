package com.utime.household.environment.vo;

import com.utime.household.dataIO.vo.InputBankCardItem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class BankCardVO extends BasicItemVO{

	/** 
	 * 은행 카드 구분
	 */
	EBankCard bc;
	
	/** 입출금 매칭 정보 */
	InputBankCardItem inputBC;
}

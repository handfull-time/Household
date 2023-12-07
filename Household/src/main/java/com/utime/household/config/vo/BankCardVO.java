package com.utime.household.config.vo;

import com.utime.household.root.vo.EInputBankCard;

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
	EInputBankCard inputBC;
}

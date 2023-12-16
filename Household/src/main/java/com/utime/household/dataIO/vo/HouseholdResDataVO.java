package com.utime.household.dataIO.vo;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.config.vo.BankCardVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class HouseholdResDataVO extends ReturnBasic{
	
	BankCardVO bcVo;
	
	int count;
}

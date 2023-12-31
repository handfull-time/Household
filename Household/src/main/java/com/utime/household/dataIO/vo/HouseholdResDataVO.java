package com.utime.household.dataIO.vo;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.BankCardVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class HouseholdResDataVO extends ReturnBasic{
	
	BankCardVO bcVo;
	
	String begin, end;
	
	/**
	 * 추가된 데이터
	 */
	List<HouseholdDataVO> addList;
	
	/**
	 * 예외된 데이터
	 */
	List<HouseholdDataVO> exList;
}

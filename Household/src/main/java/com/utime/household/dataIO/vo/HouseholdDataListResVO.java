package com.utime.household.dataIO.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.BankCardVO;

import lombok.Getter;
import lombok.Setter;

/**
 * 분석 결과
 */
@Setter
@Getter
public class HouseholdDataListResVO extends ReturnBasic{

	List<HouseholdDataVO> list;
	
	BankCardVO bcVo;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

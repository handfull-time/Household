package com.utime.household.dataIO.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HouseholdReqDataVO {
	long bcNo;
	
	List<HouseholdDataVO> list;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

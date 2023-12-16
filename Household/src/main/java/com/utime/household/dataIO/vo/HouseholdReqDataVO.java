package com.utime.household.dataIO.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class HouseholdReqDataVO {
	long bcNo;
	
	List<HouseholdDataVO> list;
}

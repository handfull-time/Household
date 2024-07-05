package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 대 분류
 */
@Setter
@Getter
public class CategoryVO extends BasicItemVO{
	
	ECategoryType cType;
	
	public CategoryVO() {
		super();
		this.cType = null;
	}
	
	public CategoryVO(String name, ECategoryType cType) {
		this(-1L, name, cType);
	}

	public CategoryVO(long no, String name, ECategoryType cType) {
		super(no, name);
		this.cType = cType;
	}
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

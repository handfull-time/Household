package com.utime.household.environment.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 소분류
 */
@Setter
@Getter
public class CategorySubStoreVO extends CategorySubVO{
	List<StoreVO> list;	
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

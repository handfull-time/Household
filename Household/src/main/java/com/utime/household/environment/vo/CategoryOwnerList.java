package com.utime.household.environment.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryOwnerList extends CategoryVO {
	
	List<CategoryVO> ownerList;
	
	List<List<CategorySubVO>> subList;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

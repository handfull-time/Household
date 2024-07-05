package com.utime.household.environment.vo;

import java.util.List;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryOwnerVO extends CategoryVO {
	
	/**
	 * 소분류
	 */
	List<CategorySubStoreVO> subCategories;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 소분류
 */
@Setter
@Getter
public class CategorySubVO extends BasicItemVO{
	
	CategoryVO owner;
	
	public CategorySubVO() {
		super();
		this.owner = null;
	}
	
	public CategorySubVO(String name, CategoryVO owner) {
		this(-1L, name, owner);
	}

	public CategorySubVO(long no, String name, CategoryVO owner) {
		super(no, name);
		this.owner = owner;
	}
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

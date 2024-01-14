package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 소분류
 */
@Setter
@Getter
@ToString(callSuper = true)
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
}

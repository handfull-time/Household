package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 대 분류
 */
@Setter
@Getter
@ToString(callSuper = true)
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
}

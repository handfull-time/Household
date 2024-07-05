package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyVO extends BasicItemVO{
	/** bean 이름 */
	private String beanName;
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}

}

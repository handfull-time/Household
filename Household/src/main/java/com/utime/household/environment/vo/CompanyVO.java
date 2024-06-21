package com.utime.household.environment.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class CompanyVO extends BasicItemVO{
	/** bean 이름 */
	private String beanName;
}

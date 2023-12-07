package com.utime.household.config.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BasicItemVO {
	
	/** 고유 번호 */
	long no;
	
	/** 이름 */
	String name;
	
	public BasicItemVO() {
		this(-1L, null);
	}
	
	public BasicItemVO(long no, String name) {
		this.no = no;
		this.name = name;
	}
}
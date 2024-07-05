package com.utime.household.environment.vo;

import com.utime.household.common.util.HouseholdUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BasicItemVO {
	
	/** 고유 번호 */
	protected long no;
	
	/** 이름 */
	protected String name;
	
	/** 사용 여부 */
	boolean enabled;
	
	public BasicItemVO() {
		this(-1L, null, false);
	}
	
	public BasicItemVO(long no, String name) {
		this(no, name, false);
	}
	
	public BasicItemVO(long no, String name, boolean enabled) {
		this.no = no;
		this.name = name;
		this.enabled = enabled;
	}
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}
}

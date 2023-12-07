package com.utime.household.common.vo;

import lombok.Data;

@Data
public class ReturnBasic {

	String code;
	String message;
	
	public ReturnBasic() {
		this(HouseholdDefine.ERROR_OK, null);
	}
	
	public ReturnBasic(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ReturnBasic setCodeMessage(String code, String message) {
		this.code = code;
		this.message = message;
		
		return this;
	}
	
	public boolean isError() {
		return ! HouseholdDefine.ERROR_OK.equals(this.code);
	}
	
}

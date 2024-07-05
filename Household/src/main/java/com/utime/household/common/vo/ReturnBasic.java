package com.utime.household.common.vo;

import com.utime.household.common.util.HouseholdUtils;

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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return ! HouseholdDefine.ERROR_OK.equals(this.code);
	}
	
	@Override
	public String toString() {
		return HouseholdUtils.toString(this);
	}
}

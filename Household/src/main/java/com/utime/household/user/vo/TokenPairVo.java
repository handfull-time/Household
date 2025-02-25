package com.utime.household.user.vo;

import com.utime.household.common.vo.ReturnBasic;

import lombok.Getter;

@Getter
public class TokenPairVo extends ReturnBasic {
	String accessToken;
	String refreshToken;
	
	public void setTokenPair(String a, String r) {
		this.accessToken = a;
		this.refreshToken = r;
	}
	
}

package com.utime.household.user.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReqVo {
	
	private String id;
	private String pw;
	private String token;
	private String sessionId;
	
	@Override
	public String toString() {
		return "LoginReqVo [id=" + id + ", pw=" + pw.getBytes().length + "bytes, token=" + token + "]";
	}
	
	
}

package com.utime.household.user.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginReqVo extends ReqUniqueVo{
	
	private String id;
	private String pw;
	
	@Override
	public String toString() {
		return "LoginReqVo [id=" + id + ", pw=" + pw.getBytes().length + "bytes, token=" + token + "]";
	}
}

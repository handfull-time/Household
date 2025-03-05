package com.utime.household.common.vo;

import com.utime.household.user.vo.UserVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class ResUserVo extends ReturnBasic {

	private UserVo user;
	
	public ResUserVo(String c, String m) {
		super(c, m);
	}
	
	public ResUserVo() {
		super();
	}
	
}

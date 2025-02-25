package com.utime.household.user.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserReqVo extends LoginReqVo {
	private long userNo;
	MultipartFile image;
	private String nickname;
	private String birthday;
	private String myRainbow;
	private String mySeason;
	private String myNumber;
}

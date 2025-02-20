package com.utime.household.user.vo;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserReqVo {
	private long userNo;
	private String id;
	@JsonIgnore
	private String pw;
	MultipartFile image;
	private String nickName;
	private String birthday;
	private String myRainbow;
	private String mySeason;
	private String myNumber;
	private String token;
	private String sessionId;
}

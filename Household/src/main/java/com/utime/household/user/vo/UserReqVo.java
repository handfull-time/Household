package com.utime.household.user.vo;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class UserReqVo extends ReqUniqueVo {
	private long userNo;
	private String id;
	@JsonIgnore
	private String pw;
	MultipartFile image;
	private String nickname;
	private String birthday;
	private String myRainbow;
	private String mySeason;
	private String myNumber;
}

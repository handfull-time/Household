package com.utime.household.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserVo {
	private long userNo;
	private Date regDate;
	private Date updateDate;
	private boolean enabled;
	private String id;
	@JsonIgnore
	private String pw;
	private byte [] imageBytes;
	private String nickName;
	private String birthday;
	private EJwtRole role;
	private String pwCheck1;
	private String pwCheck2;
	private String pwCheck3;
}

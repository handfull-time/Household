package com.utime.household.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(exclude = {"pw", "image"})
public class UserVo {
	private long userNo;
	private Date regDate;
	private Date updateDate;
	private boolean enabled;
	private String id;
	@JsonIgnore
	private String pw;
	private String image;
	private String nickname;
	private String birthday;
	private EJwtRole role;
	private String pwCheck;
}

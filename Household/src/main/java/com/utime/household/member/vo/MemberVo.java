package com.utime.household.member.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MemberVo {
	private long userNo;
	private String id;
	@JsonIgnore
	private String pw;
	private String image;
	private String nickName;
}

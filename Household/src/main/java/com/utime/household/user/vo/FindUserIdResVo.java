package com.utime.household.user.vo;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class FindUserIdResVo extends ReturnBasic {

	List<UserVo> list;
}

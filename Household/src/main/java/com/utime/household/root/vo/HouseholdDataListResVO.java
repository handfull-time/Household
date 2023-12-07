package com.utime.household.root.vo;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class HouseholdDataListResVO extends ReturnBasic{

	List<HouseholdDataVO> list;
}

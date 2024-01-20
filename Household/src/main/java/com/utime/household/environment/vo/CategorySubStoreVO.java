package com.utime.household.environment.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 소분류
 */
@Setter
@Getter
@ToString(callSuper = true)
public class CategorySubStoreVO extends CategorySubVO{
	List<StoreVO> list;	
}

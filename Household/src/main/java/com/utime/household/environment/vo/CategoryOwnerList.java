package com.utime.household.environment.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CategoryOwnerList extends CategoryVO {
	
	List<CategoryVO> ownerList;
	
	List<List<CategorySubVO>> subList;
}

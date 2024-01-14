package com.utime.household.environment.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CategoryOwnerVO {
	
	/**
	 * 대분류
	 */
	CategoryVO category;
	
	/**
	 * 소분류
	 */
	List<CategorySubVO> subCategories;
}

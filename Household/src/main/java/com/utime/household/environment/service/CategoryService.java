package com.utime.household.environment.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

/**
 * 입출금 항목 서비스
 */
public interface CategoryService {
	
	/**
	 * 전체 내역 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList();
	
	/**
	 * 전체 내역 조회
	 * @return
	 */
	List<CategoryOwnerVO> getCategoryOwnerList();
	
	/**
	 * 내역 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList(ECategoryType cType);
	
	/**
	 * 입출금 항목 저장
	 * @param vo
	 * @return
	 */
	ReturnBasic saveCategory(CategoryVO vo);
	
	/**
	 * 입출금 항목 삭제
	 * @param vo
	 * @return
	 */
	ReturnBasic deleteCategory(long no);

	/**
	 * 상세 정보 조회
	 * @param no
	 * @return
	 */
	CategoryVO getCategory(long no);
}

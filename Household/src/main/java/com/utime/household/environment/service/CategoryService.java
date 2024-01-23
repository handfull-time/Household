package com.utime.household.environment.service;

import java.util.List;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.environment.vo.CategoryOwnerList;
import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategorySubVO;
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
	 * 전체 내역 조회
	 * @param cType
	 * @return
	 */
	List<CategoryOwnerVO> getCategoryOwnerList(ECategoryType cType);
	
	/**
	 * 
	 * @param cType
	 * @return
	 */
	CategoryOwnerList getCategoryOwnerListOfList(ECategoryType cType);
	
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
	
	/**
	 * 서브 카테고리 목록
	 * @param ownerNo
	 * @return
	 */
	List<CategorySubVO> getSubCategoryList(long ownerNo);
	
	/**
	 * 서브 카테고리 상세 
	 * @param subNo
	 * @return
	 */
	CategorySubVO getSubCategory(long subNo);
	
	/**
	 * 서브 카테고리 저장
	 * @param sub
	 * @return
	 */
	ReturnBasic saveSubCategory(CategorySubVO sub);
	
	/**
	 * 서브 카테고리 삭제
	 * @param subNo
	 * @return
	 */
	ReturnBasic deleteSubCategory(long subNo );
}

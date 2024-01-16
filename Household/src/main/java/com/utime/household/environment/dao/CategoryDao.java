package com.utime.household.environment.dao;

import java.util.List;

import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

/**
 * 입출금 항목 DAO
 */
public interface CategoryDao {
	
	/**
	 * 수량
	 * @return
	 */
	int getCategoryCount();

	/**
	 * 목록 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList(ECategoryType cType);
	
	/**
	 * 전체 내역 조회
	 * @return
	 */
	List<CategoryOwnerVO> getCategoryOwnerList(ECategoryType cType);
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int saveOwnerCategory(CategoryVO vo)throws Exception;
	
	
	/**
	 * 이름 일치하는 정보 조회
	 * @param inputBC
	 * @return
	 */
	CategoryVO getOwnerCategory( String name );
	
	/**
	 * 삭제
	 * @param no
	 * @return
	 */
	int deleteOwnerCategory( long no )throws Exception;

	/**
	 * 상세 정보 조회
	 * @param no
	 * @return
	 */
	CategoryVO getOwnerCategory(long no);
	
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
	int saveSubCategory(CategorySubVO sub)throws Exception;
	
	/**
	 * 서브 카테고리 삭제
	 * @param subNo
	 * @return
	 */
	int deleteSubCategory(long subNo )throws Exception;

	
	
}


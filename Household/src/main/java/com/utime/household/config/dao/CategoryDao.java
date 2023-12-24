package com.utime.household.config.dao;

import java.util.List;

import com.utime.household.config.vo.CategoryVO;
import com.utime.household.config.vo.ECategoryType;

/**
 * 입출금 항목 DAO
 */
public interface CategoryDao {

	/**
	 * 목록 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList(ECategoryType cType);
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int saveCategory(CategoryVO vo)throws Exception;
	
	
	/**
	 * 이름 일치하는 정보 조회
	 * @param inputBC
	 * @return
	 */
	CategoryVO getCategory( String name );
	
	/**
	 * 삭제
	 * @param no
	 * @return
	 */
	int deleteCategory( long no )throws Exception;

	/**
	 * 상세 정보 조회
	 * @param no
	 * @return
	 */
	CategoryVO getCategory(long no);
	
}


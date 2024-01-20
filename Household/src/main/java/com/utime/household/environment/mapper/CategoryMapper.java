package com.utime.household.environment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.environment.vo.CategoryOwnerVO;
import com.utime.household.environment.vo.CategorySubVO;
import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

/**
 * 입출금 항목 Mapper
 */
@Mapper
public interface CategoryMapper {
	
	/**
	 * 대분류 생성
	 * @return
	 */
	public int createCategories();
	
	/**
	 * 소분류 생성
	 * @return
	 */
	public int createSubcategories();
	
	/**
	 * 수량
	 * @return
	 */
	int getCategoryCount();

	/**
	 * 대분류만 목록 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList(@Param("cType") ECategoryType cType);
	
	
	/**
	 * 전체 내역 조회
	 * @return
	 */
	List<CategoryOwnerVO> getCategoryOwnerList(@Param("cType") ECategoryType cType);
	
	
	/**
	 * 이름으로 정보 조회
	 * @param inputBC
	 * @return
	 */
	CategoryVO getCategoryFromName( @Param("name") String name );
	
	/**
	 * 번호로 정보 조회
	 * @param no
	 * @return
	 */
	CategoryVO getCategoryFromNo(@Param("no") long no);
	
	/**
	 * 추가하기 전 동일 정보 체크
	 * @param vo
	 * @return true : 동일한 것이 있다.
	 */
	boolean sameCheckCategory( CategoryVO vo );
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 */
	int insertCategory( CategoryVO vo );
	
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	int updateCategory( CategoryVO vo );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 */
	int deleteCategory( @Param("no") long no );
	
	/**
	 * 소분류 목록
	 * @param ownerNo
	 * @return
	 */
	public List<CategorySubVO> getSubCategoryList(@Param("ownerNo") long ownerNo);

	/**
	 * 소분류 상세
	 * @param subNo
	 * @return
	 */
	public CategorySubVO getSubCategory(@Param("subNo") long subNo);

	/**
	 * 소분류 추가
	 * @param vo
	 * @return
	 */
	int insertSubCategory( CategorySubVO vo );
	
	/**
	 * 소분류 수정
	 * @param vo
	 * @return
	 */
	int updateSubCategory( CategorySubVO vo );
	
	/**
	 * 소분류 삭제
	 * @param subNo
	 * @return
	 */
	int deleteSubCategory( @Param("subNo") long subNo );


	
}

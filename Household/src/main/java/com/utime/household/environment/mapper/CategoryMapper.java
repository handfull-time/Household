package com.utime.household.environment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.environment.vo.CategoryVO;
import com.utime.household.environment.vo.ECategoryType;

/**
 * 입출금 항목 Mapper
 */
@Mapper
public interface CategoryMapper {
	
	/**
	 * 카테고리 생성
	 * @return
	 */
	public int createCategory();
	
	/**
	 * 수량
	 * @return
	 */
	int getCategoryCount();

	/**
	 * 카드 은행 목록 조회
	 * @return
	 */
	List<CategoryVO> getCategoryList(@Param("cType") ECategoryType cType);
	
	
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

	
}

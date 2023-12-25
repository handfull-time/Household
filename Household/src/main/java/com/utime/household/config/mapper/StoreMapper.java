package com.utime.household.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.config.vo.StoreVO;

/**
 * 사용처 Mapper
 */
@Mapper
public interface StoreMapper {
	
	/**
	 * 사용처 매칭 테이블 생성
	 * @return
	 */
	public int createMatchUsageStore();

	/**
	 * 카드 은행 목록 조회
	 * @return
	 */
	List<StoreVO> getStoreList(@Param("categoryNo") long categoryNo);
	
	
	/**
	 * 상점 상세 정보 조회
	 * @param name
	 * @return
	 */
	StoreVO getStore( @Param("no") long no);
	
	/**
	 * 추가하기 전 동일 정보 체크
	 * @param vo
	 * @return true : 동일한 것이 있다.
	 */
	boolean sameCheckStore( StoreVO vo );
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 */
	int insertStore( StoreVO vo );
	
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	int updateStore( StoreVO vo );
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 */
	int deleteStore( @Param("no") long no );
}

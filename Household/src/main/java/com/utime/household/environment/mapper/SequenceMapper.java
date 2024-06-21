package com.utime.household.environment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 카드 은행 Mapper
 */
@Mapper
public interface SequenceMapper {
	
	/**
	 * 시퀀스 생성
	 * @return
	 */
	int createSequence();
	
	/**
	 * 시퀀스 등록
	 * @param SequenceName
	 * @param startValue
	 * @return
	 */
	int registSequence( @Param("sequenceName") String SequenceName, @Param("startValue") long startValue );
	
	 
	
//	/**
//	 * Sequence 값을 가져오는 쿼리  
//	 * @param SequenceName
//	 * @return
//	 */
//    long getNextValue( @Param("sequenceName") String SequenceName );
	
    /**
     * Sequence 값을 업데이트하는 쿼리
     * @param sequenceName
     * @return
     */
    int updateIncrementValue( @Param("sequenceName") String sequenceName);
    
    /**
     * 현재 값 조회
     * @param sequenceName
     * @return
     */
    long selectCurrentValue( @Param("sequenceName") String sequenceName);

    /**
     * 
     * @param sequenceName
     * @return
     */
	long nextValue( @Param("sequenceName") String sequenceName);
}

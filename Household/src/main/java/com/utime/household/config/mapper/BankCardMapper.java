package com.utime.household.config.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.config.vo.BankCardVO;
import com.utime.household.root.vo.EInputBankCard;

/**
 * 카드 은행 Mapper
 */
@Mapper
public interface BankCardMapper {
	
	
	/**
	 * 은행/카드 정보 생성
	 * @return
	 */
	public int createBankCard();

	/**
	 * 카드 은행 목록 조회
	 * @return
	 */
	List<BankCardVO> getBankCardList();
	
	
	/**
	 * EInputBankCard 일치하는 정보 조회
	 * @param inputBC
	 * @return
	 */
	BankCardVO getBankCard( @Param("inputBC") EInputBankCard inputBC );
	
	/**
	 * 추가하기 전 동일 정보 체크
	 * @param vo
	 * @return true : 동일한 것이 있다.
	 */
	boolean sameCheckBankCard( BankCardVO vo );
	
	/**
	 * 추가
	 * @param vo
	 * @return
	 */
	int insertBankCard(BankCardVO vo);
	
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	int updateBankCard(BankCardVO vo);
}
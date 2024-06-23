package com.utime.household.environment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.BankVO;
import com.utime.household.environment.vo.BasicItemVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.CardVO;
import com.utime.household.environment.vo.EBankCard;
import com.utime.household.environment.vo.CompanyVO;

/**
 * 카드 은행 Mapper
 */
@Mapper
public interface BankCardMapper {
	
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
	 * 은행 추가
	 * @param bvo
	 * @return
	 */
	public int insertBank(BankVO bvo);
	
	/**
	 * 카드 추가
	 * @param card
	 * @return
	 */
	public int insertCard(CardVO card);
	
	/**
	 * 카드 아이템 추가 
	 * @param item
	 * @return
	 */
	int insertCardItem(CardItemVO item);
	
	/**
	 * 카드 아이템 삭제 
	 * @param item
	 * @return
	 */
	int removeCardItem(CardItemVO item);
	
	/**
	 * 카드 아이템 수정
	 * @param item
	 * @return
	 */
	int updateCardItem(CardItemVO item);
	
	
//	/**
//	 * 은행 사 추가
//	 * @param name
//	 * @param bean
//	 * @return
//	 */
//	public int insertBankKind( @Param("name") String name, @Param("bean") String bean );
//	
//	/**
//	 * 카드 사 추가
//	 * @param name
//	 * @param bean
//	 * @return
//	 */
//	public int insertCardKind( @Param("name") String name, @Param("bean") String bean );
//
//	/**
//	 * 은행 사 조회
//	 * @param name
//	 * @return
//	 */
//	public List<CompanyVO> selectBankKind();
//	
//	/**
//	 * 카드 사 조회
//	 * @param name
//	 * @return
//	 */
//	public List<CompanyVO> selectCardKind();
//	
//
//	/**
//	 * 카드 은행 목록 조회
//	 * @return
//	 */
//	List<BankCardVO> getBankCardList(@Param("bc") EBankCard bc);
//	
//	
//	/**
//	 * 정보 조회
//	 * @param inputBC
//	 * @return
//	 */
//	BankCardVO getBankCard( @Param("no") long no );
//	
//	/**
//	 * 간단 정보 조회
//	 * @param no
//	 * @return
//	 */
//	BankCardVO getSimpleBankCard( @Param("no") long no );
//	
//	
//	/**
//	 * 수정
//	 * @param vo
//	 * @return
//	 */
//	int updateBankCard(BankCardVO vo);
//	
//	/**
//	 * 삭제
//	 * @param vo
//	 * @return
//	 * @throws Exception
//	 */
//	int deleteBankCard( @Param("no") long no );

	


	

	
}

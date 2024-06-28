package com.utime.household.environment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.environment.vo.BankCardVO;
import com.utime.household.environment.vo.BankVO;
import com.utime.household.environment.vo.CardItemVO;
import com.utime.household.environment.vo.CardVO;
import com.utime.household.environment.vo.CompanyVO;
import com.utime.household.environment.vo.EBankCard;

/**
 * 카드 은행 Mapper
 */
@Mapper
public interface BankCardMapper {
	
	/**
	 * 은행 사 추가
	 * @param name
	 * @param bean
	 * @return
	 */
	public int insertBankCompany( @Param("name") String name, @Param("bean") String bean );
	
	/**
	 * 카드 사 추가
	 * @param name
	 * @param bean
	 * @return
	 */
	public int insertCardCompany( @Param("name") String name, @Param("bean") String bean );

	/**
	 * 은행 사 조회
	 * @param name
	 * @return
	 */
	public List<CompanyVO> selectBankCompanyList();
	
	/**
	 * 은행 사 조회
	 * @return
	 */
	public CompanyVO selectBankCompany(@Param("bankNo") long bankNo);
	
	/**
	 * 카드 사 조회
	 * @param name
	 * @return
	 */
	public List<CompanyVO> selectCardCompanyList();
	
	/**
	 * 은행 사 조회
	 * @return
	 */
	public CompanyVO selectCardCompany(@Param("cardNo") long bankNo);

	/**
	 * Bank and card list
	 * @param bc
	 * @return
	 */
	List<BankCardVO> selectBankCardList(@Param("bc") EBankCard bc);
	
	/**
	 * Bank and card 
	 * @param no
	 * @return
	 */
	BankCardVO selectBankCard(@Param("no") long no);
	
	/**
	 * 간단 정보 조회
	 * @param no
	 * @return
	 */
	BankCardVO getSimpleBankCard( @Param("no") long no );

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
	int insertBankCard(BankCardVO vo) throws Exception;
	
	/**
	 * 수정
	 * @param vo
	 * @return
	 */
	int updateBankCard(BankCardVO vo) throws Exception;
	
	/**
	 * 삭제
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	int deleteBankCard( @Param("no") long no ) throws Exception;
	
	/**
	 * 은행 정보 조회
	 * @param bankNo
	 * @return
	 */
	BankVO selectBank(@Param("bankNo") long bankNo );
	
	/**
	 * 은행 추가
	 * @param bvo
	 * @return
	 */
	public int insertBank(BankVO bvo) throws Exception;
	
	/**
	 * 은행 수정
	 * @param bvo
	 * @return
	 */
	public int updateBank(BankVO bvo) throws Exception;
	
	/**
	 * 은행 삭제
	 * @param bvo
	 * @return
	 */
	public int deleteBank(@Param("no") long no) throws Exception;
	
	/**
	 * 카드 추가
	 * @param card
	 * @return
	 */
	public int insertCard(CardVO card) throws Exception;
	
	/**
	 * 카드 수정
	 * @param card
	 * @return
	 */
	public int updateCard(CardVO card) throws Exception;
	
	/**
	 * 카드 삭제
	 * @param card
	 * @return
	 */
	public int deleteCard(@Param("no") long no) throws Exception;
	
	/**
	 * 카드 아이템 리스트
	 * @param cardNo
	 * @return
	 */
	List<CardItemVO> selectCardItemList(@Param("cardNo") long cardNo);
	
	/**
	 * 카드 아이템 추가 
	 * @param item
	 * @return
	 */
	int insertCardItem(CardItemVO item);
	
	/**
	 * 카드 아이템 수정
	 * @param item
	 * @return
	 */
	int updateCardItem(CardItemVO item);

	/**
	 * 카드 아이템 삭제 
	 * @param item
	 * @return
	 */
	int deleteCardItem(@Param("no") long no);
	
	/**
	 * 카드 아이템 모두 삭제
	 * @param cardNo
	 * @return
	 */
	int deleteAllCardItem(@Param("cardNo") long cardNo) throws Exception;
	




	
}

package com.utime.household.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.utime.household.user.vo.UserVo;

@Mapper
public interface UserMapper {

	/**
	 * 회원 테이블 생성
	 * @return
	 */
	int createUser();
	
	/**
	 * 회원 추가
	 * @param vo
	 * @return
	 */
	int insertUser( UserVo vo );
	
	/**
	 * 암호 수정
	 * @param vo
	 * @return
	 */
	int updateUserPw( UserVo vo );

	/**
	 * 회원정보 수정. 암호 제외
	 * @param vo
	 * @return
	 */
	int updateUser( UserVo vo );
	
	/**
	 * 회원 id로 조회
	 * @param userNo
	 * @return
	 */
	UserVo getUserFromId( @Param("id") String id);

	/**
	 * 회원 번호로 조회
	 * @param userNo
	 * @return
	 */
	UserVo getUserFromNo( @Param("userNo") long userNo);
	
	/**
	 * 비번 찾기에서 유효한 정보 조회
	 * @param vo
	 * @return
	 */
	List<UserVo> getUserFromCheck( UserVo vo );

	/**
	 * id / pw 일치하는 계정 정보 조회 
	 * @param id
	 * @param pw
	 * @return
	 */
	long getUserAndPw(@Param("id") String id, @Param("pw") String pw);

}

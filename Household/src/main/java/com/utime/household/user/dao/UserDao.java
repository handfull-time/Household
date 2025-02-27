package com.utime.household.user.dao;

import java.util.List;

import com.utime.household.user.vo.UserVo;

public interface UserDao {

	/**
	 * 로그인 처리와 함께 사용자 정보 조회
	 * @param id
	 * @param pw
	 * @return
	 */
	UserVo procLogin(String id, String pw);
	
	/**
	 * 회원 가입
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int joinUser(UserVo user)throws Exception;

	/**
	 * 회원 정보 갱신
	 * @param user
	 * @return
	 * @throws Exception
	 */
	int updateUser(UserVo user)throws Exception;
	
	/**
	 * 사용 가능 id 확인
	 * @param id
	 * @return
	 */
	boolean checkId(String id);

	/**
	 * 일치하는 id 목록 조회
	 * @param user
	 * @return
	 */
	List<UserVo> findUserId(UserVo user);
	
	/**
	 * 비번 변경
	 * @param id
	 * @param pw
	 * @return
	 */
	int convertPw( String id, String pw)throws Exception;

	/**
	 * ID 해당 사용자 정보 조회
	 * @param id
	 * @return
	 */
	UserVo getUserFromId(String id);
	
	/**
	 * 사용자 상세 
	 * @param id
	 * @return
	 */
	UserVo getUserFromIdDetail(String id);
}

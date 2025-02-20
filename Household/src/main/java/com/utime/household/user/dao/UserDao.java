package com.utime.household.user.dao;

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
}

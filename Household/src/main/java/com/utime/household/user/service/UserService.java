package com.utime.household.user.service;

import java.io.IOException;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.vo.FindUserIdResVo;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.UserReqVo;

public interface UserService {

	/**
	 * 로그인 유효성 토큰
	 * @return
	 */
	String getNewGenToken(String sessionId);

	/**
	 * 회원 로그인
	 * @param reqVo
	 * @return
	 */
	ReturnBasic procLogin(LoginReqVo reqVo);

	/**
	 * 회원 가입
	 * @param reqVo
	 * @return
	 */
	ReturnBasic joinUser(UserReqVo reqVo)throws IOException;

	/**
	 * 사용 가능 id 확인
	 * @param id
	 * @return
	 */
	ReturnBasic checkId(String id);

	/**
	 * id 찾기
	 * @param reqVo
	 * @return
	 */
	FindUserIdResVo findUserId(UserReqVo reqVo);

	/**
	 * 비번 일치 정보 조회
	 * @param reqVo
	 * @return
	 */
	ReturnBasic findUserPw(UserReqVo reqVo);

	/**
	 * 비번 변경
	 * @param reqVo
	 * @return
	 */
	ReturnBasic convertUserPw(UserReqVo reqVo);

}

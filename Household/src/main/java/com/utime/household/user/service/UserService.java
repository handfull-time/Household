package com.utime.household.user.service;

import com.utime.household.common.vo.ReturnBasic;
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
	ReturnBasic joinUser(UserReqVo reqVo);

}

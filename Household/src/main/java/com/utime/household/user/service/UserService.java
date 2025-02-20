package com.utime.household.user.service;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.vo.LoginReqVo;

public interface UserService {

	/**
	 * 로그인 유효성 토큰
	 * @return
	 */
	String getNewGenToken(String sessionId);

	ReturnBasic procLogin(LoginReqVo reqVo);

}

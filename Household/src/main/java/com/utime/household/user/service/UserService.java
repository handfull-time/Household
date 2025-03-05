package com.utime.household.user.service;

import java.io.IOException;

import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.vo.FindUserIdResVo;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.ReqUniqueVo;
import com.utime.household.user.vo.UserReqVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	/**
	 * 
	 * @param refreshToken
	 * @return
	 */
	ReturnBasic refreshAccessToken(HttpServletRequest request, HttpServletResponse response, String refreshToken);
	
	/**
	 * 유효 정보 생성
	 * @param request
	 * @return
	 */
	ReqUniqueVo getNewGenUnique(HttpServletRequest request);

	/**
	 * 회원 로그인
	 * @param reqVo
	 * @return
	 */
	ReturnBasic procLogin(HttpServletRequest request, HttpServletResponse response, LoginReqVo reqVo);
	
	/**
	 * 로그 아웃
	 * @param request
	 * @param response
	 */
	void logout(HttpServletRequest request, HttpServletResponse response);

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

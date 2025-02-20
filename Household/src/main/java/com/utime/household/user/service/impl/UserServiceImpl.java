package com.utime.household.user.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.utime.household.common.jwt.JwtProvider;
import com.utime.household.common.util.CacheIntervalMap;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.UserReqVo;
import com.utime.household.user.vo.UserVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
	
	final CacheIntervalMap<String, String> intervalMap = new CacheIntervalMap<>(10L, TimeUnit.MINUTES);
	
	final UserDao userDao;
	final JwtProvider jwtUtil;

	@Override
	public String getNewGenToken(String sessionId) {
		
		final UUID guid = UUID.randomUUID();
		 
		final String result = guid.toString();
		
		this.intervalMap.put(result, sessionId);
		
		log.info("interval 추가: {} - {}", result, sessionId );

		return result;
	}

	@Override
	public ReturnBasic procLogin(LoginReqVo reqVo) {
		String sessionId = intervalMap.remove(reqVo.getToken());
		if( sessionId == null ) {
			log.warn("interval Key 없음: {} ", reqVo.getToken() );
			return new ReturnBasic("", "");
		}
		
		if( ! sessionId.equals(reqVo.getSessionId()) ) {
			log.warn("interval Value 불일치 : {} - {} ", sessionId, reqVo.getSessionId() );
			return new ReturnBasic("", "");
		}
		
		final UserVo user = userDao.procLogin( reqVo.getId(), reqVo.getPw());
		if( user == null ) {
			log.warn("회원 없음");
			
			this.intervalMap.put(reqVo.getToken(), sessionId);
			
			return new ReturnBasic("", "");
		}
		
		final String token = jwtUtil.generateAccessToken(user);
		
		return new ReturnBasic(HouseholdDefine.ERROR_OK, token);
	}
	
	@Override
	public ReturnBasic joinUser(UserReqVo reqVo) {
		final UserVo user = new UserVo();
		
		
		
		// TODO Auto-generated method stub
		return null;
	}
}

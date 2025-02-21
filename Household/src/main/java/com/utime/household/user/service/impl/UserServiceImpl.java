package com.utime.household.user.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.utime.household.common.jwt.JwtProvider;
import com.utime.household.common.util.CacheIntervalMap;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.vo.HouseholdDefine;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.EJwtRole;
import com.utime.household.user.vo.FindUserIdResVo;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.ReqUniqueVo;
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
	
	/**
	 * 유효성 검사
	 * @param reqVo
	 * @return true: 옳은 데이터
	 */
	private boolean validation(ReqUniqueVo reqVo) {
		String sessionId = intervalMap.get(reqVo.getToken());
		if( sessionId == null ) {
			log.warn("interval Key 없음: {} ", reqVo.getToken() );
			return false;
		}
		
		if( ! sessionId.equals(reqVo.getSessionId()) ) {
			log.warn("interval Value 불일치 : {} - {} ", sessionId, reqVo.getSessionId() );
			return false;
		}
		
		return true;
	}
	
	/**
	 * 유효성 검사 키 삭제.
	 * @param reqVo
	 */
	private void validationRemove(ReqUniqueVo reqVo) {
		this.intervalMap.remove(reqVo.getToken());
	}

	@Override
	public ReturnBasic procLogin(LoginReqVo reqVo) {
		
		if( ! this.validation(reqVo) ) {
			return new ReturnBasic("E", "");
		}
		
		
		final UserVo user = userDao.procLogin( reqVo.getId(), reqVo.getPw());
		if( user == null ) {
			log.warn("회원 없음");
			return new ReturnBasic("", "");
		}

		this.validationRemove(reqVo);
		
		final String token = jwtUtil.generateAccessToken(user);
		
		return new ReturnBasic(HouseholdDefine.ERROR_OK, token);
	}
	
	@Override
	public ReturnBasic checkId(String id) {
		
		final ReturnBasic result = new ReturnBasic();
		if( ! userDao.checkId(id) ) {
			result.setCodeMessage("E", "사용 중");
		}
		
		return result;
	}
	
	@Override
	public ReturnBasic joinUser(UserReqVo reqVo) throws IOException {
		
		if( ! this.validation(reqVo) ) {
			return new ReturnBasic("E", "");
		}
		
		final UserVo user = new UserVo();
		
		user.setUserNo(-1L);
		user.setEnabled(true);
		user.setId(reqVo.getId());
		user.setPw(reqVo.getPw());
		user.setImageBytes(HouseholdUtils.convertMultipartFileToByteArray(reqVo.getImage()));
		user.setNickname(reqVo.getNickname());
		user.setBirthday(reqVo.getBirthday());
		user.setRole(EJwtRole.User);
		user.setPwCheck1(reqVo.getMyRainbow());
		user.setPwCheck2(reqVo.getMySeason());
		user.setPwCheck3(reqVo.getMyNumber());
		
		final ReturnBasic result = new ReturnBasic();
		try {
			userDao.joinUser(user);
			
			this.validationRemove(reqVo);
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("E", e.getMessage());
		}
		
		return result;
	}

	@Override
	public FindUserIdResVo findUserId(UserReqVo reqVo) {
		
		final UserVo user = new UserVo();
		
		user.setUserNo(-1L);
		user.setBirthday(reqVo.getBirthday());
		user.setPwCheck1(reqVo.getMyRainbow());
		user.setPwCheck2(reqVo.getMySeason());
		user.setPwCheck3(reqVo.getMyNumber());
		
		final FindUserIdResVo result = new FindUserIdResVo();
		try {
			final List<UserVo> list = userDao.findUserId(user);
			result.setList(list);
			
			if( HouseholdUtils.isNotEmpty(list) ) {
				for( UserVo item : list) {
					final String id = item.getId();
					item.setId( "*" + id.substring(1, id.length()-2) + "**" );
					final String nickname = item.getNickname();
					item.setNickname( nickname.substring(0,  nickname.length()-1) + "*" );
				}
			}
			
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("E", e.getMessage());
		}
		
		return result;
	}

	@Override
	public ReturnBasic findUserPw(UserReqVo reqVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnBasic convertUserPw(UserReqVo reqVo) {
		// TODO Auto-generated method stub
		return null;
	}
}

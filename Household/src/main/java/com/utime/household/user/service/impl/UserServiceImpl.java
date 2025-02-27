package com.utime.household.user.service.impl;

import java.io.IOException;
import java.security.KeyPair;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.utime.household.common.jwt.JwtProvider;
import com.utime.household.common.util.CacheIntervalMap;
import com.utime.household.common.util.HouseholdUtils;
import com.utime.household.common.util.RsaEncDec;
import com.utime.household.common.util.Sha256;
import com.utime.household.common.vo.ReturnBasic;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.service.UserService;
import com.utime.household.user.vo.EJwtRole;
import com.utime.household.user.vo.FindUserIdResVo;
import com.utime.household.user.vo.LoginReqVo;
import com.utime.household.user.vo.ReqUniqueVo;
import com.utime.household.user.vo.TokenPairVo;
import com.utime.household.user.vo.UserReqVo;
import com.utime.household.user.vo.UserVo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {
	
	final CacheIntervalMap<String, String> intervalMap = new CacheIntervalMap<>(10L, TimeUnit.MINUTES);
	
	@Value("${security.pwSaltKey}")
    private String saltKey;
	
	final UserDao userDao;
	
	final JwtProvider jwtUtil;
	
	@Override
	public ReturnBasic refreshAccessToken(String refreshToken) {
		final ReturnBasic result = new ReturnBasic();
		
		if( HouseholdUtils.isEmpty(refreshToken)) {
			result.setCodeMessage("E", "Token is empty");
			return result;
		}
		
		if (!jwtUtil.validateToken(refreshToken)) {
			result.setCodeMessage("E", "Invalid refresh token");
			return result;
		}
		
		final String id = jwtUtil.getUsernameFromToken(refreshToken);
		if( HouseholdUtils.isEmpty(id)) {
			result.setCodeMessage("E", "id 값 추출 오류");
			return result;
		}
		
		final UserVo user = userDao.getUserFromId( id );
		if( user == null ) {
			log.warn("회원 없음");
			result.setCodeMessage("E", "회원 없음");
			return result;
		}
		
		final String newAccessToken = jwtUtil.generateAccessToken(user);
		result.setMessage(newAccessToken);
		
		return result;
	}
	
	/**
	 * Interval 에 추가.
	 * @param value
	 * @return 추가 key
	 */
	private String inputInterval( String value ) {
		
		if( value == null ) {
			return null;
		}
		
		final UUID guid = UUID.randomUUID();
		 
		final String result = guid.toString();
		
		this.intervalMap.put(result, value);
		
		log.info("interval 추가: {} - {}", result, value );

		return result;
	}
	
	@Override
	public ReqUniqueVo getNewGenUnique(HttpServletRequest request) {
		
		final ReqUniqueVo result = new ReqUniqueVo();
		result.setToken( this.inputInterval( request.getRequestedSessionId() ) );
		
		final KeyPair pair = RsaEncDec.generateRSAKeyPair();
		result.setPublicKey( RsaEncDec.getPulicKeyScript(pair) );
		result.setRsaId( this.inputInterval( RsaEncDec.getPrivateKey(pair) ) );
		
		return result;
	}
	
	/**
	 * 계정 찾기 정보 해싱
	 * @param user
	 * @return
	 */
	private String genUserUniqueHashing( UserReqVo user ) {
		return Sha256.encrypt(saltKey + user.getMyNumber() + user.getMyRainbow() + user.getMySeason());
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
	 * 암호 RSA 복호화
	 * @param reqVo
	 * @return
	 */
	private boolean convertEncPw( LoginReqVo reqVo ) {
		
		final String encPw = reqVo.getPw();
		if( HouseholdUtils.isEmpty(encPw)) {
			return true;
		}
		
		final String privateKey = intervalMap.get(reqVo.getRsaId());
		if( HouseholdUtils.isEmpty(privateKey)) {
			return false;
		}
		
		final String pw = RsaEncDec.rsaDecode( reqVo.getPw(), privateKey);
		reqVo.setPw(pw);
		
		return true;
	}
	
	/**
	 * 유효성 검사 키 삭제.
	 * @param reqVo
	 */
	private void validationRemove(ReqUniqueVo reqVo) {
		this.intervalMap.remove(reqVo.getToken());
		this.intervalMap.remove(reqVo.getRsaId());
	}

	@Override
	public TokenPairVo procLogin(LoginReqVo reqVo) {
		
		final TokenPairVo result = new TokenPairVo();

		if( ! this.validation(reqVo) ) {
			result.setCodeMessage("E", "");
			return result;
		}
		
		if( ! this.convertEncPw( reqVo ) ){
			result.setCodeMessage("E", "");
			return result;
		}
		
		final UserVo user = userDao.procLogin( reqVo.getId(), reqVo.getPw());
		if( user == null ) {
			log.warn("회원 없음");
			result.setCodeMessage("E", "");
			return result;
		}

		this.validationRemove(reqVo);
		
		final String accessToken = jwtUtil.generateAccessToken(user);
		final String refreshToken = jwtUtil.generateRefreshToken(user.getId());
		
		result.setTokenPair(accessToken, refreshToken);
		
		return result;
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
		
		if( ! this.convertEncPw( reqVo ) ){
			return new ReturnBasic("E", "");
		}
		
		final UserVo user = new UserVo();
		
		user.setUserNo(-1L);
		user.setEnabled(true);
		user.setId(reqVo.getId());
		user.setPw(reqVo.getPw());
		user.setImage(HouseholdUtils.encodeImageToBase64(reqVo.getImage()));
		user.setNickname(reqVo.getNickname());
		user.setBirthday(reqVo.getBirthday().replaceAll("-", ""));
		user.setRole(EJwtRole.User);
		user.setPwCheck( this.genUserUniqueHashing(reqVo) );
		
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
		user.setBirthday(reqVo.getBirthday().replaceAll("-", ""));
		user.setPwCheck( this.genUserUniqueHashing(reqVo) );
		
		final FindUserIdResVo result = new FindUserIdResVo();
		try {
			final List<UserVo> list = userDao.findUserId(user);
			result.setList(list);
			
			if( HouseholdUtils.isNotEmpty(list) ) {
				for( UserVo item : list) {
					final String id = item.getId();
					item.setId( id.substring(0, id.length()-2) + "**" );
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
		
		final ReturnBasic result = new ReturnBasic();
		
		final UserVo user = new UserVo();
		
		user.setUserNo(-1L);
		user.setId(reqVo.getId());
		user.setPwCheck( this.genUserUniqueHashing(reqVo) );
		
		final List<UserVo> list = userDao.findUserId(user);
		if( HouseholdUtils.isEmpty(list) ) {
			result.setCodeMessage("E", "일치 데이터 없음");
			return result;
		}
		
		final String key = inputInterval( list.get(0).getId() );
		result.setMessage(key);
		return result;
	}

	@Override
	public ReturnBasic convertUserPw(UserReqVo reqVo) {
		
		final ReturnBasic result = new ReturnBasic();
		
		final String id = intervalMap.get(reqVo.getToken());
		if( id == null ) {
			result.setCodeMessage("E", "일치 데이터 없음");
			return result;
		}
		
		if( ! this.convertEncPw( reqVo ) ){
			result.setCodeMessage("E", "암호 변경 실패");
			return result;
		}
		
		try {
			userDao.convertPw(id, reqVo.getPw());
		} catch (Exception e) {
			log.error("", e);
			result.setCodeMessage("E", e.getMessage());
		}
		
		this.validationRemove(reqVo);

		return result;
	}
}

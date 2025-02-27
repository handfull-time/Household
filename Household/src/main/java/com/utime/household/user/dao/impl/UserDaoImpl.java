package com.utime.household.user.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.common.util.Sha256;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.mapper.UserMapper;
import com.utime.household.user.vo.EJwtRole;
import com.utime.household.user.vo.UserVo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class UserDaoImpl implements UserDao{

	final CommonMapper common;
	
	final UserMapper userMapper;
	
	@Value("${security.pwSaltKey}")
    private String saltKey;
	
	
	@PostConstruct
	private void construct() {
		
		try {
			
			if( ! common.existTable("HH_USER") ) {
				log.info("HH_USER 생성");
				userMapper.createUser();
				
				common.createIndex("HH_USER_ID", "HH_USER", "ID");

				this.insertAdminUser();
			}

		}catch (Exception e) {
			log.error("", e);
		}
		
	}

	private void insertAdminUser() throws Exception{
		final UserVo admin = new UserVo();
		admin.setId("Admin");
		admin.setPw("Admin123");
		admin.setRole(EJwtRole.Admin);
		admin.setNickname("관리자");
		admin.setBirthday("20150820");
		admin.setPwCheck(Sha256.encrypt("" + System.currentTimeMillis()));
		
		this.joinUser(admin);
	}
	
	private String genPwString( UserVo user ) {
		return saltKey + "[" + user.getId() + "]-{" +  user.getUserNo() + "}" + user.getPw();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int joinUser(UserVo user) throws Exception {
		
		int result = 0;
		
		result += userMapper.insertUser(user);
		
		user.setPw( this.genPwString(user) );
		
		result += userMapper.updateUserPw(user);
		
		return result;
	}
	
	@Override
	public UserVo procLogin(String id, String pw) {
		
		UserVo result = userMapper.getUserFromId(id);
		if( result == null ) {
			log.warn("회원 없음");
			return null;
		}
		
		result.setPw(pw);
		
		final long no = userMapper.getUserAndPw(id, this.genPwString(result));
		
		if( no != result.getUserNo() ) {
			log.warn("pw 불일치");
			return null;
		}
		
		result.setPw(null);
		
		return result;
	}
	
	@Override
	public UserVo getUserFromId(String id) {
		
		return userMapper.getUserFromId(id);
	}
	
	@Override
	public int updateUser(UserVo user) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int convertPw(String id, String pw) throws Exception {
		
		final UserVo user = userMapper.getUserFromId(id);
		if( user == null ) {
			log.warn("회원 없음");
			return -1;
		}
		
		user.setPw(pw);
		
		user.setPw( this.genPwString(user) );
		
		return userMapper.updateUserPw(user);
	}
	
	@Override
	public boolean checkId(String id) {
		return userMapper.checkId(id);
	}
	
	@Override
	public List<UserVo> findUserId(UserVo user) {
		return userMapper.getUserFromCheck(user);
	}
	
	@Override
	public UserVo getUserFromIdDetail(String id) {
		
		return userMapper.getUserFromIdDetail(id);
	}
}

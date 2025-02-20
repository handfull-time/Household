package com.utime.household.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.utime.household.common.mapper.CommonMapper;
import com.utime.household.user.dao.UserDao;
import com.utime.household.user.mapper.UserMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
class UserDaoImpl implements UserDao{

	final CommonMapper common;
	
	final UserMapper userMapper;
	
	@PostConstruct
	private void construct() {
		
		try {
			
			if( ! common.existTable("HH_USER") ) {
				log.info("HH_USER 생성");
				userMapper.createUser();
				
				this.insertAdminUser();
			}
			
			
		}catch (Exception e) {
			log.error("", e);
		}
		
	}

	private void insertAdminUser() {
		// TODO Auto-generated method stub
		
	}
			
}

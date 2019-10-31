package com.mz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mz.entity.User;
import com.mz.mapper.UserMapper;
import com.mz.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private static final Logger LOGGER=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserMapper usermapper;

	
	@Override
	public int addUserEntity(User user) {
		// TODO Auto-generated method stub
		LOGGER.info("传递的插入参数----->{}",user);
		int insert = usermapper.insert(user);
		return insert;
	}

	@Override
	public int updateEntity(User user) {
		return usermapper.updateById(user);
	}


}

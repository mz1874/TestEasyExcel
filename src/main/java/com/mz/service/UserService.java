package com.mz.service;

import com.mz.entity.User;
import org.apache.poi.ss.formula.functions.T;

public interface UserService {
	
	int addUserEntity(User user);

	int updateEntity(User user);
}

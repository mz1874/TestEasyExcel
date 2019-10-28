package com.mz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.entity.User;
import com.mz.service.UserService;

@RequestMapping(value = "/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userservice;
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public void addUser() {
		User user=new User();
		user.setName("王迪");
		user.setSex("女");
		user.setAddress("上海");
		int addUserEntity = userservice.addUserEntity(user);
		System.out.println(addUserEntity);
	}
}

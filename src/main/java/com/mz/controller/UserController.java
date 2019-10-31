package com.mz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mz.entity.User;
import com.mz.service.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping(value = "/user")
@RestController
public class UserController {

	private static Logger logger= LoggerFactory.getLogger(UserController.class);

	@Value("${file.upload}")
	private String upload;

	@Autowired
	private UserService userservice;

	/**
	 * 测试添加
	 */
	@RequestMapping(value = "/add",method = RequestMethod.GET)
	public void addUser() {
		User user=new User();
		user.setName("王迪");
		user.setSex("女");
		user.setAddress("上海");
		int addUserEntity = userservice.addUserEntity(user);
	}

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	public String fileupload(@RequestBody MultipartFile file) throws IOException {
		logger.info("文件上传数据获取---->{}",file);
		// 获取文件名
		String fileName = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 文件上传路径
		String filePath = upload;
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			logger.info("文件目录不存在----开始创建----->{}",dest.getParentFile());
			dest.getParentFile().mkdirs();
		}
		logger.info("开始上传文件");
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传文件出现了错误---->{}",e);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			logger.error("上传文件出现了错误---->{}",e);
		}
		logger.info("上传文件成功");
		logger.info("开始解析excel");
		return "success";
	}


	@RequestMapping(value = "/update",method = RequestMethod.GET)
	public void setUpload(){
		User user=new User();
		user.setId("1");
		user.setAddress("南京");
		user.setSex("男");
		int i = userservice.updateEntity(user);
		logger.info("显示更新的行数 --->{}",i);
	}



}

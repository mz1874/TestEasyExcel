package com.mz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mz.entity.User;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User>  {
		
}

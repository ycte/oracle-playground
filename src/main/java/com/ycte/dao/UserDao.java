package com.ycte.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ycte.pojo.UserPojo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<UserPojo> {
}

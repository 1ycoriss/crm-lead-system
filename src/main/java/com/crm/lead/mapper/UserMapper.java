package com.crm.lead.mapper;

import com.crm.lead.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    SysUser selectByUsername(@Param("username") String username);
}
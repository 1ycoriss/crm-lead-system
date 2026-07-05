package com.crm.lead.service.impl;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LoginDTO;
import com.crm.lead.entity.SysUser;
import com.crm.lead.mapper.UserMapper;
import com.crm.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crm.lead.annotation.OperationLog;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @OperationLog("用户登录")
    public Result<String> login(LoginDTO loginDTO) {
        SysUser sysUser = userMapper.selectByUsername(loginDTO.getUsername());

        if (sysUser == null) {
            return Result.fail("用户不存在");
        }

        if (!sysUser.getPassword().equals(loginDTO.getPassword())) {
            return Result.fail("密码错误");
        }

        if (sysUser.getStatus() == null || sysUser.getStatus() != 1) {
            return Result.fail("用户已禁用");
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        String redisKey = "login:token:" + token;
        stringRedisTemplate.opsForValue().set(redisKey, sysUser.getUsername(), 30, TimeUnit.MINUTES);

        return Result.success("登录成功", token);
    }
}
package com.crm.lead.controller;

import com.crm.lead.entity.SysUser;
import com.crm.lead.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String hello() {
        return "hello lead system";
    }

    @GetMapping("/user")
    public SysUser getUser(@RequestParam("username") String username) {
        return userMapper.selectByUsername(username);
    }
}
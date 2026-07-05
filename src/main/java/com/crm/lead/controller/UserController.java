package com.crm.lead.controller;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LoginDTO;
import com.crm.lead.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }
}
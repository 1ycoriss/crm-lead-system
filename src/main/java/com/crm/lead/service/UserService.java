package com.crm.lead.service;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LoginDTO;

public interface UserService {

    Result<String> login(LoginDTO loginDTO);
}
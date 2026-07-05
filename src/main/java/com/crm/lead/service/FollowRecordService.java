package com.crm.lead.service;

import com.crm.lead.common.Result;
import com.crm.lead.dto.FollowAddDTO;

public interface FollowRecordService {

    Result<String> add(FollowAddDTO followAddDTO);
}
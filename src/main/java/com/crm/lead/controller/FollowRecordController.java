package com.crm.lead.controller;

import com.crm.lead.common.Result;
import com.crm.lead.dto.FollowAddDTO;
import com.crm.lead.service.FollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
public class FollowRecordController {

    @Autowired
    private FollowRecordService followRecordService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody FollowAddDTO followAddDTO) {
        return followRecordService.add(followAddDTO);
    }
}
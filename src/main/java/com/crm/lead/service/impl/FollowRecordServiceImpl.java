package com.crm.lead.service.impl;

import com.crm.lead.annotation.OperationLog;
import com.crm.lead.common.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import com.crm.lead.common.Result;
import com.crm.lead.dto.FollowAddDTO;
import com.crm.lead.entity.CrmLead;
import com.crm.lead.entity.FollowRecord;
import com.crm.lead.mapper.FollowRecordMapper;
import com.crm.lead.mapper.LeadMapper;
import com.crm.lead.service.FollowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FollowRecordServiceImpl implements FollowRecordService {

    @Autowired
    private FollowRecordMapper followRecordMapper;

    @Autowired
    private LeadMapper leadMapper;

    @Transactional
    @Override
    @OperationLog("新增跟进记录")
    public Result<String> add(FollowAddDTO followAddDTO) {
        CrmLead crmLead = leadMapper.selectById(followAddDTO.getLeadId());
        if (crmLead == null) {
            throw new BusinessException("线索不存在");
        }

        if (followAddDTO.getContent() == null || followAddDTO.getContent().trim().isEmpty()) {
            throw new BusinessException("跟进内容不能为空");
        }

        FollowRecord followRecord = new FollowRecord();
        followRecord.setLeadId(followAddDTO.getLeadId());
        followRecord.setFollowUserId(followAddDTO.getFollowUserId());
        followRecord.setFollowType(followAddDTO.getFollowType());
        followRecord.setFollowResult(followAddDTO.getFollowResult());
        followRecord.setContent(followAddDTO.getContent());
        followRecord.setNextFollowTime(followAddDTO.getNextFollowTime());

        int insertRows = followRecordMapper.insert(followRecord);
        if (insertRows <= 0) {
            throw new BusinessException("新增跟进记录失败");
        }

        int updateRows = leadMapper.updateAfterFollow(
                followAddDTO.getLeadId(),
                "FOLLOWING",
                LocalDateTime.now(),
                followAddDTO.getNextFollowTime()
        );

        if (updateRows <= 0) {
            throw new BusinessException("更新线索状态失败");
        }

        return Result.success("新增跟进记录成功");
    }
}
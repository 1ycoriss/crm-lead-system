package com.crm.lead.service.impl;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LeadAddDTO;
import com.crm.lead.entity.CrmLead;
import com.crm.lead.mapper.LeadMapper;
import com.crm.lead.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crm.lead.annotation.OperationLog;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.util.concurrent.TimeUnit;

import java.util.List;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadMapper leadMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @OperationLog("新增线索")
    public Result<String> addLead(LeadAddDTO leadAddDTO) {
        if (leadAddDTO.getCustomerName() == null || leadAddDTO.getCustomerName().trim().isEmpty()) {
            return Result.fail("客户姓名不能为空");
        }

        if (leadAddDTO.getPhone() == null || leadAddDTO.getPhone().trim().isEmpty()) {
            return Result.fail("手机号不能为空");
        }

        CrmLead crmLead = new CrmLead();
        crmLead.setCustomerName(leadAddDTO.getCustomerName());
        crmLead.setPhone(leadAddDTO.getPhone());
        crmLead.setCompanyName(leadAddDTO.getCompanyName());
        crmLead.setSource(leadAddDTO.getSource());
        crmLead.setRemark(leadAddDTO.getRemark());
        crmLead.setStatus("NEW");
        crmLead.setOwnerId(null);

        int rows = leadMapper.insert(crmLead);
        if (rows > 0) {
            return Result.success("新增线索成功");
        }

        return Result.fail("新增线索失败");
    }

    @Override
    public Result<List<CrmLead>> list() {
        List<CrmLead> crmLeadList = leadMapper.selectAll();
        return Result.success("查询成功", crmLeadList);
    }


    @OperationLog("领取线索")
    @Override
    public Result<String> claimLead(Long id, Long userId) {
        String lockKey = "lock:lead:claim:" + id;
        Boolean locked = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "1", 10, TimeUnit.SECONDS);

        if (locked == null || !locked) {
            return Result.fail("线索正在被领取，请稍后重试");
        }

        try {
            CrmLead crmLead = leadMapper.selectById(id);

            if (crmLead == null) {
                return Result.fail("线索不存在");
            }

            if (!"NEW".equals(crmLead.getStatus())) {
                return Result.fail("该线索已被领取或不可领取");
            }

            int rows = leadMapper.claimLead(id, userId, "CLAIMED");
            if (rows > 0) {
                // RocketMQ 接入点：领取成功后可发送消息，异步处理通知、日志和统计等非核心流程。
                System.out.println("预留MQ发送点：线索领取成功，后续可发送 RocketMQ 消息");
                return Result.success("领取成功");
            }

            return Result.fail("领取失败");
        } finally {
            stringRedisTemplate.delete(lockKey);
        }
    }
}

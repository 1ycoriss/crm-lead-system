package com.crm.lead.service;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LeadAddDTO;
import com.crm.lead.entity.CrmLead;

import java.util.List;

public interface LeadService {

    Result<String> addLead(LeadAddDTO leadAddDTO);
    Result<List<CrmLead>> list();
    Result<String> claimLead(Long id, Long userId);
}
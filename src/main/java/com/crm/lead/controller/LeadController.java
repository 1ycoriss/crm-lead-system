package com.crm.lead.controller;

import com.crm.lead.common.Result;
import com.crm.lead.dto.LeadAddDTO;
import com.crm.lead.entity.CrmLead;
import com.crm.lead.service.LeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lead")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody LeadAddDTO leadAddDTO) {
        return leadService.addLead(leadAddDTO);
    }

    @GetMapping("/list")
    public Result<List<CrmLead>> list() {
        return leadService.list();
    }

    @PostMapping("/claim/{id}")
    public Result<String> claimLead(@PathVariable("id") Long id,
                                    @RequestParam("userId") Long userId) {
        return leadService.claimLead(id, userId);
    }
}
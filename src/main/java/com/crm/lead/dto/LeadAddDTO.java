package com.crm.lead.dto;

import lombok.Data;

@Data
public class LeadAddDTO {

    private String customerName;

    private String phone;

    private String companyName;

    private String source;

    private String remark;
}
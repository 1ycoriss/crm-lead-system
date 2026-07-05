package com.crm.lead.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CrmLead {

    private Long id;

    private String customerName;

    private String phone;

    private String companyName;

    private String source;

    private String status;

    private Long ownerId;

    private LocalDateTime lastFollowTime;

    private LocalDateTime nextFollowTime;

    private String remark;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
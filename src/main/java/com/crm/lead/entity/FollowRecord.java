package com.crm.lead.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowRecord {

    private Long id;

    private Long leadId;

    private Long followUserId;

    private String followType;

    private String followResult;

    private String content;

    private LocalDateTime nextFollowTime;

    private LocalDateTime createTime;
}
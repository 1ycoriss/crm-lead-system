package com.crm.lead.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FollowAddDTO {

    private Long leadId;

    private Long followUserId;

    private String followType;

    private String followResult;

    private String content;

    private LocalDateTime nextFollowTime;
}
package com.crm.lead.mapper;

import com.crm.lead.entity.FollowRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FollowRecordMapper {

    int insert(FollowRecord followRecord);
}
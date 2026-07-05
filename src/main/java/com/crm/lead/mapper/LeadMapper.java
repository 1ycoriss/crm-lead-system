package com.crm.lead.mapper;

import com.crm.lead.entity.CrmLead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LeadMapper {

    int insert(CrmLead crmLead);
    List<CrmLead> selectAll();
    CrmLead selectById(Long id);
    int claimLead(@Param("id") Long id, @Param("ownerId") Long ownerId, @Param("status") String status);
    int updateAfterFollow(@Param("id") Long id,
                          @Param("status") String status,
                          @Param("lastFollowTime") LocalDateTime lastFollowTime,
                          @Param("nextFollowTime") LocalDateTime nextFollowTime);
}
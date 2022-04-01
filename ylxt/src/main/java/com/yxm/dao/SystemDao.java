package com.yxm.dao;

import com.yxm.po.SysInvoice;
import com.yxm.po.SysIssue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemDao {
    Integer selectIssueCount();
    List<SysIssue> selectIssueAll(@Param("page") Integer page,@Param("limit")Integer limit);

    Integer deleteIssueById(Integer issueId);

    Integer insertIssue(SysIssue sysIssue);

    List<SysIssue> selectIssue();
}

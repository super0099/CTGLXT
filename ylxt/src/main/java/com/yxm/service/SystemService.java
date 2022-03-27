package com.yxm.service;

import com.yxm.po.SysInvoice;
import com.yxm.po.SysIssue;
import com.yxm.vo.LayuiTableData;

import java.util.List;

public interface SystemService {
    LayuiTableData<SysInvoice> selectInvoiceAll(Integer page, Integer limit,String buyerName,String invoiceNumber);

    SysInvoice selectInvoiceById(Integer invoiceId);

    LayuiTableData<SysIssue> selectIssueAll(Integer page,Integer limit);

    boolean deleteIssueById(Integer issueId);

    boolean insertIssue(String rightCharacter);
    List<SysIssue> selectIssue();
}

package com.yxm.service.impl;

import com.yxm.dao.InvoiceDao;
import com.yxm.dao.SystemDao;
import com.yxm.po.SysInvoice;
import com.yxm.po.SysIssue;
import com.yxm.service.SystemService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private SystemDao systemDao;

    @Override
    public LayuiTableData<SysInvoice> selectInvoiceAll(Integer page, Integer limit,String buyerName,String invoiceNumber) {
        Integer count = this.invoiceDao.selectInvoiceCount();
        List<SysInvoice> data = this.invoiceDao.selectInvoiceAll(page,limit,buyerName,invoiceNumber);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public SysInvoice selectInvoiceById(Integer invoiceId) {
        return this.invoiceDao.selectInvoiceById(invoiceId);
    }

    @Override
    public LayuiTableData<SysIssue> selectIssueAll(Integer page, Integer limit) {
        Integer count = this.systemDao.selectIssueCount();
        List<SysIssue> data = this.systemDao.selectIssueAll(page,limit);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public boolean deleteIssueById(Integer issueId) {
        return this.systemDao.deleteIssueById(issueId)>0;
    }

    @Override
    public boolean insertIssue(String rightCharacter) {
        SysIssue sysIssue = new SysIssue();
        sysIssue.setContent(rightCharacter);
        sysIssue.setReleaseDate(new Date());
        return this.systemDao.insertIssue(sysIssue)>0;
    }

    @Override
    public List<SysIssue> selectIssue() {
        return this.systemDao.selectIssue();
    }
}

package com.yxm.service.impl;

import com.yxm.dao.IncomeDao;
import com.yxm.dao.InformDao;
import com.yxm.dao.InvoiceDao;
import com.yxm.po.SysElder;
import com.yxm.po.SysIncome;
import com.yxm.po.SysInvoice;
import com.yxm.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class InformServiceImpl implements InformService {

    @Autowired
    private InformDao informDao;

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private IncomeDao incomeDao;

    @Override
    public List<SysElder> selectElderInform() {
        return this.informDao.selectElderInform();
    }

    @Override
    public boolean updateCollection(Integer collectionId,SysInvoice sysInvoice,String userName) {
        boolean updateColl = this.informDao.updateCollection(collectionId)>0;
        //修改账单
        if(updateColl){
            sysInvoice.setOperation(userName);
            //新增发票
            boolean addInvoice = this.invoiceDao.InsertInvoice(sysInvoice)>0;
            if(addInvoice){
                SysIncome income = new SysIncome();
                income.setIncomeDate(new Date());
                income.setIncomeType("月度账单");
                income.setSumMoney(sysInvoice.getPrice());
                boolean addIncome = this.incomeDao.insertIncome(income)>0;
                if(!addIncome){
                    throw new RuntimeException("新增收入信息出错");
                }
            }else {
                throw new RuntimeException("新增发票出错");
            }
        }else {
            throw new RuntimeException("修改账单表出错");
        }
        return true;
    }
}

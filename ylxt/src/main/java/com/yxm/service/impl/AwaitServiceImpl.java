package com.yxm.service.impl;

import com.yxm.dao.*;
import com.yxm.po.SysAlteration;
import com.yxm.po.SysElder;
import com.yxm.po.SysIncome;
import com.yxm.po.SysInvoice;
import com.yxm.service.AwaitService;
import com.yxm.vo.ElderIndentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AwaitServiceImpl implements AwaitService {
    @Autowired
    private AwaitDao awaitDao;
    @Autowired
    private CostDao costDao;
    @Autowired
    private InformDao informDao;
    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private IncomeDao incomeDao;
    @Autowired
    private AlterationDao alterationDao;
    @Autowired
    private ElderDao elderDao;

    @Override
    public List<SysElder> selectAwaitElderAll() {
        return this.awaitDao.selectAwaitElder();
    }

    @Override
    public ElderIndentData selectAwaitElder(Integer elderId) {
        ElderIndentData elderIndentData = this.costDao.selectElderData(elderId);
        Byte state = this.awaitDao.collectionState(elderId);
        elderIndentData.setCollectionState(state);
        return elderIndentData;
    }

    @Override
    public boolean updateCollection(Integer collectionId, SysInvoice sysInvoice, String userName,Integer berthId,Integer appointmentId,String checkInCode) {
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
                if(addIncome){
                    //修改预约单改成已入住的状态
                    boolean updateAppointment = this.awaitDao.updateAppointmentById(appointmentId)>0;
                    if(updateAppointment){
                        boolean updateBerth = this.awaitDao.updateBerthById(berthId)>0;
                        if(updateBerth){
                            SysElder sysElder = this.elderDao.selectElderByAppointmentId(appointmentId);
                            SysAlteration sysAlteration = new SysAlteration();
                            sysAlteration.setElderName(sysElder.getElderName());
                            sysAlteration.setAlterationType((byte) 1);
                            sysAlteration.setAlterationTime(new Date());
                            sysAlteration.setElderId(sysElder.getId());
                            boolean isOk = this.alterationDao.insertAlteration(sysAlteration)>0;
                            if(!isOk){
                                throw new RuntimeException("新增人员管理信息出错");
                            }
                        }else {
                            throw new RuntimeException("修改床位信息出错");
                        }
                    }else {
                        throw new RuntimeException("修改预约表信息出错");
                    }
                }else {
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

    @Override
    public boolean elderCheckIn(Integer berthId, Integer appointmentId) {
        //修改预约单改成已入住的状态
        boolean updateAppointment = this.awaitDao.updateAppointmentById(appointmentId)>0;
        if(updateAppointment){
            boolean updateBerth = this.awaitDao.updateBerthById(berthId)>0;
            if(updateBerth){
                SysElder sysElder = this.elderDao.selectElderByAppointmentId(appointmentId);
                SysAlteration sysAlteration = new SysAlteration();
                sysAlteration.setElderName(sysElder.getElderName());
                sysAlteration.setAlterationType((byte) 1);
                sysAlteration.setAlterationTime(new Date());
                sysAlteration.setElderId(sysElder.getId());
                boolean isOk = this.alterationDao.insertAlteration(sysAlteration)>0;
                if(!isOk){
                    throw new RuntimeException("新增人员管理信息出错");
                }
            }else {
                throw new RuntimeException("修改床位信息出错");
            }
        }else {
            throw new RuntimeException("修改预约表信息出错");
        }
        return true;
    }
}

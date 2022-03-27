package com.yxm.service.impl;

import com.yxm.dao.*;
import com.yxm.po.*;
import com.yxm.service.UnsubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class UnsubscribeServiceImpl implements UnsubscribeService {
    @Autowired
    private ExpireDao expireDao;
    @Autowired
    private ElderDao elderDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private AlterationDao alterationDao;
    @Autowired
    private NursetypeDao nursetypeDao;
    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private CheckoutDao checkoutDao;

    @Override
    public List<SysElder> selectExpireElder() {
        return this.expireDao.selectExpireElderEd();
    }

    @Override
    public boolean deleteByElderId(Integer elderId) {
        SysElder sysElder = this.elderDao.selectElderById(elderId);
        //查出长者的预约单信息
        SysAppointment sysAppointment = this.appointmentDao.selectAppointmentByElderId(elderId);
        SimpleDateFormat simpleDateFormats = new SimpleDateFormat("yyyyMMdd");
        Integer endDate = Integer.parseInt(simpleDateFormats.format(new Date(String.valueOf(sysAppointment.getEndDate()))));
        Integer endDates = Integer.parseInt(simpleDateFormats.format(new Date()));
        //这个判断它是否属于到期
        if(endDate>endDates){
            Integer NurseType=0;
            if (sysAppointment.getNurseType()==1){
                NurseType=1;
            }
            if (sysAppointment.getNurseType()==2){
                NurseType=2;
            }
            if (sysAppointment.getNurseType()==3){
                NurseType=3;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
            Date date = new Date();
            //获取到他当月的账单
            SysCollection sysCollection = this.collectionDao.selectCollection(elderId,simpleDateFormat.format(date),date.getMonth()+1);
            if(sysCollection!=null){
                SysNursetype sysNursetype = this.nursetypeDao.selectNurseTypeById(NurseType);
                BigDecimal count = sysNursetype.getAlimony().add(sysNursetype.getBerth()).add(sysNursetype.getServiceCharge()).add(sysNursetype.getBoardWages());
                SysCheckout sysCheckout = new SysCheckout();
                sysCheckout.setElderId(elderId);
                sysCheckout.setElderName(sysElder.getElderName());
                sysCheckout.setElderIdCode(sysElder.getIDNumber());
                sysCheckout.setExpire(sysCollection.getClearing());
                sysCheckout.setPracticalExpire(new Date());
                sysCheckout.setAmount(count);
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");
                Date date1 = new Date(String.valueOf(sysCollection.getClearing()));
                //判断账单创建日期和当前日期相差多少天
                Integer day = (Integer.parseInt(simpleDateFormat1.format(date)))-(Integer.parseInt(simpleDateFormat1.format(date1)));
                System.out.println(day);
                if(day>=0&&day<10){
                    sysCheckout.setNeedRefund(count.multiply(new BigDecimal("0.6")));
                }
                if(day>10&&day<20){
                    sysCheckout.setNeedRefund(count.multiply(new BigDecimal("0.3")));
                }
                if (day>20&&day<=31){
                    sysCheckout.setNeedRefund(new BigDecimal(0));
                }
                sysCheckout.setState((byte) 1);
                boolean isOk = this.checkoutDao.insertCheckout(sysCheckout)>0;
                if (!isOk){
                    throw new RuntimeException("新增退住结算出错");
                }
            }
        }
        boolean condition = this.appointmentDao.updateAppointment(sysAppointment.getId())>0;
        if (condition){
            boolean condition1 = this.locationDao.updateBerths(sysElder.getBerthId())>0;
            if (condition1){
                boolean condition2 = this.elderDao.updateElderById(sysElder.getId())>0;
                if(condition2){
                    SysAlteration sysAlteration = new SysAlteration();
                    sysAlteration.setAlterationTime(new Date());
                    sysAlteration.setAlterationType((byte) 3);
                    sysAlteration.setElderId(sysElder.getId());
                    sysAlteration.setElderName(sysElder.getElderName());
                    boolean condition3 = this.alterationDao.insertAlteration(sysAlteration)>0;
                    if(!condition3){
                        throw new RuntimeException("新增人员管理信息出错");
                    }
                }else {
                    throw new RuntimeException("修改长者信息出错");
                }
            }else {
                throw new RuntimeException("修改床位信息出错");
            }
        }else {
            throw new RuntimeException("修改预约信息出错");
        }
        return true;
    }

    @Override
    public boolean contract(Date endDate,String checkInCode) {
        return this.appointmentDao.contract(endDate,checkInCode)>0;
    }

    @Override
    public Integer selectArrearage(Integer elderId) {
        return this.appointmentDao.selectArrearage(elderId);
    }
}

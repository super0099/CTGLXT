package com.yxm.service.impl;

import com.yxm.dao.AppointmentDao;
import com.yxm.dao.BondsmanDao;
import com.yxm.dao.ElderDao;
import com.yxm.dao.LocationDao;
import com.yxm.po.*;
import com.yxm.service.AppointmentService;
import com.yxm.service.CostService;
import com.yxm.vo.BondsmanList;
import com.yxm.vo.NursetTypeCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private ElderDao elderDao;
    @Autowired
    private BondsmanDao bondsmanDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private CostService costService;

    @Override
    public Integer selectAppointmentCount() {
        return this.appointmentDao.selectAppointmentCount();
    }

    @Transactional
    @Override
    public boolean insertAppointmentData(SysAppointment sysAppointment, BondsmanList bondsmanList) throws ParseException {
        //长者信息
        SysElder sysElder = new SysElder();
        sysElder.setAppointmentId(sysAppointment.getCheckInCode());
        sysElder.setElderName(sysAppointment.getElderName());
        sysElder.setBerthId(sysAppointment.getSite());
        sysElder.setElderGender(sysAppointment.getGender());
        sysElder.setPortrait(sysAppointment.getPortrait());
        sysElder.setBirthday(sysAppointment.getBirth());
        sysElder.setIDNumber(sysAppointment.getIDCode());
        sysElder.setResidence(sysAppointment.getResidence());
        //担保人1
        SysBondsman sysBondsman1 = new SysBondsman();
        sysBondsman1.setAppointmentId(sysAppointment.getCheckInCode());
        sysBondsman1.setBondsmanName(bondsmanList.getBondsmanName1());
        sysBondsman1.setIDNumber(bondsmanList.getIDNumber1());
        sysBondsman1.setHomeAddress(bondsmanList.getHomeAddress1());
        sysBondsman1.setPhone(bondsmanList.getPhone1());
        sysBondsman1.setRelation(bondsmanList.getRelation1());
        sysBondsman1.setWorkUnit(bondsmanList.getWorkUnit1());
        //担保人2
        SysBondsman sysBondsman2 = new SysBondsman();
        sysBondsman2.setAppointmentId(sysAppointment.getCheckInCode());
        sysBondsman2.setBondsmanName(bondsmanList.getBondsmanName2());
        sysBondsman2.setIDNumber(bondsmanList.getIDNumber2());
        sysBondsman2.setHomeAddress(bondsmanList.getHomeAddress2());
        sysBondsman2.setPhone(bondsmanList.getPhone2());
        sysBondsman2.setRelation(bondsmanList.getRelation2());
        sysBondsman2.setWorkUnit(bondsmanList.getWorkUnit2());
        //修改床位信息

        List<SysBondsman> bList = new ArrayList<>();
        bList.add(sysBondsman1);
        bList.add(sysBondsman2);
        //先新增入住信息表,再新增长者信息表,在新增担保人信息,然后再
        boolean insertAppointment = this.appointmentDao.insetAppointment(sysAppointment)>0;
        if (insertAppointment){
            Integer insertElder= this.elderDao.insertElder(sysElder);
            Integer sysElderId = sysElder.getId();
            if(insertElder>0){
                for (SysBondsman bondsman: bList) {
                    boolean isOk = this.bondsmanDao.insertBondsman(bondsman)>0;
                    if (isOk){

                    }else {
                        throw new RuntimeException("新增担保人表出错");
                    }
                }
                boolean addBerth = this.locationDao.updateBerth(sysAppointment.getSite())>0;
                if(!addBerth){
                    throw new RuntimeException("修改床位表出错");
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.roll(Calendar.DAY_OF_MONTH, -1);
                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
                Date expireDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
                Integer NurseTypeId=null;
                //转成Id
                if(sysAppointment.getNurseType()==1){
                    NurseTypeId = 1;
                }
                if(sysAppointment.getNurseType()==2){
                    NurseTypeId = 2;
                }
                if(sysAppointment.getNurseType()==3){
                    NurseTypeId = 3;
                }
                SysNursetype nursetype = this.costService.selectNurseType(NurseTypeId);
                BigDecimal money = ((nursetype.getAlimony().add(nursetype.getBerth())).add(nursetype.getBoardWages())).add(nursetype.getServiceCharge());
                SysCollection collection = new SysCollection();
                collection.setClearing(null);
                collection.setElderId(sysElderId);
                collection.setExpire(expireDate);
                collection.setTotalMoney(money);
                collection.setPractical(money);
                collection.setState((byte) 1);
                boolean Ok = this.costService.insertElderOrder(collection);
                if(Ok){

                }else {
                    throw new RuntimeException("新增长者预约单出错");
                }
            }else {
                throw new RuntimeException("新增长者表出错");
            }
        }else {
            throw new RuntimeException("新增入住表出错");
        }
        return true;
    }

    @Override
    public SysElder selectElder(String checkInCode) {
        return this.elderDao.selectElder(checkInCode);
    }

    @Override
    public SysAppointment selectAppointmentByElderId(Integer elderId) {
        return this.appointmentDao.selectAppointmentByElderId(elderId);
    }

    @Override
    public SysAppointment selectAppointmentByCheckInCode(String checkInCode) {
        return this.appointmentDao.selectAppointmentByCheckInCode(checkInCode);
    }

}

package com.yxm.service.impl;

import com.yxm.dao.AppointmentDao;
import com.yxm.dao.ComboDao;
import com.yxm.dao.ElderDao;
import com.yxm.po.SysAppointment;
import com.yxm.po.SysCombo;
import com.yxm.po.SysElder;
import com.yxm.service.ReservationService;
import com.yxm.vo.H5SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ElderDao elderDao;
    @Autowired
    private AppointmentDao appointmentDao;
    @Autowired
    private ComboDao comboDao;
    @Override
    public List<H5SelectVo> selectBerthGrade() {
        List<SysElder> sysElderList = this.elderDao.selectCheckInAll();
        List<H5SelectVo> rList=new ArrayList<>();
        for (SysElder sysElder:sysElderList) {
            rList.add(new H5SelectVo(String.valueOf(sysElder.getId()),sysElder.getElderName()));
        }
        return rList;
    }

    @Override
    public SysAppointment selectElderNurse(Integer elderId) {
        SysAppointment sysAppointment = this.appointmentDao.selectAppointmentByElderId(elderId);
        return sysAppointment;
    }

    @Override
    public List<H5SelectVo> selectBreakfast(Integer comboGrade,Integer comboType) {
        List<SysCombo> sysComboList = this.comboDao.selectBreakfast(comboGrade,comboType);
        List<H5SelectVo> rList=new ArrayList<>();
        for (SysCombo sysCombo:sysComboList) {
            rList.add(new H5SelectVo(String.valueOf(sysCombo.getId()),sysCombo.getComboName()));
        }
        return rList;
    }

    @Override
    public List<H5SelectVo> selectLunch(Integer comboGrade, Integer comboType) {
        List<SysCombo> sysComboList = this.comboDao.selectBreakfast(comboGrade,comboType);
        List<H5SelectVo> rList=new ArrayList<>();
        for (SysCombo sysCombo:sysComboList) {
            rList.add(new H5SelectVo(String.valueOf(sysCombo.getId()),sysCombo.getComboName()));
        }
        return rList;
    }

    @Override
    public List<H5SelectVo> selectDinner(Integer comboGrade, Integer comboType) {
        List<SysCombo> sysComboList = this.comboDao.selectBreakfast(comboGrade,comboType);
        List<H5SelectVo> rList=new ArrayList<>();
        for (SysCombo sysCombo:sysComboList) {
            rList.add(new H5SelectVo(String.valueOf(sysCombo.getId()),sysCombo.getComboName()));
        }
        return rList;
    }

    @Override
    public SysCombo selectComboById(Integer comboId) {
        return this.comboDao.selectComboById(comboId);
    }
}

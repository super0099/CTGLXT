package com.yxm.service.impl;

import com.yxm.dao.ElderDao;
import com.yxm.po.SysAppointment;
import com.yxm.po.SysBondsman;
import com.yxm.po.SysElder;
import com.yxm.service.ElderService;
import com.yxm.vo.ElderAndBerth;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElderServiceImpl implements ElderService {

    @Autowired
    private ElderDao elderDao;
    @Override
    public LayuiTableData<ElderAndBerth> selectElderAll(Integer page, Integer limit,String elderName,String berthNumber){
        List<ElderAndBerth> data = this.elderDao.selectElderAll(page,limit,elderName,berthNumber);
        Integer count = this.elderDao.selectElderCount();
        return new LayuiTableData<>(count,data);
    }

    @Override
    public SysElder selectElderById(Integer elderId) {
        return this.elderDao.selectElderById(elderId);
    }

    @Override
    public boolean updateElder(SysElder sysElder) {
        return this.elderDao.updateElder(sysElder)>0;
    }

    @Override
    public boolean deleteElderById(Integer elderId) {
        return this.elderDao.deleteElderById(elderId)>0;
    }

    @Override
    public SysElder selectElderByAppointmentId(Integer appointmentId) {
        return this.elderDao.selectElderByAppointmentId(appointmentId);
    }
}

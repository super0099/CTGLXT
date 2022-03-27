package com.yxm.service.impl;

import com.yxm.dao.LocationDao;
import com.yxm.po.SysBerth;
import com.yxm.po.SysNursetype;
import com.yxm.po.SysPosition;
import com.yxm.service.LocationService;
import com.yxm.vo.BerthAndElder;
import com.yxm.vo.BerthGrade;
import com.yxm.vo.H5SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationDao locationDao;
    @Override
    public List<BerthAndElder> selectBerthAll(Integer berthId) {
        return locationDao.selectBerthAll(berthId);
    }

    @Override
    public List<H5SelectVo> selectBerthGrade(Integer grade) {
        List<BerthGrade> positionList=this.locationDao.selectBerthGrade(grade);
        List<H5SelectVo> rList=new ArrayList<>();
        for (BerthGrade position:positionList) {
            rList.add(new H5SelectVo(String.valueOf(position.getId()),position.getSite()));
        }
        return rList;
    }

    @Override
    public List<H5SelectVo> selectNurseTypeBerth() {
        List<SysNursetype> sysNursetypes = this.locationDao.selectNurseTypeBerth();
        List<H5SelectVo> rList=new ArrayList<>();
        for (SysNursetype position:sysNursetypes) {
            rList.add(new H5SelectVo(String.valueOf(position.getId()),position.getNurseType()));
        }
        return rList;
    }

    @Override
    public SysBerth selectBerthById(Integer berthId) {
        return this.locationDao.selectBerthById(berthId);
    }
}

package com.yxm.service.impl;

import com.yxm.dao.ElderDao;
import com.yxm.po.SysElder;
import com.yxm.service.ArrearageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class ArrearageServiceImpl implements ArrearageService {
    @Autowired
    private ElderDao elderDao;
    @Override
    public List<SysElder> selectArrearageElder() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        Integer years = Integer.parseInt(simpleDateFormat.format(date));
        return this.elderDao.selectArrearageElder(years,date.getMonth()+1);
    }
}

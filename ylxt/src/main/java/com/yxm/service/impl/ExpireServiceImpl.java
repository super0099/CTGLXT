package com.yxm.service.impl;

import com.yxm.dao.BondsmanDao;
import com.yxm.dao.ExpireDao;
import com.yxm.po.SysBondsman;
import com.yxm.po.SysElder;
import com.yxm.service.ExpireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpireServiceImpl implements ExpireService {
    @Autowired
    private ExpireDao expireDao;
    @Autowired
    private BondsmanDao bondsmanDao;
    @Override
    public List<SysElder> selectExpireElder() {
        return this.expireDao.selectExpireElder();
    }

    @Override
    public List<SysBondsman> selectElderBondsman(String appointmentId) {
        return this.bondsmanDao.selectElderBondsman(appointmentId);
    }
}

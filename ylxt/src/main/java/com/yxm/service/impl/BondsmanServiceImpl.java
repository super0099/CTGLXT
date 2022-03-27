package com.yxm.service.impl;

import com.yxm.dao.BondsmanDao;
import com.yxm.po.SysBondsman;
import com.yxm.service.BondsmanService;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BondsmanServiceImpl implements BondsmanService {
    @Autowired
    private BondsmanDao bondsmanDao;
    @Override
    public LayuiTableData<SysBondsman> selectBondsmanAll(Integer page, Integer limit, String elderName, String bondsmanName, String phone) {
        List<SysBondsman> data = this.bondsmanDao.selectBondsmanAll(page,limit,elderName,bondsmanName,phone);
        Integer count = bondsmanDao.selectBondsmanCount();
        return new LayuiTableData<>(count,data);
    }

    @Override
    public SysBondsman selectBondsmanById(Integer bondsmanId) {
        return this.bondsmanDao.selectBondsmanById(bondsmanId);
    }

    @Override
    public boolean updateBondsman(SysBondsman sysBondsman) {
        return this.bondsmanDao.updateBondsman(sysBondsman)>0;
    }

    @Override
    public Integer selectElderB(Integer elderId) {
        return this.bondsmanDao.selectElderB(elderId);
    }

    @Override
    public boolean deleteBondsman(Integer bondsmanId) {
        return this.bondsmanDao.deleteBondsman(bondsmanId)>0;
    }
}

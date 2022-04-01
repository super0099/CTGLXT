package com.yxm.service.impl;

import com.yxm.dao.ComboDao;
import com.yxm.po.SysCombo;
import com.yxm.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ComboServiceImpl implements ComboService {
    @Autowired
    private ComboDao comboDao;
    @Override
    public List<SysCombo> selectComboAll() {
        return this.comboDao.selectComboAll();
    }
}

package com.yxm.service.impl;

import com.yxm.dao.AccountDao;
import com.yxm.po.SysPosition;
import com.yxm.po.SysUser;
import com.yxm.service.AccountService;
import com.yxm.vo.H5SelectVo;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public LayuiTableData<SysUser> selectUserAll(Integer page,Integer limit) {
        Integer count = this.accountDao.selectUserCount();
        List<SysUser> data = this.accountDao.selectUserAll(page,limit);
        return new LayuiTableData<>(count,data);
    }

    @Override
    public List<H5SelectVo> selectPositionForH5Select() {
        List<SysPosition> positionList =this.accountDao.selectPosition();
        List<H5SelectVo> rList = new ArrayList<>();
        for (SysPosition sysPosition: positionList) {
            rList.add(new H5SelectVo(String.valueOf(sysPosition.getId()),sysPosition.getPositionName()));
        }
        return rList;
    }

    @Override
    public SysUser selectUserById(Integer userId) {
        return this.accountDao.selectUserById(userId);
    }

    @Override
    public boolean deleteElderById(Integer userId) {
        return this.accountDao.deleteElderById(userId)>0;
    }

    @Override
    public boolean insert(SysUser sysUser) {

        return this.accountDao.insert(sysUser)>0;
    }

    @Override
    public boolean update(SysUser sysUser) {
        return this.accountDao.update(sysUser)>0;
    }

    @Override
    public boolean updates(SysUser sysUser) {
        return this.accountDao.updates(sysUser)>0;
    }

    @Override
    public boolean delete(Integer userId) {
        return this.accountDao.delete(userId)>0;
    }
}

package com.yxm.service;

import com.yxm.dao.AccountDao;
import com.yxm.po.SysUser;
import com.yxm.vo.H5SelectVo;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AccountService {

    LayuiTableData<SysUser> selectUserAll(Integer page,Integer limit);
    List<H5SelectVo> selectPositionForH5Select();
    SysUser selectUserById(Integer userId);
    boolean deleteElderById(Integer userId);
    boolean insert(SysUser sysUser);
    boolean update(SysUser sysUser);
    boolean updates(SysUser sysUser);
    boolean delete(Integer userId);
}

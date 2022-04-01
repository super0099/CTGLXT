package com.yxm.dao;

import com.yxm.po.SysIncome;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDao {
    Integer insertIncome(SysIncome sysIncome);
}

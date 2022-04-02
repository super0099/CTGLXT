package com.yxm.dao;

import com.yxm.po.dbConsumption;

import java.math.BigDecimal;
import java.util.List;

public interface IWalletDao {
    BigDecimal selectBalance(Integer userId);
    List<dbConsumption> selectConsumption(Integer userId);
    boolean rechargeMoney(Integer userId,BigDecimal money);
    boolean Addconsumption(dbConsumption consumption);
    boolean consumption(Integer userId,BigDecimal money);
}

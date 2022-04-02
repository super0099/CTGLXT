package com.yxm.service;

import com.yxm.po.dbConsumption;

import java.math.BigDecimal;
import java.util.List;

public interface IWalletService {
    BigDecimal selectBalance(Integer userId);
    List<dbConsumption> selectConsumption(Integer userId);
    boolean recharge(Integer userId,BigDecimal money);
}

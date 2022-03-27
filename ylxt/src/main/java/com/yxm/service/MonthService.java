package com.yxm.service;

import com.yxm.vo.NursetTypeCost;

import java.math.BigDecimal;

public interface MonthService {
    NursetTypeCost selectNurseTypeCost(Integer month,Integer year);
    BigDecimal selectIncomeCost(Integer month,Integer year);
    Integer selectIncomeCount(Integer month,Integer year);
}

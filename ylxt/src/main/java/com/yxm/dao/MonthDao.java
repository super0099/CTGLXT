package com.yxm.dao;

import com.yxm.vo.NursetTypeCost;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MonthDao {
        NursetTypeCost selectNurseTypeCost(@Param("month") Integer month,@Param("year") Integer year);
        BigDecimal selectIncomeCost(@Param("month") Integer month,@Param("year") Integer year);
        Integer selectIncomeCount(@Param("month") Integer month,@Param("year") Integer year);
}

package com.yxm.dao;

import com.yxm.po.SysElder;
import com.yxm.po.SysIncome;
import com.yxm.vo.ElderIndentData;
import com.yxm.vo.SysMonthVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostDao {
    List<SysElder> selectElder();
    ElderIndentData selectElderData(Integer elderId);

    Integer selectPaymentCount(Integer elderId);
    Integer selectElderAllSettleCount(Integer elderId);

    SysMonthVo selectMonthVo(String years);

    Integer selectIncomeCount();

    List<SysIncome> selectIncomeAll(@Param("page")Integer page, @Param("limit") Integer limit);

    Integer selectArrearageElderCount(@Param("elderId") Integer elderId,@Param("years") Integer years,@Param("months") Integer months);
}

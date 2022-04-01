package com.yxm.dao;

import com.yxm.po.SysOrder;
import com.yxm.vo.OrderAndCombo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderDao {
    SysOrder selectOrderByElderIdAndDate(@Param("elderId") Integer elderId, @Param("orderDate")Date orderDate);
    Integer insertOrder(SysOrder sysOrder);
    Integer selectOrderCount();
    List<OrderAndCombo> selectOrderAll(@Param("page") Integer page, @Param("limit")Integer limit,@Param("elderName")String elderName);
}

package com.yxm.dao;

import com.yxm.po.SysCheckout;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutDao {
    Integer insertCheckout(SysCheckout sysCheckout);
    Integer selectCheckoutCount();
    List<SysCheckout> selectCheckoutAll(@Param("page") Integer page, @Param("limit") Integer limit);
    SysCheckout selectCheckOutById(Integer checkOutId);
    Integer updateCheckOutById(SysCheckout sysCheckout);
}

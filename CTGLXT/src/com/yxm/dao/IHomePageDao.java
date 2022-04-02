package com.yxm.dao;

import com.yxm.po.dbWorderForm;
import com.yxm.vo.CollectAndMenu;
import com.yxm.vo.HomePageWallet;
import com.yxm.vo.OrderMenu;

import java.util.List;

public interface IHomePageDao {
    HomePageWallet selectHomePageData(Integer userId);
    //首页查询用户的优惠卷数
    int selectDiscount(Integer userId);
    List<dbWorderForm> selectOrder(Integer userId);
    List<OrderMenu> selectOrderMenu(Integer orderFormId);
    List<CollectAndMenu> selectCollect(Integer userId);
    boolean removeMenu(Integer Id);
}

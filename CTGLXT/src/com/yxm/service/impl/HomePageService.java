package com.yxm.service.impl;

import com.yxm.dao.IHomePageDao;
import com.yxm.dao.impl.HomePageDao;
import com.yxm.po.dbWorderForm;
import com.yxm.service.IHomePageService;
import com.yxm.vo.*;

import java.util.ArrayList;
import java.util.List;

public class HomePageService implements IHomePageService {
    private static IHomePageDao homePageDao = new HomePageDao();
    @Override
    public HomePageWallet selectHomePageData(Integer userId) {
        return this.homePageDao.selectHomePageData(userId);
    }

    @Override
    public int selectDiscount(Integer userId) {
        return this.homePageDao.selectDiscount(userId);
    }

    @Override
    public OrderAndMenu DataCount(Integer userId) {
        OrderAndMenu orderAndMenu = new OrderAndMenu();
        orderAndMenu.setOrder(selectOrder(userId));
        orderAndMenu.setMenu(selectOrderMenu(userId));
        return orderAndMenu;
    }

    @Override
    public List<CollectAndMenu> selectCollect(Integer userId) {
        return this.homePageDao.selectCollect(userId);
    }

    @Override
    public boolean removeMenu(Integer Id) {
        return this.homePageDao.removeMenu(Id);
    }

    public List<dbWorderForm> selectOrder(Integer userId) {
        return this.homePageDao.selectOrder(userId);
    }
    public List<OrderMenu> selectOrderMenu(Integer userId) {
        List<dbWorderForm> dbWorderFormList = selectOrder(userId);
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for (dbWorderForm worder:dbWorderFormList) {
            orderMenuList.addAll(this.homePageDao.selectOrderMenu(worder.getId()));
        }
        return orderMenuList;
    }
}

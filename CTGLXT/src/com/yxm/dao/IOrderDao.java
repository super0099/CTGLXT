package com.yxm.dao;

import com.yxm.po.dbMenu;
import com.yxm.po.dbMenuOrder;
import com.yxm.po.dbWorderForm;
import com.yxm.vo.AddShopingCar;
import com.yxm.vo.OrderMenuList;

import java.util.List;

public interface IOrderDao {
    dbMenu selectMenu(Integer menuId);
    boolean addOrder(dbWorderForm worderForm);
    List<AddShopingCar> selectCarMenu(Integer carId);
    boolean addMenuOrder(dbMenuOrder dbMenuOrder);
    dbWorderForm selectOrder(String orderNumber);
    boolean deleteCarMneu(Integer carId);
    dbWorderForm selectrWorderForm(Integer orderId);
    List<OrderMenuList> orderMenuList(Integer orderId);
    boolean updataOrderState(Integer orderId);
}

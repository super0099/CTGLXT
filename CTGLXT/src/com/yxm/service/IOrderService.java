package com.yxm.service;

import com.yxm.po.dbMenu;
import com.yxm.po.dbMenuOrder;
import com.yxm.po.dbWorderForm;
import com.yxm.vo.AddShopingCar;

import java.util.List;

public interface IOrderService {
    dbMenu selectMenu(Integer menuId);
    List<AddShopingCar> selectCarMenu(Integer carId);
    boolean addOrder(dbWorderForm worderForm,dbMenuOrder dbMenuOrder);
    boolean addOrderList(dbWorderForm worderForm,List<AddShopingCar> addShopingCar,Integer carId);
    boolean orderPayment(Integer orderId,Integer userId);
}

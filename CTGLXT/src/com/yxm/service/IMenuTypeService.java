package com.yxm.service;

import com.yxm.po.dbGoods;
import com.yxm.po.dbMenu;
import com.yxm.vo.AddShopingCar;

import java.util.List;

public interface IMenuTypeService {
    List<dbMenu> selectMenuCount();
    List<dbMenu> selectMenuType(Integer menuTypeId);
    List<AddShopingCar> selectShopingCar(Integer userId);
    List<dbMenu> selectMenuName(String menuName);
    int selectMyCar(Integer userId);
    int selectCarMenu(Integer carId,Integer menuId);
    boolean addShopingCar(Integer carId,Integer menuId);
    dbGoods alterGoods(Integer carId, Integer menuId);
    boolean pushGoods(dbGoods dbGoods);
    boolean minusGoods(dbGoods dbGoods);
    boolean deleteGoods(Integer carId,Integer menuId);
    int inserCar(Integer userId);
    boolean isOk(Integer userId);
    boolean addShopingCarS(Integer carId,Integer menuId,Integer quantity);
}

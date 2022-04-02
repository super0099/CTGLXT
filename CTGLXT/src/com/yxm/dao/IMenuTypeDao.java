package com.yxm.dao;

import com.yxm.po.ShoppingCar;
import com.yxm.po.dbGoods;
import com.yxm.po.dbMenu;
import com.yxm.vo.AddShopingCar;

import java.util.List;

public interface IMenuTypeDao {
    List<dbMenu> selectMenuCount();
    List<dbMenu> selectMenuType(Integer menuTypeId);
    List<dbMenu> selectMenuName(String menuName);
    List<AddShopingCar> selectShopingCar(Integer userId);
    //查询用户的购物车Id
    int selectMyCar(Integer userId);
    int selectCarMenu(Integer carId,Integer menuId);
    //新增
    boolean addShopingCar(Integer carId,Integer menuId);
    //查询出要修改的购物车里的数据
    dbGoods alterGoods(Integer carId,Integer menuId);
    boolean pushGoods(dbGoods dbGoods);
    boolean minusGoods(dbGoods dbGoods);
    boolean deleteGoods(Integer carId,Integer menuId);
    int inserCar(Integer userId);
    boolean isOk(Integer userId);
    boolean addShopingCarS(Integer carId,Integer menuId,Integer quantity);
}

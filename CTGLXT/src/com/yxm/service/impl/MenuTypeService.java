package com.yxm.service.impl;

import com.yxm.dao.IMenuTypeDao;
import com.yxm.dao.impl.MenuTypeDao;
import com.yxm.po.dbGoods;
import com.yxm.po.dbMenu;
import com.yxm.service.IMenuTypeService;
import com.yxm.vo.AddShopingCar;

import java.util.List;

public class MenuTypeService implements IMenuTypeService {
    private static IMenuTypeDao menuTypeDao = new MenuTypeDao();
    @Override
    public List<dbMenu> selectMenuCount() {
        return this.menuTypeDao.selectMenuCount();
    }

    @Override
    public List<dbMenu> selectMenuType(Integer menuTypeId) {
        return this.menuTypeDao.selectMenuType(menuTypeId);
    }

    /**
     * 查询用户购物车所以的菜品
     * @param userId
     * @return
     */
    @Override
    public List<AddShopingCar> selectShopingCar(Integer userId) {
        return this.menuTypeDao.selectShopingCar(userId);
    }

    @Override
    public List<dbMenu> selectMenuName(String menuName) {
        return this.menuTypeDao.selectMenuName(menuName);
    }

    @Override
    public int selectMyCar(Integer userId) {
        return this.menuTypeDao.selectMyCar(userId);
    }

    @Override
    public int selectCarMenu(Integer carId, Integer menuId) {
        return this.menuTypeDao.selectCarMenu(carId,menuId);
    }

    @Override
    public boolean addShopingCar(Integer carId, Integer menuId) {
        return this.menuTypeDao.addShopingCar(carId,menuId);
    }

    @Override
    public dbGoods alterGoods(Integer carId, Integer menuId) {
        return this.menuTypeDao.alterGoods(carId,menuId);
    }

    @Override
    public boolean pushGoods(dbGoods dbGoods) {
        return this.menuTypeDao.pushGoods(dbGoods);
    }

    @Override
    public boolean minusGoods(dbGoods dbGoods) {
        return this.menuTypeDao.minusGoods(dbGoods);
    }

    @Override
    public boolean deleteGoods(Integer carId, Integer menuId) {
        return this.menuTypeDao.deleteGoods(carId,menuId);
    }

    @Override
    public int inserCar(Integer userId) {
        return this.menuTypeDao.inserCar(userId);
    }

    @Override
    public boolean isOk(Integer userId) {
        return this.menuTypeDao.isOk(userId);
    }

    @Override
    public boolean addShopingCarS(Integer carId, Integer menuId, Integer quantity) {
        return this.menuTypeDao.addShopingCarS(carId,menuId,quantity);
    }
}

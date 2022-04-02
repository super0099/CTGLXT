package com.yxm.service.impl;

import com.yxm.dao.IParticularDao;
import com.yxm.dao.impl.ParticularDao;
import com.yxm.po.dbMenu;
import com.yxm.service.IParticularService;
import com.yxm.util.JdbcUtils;

import java.sql.SQLException;
import java.util.List;

public class ParticularService implements IParticularService {
    private static IParticularDao particularDao = new ParticularDao();
    @Override
    public dbMenu selectMenu(Integer menuId) {
        return this.particularDao.selectMenu(menuId);
    }

    @Override
    public List<dbMenu> ranking() {
        return this.particularDao.ranking();
    }

    @Override
    public int myCollectMenu(Integer userId, Integer menuId) {
        return this.particularDao.myCollectMenu(userId,menuId);
    }

    @Override
    public boolean addCollect(Integer userId, Integer menuId) {
        boolean boolR = false;
        try {
            JdbcUtils.beginTransaction();
            boolean isOK = this.particularDao.collect(menuId);
            if(!isOK){
                throw new SQLException("在菜品表中自增收藏数量失败");
            }
            boolean Ok = this.particularDao.addCollect(userId,menuId);
            if(Ok){
                boolR =true;
                JdbcUtils.commitTransaction();
            }else {
                throw new SQLException("收藏失败");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                JdbcUtils.rollbackTransaction();//事务回滚
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return boolR;
    }
}

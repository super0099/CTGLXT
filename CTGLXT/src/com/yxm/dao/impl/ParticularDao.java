package com.yxm.dao.impl;

import com.yxm.dao.IParticularDao;
import com.yxm.po.dbMenu;
import com.yxm.util.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParticularDao implements IParticularDao {
    private static final String selectMenu="SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu WHERE Id=?";
    private static final String ranking = "SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu ORDER BY market DESC";
    private static final String collect ="UPDATE db_menu SET collectS = collectS+1 WHERE Id = ?";
    private static final String myCollectMenu = "SELECT COUNT(Id) FROM db_collect WHERE userId = ? AND nemuId = ?";
    private static final String addCollect="INSERT INTO db_collect(nemuId,userId) VALUE(?,?)";
    @Override
    public dbMenu selectMenu(Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbMenu dbmenu = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectMenu);
            ps.setInt(1,menuId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbmenu = new dbMenu();
                dbmenu.setChefId(rs.getInt("chefId"));
                dbmenu.setPutawayDate(new Date(rs.getTimestamp("putawayDate").getTime()));
                dbmenu.setCollectS(rs.getInt("collectS"));
                dbmenu.setId(rs.getInt("Id"));
                dbmenu.setMenuTypeId(rs.getInt("menuTypeId"));
                dbmenu.setMenuName(rs.getString("menuName"));
                dbmenu.setPrice(rs.getBigDecimal("price"));
                dbmenu.setIntroduce(rs.getString("introduce"));
                dbmenu.setMarket(rs.getInt("market"));
                dbmenu.setNumber(rs.getString("number"));
                dbmenu.setPicture(rs.getString("picture"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbmenu;
    }

    @Override
    public List<dbMenu> ranking() {
        List<dbMenu> dbMenuList = new ArrayList<>();
        Connection conn= null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(ranking);
            rs = ps.executeQuery();
            while (rs.next()){
                dbMenu dbMenu = new dbMenu();
                dbMenu.setChefId(rs.getInt("chefId"));
                dbMenu.setPutawayDate(new Date(rs.getTimestamp("putawayDate").getTime()));
                dbMenu.setCollectS(rs.getInt("collectS"));
                dbMenu.setId(rs.getInt("Id"));
                dbMenu.setMenuTypeId(rs.getInt("menuTypeId"));
                dbMenu.setMenuName(rs.getString("menuName"));
                dbMenu.setPrice(rs.getBigDecimal("price"));
                dbMenu.setIntroduce(rs.getString("introduce"));
                dbMenu.setMarket(rs.getInt("market"));
                dbMenu.setNumber(rs.getString("number"));
                dbMenu.setPicture(rs.getString("picture"));
                dbMenuList.add(dbMenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbMenuList;
    }

    @Override
    public boolean collect(Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(collect);
            ps.setInt(1,menuId);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public int myCollectMenu(Integer userId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            ps  = conn.prepareStatement(myCollectMenu);
            ps.setInt(1,userId);
            ps.setInt(2,menuId);
            rs = ps.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return count;
    }

    @Override
    public boolean addCollect(Integer userId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(addCollect);
            ps.setInt(1,menuId);
            ps.setInt(2,userId);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }
}

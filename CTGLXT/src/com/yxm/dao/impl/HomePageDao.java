package com.yxm.dao.impl;

import com.yxm.dao.IHomePageDao;
import com.yxm.po.dbWorderForm;
import com.yxm.util.JdbcUtils;
import com.yxm.vo.CollectAndMenu;
import com.yxm.vo.HomePageWallet;
import com.yxm.vo.OrderMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePageDao implements IHomePageDao {
    private static final String selectHomePageData = "SELECT db_user.userName,db_user.nickname,db_user.portrait,db_wallet.balance FROM db_user LEFT JOIN  db_wallet ON db_user.Id = db_wallet.userId  WHERE db_user.Id = ?";
    private static final String selectDiscount = "SELECT COUNT(id) FROM db_discounts WHERE userId = ?";
    private static final String selectOrder = "SELECT Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip,linkman FROM db_worderform WHERE userId = ? ORDER BY orderDate DESC";
    private static final String selectOrderMenu = "SELECT db_menuorder.Id,db_menuorder.orderFormId,db_menuorder.nemuId,db_menuorder.orderType,db_menuorder.quantity,db_menu.menuName,db_menu.picture FROM db_menuorder INNER JOIN db_menu ON db_menuorder.nemuId = db_menu.Id WHERE orderFormId = ?";
    private static final String selectCollect = "SELECT db_collect.Id,db_collect.nemuId,db_collect.userId,db_menu.menuName,db_menu.picture FROM db_collect INNER JOIN db_menu ON db_collect.nemuId = db_menu.Id WHERE userId=?";
    private static final String removemenu="DELETE FROM db_collect WHERE Id=?";
    //首页的数据回填
    @Override
    public HomePageWallet selectHomePageData(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HomePageWallet homePagePrivilege = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectHomePageData);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                homePagePrivilege = new HomePageWallet();
                homePagePrivilege.setBalance(rs.getBigDecimal("balance"));
                homePagePrivilege.setUserName(rs.getString("userName"));
                homePagePrivilege.setPortrait(rs.getString("portrait"));
                homePagePrivilege.setNickname(rs.getString("nickname"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return homePagePrivilege;
    }

    @Override
    public int selectDiscount(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectDiscount);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                count= rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return count;
    }

    @Override
    public List<dbWorderForm> selectOrder(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<dbWorderForm> dbWorderFormList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectOrder);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbWorderForm dbWorderForm = new dbWorderForm();
                //Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip,linkman
                dbWorderForm.setId(rs.getInt("Id"));
                dbWorderForm.setOrderNumber(rs.getString("orderNumber"));
                dbWorderForm.setOrderDate(new java.util.Date(rs.getTimestamp("orderDate").getTime()));
                dbWorderForm.setRemark(rs.getString("remark"));
                dbWorderForm.setPrice(rs.getBigDecimal("price"));
                dbWorderForm.setOrderStatus(rs.getInt("orderStatus"));
                dbWorderForm.setUserId(rs.getInt("userId"));
                dbWorderForm.setUserPhone(rs.getString("userPhone"));
                dbWorderForm.setSite(rs.getString("site"));
                dbWorderForm.setTip(rs.getBigDecimal("tip"));
                dbWorderForm.setLinkman(rs.getString("linkman"));
                dbWorderFormList.add(dbWorderForm);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbWorderFormList;
    }

    @Override
    public List<OrderMenu> selectOrderMenu(Integer orderFormId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderMenu> orderMenuList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectOrderMenu);
            ps.setInt(1,orderFormId);
            rs = ps.executeQuery();
            while (rs.next()){
                OrderMenu orderMenu = new OrderMenu();
                orderMenu.setId(rs.getInt("Id"));
                orderMenu.setMenuName(rs.getString("menuName"));
                orderMenu.setOrderType(rs.getInt("orderType"));
                orderMenu.setOrderFormId(rs.getInt("orderFormId"));
                orderMenu.setPicture(rs.getString("picture"));
                orderMenu.setNemuId(rs.getInt("nemuId"));
                orderMenu.setQuantity(rs.getInt("quantity"));
                orderMenuList.add(orderMenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return orderMenuList;
    }

    @Override
    public List<CollectAndMenu> selectCollect(Integer userId) {
        Connection conn= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CollectAndMenu> collectAndMenuList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectCollect);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                CollectAndMenu collectAndMenu = new CollectAndMenu();
                collectAndMenu.setMenuName(rs.getString("menuName"));
                collectAndMenu.setPicture(rs.getString("picture"));
                collectAndMenu.setMenuId(rs.getInt("nemuId"));
                collectAndMenu.setUserId(rs.getInt("userId"));
                collectAndMenu.setId(rs.getInt("Id"));
                collectAndMenuList.add(collectAndMenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return collectAndMenuList;
    }

    @Override
    public boolean removeMenu(Integer Id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(removemenu);
            ps.setInt(1,Id);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

}

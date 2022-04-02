package com.yxm.dao.impl;

import com.yxm.dao.IFuctionDao;
import com.yxm.dao.IOrderDao;
import com.yxm.po.dbMenu;
import com.yxm.po.dbMenuOrder;
import com.yxm.po.dbWorderForm;
import com.yxm.util.JdbcUtils;
import com.yxm.vo.AddShopingCar;
import com.yxm.vo.OrderMenuList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDao implements IOrderDao {
    private static final String selectMenu = "SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu WHERE Id = ?";
    private static final String addOrder="INSERT INTO db_worderform(orderNumber,orderDate,price,userId,orderStatus,userPhone,site,tip,linkman) VALUE(?,NOW(),?,?,?,?,?,?,?)";
    private static final String selectCarMenu="SELECT db_menu.menuName,db_menu.Id,db_menu.price,db_menu.picture,db_goods.number FROM db_menu INNER JOIN db_goods ON db_menu.Id = db_goods.menuId WHERE db_goods.carId = ?";
    private static final String addMenuOrder = "INSERT INTO db_menuorder(orderFormId,nemuId,orderType,quantity) VALUE(?,?,?,?)";
    private static final String selectOrderNumber = "SELECT Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip FROM db_worderform WHERE orderNumber = ?";
    private static final String deleteCarMneu = "DELETE FROM db_goods WHERE carId = ?";
    private static final String selectrWorderForm = "SELECT Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip,linkman FROM db_worderform WHERE id = ?";
    private static final String orderMenuList="SELECT db_menuorder.quantity,db_menu.menuName,db_menu.price,db_menu.picture FROM db_menuorder INNER JOIN db_menu ON db_menuorder.nemuId = db_menu.Id INNER JOIN db_worderform ON db_menuorder.orderFormId =db_worderform.Id WHERE db_worderform.Id=?";
    private static final String updataOrderState = "UPDATE db_worderform SET orderStatus=2 WHERE Id=?";
    @Override
    public dbMenu selectMenu(Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbMenu dbMenu = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectMenu);
            ps.setInt(1,menuId);
            rs = ps.executeQuery();
            while (rs.next()){
                //Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture
                dbMenu = new dbMenu();
                dbMenu.setId(rs.getInt("Id"));
                dbMenu.setMenuName(rs.getString("menuName"));
                dbMenu.setPrice(rs.getBigDecimal("price"));
                dbMenu.setIntroduce(rs.getString("introduce"));
                dbMenu.setMarket(rs.getInt("market"));
                dbMenu.setCollectS(rs.getInt("collectS"));
                dbMenu.setPutawayDate(new Date(rs.getTimestamp("putawayDate").getTime()));
                dbMenu.setChefId(rs.getInt("chefId"));
                dbMenu.setNumber(rs.getString("number"));
                dbMenu.setMenuTypeId(rs.getInt("menuTypeId"));
                dbMenu.setPicture(rs.getString("picture"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbMenu;
    }

    @Override
    public boolean addOrder(dbWorderForm worderForm) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(addOrder);
            //Id  orderNumber  orderDate            remark  price   orderStatus  userId  userPhone  site    tip
            ps.setString(1,worderForm.getOrderNumber());
            ps.setBigDecimal(2,worderForm.getPrice());
            ps.setInt(3,worderForm.getUserId());
            ps.setInt(4,worderForm.getOrderStatus());
            ps.setString(5,worderForm.getUserPhone());
            ps.setString(6,worderForm.getSite());
            ps.setBigDecimal(7,worderForm.getTip());
            ps.setString(8,worderForm.getLinkman());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public List<AddShopingCar> selectCarMenu(Integer carId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AddShopingCar> addShopingCarList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectCarMenu);
            ps.setInt(1,carId);
            rs = ps.executeQuery();
            while (rs.next()){
                AddShopingCar addShopingCar = new AddShopingCar();
                addShopingCar.setAmount(rs.getInt("number"));
                addShopingCar.setId(rs.getInt("db_menu.Id"));
                addShopingCar.setPrice(rs.getBigDecimal("price"));
                addShopingCar.setPicture(rs.getString("picture"));
                addShopingCar.setMenuName(rs.getString("menuName"));
                addShopingCarList.add(addShopingCar);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return addShopingCarList;
    }

    @Override
    public boolean addMenuOrder(dbMenuOrder dbMenuOrder) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(addMenuOrder);
            //Id  orderFormId  nemuId  orderType  quantity
            ps.setInt(1,dbMenuOrder.getOrderFormId());
            ps.setInt(2,dbMenuOrder.getNemuId());
            ps.setInt(3,dbMenuOrder.getOrderType());
            ps.setInt(4,dbMenuOrder.getQuantity());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public dbWorderForm selectOrder(String orderNumber) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbWorderForm worderForm = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectOrderNumber);
            ps.setString(1,orderNumber);
            rs = ps.executeQuery();
            while (rs.next()){
                //Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip
                worderForm = new dbWorderForm();
                worderForm.setId(rs.getInt("Id"));
                worderForm.setUserPhone(rs.getString("userPhone"));
                worderForm.setOrderStatus(rs.getInt("orderStatus"));
                worderForm.setTip(rs.getBigDecimal("tip"));
                worderForm.setOrderNumber(rs.getString("orderNumber"));
                worderForm.setUserId(rs.getInt("userId"));
                worderForm.setPrice(rs.getBigDecimal("price"));
                worderForm.setSite(rs.getString("site"));
                worderForm.setRemark(rs.getString("remark"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return worderForm;
    }

    @Override
    public boolean deleteCarMneu(Integer carId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(deleteCarMneu);
            ps.setInt(1,carId);
            ps.executeUpdate();
            boolR = true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }
    @Override
    public dbWorderForm selectrWorderForm(Integer orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbWorderForm worderForm = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectrWorderForm);
            ps.setInt(1,orderId);
            rs = ps.executeQuery();
            while (rs.next()){
                //Id,orderNumber,orderDate,remark,price,orderStatus,userId,userPhone,site,tip
                worderForm = new dbWorderForm();
                worderForm.setId(rs.getInt("Id"));
                worderForm.setUserPhone(rs.getString("userPhone"));
                worderForm.setOrderStatus(rs.getInt("orderStatus"));
                worderForm.setTip(rs.getBigDecimal("tip"));
                worderForm.setOrderNumber(rs.getString("orderNumber"));
                worderForm.setUserId(rs.getInt("userId"));
                worderForm.setPrice(rs.getBigDecimal("price"));
                worderForm.setSite(rs.getString("site"));
                worderForm.setRemark(rs.getString("remark"));
                worderForm.setLinkman(rs.getString("linkman"));
                worderForm.setOrderDate(new Date(rs.getTimestamp("orderDate").getTime()));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return worderForm;
    }

    @Override
    public List<OrderMenuList> orderMenuList(Integer orderId) {
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderMenuList> orderMenuLists = new ArrayList<>();
        try {
            conn=JdbcUtils.getConnection();
            ps =conn.prepareStatement(orderMenuList);
            ps.setInt(1,orderId);
            rs=ps.executeQuery();
            OrderMenuList orderMenuList1 = null;
            while (rs.next()){
                orderMenuList1 = new OrderMenuList();
                orderMenuList1.setMenuName(rs.getString("menuName"));
                orderMenuList1.setPrice(rs.getBigDecimal("price"));
                orderMenuList1.setQuantity(rs.getInt("quantity"));
                orderMenuList1.setPicture(rs.getString("picture"));
                orderMenuLists.add(orderMenuList1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return orderMenuLists;
    }

    @Override
    public boolean updataOrderState(Integer orderId) {
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR =false;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(updataOrderState);
            ps.setInt(1,orderId);
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }
}

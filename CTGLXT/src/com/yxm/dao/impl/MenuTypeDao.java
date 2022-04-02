package com.yxm.dao.impl;

import com.yxm.dao.IMenuTypeDao;
import com.yxm.po.ShoppingCar;
import com.yxm.po.dbGoods;
import com.yxm.po.dbMenu;
import com.yxm.util.JdbcUtils;
import com.yxm.vo.AddShopingCar;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenuTypeDao implements IMenuTypeDao {
    private static final String selectMenuCount = "SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu";
    private static final String selectMenuType = "SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu WHERE menuTypeId=?";
    private static final String selectShopingCar = "SELECT db_menu.menuName,db_menu.Id,db_menu.picture,db_menu.price,db_goods.number,db_shoppingcar.id FROM db_shoppingcar INNER JOIN db_goods ON db_shoppingcar.id = db_goods.carId INNER JOIN db_menu ON db_goods.menuId = db_menu.Id WHERE db_shoppingcar.userId = ?";
    private static final String selectMenuName = "SELECT Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture FROM db_menu WHERE menuName LIKE ?";
    private static final String selectMyCar = "SELECT id FROM db_shoppingcar WHERE userId=?";
    private static final String addShopingCar = "INSERT INTO db_goods(carId,menuId,number) VALUE(?,?,1)";
    private static final String selectCarMenu = "SELECT COUNT(Id) FROM db_goods WHERE carId = ? AND menuId=?";
    private static final String alterGoods = "SELECT Id,carId,menuId,number FROM db_goods WHERE carId=? AND menuId=?";
    private static final String pushGoods = "UPDATE db_goods SET number = ? WHERE Id = ?";
    private static final String deleteGoods = "DELETE FROM db_goods WHERE carId = ? AND menuId = ?";
    private static final String inserCar="SELECT COUNT(id) FROM db_shoppingcar WHERE userId=?";
    private static final String isOk= "INSERT INTO db_shoppingcar(userId) VALUE(?)";
    private static final String addShopingCarS = "INSERT INTO db_goods(carId,menuId,number) VALUE(?,?,?)";
    //查询所以菜品
    @Override
    public List<dbMenu> selectMenuCount() {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        List<dbMenu> dbMenus = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement(selectMenuCount);
            rs = ps.executeQuery();
            while (rs.next()){
                dbMenu dbmenu = new dbMenu();
                //Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture
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
                dbMenus.add(dbmenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbMenus;
    }
    //根据菜类型查询菜
    @Override
    public List<dbMenu> selectMenuType(Integer menuTypeId) {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        List<dbMenu> dbMenus = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();

            ps = conn.prepareStatement(selectMenuType);
            ps.setInt(1,menuTypeId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbMenu dbmenu = new dbMenu();
                //Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture
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
                dbMenus.add(dbmenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbMenus;
    }
    //根据菜名查询菜
    @Override
    public List<dbMenu> selectMenuName(String menuName) {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        List<dbMenu> dbMenus = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectMenuName);
            if(menuName==null)menuName="";
            ps.setString(1,"%"+menuName+"%");
            rs = ps.executeQuery();
            while (rs.next()){
                dbMenu dbmenu = new dbMenu();
                //Id,menuName,price,introduce,market,collectS,putawayDate,number,chefId,menuTypeId,picture
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
                dbMenus.add(dbmenu);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbMenus;
    }
    //查询我的购物车所以货物
    @Override
    public List<AddShopingCar> selectShopingCar(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<AddShopingCar> addShopingCarList = new ArrayList<>();
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectShopingCar);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                AddShopingCar addShopingCar = new AddShopingCar();
                addShopingCar.setCarId(rs.getInt("db_shoppingcar.id"));
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
    //查询我的购物车Id
    @Override
    public int selectMyCar(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer carId = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectMyCar);
            ps.setInt(1,userId);
            rs = ps.executeQuery();
            while (rs.next()){
                carId=rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return carId;
    }
    //添加菜品进入购物车
    @Override
    public boolean addShopingCar(Integer carId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(addShopingCar);
            ps.setInt(1,carId);
            ps.setInt(2,menuId);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return boolR;
    }

    @Override
    public dbGoods alterGoods(Integer carId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        dbGoods dbGoods = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(alterGoods);
            ps.setInt(1,carId);
            ps.setInt(2,menuId);
            rs = ps.executeQuery();
            while (rs.next()){
                dbGoods = new dbGoods();
                dbGoods.setId(rs.getInt("Id"));
                dbGoods.setNumber(rs.getInt("number"));
                dbGoods.setMenuId(rs.getInt(menuId));
                dbGoods.setCarId(rs.getInt(carId));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return dbGoods;
    }

    @Override
    public boolean pushGoods(dbGoods dbGoods) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(pushGoods);
            ps.setInt(1,dbGoods.getNumber()+1);
            ps.setInt(2,dbGoods.getId());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean minusGoods(dbGoods dbGoods) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(pushGoods);
            ps.setInt(1,dbGoods.getNumber()-1);
            ps.setInt(2,dbGoods.getId());
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean deleteGoods(Integer carId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn=JdbcUtils.getConnection();
            ps = conn.prepareStatement(deleteGoods);
            ps.setInt(1,carId);
            ps.setInt(2,menuId);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public int inserCar(Integer userId) {
        Connection conn =null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(inserCar);
            ps.setInt(1,userId);
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
    public boolean isOk(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(isOk);
            ps.setInt(1,userId);
            boolR=ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn,ps,rs);
        }
        return boolR;
    }

    @Override
    public boolean addShopingCarS(Integer carId, Integer menuId, Integer quantity) {
        Connection conn = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        boolean boolR = false;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(addShopingCarS);
            ps.setInt(1,carId);
            ps.setInt(2,menuId);
            ps.setInt(3,quantity);
            boolR = ps.executeUpdate()==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return boolR;
    }

    //查询该菜品是否已经在购物车中
    @Override
    public int selectCarMenu(Integer carId, Integer menuId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int menuCount = 0;
        try {
            conn =JdbcUtils.getConnection();
            ps = conn.prepareStatement(selectCarMenu);
            ps.setInt(1,carId);
            ps.setInt(2,menuId);
            rs = ps.executeQuery();
            while (rs.next()){
                menuCount = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return menuCount;
    }

}

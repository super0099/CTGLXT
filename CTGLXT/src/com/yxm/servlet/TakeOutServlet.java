package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbGoods;
import com.yxm.po.dbUser;
import com.yxm.service.IMenuTypeService;
import com.yxm.service.impl.MenuTypeService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.vo.AddShopingCar;
import com.yxm.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class TakeOutServlet extends BaseServlet {
    private static IMenuTypeService menuTypeService = new MenuTypeService();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        if(dbuser!=null){
            request.setAttribute("dbUser",dbuser);
            request.getRequestDispatcher("/jsp/takeout.jsp").forward(request,response);
        }else {
            //未登录  重定向到项目的根路径 跳转到login页面
            response.sendRedirect(request.getContextPath());
        }
    }

    /**
     * 默认查询所以,菜品
     * @param request
     * @param response
     * @throws IOException
     */
    public void selectMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        retunJson(response,this.menuTypeService.selectMenuCount());
    }
    public void selectMenuType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String menuTypeId = request.getParameter("menuTypeId");
        retunJson(response,this.menuTypeService.selectMenuType(Integer.parseInt(menuTypeId)));
    }
    public void addShopingCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String menuId = request.getParameter("menuId");
        String userId = request.getParameter("userId");
        if(Tools.isNotNull(userId)){
            //获取当前用户的购物车Id
            Integer myCarId = this.menuTypeService.selectMyCar(Integer.parseInt(userId));
            if(Tools.isNotNull(menuId)){
                Integer count = this.menuTypeService.selectCarMenu(myCarId,Integer.parseInt(menuId));
                if(count==0){
                    boolean isOK = this.menuTypeService.addShopingCar(myCarId,Integer.parseInt(menuId));
                    if(isOK){
                        List<AddShopingCar> CarData = this.menuTypeService.selectShopingCar(Integer.parseInt(userId));
                        jsonMsg.setState(true);
                        jsonMsg.setData(CarData);
                    }else {
                        jsonMsg.setMsg("添加失败");
                    }
                }else {
                    jsonMsg.setMsg("该菜品已经在购物车中");
                }
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
    public void selectMenuName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String muneName = request.getParameter("muneName");
        retunJson(response,this.menuTypeService.selectMenuName(muneName));
    }
    public void push(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String carId = request.getParameter("carId");
        String menuId = request.getParameter("menuId");

        if(Tools.isNotNull(carId)){
            if(Tools.isNotNull(menuId)){
                Integer Cid = Integer.parseInt(carId);
                Integer Mid = Integer.parseInt(menuId);
                dbGoods dbGoods = this.menuTypeService.alterGoods(Cid,Mid);
                if(dbGoods!=null){
                    boolean isOk = this.menuTypeService.pushGoods(dbGoods);
                    if(isOk){
                        jsonMsg.setState(true);
                    }else {
                        jsonMsg.setMsg("修改失败");
                    }
                }else {
                    jsonMsg.setMsg("数据异常,无法获取你的菜品信息");
                }
            }else {
                jsonMsg.setMsg("数据异常,无法获取你的菜品信息");
            }
        }else {
            jsonMsg.setMsg("数据异常,无法获取你的购物车信息");
        }
        retunJson(response,jsonMsg);
    }
    public void minus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String carId = request.getParameter("carId");
        String menuId = request.getParameter("menuId");

        if(Tools.isNotNull(carId)){
            if(Tools.isNotNull(menuId)){
                Integer Cid = Integer.parseInt(carId);
                Integer Mid = Integer.parseInt(menuId);
                dbGoods dbGoods = this.menuTypeService.alterGoods(Cid,Mid);
                if(dbGoods!=null){
                    boolean isOk = this.menuTypeService.minusGoods(dbGoods);
                    if(isOk){
                        jsonMsg.setState(true);
                    }else {
                        jsonMsg.setMsg("修改失败");
                    }
                }else {
                    jsonMsg.setMsg("数据异常,无法获取你的菜品信息");
                }
            }else {
                jsonMsg.setMsg("数据异常,无法获取你的菜品信息");
            }
        }else {
            jsonMsg.setMsg("数据异常,无法获取你的购物车信息");
        }
        retunJson(response,jsonMsg);
    }
    public void deletes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String carId = request.getParameter("carId");
        String menuId = request.getParameter("menuId");
        if(Tools.isNotNull(carId)){
            if(Tools.isNotNull(menuId)){
                Integer Cid = Integer.parseInt(carId);
                Integer Mid = Integer.parseInt(menuId);
                boolean isOK = this.menuTypeService.deleteGoods(Cid,Mid);
                if(isOK){
                    jsonMsg.setState(true);
                }else {
                    jsonMsg.setMsg("移除失败");
                }
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
    public void inserCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String o = request.getParameter("userId");
        Integer userId = Integer.parseInt(o);
        int count = this.menuTypeService.inserCar(userId);
        if(count==0){
            boolean isOk = this.menuTypeService.isOk(userId);
            if(isOk){
                jsonMsg.setState(true);
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }
        retunJson(response,jsonMsg);
    }
    public void addShopingCarS(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String menuId = request.getParameter("menuId");
        String userId = request.getParameter("userId");
        String quantity = request.getParameter("quantity");
        if(Tools.isNotNull(userId)){
            //获取当前用户的购物车Id
            Integer myCarId = this.menuTypeService.selectMyCar(Integer.parseInt(userId));
            if(Tools.isNotNull(menuId)){
                //判断该菜品是否在购物车内
                Integer count = this.menuTypeService.selectCarMenu(myCarId,Integer.parseInt(menuId));
                if(count==0){
                    //
                    boolean isOK = this.menuTypeService.addShopingCarS(myCarId,Integer.parseInt(menuId),Integer.parseInt(quantity));
                    if(isOK){
                        List<AddShopingCar> CarData = this.menuTypeService.selectShopingCar(Integer.parseInt(userId));
                        jsonMsg.setState(true);
                        jsonMsg.setData(CarData);
                    }else {
                        jsonMsg.setMsg("添加失败");
                    }
                }else {
                    jsonMsg.setMsg("该菜品已经在购物车中");
                }
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
}


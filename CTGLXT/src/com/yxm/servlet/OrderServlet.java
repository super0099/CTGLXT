package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.dao.IOrderDao;
import com.yxm.dao.impl.OrderDao;
import com.yxm.po.*;
import com.yxm.service.IOrderService;
import com.yxm.service.ISiteService;
import com.yxm.service.impl.OrderService;
import com.yxm.service.impl.SiteService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.vo.AddShopingCar;
import com.yxm.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private IOrderService orderService = new OrderService();
    private ISiteService siteService = new SiteService();
    private IOrderDao orderDao = new OrderDao();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String carId = request.getParameter("carId");
        String menuId = request.getParameter("menuId");
        dbUser dbUser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("carId",carId);
        request.setAttribute("menuId",menuId);
        request.setAttribute("dbUser",dbUser);
        request.getRequestDispatcher("/jsp/orderAffirm.jsp").forward(request,response);
    }
    public void firstHtml(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbUser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        String orderId = request.getParameter("orderId");
        dbWorderForm order = this.orderDao.selectrWorderForm(Integer.parseInt(orderId));
        request.setAttribute("order",order);
        request.setAttribute("dbUser",dbUser);
        request.getRequestDispatcher("/jsp/orderParticular.jsp").forward(request,response);

    }
    public void selectCarMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String carId = request.getParameter("carId");
        retunJson(response,this.orderService.selectCarMenu(Integer.parseInt(carId)));
    }
    public void selectMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String menuId = request.getParameter("menuId");
        retunJson(response,this.orderService.selectMenu(Integer.parseInt(menuId)));
    }
    public void addWorderForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
        JsonMsg jsonMsg = new JsonMsg();
        String carId = request.getParameter("carId");
        String menuId = request.getParameter("menuId");
        String siteId = request.getParameter("siteId");
        if(Tools.isNotNull(menuId)||Tools.isNotNull(carId)){
            if(Tools.isNotNull(menuId)){
                //???????????????????????????
                dbMenu dbMenu = this.orderService.selectMenu(Integer.parseInt(menuId));
                //????????????????????????????????????
                dbSite dbSite = this.siteService.selectSiteData(Integer.parseInt(siteId));
                //???????????????
                dbWorderForm dbWorderForms = new dbWorderForm();
                dbWorderForms.setOrderNumber(dateFormat.format(new Date())+System.nanoTime()+ Tools.getFileExt(dbSite.getPhone()));
                dbWorderForms.setSite(dbSite.getSite());
                dbWorderForms.setOrderStatus(1);
                dbWorderForms.setPrice(dbMenu.getPrice());
                dbWorderForms.setTip(dbMenu.getPrice().multiply(new BigDecimal(0.1)));
                dbWorderForms.setUserId(dbSite.getUserId());
                dbWorderForms.setUserPhone(dbSite.getPhone());
                dbWorderForms.setLinkman(dbSite.getLinkman());
                //?????????????????????
                dbMenuOrder dbMenuOrder = new dbMenuOrder();
                dbMenuOrder.setOrderType(1);
                dbMenuOrder.setNemuId(dbMenu.getId());
                dbMenuOrder.setQuantity(1);
                boolean isOK = this.orderService.addOrder(dbWorderForms,dbMenuOrder);
                dbWorderForm worderForm = this.orderDao.selectOrder(dbWorderForms.getOrderNumber());
                if(isOK){
                    jsonMsg.setState(true);
                    jsonMsg.setData(worderForm);
                }else {
                    jsonMsg.setMsg("????????????");
                }
            }
            if(Tools.isNotNull(carId)){
                List<AddShopingCar> addShopingCar = this.orderService.selectCarMenu(Integer.parseInt(carId));
                dbSite dbSite = this.siteService.selectSiteData(Integer.parseInt(siteId));
                //???????????????
                dbWorderForm dbWorderForm = new dbWorderForm();
                dbWorderForm.setOrderNumber(dateFormat.format(new Date())+System.nanoTime()+ Tools.getFileExt(dbSite.getPhone()));
                dbWorderForm.setSite(dbSite.getSite());
                dbWorderForm.setOrderStatus(1);
                dbWorderForm.setUserId(dbSite.getUserId());
                dbWorderForm.setUserPhone(dbSite.getPhone());
                dbWorderForm.setLinkman(dbSite.getLinkman());
                Integer c = Integer.parseInt(carId);
                boolean isOK = this.orderService.addOrderList(dbWorderForm,addShopingCar,c);
                dbWorderForm worderForm = this.orderDao.selectOrder(dbWorderForm.getOrderNumber());
                if(isOK){
                    jsonMsg.setState(true);
                    jsonMsg.setData(worderForm);
                }else {
                    jsonMsg.setMsg("????????????");
                }
            }
        }else {
            jsonMsg.setMsg("????????????");
        }
        retunJson(response,jsonMsg);
    }
    public void menuList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderId = request.getParameter("orderId");
        retunJson(response,this.orderDao.orderMenuList(Integer.parseInt(orderId)));
    }
    public void orderPayment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        HttpSession session = request.getSession();
        dbUser dbUser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        String orderId = request.getParameter("orderId");
        boolean isOk = this.orderService.orderPayment(Integer.parseInt(orderId),dbUser.getId());
        if(isOk){
            jsonMsg.setState(true);
            jsonMsg.setMsg("????????????");
        }else {
            jsonMsg.setMsg("????????????????????????");
        }
        retunJson(response,jsonMsg);
    }
}

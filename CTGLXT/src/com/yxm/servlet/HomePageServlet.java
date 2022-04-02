package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbUser;
import com.yxm.service.IHomePageService;
import com.yxm.service.impl.HomePageService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomePageServlet extends BaseServlet {
    private static IHomePageService homePageService = new HomePageService();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/homePage.jsp").forward(request,response);
    }
    public void order(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/order.jsp").forward(request,response);
    }
    public void preferential(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/preferential.jsp").forward(request,response);
    }
    public void balance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/balance.jsp").forward(request,response);
    }
    public void myData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/myData.jsp").forward(request,response);
    }
    public void site(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/site.jsp").forward(request,response);
    }
    public void menu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session =request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        request.setAttribute("dbuser", dbuser);
        request.getRequestDispatcher("/jsp/menu.jsp").forward(request,response);
    }
    //查询首页所以数据
    public void selectHomePageData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.homePageService.selectHomePageData(Integer.parseInt(userId)));
    }
    public void selectDiscount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.homePageService.selectDiscount(Integer.parseInt(userId)));
    }
    public void selectOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.homePageService.DataCount(Integer.parseInt(userId)));
    }
    public void selectCollect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.homePageService.selectCollect(Integer.parseInt(userId)));
    }
    public void removeMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String Id = request.getParameter("Id");
        JsonMsg jsonMsg = new JsonMsg();
        if(Tools.isNotNull(Id)){
            boolean isOk = this.homePageService.removeMenu(Integer.parseInt(Id));
            if(isOk){

                jsonMsg.setMsg("移除成功");
                jsonMsg.setState(true);
            }else {
                jsonMsg.setMsg("移除失败");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
}

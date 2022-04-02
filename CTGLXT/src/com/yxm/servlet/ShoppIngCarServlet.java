package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbUser;
import com.yxm.service.IMenuTypeService;
import com.yxm.service.impl.MenuTypeService;
import com.yxm.util.ProjectParameter;
import com.yxm.vo.AddShopingCar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ShoppIngCarServlet extends BaseServlet {
    private static IMenuTypeService menuTypeService = new MenuTypeService();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser)session.getAttribute(ProjectParameter.SESSION_USER);
        if(dbuser!=null){
            request.setAttribute("dbUser",dbuser);
            request.getRequestDispatcher("/jsp/shoppingCar.jsp").forward(request,response);
        }else {
            //未登录  重定向到项目的根路径 跳转到login页面
            response.sendRedirect(request.getContextPath());
        }
    }
    public void selectMune(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String o = request.getParameter("userId");
        List<AddShopingCar> data = this.menuTypeService.selectShopingCar(Integer.parseInt(o));
        retunJson(response,data);
    }
}

package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbUser;
import com.yxm.util.ProjectParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PersonalServlet extends BaseServlet {
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        if(dbuser!=null){
            request.setAttribute("dbuser",dbuser);
            request.getRequestDispatcher("/jsp/personal.jsp").forward(request,response);
        }else {
            //未登录  重定向到项目的根路径 跳转到login页面
            response.sendRedirect(request.getContextPath());
        }
    }
}

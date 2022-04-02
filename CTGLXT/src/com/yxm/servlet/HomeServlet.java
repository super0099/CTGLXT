package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbUser;
import com.yxm.util.ProjectParameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeServlet extends BaseServlet {
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        if(dbuser.getUserType()==1){
            request.getRequestDispatcher("/jsp/staff.jsp").forward(request,response);
        }
        if(dbuser.getUserType()==2){
            request.setAttribute("dbuser", dbuser);
            request.getRequestDispatcher("/jsp/client.jsp").forward(request,response);
        }
        if(dbuser.getUserType()==3){
            request.getRequestDispatcher("/jsp/administrator.jsp").forward(request,response);
        }
    }
}

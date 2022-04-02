package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbMenu;
import com.yxm.po.dbUser;
import com.yxm.service.IParticularService;
import com.yxm.service.impl.ParticularService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ParticularServlet extends BaseServlet {
    private static IParticularService particularService = new ParticularService();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser)session.getAttribute(ProjectParameter.SESSION_USER);
        if(dbuser!=null){
            String menuId = request.getParameter("t");
            dbMenu dbMenu = this.particularService.selectMenu(Integer.parseInt(menuId));
            request.setAttribute("dbUser",dbuser);
            request.setAttribute("dbMenu",dbMenu);
            request.getRequestDispatcher("/jsp/particular.jsp").forward(request,response);
        }else {
            //未登录  重定向到项目的根路径 跳转到login页面
            response.sendRedirect(request.getContextPath());
        }

    }
    public void selectMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String menuId = request.getParameter("menuId");
        if(Tools.isNotNull(menuId)){
            dbMenu dbMenu = this.particularService.selectMenu(Integer.parseInt(menuId));
            jsonMsg.setState(true);
            jsonMsg.setData(dbMenu);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
    public void ranking(HttpServletRequest request, HttpServletResponse response) throws IOException {
        retunJson(response,this.particularService.ranking());
    }
    public void collect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String mId = request.getParameter("menuId");
        String uId = request.getParameter("userId");
        if(Tools.isNotNull(mId)){
            if(Tools.isNotNull(uId)){
                int menuId = Integer.parseInt(mId);
                int userId = Integer.parseInt(uId);
                int count = this.particularService.myCollectMenu(userId,menuId);
                if(count==0){
                    boolean isOK = this.particularService.addCollect(userId,menuId);
                    if(isOK){
                        jsonMsg.setState(true);
                        jsonMsg.setMsg("收藏成功");
                    }else {
                        jsonMsg.setMsg("收藏失败");
                    }
                }else {
                    jsonMsg.setMsg("该菜品已经在你的收藏簿中");
                }
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("数据异常,无法获取该菜品");
        }
        retunJson(response,jsonMsg);
    }
}

package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbSite;
import com.yxm.po.dbUser;
import com.yxm.service.ISiteService;
import com.yxm.service.impl.SiteService;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.vo.JsonMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SiteServlet extends BaseServlet {
    private static ISiteService siteService = new SiteService();
    public void selectSite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.siteService.selectSite(Integer.parseInt(userId)));
    }
    public void addSite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String userId = request.getParameter("userId");
        String Phone = request.getParameter("Phone");
        String Message = request.getParameter("Message");
        String addressee = request.getParameter("addressee");
        if(Tools.isNotNull(userId)){
            if(Tools.isNotNull(Phone)){
                if(Tools.isNotNull(Message)){
                    if(Tools.isNotNull(addressee)){
                        dbSite dbSite =new dbSite();
                        dbSite.setSite(Message);
                        dbSite.setUserId(Integer.parseInt(userId));
                        dbSite.setLinkman(addressee);
                        dbSite.setPhone(Phone);
                        boolean isOK =this.siteService.addSite(dbSite);
                        if(isOK){
                            jsonMsg.setState(true);
                            jsonMsg.setMsg("新增成功");
                        }else {
                            jsonMsg.setMsg("新增失败");
                        }
                    }else {
                        jsonMsg.setMsg("联系人不能为空");
                    }
                }else {
                    jsonMsg.setMsg("地址信息不能为空");
                }
            }else {
                jsonMsg.setMsg("联系电话不能为空");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
    public void selectSites(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String siteId = request.getParameter("siteId");
        retunJson(response,this.siteService.selectSiteData(Integer.parseInt(siteId)));
    }
    public void compileSite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        dbUser dbuser = (dbUser) session.getAttribute(ProjectParameter.SESSION_USER);
        JsonMsg jsonMsg = new JsonMsg();
        String Phone = request.getParameter("Phone");
        String Message = request.getParameter("Message");
        String addressee = request.getParameter("addressee");
        String siteId = request.getParameter("siteId");
        if(Tools.isNotNull(siteId)){
            if(Tools.isNotNull(Phone)){
                if(Tools.isNotNull(Message)){
                    if(Tools.isNotNull(addressee)){
                        dbSite dbSite =new dbSite();
                        dbSite.setSite(Message);
                        dbSite.setUserId(dbuser.getId());
                        dbSite.setLinkman(addressee);
                        dbSite.setPhone(Phone);
                        boolean isOK =this.siteService.compileSite(dbSite,Integer.parseInt(siteId));
                        if(isOK){
                            jsonMsg.setState(true);
                            jsonMsg.setMsg("修改成功");
                        }else {
                            jsonMsg.setMsg("修改失败");
                        }
                    }else {
                        jsonMsg.setMsg("联系人不能为空");
                    }
                }else {
                    jsonMsg.setMsg("地址信息不能为空");
                }
            }else {
                jsonMsg.setMsg("联系电话不能为空");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
    public void deleteSite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String siteId = request.getParameter("siteId");
        if(Tools.isNotNull(siteId)){
            boolean isOK = this.siteService.deleteSite(Integer.parseInt(siteId));
            if(isOK){
                jsonMsg.setState(true);
                jsonMsg.setMsg("删除成功");
            }else {
                jsonMsg.setMsg("删除失败");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
}

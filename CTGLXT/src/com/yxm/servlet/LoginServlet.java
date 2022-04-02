package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.po.dbUser;
import com.yxm.service.ILoginService;
import com.yxm.service.impl.LoginService;
import com.yxm.util.MD5Util;
import com.yxm.util.ProjectParameter;
import com.yxm.util.Tools;
import com.yxm.util.ValidateImage.PngCaptcha;
import com.yxm.vo.JsonMsg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

public class LoginServlet extends BaseServlet {
    private static ILoginService loginService = new LoginService();
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/login.jsp").forward(request,response);
    }

    /**
     * 验证码
     * @param request
     * @param response
     * @throws IOException
     */
    public void imgCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //设置返回类型
        response.setContentType("image/png");
        PngCaptcha captcha = new PngCaptcha(135,50,5);
        OutputStream out = response.getOutputStream();
        String identityKay = captcha.out(out);
        HttpSession session = request.getSession();
        session.setAttribute(ProjectParameter.SESSION_LOGIN_IDENTITY,identityKay);
        out.flush();
        out.close();
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request,HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("password");
        String code = request.getParameter("code");
        HttpSession session = request.getSession();
        String sessionidentityKey = (String) session.getAttribute(ProjectParameter.SESSION_LOGIN_IDENTITY);
        if(Tools.isNotNull(sessionidentityKey)){
            if(sessionidentityKey.equalsIgnoreCase(code)){
                if(Tools.isNotNull(userName)){
                    dbUser dbuser = this.loginService.selectUser(userName);
                    if(dbuser.getUserName()!=null){
                        if(Tools.isNotNull(userPassword)){
                            String md5Pass = MD5Util.getMD5(userPassword);
                            if(dbuser.getUserPassword().equals(md5Pass)){
                                session.setAttribute(ProjectParameter.SESSION_USER,dbuser);
                                session.removeAttribute(ProjectParameter.SESSION_LOGIN_IDENTITY);
                                jsonMsg.setState(true);
                                jsonMsg.setData(dbuser);
                            }else {
                                jsonMsg.setMsg("密码不正确");
                            }
                        }else {
                            jsonMsg.setMsg("密码不能为空");
                        }
                    }else {
                        jsonMsg.setMsg("该账号不存在");
                    }
                }else {
                    jsonMsg.setMsg("请输入账号");
                }
            }else {
                jsonMsg.setMsg("验证码不确定");
            }
        }else {
            jsonMsg.setMsg("非法访问");
        }
        retunJson(response,jsonMsg);
    }

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void register(HttpServletRequest request,HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String Accounts = request.getParameter("Accounts");
        String Passwords = request.getParameter("Passwords");
        String Phone = request.getParameter("Phone");
        if(Tools.isNotNull(Accounts)){
            dbUser oldUser = this.loginService.selectUser(Accounts);
            if(oldUser==null){
                if(Tools.isNotNull(Passwords)){
                    if(Tools.isNotNull(Phone)){
                        int userPhoneCount = loginService.selectCountPhone(Phone);
                        dbUser dbuser = new dbUser();
                        if(userPhoneCount==0){
                            dbuser.setUserType(2);
                            dbuser.setUserPhone(Phone);
                            dbuser.setUserPrivilege(1);
                            dbuser.setUserPassword(MD5Util.getMD5(Passwords));
                            dbuser.setUserName(Accounts);
                            boolean isOK = this.loginService.inserUser(dbuser);
                            if(isOK){
                                jsonMsg.setState(true);
                                jsonMsg.setMsg("注册成功");
                                jsonMsg.setData(dbuser);
                            }else {
                                jsonMsg.setMsg("服务器数据异常");
                            }
                        }else {
                            jsonMsg.setMsg("该手机号已经注册过平台账号了");
                        }
                    }else {
                        jsonMsg.setMsg("手机号不能为空");
                    }
                }else {
                    jsonMsg.setMsg("密码不能为空");
                }
            }else {
                jsonMsg.setMsg("该账号已经存在");
            }
        }else {
            jsonMsg.setMsg("账号不能为空");
        }
        retunJson(response,jsonMsg);
    }
    public void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(ProjectParameter.SESSION_USER);
        retunJson(response,true);
    }
}

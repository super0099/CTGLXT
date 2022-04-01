package com.yxm.controller;

import com.yxm.po.SysUser;
import com.yxm.service.LoginService;
import com.yxm.util.JsonMsg;
import com.yxm.util.LoginSessionManager;
import com.yxm.util.MD5Util;
import com.yxm.util.ProjectParameter;
import com.yxm.util.ValidateImage.PngCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @RequestMapping("/index")
    public String index(){
        return "/login";
    }

    /**
     *验证码
     */
    @RequestMapping("/imgCode")
    public void imgCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
    @RequestMapping(value = "/onblur",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg onblur(String userName){
        JsonMsg jsonMsg = new JsonMsg();
        SysUser user = loginService.selectUserByName(userName);
        if(user!=null){
            String positionName = loginService.selectPositionName(user.getPositionId());
            jsonMsg.setMsg(positionName);
            jsonMsg.setState(true);
        }
        return jsonMsg;
    }

    /**
     *登录功能
     * @return
     */
    @RequestMapping(value = "/login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectUserName(SysUser sysUser, HttpServletRequest request){
        JsonMsg jsonMsg = new JsonMsg();
        String authCodes = request.getParameter("authCode");
        HttpSession session = request.getSession();
        String authCode = (String) session.getAttribute(ProjectParameter.SESSION_LOGIN_IDENTITY);
        if(authCodes.equalsIgnoreCase(authCode)){
            if(sysUser.getUserName()!=null||sysUser.getUserName()!=""){
                try{
                    SysUser user = loginService.selectUserByName(sysUser.getUserName());
                    if(user!=null){
                        String userPassword = MD5Util.getMD5(sysUser.getPassword()+user.getSalt());
                        if(user.getPassword().equals(userPassword)){
                            session.setAttribute(ProjectParameter.SESSION_USER,user);
                            LoginSessionManager.getInstance().addLoginSession(user.getId(),session.getId());
                            jsonMsg.setState(true);
                        }else {
                            jsonMsg.setMsg("密码错误");
                        }
                    }else {
                        jsonMsg.setMsg("该账号不存在");
                    }
                }catch (RuntimeException e){
                    jsonMsg.setMsg("登录异常"+e.getMessage());
                }
            }else {
                jsonMsg.setMsg("请输入账号");
            }
        }else {
            jsonMsg.setMsg("验证码不正确");
        }
        return jsonMsg;
    }
    /**
     *退出登录
     * **/
    @RequestMapping(value = "/loginOut",produces = "application/json;charset=utf-8")
    @ResponseBody
    public boolean loginOut(HttpSession session) {
        session.removeAttribute(ProjectParameter.SESSION_USER);
        //移除用户的userid和sessionId
        LoginSessionManager.getInstance().removeBySessionId(session.getId());
        return true;
    }
}

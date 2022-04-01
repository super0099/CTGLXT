package com.yxm.WebSocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxm.po.SysUser;
import com.yxm.util.GetHttpSessionConfigurator;
import com.yxm.util.LoginSessionManager;
import com.yxm.util.ProjectParameter;
import com.yxm.vo.OutLogin;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/home",configurator= GetHttpSessionConfigurator.class)
public class HomeWebSocket {
    //创建用户池
    private static Map<Integer,HomeWebSocket> loginUser = new HashMap<>();
    //创建全局websocket的session变量,websocket的session和HttpSession是有区别的
    private Session session;
    //创建HttpSession的全局变量
    private HttpSession httpSession;
    private SysUser sysUser;
    //序列化
    private final ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void OnOpen(Session session, EndpointConfig end) throws IOException {
        httpSession = (HttpSession) end.getUserProperties().get(HttpSession.class.getName());
        this.session =session;
        this.sysUser = (SysUser) httpSession.getAttribute(ProjectParameter.SESSION_USER);
        boolean isOk = loginUser.containsKey(sysUser.getId());
        if(isOk){
            OutLogin outLogin = new OutLogin();
            outLogin.setStateCode(1);
            outLogin.setMsg("你的账号已在别处登录,您的密码可能已经泄露请尽快更改密码");
            String strJson = objectMapper.writeValueAsString(outLogin);
            for(Integer key : loginUser.keySet()){
                if(key==sysUser.getId()){
                    HomeWebSocket userSession = loginUser.get(key);
                    userSession.session.getBasicRemote().sendText(strJson);
                }
            }
            loginUser.put(sysUser.getId(),this);
        }else {
            loginUser.put(sysUser.getId(),this);
        }
        System.out.println(sysUser.getUserName()+"用户登录成功");
    }

    @OnMessage
    public void OnMessage(String msg){

    }
    //
    @OnError
    public void OnError(Throwable error){
        System.out.println("发生错误"+error.getMessage());
    }

    @OnClose
    public void OnClose(){
        //区别是用户自己退出还是被顶下线
        String sessionId = LoginSessionManager.getInstance().getSessionIdByUserId(sysUser.getId());
        if(sessionId==null||sessionId==""){
            loginUser.remove(sysUser.getId());
        }
        System.out.println(sysUser.getUserName()+"用户退出登录");
    }
}

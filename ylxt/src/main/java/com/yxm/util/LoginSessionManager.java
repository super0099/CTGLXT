package com.yxm.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//设置每个账号只能在一个地方登录
public class LoginSessionManager {
    private static final Logger logger = LogManager.getLogger(LoginSessionManager.class);
    //启动加载自身的实例
    private static final LoginSessionManager loginSessionManager = new LoginSessionManager();
    private final Map<Integer,String> loginUser = new HashMap<>();
    //私有的构造器,外面无法实例当前类对象
    private LoginSessionManager(){
    }
    //
    public static LoginSessionManager getInstance(){
        return loginSessionManager;
    }
    //
    public void addLoginSession(Integer userId,String sessionId){
        //登录之前把对应的sessionId移除掉,不管有没有。避免的是在同一个浏览器内打开两个访问页面,虽然登录的用户不一样但是都是在同一会话中，先把当前会话移除开，再进行登录
        removeBySessionId(sessionId);
        //
        if(userId!=null&&Tools.isNotNull(sessionId)){
            //
            String oldSessionId = loginUser.put(userId,sessionId);
            if (oldSessionId!=null){
                logger.info("旧的sessionId被移除,userId="+userId+";sessionId="+sessionId+";");
            }
        }
    }

    /**
     * 根据用户Id获取sessionId
     * @param userId
     * @return
     */
    public String getSessionIdByUserId(Integer userId){
        String sessionId = loginUser.get(userId);
        if (sessionId==null){
            sessionId="";
        }
        return sessionId;
    }

    public void removeBySessionId(String sessionId){
        List<Integer> removeKeys = new ArrayList<>();
        //根据value值去获取key值,因为可能存在两个相同的value值
        for (Map.Entry<Integer,String> entry:loginUser.entrySet()) {
            if(entry.getValue()!=null&&entry.getValue().equals(sessionId)){
                removeKeys.add(entry.getKey());
            }
        }
        for (Integer key:removeKeys) {
            loginUser.remove(key);
        }
    }
}

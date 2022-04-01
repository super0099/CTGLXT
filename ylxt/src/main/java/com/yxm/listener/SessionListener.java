package com.yxm.listener;

import com.yxm.util.LoginSessionManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//监听session的创建和移除
public class SessionListener implements HttpSessionListener {
    private static final Logger logger = LogManager.getLogger(LoginSessionManager.class);
    //session创建监听器
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("Session创建: sessionId="+se.getSession().getId());
    }
    //session移除监听器
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Session移除: sessionId="+se.getSession().getId());
        LoginSessionManager.getInstance().removeBySessionId(se.getSession().getId());
    }
}

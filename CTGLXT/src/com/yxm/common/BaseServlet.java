package com.yxm.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxm.util.Tools;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    private static ObjectMapper objectMapper = new ObjectMapper();//需要引入jackson等三个包
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String mothed = request.getParameter("mothed");
        if(!Tools.isNotNull(mothed))mothed="index";
        try {
            Method doMothod =this.getClass().getMethod(mothed,HttpServletRequest.class,HttpServletResponse.class);
            doMothod.invoke(this,request,response);
            System.out.println("调用了"+getClass().getSimpleName()+"的方法["+mothed+"]");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            String errorMsg = getClass().getSimpleName()+"的方法["+mothed+"]不存在,或者无法调用";
            System.out.println(errorMsg);
        }
    }

    public void retunJson(HttpServletResponse response,Object obj) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String strJson = objectMapper.writeValueAsString(obj);//需要引入jackson等三个包
        out.write(strJson);
        out.flush();
        out.close();
     }
}

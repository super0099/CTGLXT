package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.service.IPreferentialService;
import com.yxm.service.impl.PreferentialService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PreferentialServlet extends BaseServlet {
    private static IPreferentialService preferentialService = new PreferentialService();
    public void selectPreferential(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.preferentialService.selectPreferentialCount(Integer.parseInt(userId)));
    }
    public void selectPreferentialList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.preferentialService.selectPreferentialList(Integer.parseInt(userId)));
    }
}

package com.yxm.servlet;

import com.yxm.common.BaseServlet;
import com.yxm.service.IWalletService;
import com.yxm.service.impl.WalletService;
import com.yxm.util.Tools;
import com.yxm.vo.JsonMsg;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

public class WalletServlet extends BaseServlet {
    private IWalletService walletService = new WalletService();
    public void selectBalance(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.walletService.selectBalance(Integer.parseInt(userId)));
    }
    public void selectConsumption(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        retunJson(response,this.walletService.selectConsumption(Integer.parseInt(userId)));
    }
    public void recharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        String userId = request.getParameter("userId");
        String rechargeNumber = request.getParameter("rechargeNumber");
        if(Tools.isNotNull(userId)){
            if(Tools.isNotNull(rechargeNumber)){
                BigDecimal rechargeMoney = new BigDecimal(Integer.parseInt(rechargeNumber));
                boolean isOk = this.walletService.recharge(Integer.parseInt(userId), rechargeMoney);
                if(isOk){
                    jsonMsg.setState(true);
                    jsonMsg.setMsg("充值成功");
                }else {
                    jsonMsg.setMsg("充值失败");
                }
            }else {
                jsonMsg.setMsg("充值金额不能为0");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        retunJson(response,jsonMsg);
    }
}

package com.yxm.controller;

import com.yxm.po.SysCheckout;
import com.yxm.service.AbdicateService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/abdicate")
@Controller
public class AbdicateController {
    @Autowired
    private AbdicateService abdicateService;

    @RequestMapping(value = "/selectCheckoutAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysCheckout> selectCheckoutAll(Integer page,Integer limit){
        return this.abdicateService.selectCheckoutAll(page,limit);
    }

    @RequestMapping(value = "/selectCheckOutById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectCheckOutById(Integer checkOutId,Integer CheckState){
        JsonMsg jsonMsg = new JsonMsg();
        if (CheckState==1){
            if(checkOutId!=null||checkOutId!=0){
                jsonMsg.setState(true);
                jsonMsg.setData(this.abdicateService.selectCheckOutById(checkOutId));
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("已经办理过了");
        }
        return jsonMsg;
    }
    //
    @RequestMapping(value = "/updateCheckOutById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateCheckOutById(SysCheckout sysCheckout){
        JsonMsg jsonMsg = new JsonMsg();
        if(sysCheckout!=null){
            boolean isOk = this.abdicateService.updateCheckOutById(sysCheckout);
            if(isOk){
                jsonMsg.setMsg("退款成功");
                jsonMsg.setState(true);
            }else {
                jsonMsg.setMsg("退款失败");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }
}

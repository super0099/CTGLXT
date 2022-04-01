package com.yxm.controller;

import com.yxm.po.SysCollection;
import com.yxm.po.SysElder;
import com.yxm.service.UnsubscribeService;
import com.yxm.util.JsonMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@RequestMapping("/unsubscribe")
@Controller
public class UnsubscribeController {

    @Autowired
    private UnsubscribeService unsubscribeService;


    @RequestMapping(value = "/selectExpireElder",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectExpireElder(){
        List<SysElder> sysElderList = this.unsubscribeService.selectExpireElder();
        return sysElderList;
    }

    @RequestMapping(value = "/deleteByElderId",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteByElderId(Integer elderId){
        JsonMsg jsonMsg = new JsonMsg();
        Integer count = this.unsubscribeService.selectArrearage(elderId);
        if (count>0){
            jsonMsg.setMsg("该长者存在欠费的账单,请前往缴费");
            return jsonMsg;
        }
        try{
            boolean isOk = this.unsubscribeService.deleteByElderId(elderId);
            if (isOk){
                jsonMsg.setState(true);
                jsonMsg.setMsg("退出成功");
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("退住异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/contract",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg contract(String checkInCode,@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        JsonMsg jsonMsg = new JsonMsg();
        boolean isOk = this.unsubscribeService.contract(endDate,checkInCode);
        if(isOk){
            jsonMsg.setState(true);
            jsonMsg.setMsg("续约成功");
        }else {
            jsonMsg.setMsg("续约失败");
        }
        return jsonMsg;
    }
}

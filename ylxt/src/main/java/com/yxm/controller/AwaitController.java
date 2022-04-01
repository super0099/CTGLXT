package com.yxm.controller;

import com.yxm.po.SysCollection;
import com.yxm.po.SysElder;
import com.yxm.po.SysInvoice;
import com.yxm.po.SysUser;
import com.yxm.service.AwaitService;
import com.yxm.service.CollectionService;
import com.yxm.util.JsonMsg;
import com.yxm.util.ProjectParameter;
import com.yxm.vo.ElderIndentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping("/await")
@Controller
public class AwaitController {
    @Autowired
    private AwaitService awaitService;
    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/selectAwaitElderAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectAwaitElder(){
        return this.awaitService.selectAwaitElderAll();
    }

    @RequestMapping(value = "/selectAwaitElder",produces = "application/json;charst=utf-8")
    @ResponseBody
    public JsonMsg selectAwaitElder(Integer elderId){
        JsonMsg jsonMsg = new JsonMsg();
        if (elderId!=null||elderId!=0){
            ElderIndentData elderIndentData = this.awaitService.selectAwaitElder(elderId);
            if(elderIndentData!=null){
                jsonMsg.setState(true);
                jsonMsg.setData(elderIndentData);
            }else {
                jsonMsg.setMsg("数据异常");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "updateElderAllData",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateElderAllData(SysInvoice sysInvoice, HttpSession session,
                                      Integer berthId,Integer appointmentId,Integer elderId,String checkInCode){
        JsonMsg jsonMsg = new JsonMsg();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String year =simpleDateFormat.format(date);
        SysCollection sysCollection = this.collectionService.selectElderOrder(elderId,year);
        SysUser sysUser = (SysUser) session.getAttribute(ProjectParameter.SESSION_USER);
        try {
            boolean isOk = this.awaitService.updateCollection(sysCollection.getId(),sysInvoice,sysUser.getUserName(),berthId,appointmentId,checkInCode);
            if(isOk){
                jsonMsg.setMsg("办理成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("办理异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/elderCheckIn",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg elderCheckIn(Integer berthId,Integer appointmentId){
        JsonMsg jsonMsg = new JsonMsg();
        try {
            boolean isOk = this.awaitService.elderCheckIn(berthId,appointmentId);
            if(isOk){
                jsonMsg.setMsg("办理成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("办理异常");
        }
        return jsonMsg;
    }
}

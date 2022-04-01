package com.yxm.controller;

import com.yxm.po.SysAppointment;
import com.yxm.po.SysElder;
import com.yxm.po.SysInvoice;
import com.yxm.po.SysUser;
import com.yxm.service.AppointmentService;
import com.yxm.service.ElderService;
import com.yxm.service.InformService;
import com.yxm.util.JsonMsg;
import com.yxm.util.ProjectParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/inform")
@Controller
public class InformController {

    @Autowired
    private InformService informService;
    @Autowired
    private ElderService elderService;
    @Autowired
    private AppointmentService appointmentService;

    @RequestMapping(value = "/selectElderInform",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectElderInform(){
        return this.informService.selectElderInform();
    }

    @RequestMapping(value = "/selectElderById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public SysAppointment selectElderById(Integer elderId){
        return this.appointmentService.selectAppointmentByElderId(elderId);
    }
    @RequestMapping(value = "/updateCollection",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateCollection(Integer collectionId, SysInvoice sysInvoice, HttpSession session){
        JsonMsg jsonMsg = new JsonMsg();
        SysUser sysUser = (SysUser) session.getAttribute(ProjectParameter.SESSION_USER);
        try {
            boolean isOk = this.informService.updateCollection(collectionId,sysInvoice,sysUser.getUserName());
            if(isOk){
                jsonMsg.setMsg("缴费成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("缴费异常");
        }
        return jsonMsg;
    }
}

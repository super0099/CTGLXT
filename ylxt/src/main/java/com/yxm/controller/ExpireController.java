package com.yxm.controller;

import com.yxm.po.SysBondsman;
import com.yxm.po.SysElder;
import com.yxm.service.ExpireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/expire")
@Controller
public class ExpireController {
    @Autowired
    private ExpireService expireService;

    @RequestMapping(value = "/selectExpireElder",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectExpireElder(){
        return this.expireService.selectExpireElder();
    }

    @RequestMapping(value = "selectElderBondsman",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysBondsman> selectElderBondsman(String appointmentId){
        return this.expireService.selectElderBondsman(appointmentId);
    }

}

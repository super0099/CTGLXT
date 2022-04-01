package com.yxm.controller;

import com.yxm.service.ConsumeService;
import com.yxm.vo.NursetTypeCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/consume")
@Controller
public class ConsumeController {
    @Autowired
    private ConsumeService consumeService;
    @RequestMapping(value = "/selectNurseTypeCost")
    @ResponseBody
    public NursetTypeCost selectNurseTypeCost(String appointmentId){
        return this.consumeService.selectNurseTypeCost(appointmentId);
    }
}

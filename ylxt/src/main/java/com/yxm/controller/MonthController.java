package com.yxm.controller;

import com.yxm.service.MonthService;
import com.yxm.vo.NursetTypeCost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@RequestMapping("/month")
@Controller
public class MonthController {
    @Autowired
    private MonthService monthService;

    @RequestMapping(value = "/selectNurseTypeCost",produces = "application/json;charset=utf-8")
    @ResponseBody
    public NursetTypeCost selectNurseTypeCost(Integer month,Integer year){
        return this.monthService.selectNurseTypeCost(month,year);
    }

    @RequestMapping(value = "/selectIncomeCost",produces = "application/json;charset=utf-8")
    @ResponseBody
    public BigDecimal selectIncomeCost(Integer month,Integer year){
        return this.monthService.selectIncomeCost(month,year);
    }

    @RequestMapping(value = "selectIncomeCount",produces = "application/json;charset=utf-8")
    @ResponseBody
    public Integer selectIncomeCount(Integer month,Integer year){
        return this.monthService.selectIncomeCount(month,year);
    }
}

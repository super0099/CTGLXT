package com.yxm.controller;

import com.yxm.service.ManagementService;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.OrderAndCombo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/management")
@Controller
public class ManagementController {
    @Autowired
    private ManagementService managementService;
    @RequestMapping(value = "/selectOrderAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<OrderAndCombo> selectOrderAll(Integer page, Integer limit,
                                                        @RequestParam(value = "elderName",required = false)String elderName){
        return this.managementService.selectOrderAll(page,limit,elderName);
    }
}

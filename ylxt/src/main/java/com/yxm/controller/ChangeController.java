package com.yxm.controller;

import com.yxm.service.ChangeService;
import com.yxm.vo.ElderAndAlteration;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/change")
@Controller
public class ChangeController {
    @Autowired
    private ChangeService changeService;
    @RequestMapping(value = "/selectAlterationAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<ElderAndAlteration> selectAlterationAll(Integer page, Integer limit,
                                                                  @RequestParam(value = "elderName",required = false)String elderName,
                                                                  @RequestParam(value = "idNumber",required = false)String idNumber){
        return this.changeService.selectAlterationAll(page,limit,elderName,idNumber);
    }
}

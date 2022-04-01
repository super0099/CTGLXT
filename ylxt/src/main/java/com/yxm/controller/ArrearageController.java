package com.yxm.controller;

import com.yxm.po.SysElder;
import com.yxm.service.ArrearageService;
import com.yxm.service.CollectionService;
import com.yxm.vo.CollectionAndNursetype;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/arrearage")
@Controller
public class ArrearageController {
    @Autowired
    private ArrearageService arrearageService;
    @Autowired
    private CollectionService collectionService;

    @RequestMapping(value = "/selectArrearageElder",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectArrearageElder(){
        return this.arrearageService.selectArrearageElder();
    }

    @RequestMapping(value = "/selectElderPayment",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<CollectionAndNursetype> selectElderPayment(Integer elderId){
        return this.collectionService.selectArrearageElder(elderId);
    }
}

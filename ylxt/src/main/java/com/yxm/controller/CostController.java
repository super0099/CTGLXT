package com.yxm.controller;

import com.yxm.po.SysElder;
import com.yxm.po.SysIncome;
import com.yxm.service.CollectionService;
import com.yxm.service.CostService;
import com.yxm.vo.CollectionAndNursetype;
import com.yxm.vo.ElderIndentData;
import com.yxm.vo.IncomeColumnarVo;
import com.yxm.vo.LayuiTableData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cost")
public class CostController {
    @Autowired
    private CostService costService;
    @Autowired
    private CollectionService collectionService;

    /**
     * 预收款
     * @return
     */
    @RequestMapping("/advance")
    public String index(){
        return "/advance";
    }

    /**
     * 收入查询
     * @return
     */
    @RequestMapping("/income")
    public String income(){
        return "/income";
    }

    /**
     * 月度结算
     * @return
     */
    @RequestMapping("/month")
    public String month(){
        return "/month";
    }

    /**
     * 退住结算
     * @return
     */
    @RequestMapping("/unsubscribe")
    public String unsubscribe(){
        return "/abdicate";
    }

    /**
     * 月费标准
     * @return
     */
    @RequestMapping("/standard")
    public String standard(){
        return "/standard";
    }

    @RequestMapping("/arrearage")
    public String arrearage(){
        return "/arrearage";
    }

    @RequestMapping(value = "/selectElderCost",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<SysElder> selectElderCost(){
        return costService.selectElder();
    }

    @RequestMapping(value = "/selectElderData",produces = "application/json;charset=utf-8")
    @ResponseBody
    public ElderIndentData selectElderData(Integer elderId){
        return this.costService.selectElderData(elderId);
    }

    @RequestMapping(value = "/selectElderPayment",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<CollectionAndNursetype> selectElderPayment(Integer elderId){
        return this.collectionService.selectElderPayment(elderId);
    }

    @RequestMapping(value = "/selectElderAllSettle",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<CollectionAndNursetype> selectElderAllSettle(Integer elderId){
        return this.collectionService.selectElderAllSettle(elderId);
    }
    @RequestMapping(value = "/selectIncomeColumnarVo",produces = "application/json;charset=utf-8")
    @ResponseBody
    public IncomeColumnarVo selectIncomeColumnarVo(String years){
        IncomeColumnarVo incomeColumnarVo = this.costService.selectIncomeColumnarVo(years);
        return incomeColumnarVo;
    }

    @RequestMapping(value = "/selectIncomeAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysIncome> selectIncomeAll(Integer page,Integer limit){
        return this.costService.selectIncomeAll(page,limit);
    }
}

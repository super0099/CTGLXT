package com.yxm.controller;

import com.yxm.po.SysInvoice;
import com.yxm.po.SysIssue;
import com.yxm.service.SystemService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/system")
@Controller
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping("/invoice")
    public String invoice(){
        return "/invoice";
    }

    @RequestMapping("/issue")
    public String issue(){
        return "/issue";
    }

    @RequestMapping("/jurisdiction")
    public String jurisdiction(){
        return "/jurisdiction";
    }

    @RequestMapping("/account")
    public String account(){
        return "/account";
    }

    @RequestMapping(value = "/maintain")
    public String maintain(){
        return "/maintain";
    }

    @RequestMapping(value = "/selectInvoiceAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysInvoice> selectInvoiceAll(Integer page, Integer limit,
                                                       @RequestParam(value = "buyerName",required = false)String buyerName,
                                                       @RequestParam(value = "invoiceNumber",required = false)String invoiceNumber){
        return this.systemService.selectInvoiceAll(page,limit,buyerName,invoiceNumber);
    }

    @RequestMapping("/selectInvoiceById")
    @ResponseBody
    public JsonMsg selectInvoiceById(Integer invoiceId){
        JsonMsg jsonMsg = new JsonMsg();
        if(invoiceId!=null){
            jsonMsg.setState(true);
            jsonMsg.setData(this.systemService.selectInvoiceById(invoiceId));
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/selectIssueAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysIssue> selectIssueAll(Integer page,Integer limit){
        return this.systemService.selectIssueAll(page,limit);
    }

    @RequestMapping(value = "/deleteIssueById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteIssueById(Integer issueId){
        JsonMsg jsonMsg = new JsonMsg();
        if(issueId!=null||issueId!=0){
            boolean isOk = this.systemService.deleteIssueById(issueId);
            if(isOk){
                jsonMsg.setMsg("删除成功");
                jsonMsg.setState(true);
            }else {
                jsonMsg.setMsg("删除失败");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/insertIssue",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insertIssue(String rightCharacter){
        JsonMsg jsonMsg = new JsonMsg();
        if(rightCharacter!=null||rightCharacter!=""){
            boolean isOk = this.systemService.insertIssue(rightCharacter);
            if(isOk){
                jsonMsg.setMsg("发布成功");
                jsonMsg.setState(true);
            }else {
                jsonMsg.setMsg("发布失败");
            }
        }else {
            jsonMsg.setMsg("请输入需要发布的信息内容");
        }
        return jsonMsg;
    }
}

package com.yxm.controller;

import com.yxm.po.SysOut;
import com.yxm.service.OutService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RequestMapping("/out")
@Controller
public class OutController {
    @Autowired
    private OutService outService;

    @RequestMapping(value = "/selectOutAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysOut> selectOutAll(Integer page, Integer limit,
                                               @RequestParam(value = "elderName",required = false)String elderName,
                                               @RequestParam(value = "outTimes",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date outTimes){
        return this.outService.selectOutAll(page,limit,elderName,outTimes);
    }

    @RequestMapping(value = "/insert",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insert(SysOut sysOut){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysOut.getElderIdCode()==null){
            jsonMsg.setMsg("身份证号不能为空");
            return jsonMsg;
        }
        if (sysOut.getElderName()==null){
            jsonMsg.setMsg("名字不能为空");
            return jsonMsg;
        }
        if (sysOut.getOutTime()==null){
            jsonMsg.setMsg("外出时间不能为空");
            return jsonMsg;
        }
        if(sysOut.getReason()==null){
            jsonMsg.setMsg("外出理由不能为空");
            return jsonMsg;
        }
        boolean isOk = this.outService.insert(sysOut);
        if(isOk){
            jsonMsg.setMsg("登记成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("登记失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/update",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg update(SysOut sysOut){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysOut.getId()==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        if (sysOut.getElderIdCode()==null){
            jsonMsg.setMsg("身份证号不能为空");
            return jsonMsg;
        }
        if (sysOut.getElderName()==null){
            jsonMsg.setMsg("名字不能为空");
            return jsonMsg;
        }
        if (sysOut.getOutTime()==null){
            jsonMsg.setMsg("外出时间不能为空");
            return jsonMsg;
        }
        if(sysOut.getReason()==null){
            jsonMsg.setMsg("外出理由不能为空");
            return jsonMsg;
        }
        boolean isOk = this.outService.update(sysOut);
        if (isOk){
            jsonMsg.setMsg("修改成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("修改失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/selectById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectById(Integer outId){
        JsonMsg jsonMsg = new JsonMsg();
        if(outId!=null){
            jsonMsg.setState(true);
            jsonMsg.setData(this.outService.selectById(outId));
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/deleteById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteById(Integer outId){
        JsonMsg jsonMsg = new JsonMsg();
        if (outId==null||outId==0){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        boolean isOk = this.outService.deleteById(outId);
        if (isOk){
            jsonMsg.setMsg("删除成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("删除失败");
        }
        return jsonMsg;
    }
}

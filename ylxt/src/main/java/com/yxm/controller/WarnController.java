package com.yxm.controller;

import com.yxm.po.SysWarn;
import com.yxm.service.WarnService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/warn")
@Controller
public class WarnController {
    @Autowired
    private WarnService warnService;

    @RequestMapping(value = "/selectWarnAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysWarn> selectWarnAll(Integer page, Integer limit,
                                                 @RequestParam(value = "elderName",required = false)String elderName,
                                                 @RequestParam(value = "idCode",required = false) String idCode){
        return this.warnService.selectWarnAll(page,limit,elderName,idCode);
    }

    @RequestMapping(value = "/selectById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectById(Integer warnId){
        JsonMsg jsonMsg = new JsonMsg();
        if (warnId!=null){
            jsonMsg.setData(this.warnService.selectById(warnId));
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/insert",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insert(SysWarn sysWarn){
        JsonMsg jsonMsg = new JsonMsg();
        if(sysWarn.getBerthNumber()==null){
            jsonMsg.setMsg("床位号不能为空");
            return jsonMsg;
        }
        if(sysWarn.getDateCreated()==null){
            jsonMsg.setMsg("开始日期不能为空");
            return jsonMsg;
        }
        if(sysWarn.getWarnContent()==null){
            jsonMsg.setMsg("医嘱内容不能为空");
            return jsonMsg;
        }
        if(sysWarn.getElderIdCode()==null){
            jsonMsg.setMsg("长者身份证号不能为空");
            return jsonMsg;
        }
        if(sysWarn.getElderName()==null){
            jsonMsg.setMsg("长者姓名不能为空");
            return jsonMsg;
        }
        if(sysWarn.getValidity()==null){
            jsonMsg.setMsg("到期时间不能为空");
            return jsonMsg;
        }
        boolean isOk = this.warnService.Insert(sysWarn);
        if (isOk){
            jsonMsg.setMsg("新增成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("新增失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "update",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg update(SysWarn sysWarn){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysWarn.getId()==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        if(sysWarn.getBerthNumber()==null){
            jsonMsg.setMsg("床位号不能为空");
            return jsonMsg;
        }
        if(sysWarn.getDateCreated()==null){
            jsonMsg.setMsg("开始日期不能为空");
            return jsonMsg;
        }
        if(sysWarn.getWarnContent()==null){
            jsonMsg.setMsg("医嘱内容不能为空");
            return jsonMsg;
        }
        if(sysWarn.getElderIdCode()==null){
            jsonMsg.setMsg("长者身份证号不能为空");
            return jsonMsg;
        }
        if(sysWarn.getElderName()==null){
            jsonMsg.setMsg("长者姓名不能为空");
            return jsonMsg;
        }
        if(sysWarn.getValidity()==null){
            jsonMsg.setMsg("到期时间不能为空");
            return jsonMsg;
        }
        boolean isOk = this.warnService.update(sysWarn);
        if (isOk){
            jsonMsg.setMsg("修改成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("修改失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "deleteById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg delete(Integer warnId){
        JsonMsg jsonMsg = new JsonMsg();
        if (warnId==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        boolean isOk = this.warnService.delete(warnId);
        if(isOk){
            jsonMsg.setMsg("删除成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("删除失败");
        }
        return jsonMsg;
    }
}

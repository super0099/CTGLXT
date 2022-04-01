package com.yxm.controller;

import com.yxm.po.SysNursetype;
import com.yxm.service.MaintainService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/maintain")
@Controller
public class MaintainController {
    @Autowired
    private MaintainService maintainService;

    @RequestMapping(value = "/selectNurseTypeAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysNursetype> selectNurseTypeAll(Integer page,Integer limit){
        return this.maintainService.selectNurseTypeAll(page,limit);
    }

    @RequestMapping(value = "/selectUserById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectUserById(Integer id){
        JsonMsg jsonMsg = new JsonMsg();
        if (id!=null){
            SysNursetype sysNursetype = this.maintainService.selectUserById(id);
            jsonMsg.setData(sysNursetype);
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return  jsonMsg;
    }

    @RequestMapping(value = "/insert",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insert(SysNursetype sysNursetype){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysNursetype.getNurseType()==null){
            jsonMsg.setMsg("类型名称不能为空");
            return jsonMsg;
        }
        if(sysNursetype.getAlimony()==null){
            jsonMsg.setMsg("生活设施费不能为空");
            return jsonMsg;
        }
        if (sysNursetype.getBerth()==null){
            jsonMsg.setMsg("床位费不能为空");
            return jsonMsg;
        }
        if (sysNursetype.getBoardWages()==null){
            jsonMsg.setMsg("伙食费不能为空");
            return jsonMsg;
        }
        if(sysNursetype.getServiceCharge()==null){
            jsonMsg.setMsg("护理费不能为空");
            return jsonMsg;
        }
        boolean isOk = this.maintainService.insert(sysNursetype);
        if (isOk){
            jsonMsg.setMsg("新增成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("新增失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/update",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg update(SysNursetype sysNursetype){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysNursetype.getId()==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        if (sysNursetype.getNurseType()==null){
            jsonMsg.setMsg("类型名称不能为空");
            return jsonMsg;
        }
        if(sysNursetype.getAlimony()==null){
            jsonMsg.setMsg("生活设施费不能为空");
            return jsonMsg;
        }
        if (sysNursetype.getBerth()==null){
            jsonMsg.setMsg("床位费不能为空");
            return jsonMsg;
        }
        if (sysNursetype.getBoardWages()==null){
            jsonMsg.setMsg("伙食费不能为空");
            return jsonMsg;
        }
        if(sysNursetype.getServiceCharge()==null){
            jsonMsg.setMsg("护理费不能为空");
            return jsonMsg;
        }
        boolean isOk = this.maintainService.update(sysNursetype);
        if(isOk){
            jsonMsg.setMsg("修改成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("修改成功");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg delete(Integer id){
        JsonMsg jsonMsg = new JsonMsg();
        if(id==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        boolean isOk = this.maintainService.delete(id);
        if (isOk){
            jsonMsg.setMsg("删除成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("删除失败");
        }
        return jsonMsg;
    }
}

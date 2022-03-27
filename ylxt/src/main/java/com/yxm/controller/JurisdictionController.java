package com.yxm.controller;

import com.yxm.po.SysPosition;
import com.yxm.service.JurisdictionService;
import com.yxm.util.JsonMsg;
import com.yxm.util.Tools;
import com.yxm.vo.LayuiTableData;
import com.yxm.vo.LayuiTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/jurisdiction")
@Controller
public class JurisdictionController {
    @Autowired
    private JurisdictionService jurisdictionService;

    @RequestMapping(value = "/selectPageList",produces = "application/json;cahrset=utf-8")
    @ResponseBody
    public LayuiTableData<SysPosition> selectPageList(Integer page,Integer limit,
                                                      @RequestParam(value = "positionName",required = false) String positionName){
        return this.jurisdictionService.selectPageList(page,limit,positionName);
    }

    @RequestMapping(value = "/selectById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectById(Integer positionId){
        JsonMsg jsonMsg = new JsonMsg();
        if (positionId!=null||positionId!=0){
            SysPosition sysPosition = this.jurisdictionService.selectById(positionId);
            jsonMsg.setData(sysPosition);
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/insert",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insert(SysPosition sysPosition){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysPosition.getPositionName()==null){
            jsonMsg.setMsg("职位名称不能为空");
            return jsonMsg;
        }
        if(sysPosition.getCreateDate()==null){
            jsonMsg.setMsg("创建时间不能为空");
            return jsonMsg;
        }
        boolean isOk = this.jurisdictionService.insert(sysPosition);
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
    public JsonMsg update(SysPosition sysPosition){
        JsonMsg jsonMsg = new JsonMsg();
        if (sysPosition.getPositionName()==null){
            jsonMsg.setMsg("职位名称不能为空");
            return jsonMsg;
        }
        if(sysPosition.getCreateDate()==null){
            jsonMsg.setMsg("创建时间不能为空");
            return jsonMsg;
        }
        boolean isOk = this.jurisdictionService.update(sysPosition);
        if (isOk){
            jsonMsg.setMsg("修改成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("修改失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/deleteById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteById(Integer positionId){
        JsonMsg jsonMsg = new JsonMsg();
        if (positionId!=null){
            if(positionId!=1){
                return this.jurisdictionService.deleteById(positionId);
            }else {
                jsonMsg.setMsg("超级管理员不能被删除");
            }
        }else {
            jsonMsg.setMsg("删除失败");
        }
        return jsonMsg;
    }

    /**
     * 查询授权的菜单数据 for layui Tree
     */
    @RequestMapping(value = "/selectMenuForLayuiTree",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectMenuForLayuiTree(Integer positionId) {
        JsonMsg jsonMsg=new JsonMsg();
        if (positionId!=null && positionId>0){
            List<LayuiTreeVo> data = this.jurisdictionService.selectMenuForLayuiTree(positionId);
            jsonMsg.setState(true);
            jsonMsg.setData(data);
        }else {
            jsonMsg.setMsg("参数异常");
        }
        return jsonMsg;
    }

    /**
     * 角色授权
     */
    @RequestMapping(value = "/updateRoleAuthorize",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateRoleAuthorize(Integer roleId,
                                       @RequestParam("selectMenuIds") String strSelectMenuIdsAll){
        JsonMsg jsonMsg=new JsonMsg();
        //本次授权选中的id 1,2,3,4....
        if (roleId!=null && roleId>0){
            if(roleId!=1){
                List<Integer> selectMenuIdList=new ArrayList<>();
                //分割字符串
                String[] strSelectMenuIds=strSelectMenuIdsAll.split(",");
                //遍历转换
                for(String strSelectMenuId : strSelectMenuIds){
                    if (Tools.isInteger(strSelectMenuId)){
                        selectMenuIdList.add(Integer.parseInt(strSelectMenuId));
                    }
                }
                //调用服务端 进行权限授权
                try {
                    jsonMsg=this.jurisdictionService.authorize(roleId,selectMenuIdList);
                }catch (RuntimeException e){
                    jsonMsg.setMsg(e.getMessage());
                }
            }else {
                jsonMsg.setMsg("超级管理员是最高权限,无法修改");
            }
        }else {
            jsonMsg.setMsg("参数异常");
        }
        return jsonMsg;
    }
}

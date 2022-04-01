package com.yxm.controller;
import com.yxm.po.*;
import com.yxm.service.ElderService;
import com.yxm.service.LocationService;
import com.yxm.service.OrderService;
import com.yxm.service.ReservationService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.H5SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/reservation")
@Controller
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ElderService elderService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/selectBerth",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectBerth(){
        List<H5SelectVo> data = this.reservationService.selectBerthGrade();
        return data;
    }

    @RequestMapping(value = "/selectElderNurse",produces = "application/json;charset=utf-8")
    @ResponseBody
    public SysAppointment selectElderNurse(Integer elderId){
        return this.reservationService.selectElderNurse(elderId);
    }

    @RequestMapping(value = "/selectBreakfast",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectBreakfast(Integer comboGrade,Integer comboType){
        return this.reservationService.selectBreakfast(comboGrade,comboType);
    }
    @RequestMapping(value = "/selectLunch",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectLunch(Integer comboGrade,Integer comboType){
        return this.reservationService.selectLunch(comboGrade,comboType);
    }
    @RequestMapping(value = "/selectDinner",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectDinner(Integer comboGrade,Integer comboType){
        return this.reservationService.selectDinner(comboGrade,comboType);
    }

    @RequestMapping(value = "/selectComboById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public SysCombo selectComboById(Integer comboId){
        return this.reservationService.selectComboById(comboId);
    }

    @RequestMapping(value = "/selectBerthData",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectBerthData(Integer elderId){
        JsonMsg jsonMsg = new JsonMsg();
        if(elderId!=null){
            SysElder sysElder =this.elderService.selectElderById(elderId);
            SysBerth sysBerth = this.locationService.selectBerthById(sysElder.getBerthId());
            jsonMsg.setState(true);
            jsonMsg.setData(sysBerth);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/insertOrder",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insertOrder(SysOrder sysOrder){
        JsonMsg jsonMsg = new JsonMsg();
        if(sysOrder!=null){
            SysOrder sysOrder1 = this.orderService.selectOrderByElderIdAndDate(sysOrder.getElderId(),sysOrder.getOrderDate());
            if (sysOrder1==null){
                boolean isOk = this.orderService.insertOrder(sysOrder);
                if (isOk){
                    jsonMsg.setMsg("订餐成功");
                    jsonMsg.setState(true);
                }else {
                    jsonMsg.setMsg("订餐失败");
                }
            }else {
                jsonMsg.setMsg("该长者在今天已经订餐了");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }
}

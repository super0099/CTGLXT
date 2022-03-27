package com.yxm.controller;

import com.yxm.service.LocationService;
import com.yxm.util.JsonMsg;
import com.yxm.vo.BerthAndElder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/nurse")
public class LocationController {
    @Autowired
    private LocationService locationService;

    @RequestMapping("/location")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("/berth");
        return mv;
    }

    @RequestMapping("/warn")
    public String warn(){
        return "/warn";
    }

    @RequestMapping(value = "/selectBerthAll",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonMsg selectBerthAll(Integer berthId){
        JsonMsg jsonMsg = new JsonMsg();
        if(berthId!=null){
            List<BerthAndElder> berthAndElders = this.locationService.selectBerthAll(berthId);
            jsonMsg.setState(true);
            jsonMsg.setData(berthAndElders);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return  jsonMsg;
    }

    @RequestMapping("/out")
    public String out(){
        return "/out";
    }
}

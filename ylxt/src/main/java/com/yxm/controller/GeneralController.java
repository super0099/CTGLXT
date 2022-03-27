package com.yxm.controller;

import com.yxm.po.SysCombo;
import com.yxm.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/general")
@Controller
public class GeneralController {

    @Autowired
    private ComboService comboService;

    @RequestMapping("/reservation")
    public String reservation(){
        return "/reservation";
    }

    @RequestMapping("/management")
    public String management(){
        return "/management";
    }

    @RequestMapping("/menu")
    public ModelAndView menu(){
        ModelAndView mv = new ModelAndView("/menu");
        List<SysCombo> sysCombos = this.comboService.selectComboAll();
        mv.addObject("sysCombos",sysCombos);
        return mv;
    }
}

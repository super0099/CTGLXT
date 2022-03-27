package com.yxm.controller;

import com.yxm.dao.NursetypeDao;
import com.yxm.po.*;
import com.yxm.service.AppointmentService;
import com.yxm.service.BondsmanService;
import com.yxm.service.ElderService;
import com.yxm.service.LocationService;
import com.yxm.util.JsonMsg;
import com.yxm.util.Tools;
import com.yxm.vo.BondsmanList;
import com.yxm.vo.ElderAndBerth;
import com.yxm.vo.H5SelectVo;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/reception")
public class ReceptionController {
    @Autowired
    private NursetypeDao nursetypeDao;
    @Autowired
    private ElderService elderService;
    @Autowired
    private BondsmanService bondsmanService;
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private LocationService locationService;

    private static final String UPLOAD_PATH="G:/yxm/javaProjectUp/BaseAdmin/user/";
    /**
     * 页面跳转,预约办理
     * @return
     */
    @RequestMapping("/appointment")
    public ModelAndView appointment(){
        ModelAndView mv = new ModelAndView("/appointment");
        Random random = new Random();
        String userName = String.valueOf(random.nextInt(90000) + 10000);
        mv.addObject("count",userName);
        return mv;
    }

    /**
     *页面跳转,长者信息
     * @return
     */
    @RequestMapping("/particular")
    public String particular(){
        return "/particular";
    }

    /**
     * 页面跳转,担保人信息
     * @return
     */
    @RequestMapping("/guaranteeChange")
    public String guaranteeChange(){
        return "/guaranteeChange";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/inform")
    public String inform(){
        return "/inform";
    }

    /**
     * 待入职长者信息
     * @return
     */
    @RequestMapping(value = "/await",produces = "application/json;charset=utf-8")
    public String await(){
        return "/await";
    }

    /**
     * 办理入住
     * @return
     */
    @RequestMapping(value ="check",produces = "application/json;charset=utf-8")
    public String check(){
        return "/check";
    }

    /**
     * 到期提醒
     * @return
     */
    @RequestMapping(value = "/expire")
    public String expire(){
        return "/expire";
    }

    /**
     * 续约
     * @return
     */
    @RequestMapping("/renew")
    public String renew(){
        return "/renew";
    }

    /**
     * 办理退住
     * @return
     */
    @RequestMapping("/unsubscribe")
    public String unsubscribe(){
        return "/unsubscribe";
    }

    /**
     * 长者变更管理
     * @return
     */
    @RequestMapping("/change")
    public String change(){
        return "/change";
    }

    /**
     * 长者每月数据统计
     * @return
     */
    @RequestMapping("/consume")
    public String consume(){
        return "/consume";
    }
    /**
     * 预约办理,护理类型勾选下拉框回填护理类型数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/NurseType",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JsonMsg selectNurseTypeById(Integer id){
        JsonMsg jsonMsg = new JsonMsg();
        if(id!=null){
            SysNursetype nursetype = this.nursetypeDao.selectNurseTypeById(id);
            jsonMsg.setState(true);
            jsonMsg.setData(nursetype);
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/selectBerth",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectBerth(Integer grade){
        List<H5SelectVo> data = this.locationService.selectBerthGrade(grade);
        return data;
    }
    @RequestMapping(value = "/selectNurseTypeBerth",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectNurseTypeBerth(){
        List<H5SelectVo> data = this.locationService.selectNurseTypeBerth();
        return data;
    }
    /**
     * 返回长者信息数据
     * @param page
     * @param limit
     * @param elderName
     * @param berthNumber
     * @return
     */
    @RequestMapping(value = "/selectElderAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<ElderAndBerth> selectElderAll(Integer page, Integer limit,
                                                        @RequestParam(value = "elderName",required = false)String elderName,
                                                        @RequestParam(value = "berthNumber",required = false)String berthNumber){
        return this.elderService.selectElderAll(page,limit,elderName,berthNumber);
    }

    /**
     * 返回担保人信息数据
     * @param page
     * @param limit
     * @param elderName
     * @param bondsmanName
     * @param phone
     * @return
     */
    @RequestMapping(value = "/selectBondsmanAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysBondsman> selectBondsmanAll(Integer page, Integer limit,
                                                         @RequestParam(value = "elderName",required = false)String elderName,
                                                         @RequestParam(value = "bondsmanName",required = false)String bondsmanName,
                                                         @RequestParam(value = "phone",required = false)String phone){
        return this.bondsmanService.selectBondsmanAll(page,limit,elderName,bondsmanName,phone);
    }

    @RequestMapping(value = "/insertAppointment",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg insertAppointment(SysAppointment appointment, BondsmanList bondsmanList,MultipartFile portraitFile) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        if(appointment.getNurseType()==null||appointment.getNurseType()==0){
            jsonMsg.setMsg("长者护理类型不能为空");
            return jsonMsg;
        }
        if(appointment.getSite()==null){
            jsonMsg.setMsg("长者入住地点不能为空");
            return jsonMsg;
        }
        if(portraitFile==null){
            jsonMsg.setMsg("长者照片不能为空");
            return jsonMsg;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
        //判断文件存放目录是否存在
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        //拼接文件名  item.getName()--》文件名
        String fileName = dateFormat.format(new Date()) + System.nanoTime() + Tools.getFileExt(portraitFile.getOriginalFilename());
        //存放路径
        String filePaths = UPLOAD_PATH + fileName;
        //把文件名保存到需要新增的对象中
        appointment.setPortrait(fileName);
        try {
            boolean isOk = this.appointmentService.insertAppointmentData(appointment,bondsmanList);
            if(isOk){
                File saveFile = new File(filePaths);
                //保存文件到硬盘
                portraitFile.transferTo(saveFile);
                jsonMsg.setMsg("预约成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException | ParseException e){
            jsonMsg.setMsg("预约异常");
        }
        return jsonMsg;
    }


    /**
     * 修改长者信息获取数据回填
     * @param elderId
     * @return
     */
    @RequestMapping(value = "/selectElderById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectElderById(Integer elderId){
        JsonMsg jsonMsg = new JsonMsg();
        if (elderId!=null||elderId!=0){
            jsonMsg.setData(this.elderService.selectElderById(elderId));
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    /**
     * 修改长者信息
     * @param sysElder
     * @param idnumber
     * @param portraitFile
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/updateElder",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateElder(SysElder sysElder,String idnumber,MultipartFile portraitFile) throws IOException {
        JsonMsg jsonMsg=new JsonMsg();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
        if(sysElder==null){
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        if(sysElder.getElderName()==null){
            jsonMsg.setMsg("长者姓名不能为空");
            return jsonMsg;
        }
        if(sysElder.getElderGender()==null){
            jsonMsg.setMsg("长者姓别不能为空");
            return jsonMsg;
        }
        if(sysElder.getBirthday()==null){
            jsonMsg.setMsg("长者生日不能为空");
            return jsonMsg;
        }
        if (idnumber==null){
            jsonMsg.setMsg("长者身份证不能为空");
            return jsonMsg;
        }
        //判断文件存放目录是否存在
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        sysElder.setIDNumber(idnumber);
        String fileName ="";
        if(portraitFile!=null&&portraitFile.getBytes().length>0){
            //获取新图片路径
            fileName = dateFormat.format(new Date())+System.nanoTime()+Tools.getFileExt(portraitFile.getOriginalFilename());
            //把文件名保存到需要新增的对象中
            sysElder.setPortrait(fileName);
        }
        SysElder user = this.elderService.selectElderById(sysElder.getId());

        try{
            boolean isOk=this.elderService.updateElder(sysElder);
            if (isOk){
                if(portraitFile!=null&&portraitFile.getBytes().length>0){
                    File ImgPath = new File(UPLOAD_PATH+fileName);
                    //保存图片到磁盘
                    portraitFile.transferTo(ImgPath);

                    //删除旧图片
                    //获取旧图片路径
                    String oldPath = UPLOAD_PATH+user.getPortrait();
                    //
                    File oldImg = new File(oldPath);
                    if (oldImg.exists()){
                        oldImg.delete();
                    }
                }
                jsonMsg.setState(true);
                jsonMsg.setMsg("修改成功");
            }else{
                jsonMsg.setMsg("修改失败");
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("修改异常");
        }
        return jsonMsg;
    }

    /**
     * 删除长者信息
     * @param elderId
     * @return
     */
    @RequestMapping(value = "/deleteElderById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteElderById(Integer elderId){
        JsonMsg jsonMsg = new JsonMsg();
        SysAppointment sysAppointment = this.appointmentService.selectAppointmentByElderId(elderId);
        if (sysAppointment.getState()==3){
            try{
                boolean isOk = this.elderService.deleteElderById(elderId);
                if (isOk){
                    jsonMsg.setMsg("删除成功");
                    jsonMsg.setState(true);
                }
            }catch (RuntimeException e){
                jsonMsg.setMsg("删除异常");
            }
        }else {
            jsonMsg.setMsg("该长者信息正在使用中,不能删除");
        }
        return jsonMsg;
    }

    /**
     * 回填担保人信息
     * @param bondsmanId
     * @return
     */
    @RequestMapping(value = "/selectBondsmanById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectBondsmanById(Integer bondsmanId){
        JsonMsg jsonMsg = new JsonMsg();
        if (bondsmanId!=null||bondsmanId!=0){
            jsonMsg.setData(this.bondsmanService.selectBondsmanById(bondsmanId));
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    /**
     * 修改担保人信息
     * @param sysBondsman
     * @param idnumber
     * @return
     */
    @RequestMapping(value = "updateBondsman",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg updateBondsman(SysBondsman sysBondsman,String idnumber){
        JsonMsg jsonMsg = new JsonMsg();
        if(sysBondsman.getBondsmanName()==null||sysBondsman.getBondsmanName()==""){
            jsonMsg.setMsg("担保人姓名不能为空");
            return jsonMsg;
        }
        if(sysBondsman.getHomeAddress()==null||sysBondsman.getHomeAddress()==""){
            jsonMsg.setMsg("担保人家庭地址不能为空");
            return jsonMsg;
        }
        if(sysBondsman.getPhone()==null||sysBondsman.getPhone()==""){
            jsonMsg.setMsg("担保人手机号不能为空");
            return jsonMsg;
        }
        if(idnumber==null||idnumber==""){
            jsonMsg.setMsg("担保人身份证不能为空");
            return jsonMsg;
        }
        if(sysBondsman.getRelation()==null){
            jsonMsg.setMsg("请选择担保人关系");
            return jsonMsg;
        }
        if (sysBondsman.getWorkUnit()==null||sysBondsman.getWorkUnit()==""){
            jsonMsg.setMsg("请填写担保人的工作单位");
            return jsonMsg;
        }
        sysBondsman.setIDNumber(idnumber);
        try {
            boolean isOk = this.bondsmanService.updateBondsman(sysBondsman);
            if(isOk){
                jsonMsg.setMsg("修改成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException e){
            jsonMsg.setMsg("修改异常");
        }
        return jsonMsg;
    }

    /**
     * 删除成功
     * @param bondsmanId
     * @param appointmentId
     * @return
     */
    @RequestMapping(value = "/deleteBondsmanById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteBondsmanById(Integer bondsmanId,Integer appointmentId){
        JsonMsg jsonMsg = new JsonMsg();
        if(bondsmanId!=null||bondsmanId!=0){
            if(appointmentId!=null||appointmentId!=0){
                SysElder sysElder = this.elderService.selectElderByAppointmentId(appointmentId);
                Integer count = this.bondsmanService.selectElderB(sysElder.getId());
                if(count>1){
                    try {
                        boolean isOk = this.bondsmanService.deleteBondsman(bondsmanId);
                        if(isOk){
                            jsonMsg.setMsg("删除成功");
                            jsonMsg.setState(true);
                        }
                    }catch (RuntimeException e){
                        jsonMsg.setMsg("删除异常");
                        return jsonMsg;
                    }
                }else {
                    jsonMsg.setMsg("长者至少需要一个担保人信息");
                    return jsonMsg;
                }
            }else {
                jsonMsg.setMsg("数据异常");
                return jsonMsg;
            }
        }else {
            jsonMsg.setMsg("数据异常");
            return jsonMsg;
        }
        return jsonMsg;
    }
}

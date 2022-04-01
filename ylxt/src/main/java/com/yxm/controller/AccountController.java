package com.yxm.controller;

import com.yxm.po.SysUser;
import com.yxm.service.AccountService;
import com.yxm.util.JsonMsg;
import com.yxm.util.MD5Util;
import com.yxm.util.Tools;
import com.yxm.vo.H5SelectVo;
import com.yxm.vo.LayuiTableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RequestMapping("/account")
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

    private static final String UPLOAD_PATH="G:/yxm/javaProjectUp/BaseAdmin/user/";

    @RequestMapping(value = "/selectUserAll",produces = "application/json;charset=utf-8")
    @ResponseBody
    public LayuiTableData<SysUser> selectUserAll(Integer page,Integer limit){
        return this.accountService.selectUserAll(page,limit);
    }

    @RequestMapping(value = "/selectPositionForH5Select",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<H5SelectVo> selectPositionForH5Select(){
        return this.accountService.selectPositionForH5Select();
    }

    @RequestMapping(value = "/selectUserById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg selectUserById(Integer userId){
        JsonMsg jsonMsg = new JsonMsg();
        if (userId!=null){
            jsonMsg.setState(true);
            jsonMsg.setData(this.accountService.selectUserById(userId));
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/deleteElderById",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg deleteElderById(Integer userId){
        JsonMsg jsonMsg = new JsonMsg();
        if(userId!=null){
            if(userId!=1){
                //把用户头像也删除
                SysUser sysUser = this.accountService.selectUserById(userId);
                boolean isOk = this.accountService.deleteElderById(userId);
                if (isOk){
                    if (sysUser.getPortrait()!=null){
                        //获取旧图片路径
                        String oldPath = UPLOAD_PATH+sysUser.getPortrait();
                        File oldImg = new File(oldPath);
                        if (oldImg.exists()){
                            oldImg.delete();
                        }
                    }
                    jsonMsg.setMsg("删除成功");
                    jsonMsg.setState(true);
                }else {
                    jsonMsg.setMsg("删除失败");
                }
            }else {
                jsonMsg.setMsg("超级管理不允许删除");
            }
        }else {
            jsonMsg.setMsg("数据异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/insert",produces = "application/json;cahrset=utf-8")
    @ResponseBody
    public JsonMsg insert(SysUser sysUser, MultipartFile portraitFile){
        JsonMsg jsonMsg = new JsonMsg();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");

        //判断文件存放目录是否存在
        File uploadDir = new File(UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String filePaths = "";
        if (portraitFile!=null){
            //拼接文件名  item.getName()--》文件名
            String fileName = dateFormat.format(new Date()) + System.nanoTime() + Tools.getFileExt(portraitFile.getOriginalFilename());
            //存放路径
            filePaths = UPLOAD_PATH + fileName;
            //把文件名保存到需要新增的对象中
            sysUser.setPortrait(fileName);
        }else {
            jsonMsg.setMsg("头像不能为空");
            return jsonMsg;
        }

        if (sysUser.getUserName()==null){
            jsonMsg.setMsg("用户名不能为空");
            return jsonMsg;
        }
        if(sysUser.getPassword()!=null){
            Random random = new Random();
            //生成一个随机的8位数作为盐   10000000 ~ 99999999
            String salt = String.valueOf(random.nextInt(90000000) + 10000000);
            //对输入的密码+盐 取MD5值
            String userPassword = MD5Util.getMD5(sysUser.getPassword() + salt);
            sysUser.setPassword(userPassword);
            sysUser.setSalt(salt);
        }else {
            jsonMsg.setMsg("密码不能为空");
            return jsonMsg;
        }
        if (sysUser.getPositionId()==null){
            jsonMsg.setMsg("请选择职位");
            return jsonMsg;
        }
        if (sysUser.getUserState()==null){
            jsonMsg.setMsg("请选择用户状态");
            return jsonMsg;
        }
        if(sysUser.getRealName()==null){
            jsonMsg.setMsg("请填写真实姓名");
            return jsonMsg;
        }
        try {
            boolean isOk = this.accountService.insert(sysUser);
            if(isOk){
                File saveFile = new File(filePaths);
                //保存文件到硬盘
                portraitFile.transferTo(saveFile);
                jsonMsg.setMsg("新增成功");
                jsonMsg.setState(true);
            }
        }catch (RuntimeException | IOException e){
            jsonMsg.setMsg("新增异常");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/update",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg update(SysUser sysUser,MultipartFile portraitFile) throws IOException {
        JsonMsg jsonMsg = new JsonMsg();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");
        if(sysUser.getUserName()!=null){
            if (sysUser.getId()==1&&sysUser.getPositionId()!=1){
                jsonMsg.setMsg("该用户不能修改职位");
                return jsonMsg;
            }
            //判断文件存放目录是否存在
            File uploadDir = new File(UPLOAD_PATH);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            String fileName ="";
            if(portraitFile!=null&&portraitFile.getBytes().length>0){
                //获取新图片路径
                fileName = dateFormat.format(new Date())+System.nanoTime()+Tools.getFileExt(portraitFile.getOriginalFilename());
                //把文件名保存到需要新增的对象中
                sysUser.setPortrait(fileName);
            }
            SysUser sysUser1 = this.accountService.selectUserById(sysUser.getId());
            try{
                boolean isOk=this.accountService.update(sysUser);
                if (isOk){
                    if(portraitFile!=null&&portraitFile.getBytes().length>0){
                        File ImgPath = new File(UPLOAD_PATH+fileName);
                        //保存图片到磁盘
                        portraitFile.transferTo(ImgPath);
                        //删除旧图片
                        //获取旧图片路径
                        String oldPath = UPLOAD_PATH+sysUser1.getPortrait();
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
        }else {
            jsonMsg.setMsg("用户名不能为空");
            return jsonMsg;
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg delete(Integer userId){
        JsonMsg jsonMsg = new JsonMsg();
        if (userId==1){
            jsonMsg.setMsg("超级用户不能删除");
            return jsonMsg;
        }
        boolean isOk = this.accountService.delete(userId);
        if (isOk){
            jsonMsg.setMsg("删除成功");
            jsonMsg.setState(true);
        }else {
            jsonMsg.setMsg("删除失败");
        }
        return jsonMsg;
    }

    @RequestMapping(value = "/resetPassword",produces = "application/json;charset=utf-8")
    @ResponseBody
    public JsonMsg resetPassword(Integer userId,String password){
        JsonMsg jsonMsg=new JsonMsg();
        if (userId!=null&&userId>0){
            if (Tools.isNotNull(password)){
                //查询未修改的数据
                SysUser oldUser=this.accountService.selectUserById(userId);
                Random random = new Random();
                //生成一个随机的8位数作为盐   10000000 ~ 99999999
                String salt = String.valueOf(random.nextInt(90000000) + 10000000);
                //对输入的密码+盐 取MD5值
                String userPassword = MD5Util.getMD5(password + salt);
                oldUser.setPassword(userPassword);
                oldUser.setSalt(salt);
                //调用修改方法
                try{
                    boolean isOk= this.accountService.updates(oldUser);
                    if (isOk){
                        jsonMsg.setState(true);
                        jsonMsg.setMsg("重置成功");
                    }else {
                        jsonMsg.setMsg("重置失败");
                    }
                }catch (RuntimeException e){
                    jsonMsg.setMsg("删除异常");
                }
            }else {
                jsonMsg.setMsg("请输入新密码");
            }
        }else {
            jsonMsg.setMsg("非法访问");
        }
        return jsonMsg;
    }
}

using FJDPXT.EntityClass;
using FJDPXT.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FJDPXT.Filter
{
    public class PermissionFilter : ActionFilterAttribute
    {

        //实例化model
        FJDPXTEntities1 myModel = new FJDPXTEntities1();

        /// <summary>
        /// 在方法执行前 执行
        /// </summary>
        /// <param name="filterContext"></param>
        public override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            base.OnActionExecuting(filterContext);

            int loginUserID = 0;
            try
            {
                //获取请求的URL
                string url = filterContext.HttpContext.Request.Url.AbsolutePath;
                //===检查是否登录
                //跳过登录检查的URL
                if (url == "/" || url == "/Main/Login" || url == "/Main/CreateValidImage" || url == "/Main/UserLogin")
                {
                    return;
                }

                loginUserID = Convert.ToInt32(filterContext.HttpContext.Session["userID"].ToString());

                //===权限检查
                if (url == "/" || url.Contains("/Main/"))
                {
                    return;
                }
                //第一种：获取在登录时 查询的权限模块信息
                List<ModuleVo> userModules = filterContext.HttpContext.Session["userModules"] as List<ModuleVo>;

                //第二种,每一次请求都查询一遍权限
                //List<ModuleVo> userModules = (from tabModule in myModel.S_Module
                //                              join tabP in myModel.S_Permission
                //                               on tabModule.moduleID equals tabP.moduleID
                //                              join tabUseType in myModel.S_UserType
                //                               on tabP.UserTypeID equals tabUseType.userTypeID
                //                              join tabUser in myModel.S_User
                //                               on tabUseType.userTypeID equals tabUser.userTypeID
                //                              where tabUser.userID == loginUserID
                //                              select new ModuleVo
                //                              {
                //                                  moduleID = tabModule.moduleID,
                //                                  moduleName = tabModule.moduleName,
                //                                  moduleDescrible = tabModule.moduleDescrible,
                //                                  moduleFarID = tabModule.moduleFarID,
                //                                  blFun = tabModule.blFun,
                //                                  parentModule = (from tabModuleF in myModel.S_Module
                //                                                  where tabModuleF.moduleID == tabModule.moduleFarID
                //                                                  select tabModuleF).FirstOrDefault()
                //                              }).ToList();
                string[] strUrls = url.Split('/');//根据/分割 -> 0-空字符串，1-区域名称，2-控制器名称，3-Action
                if (strUrls.Length >= 4)
                {
                    string areaName = strUrls[1];//获取区域名称
                    string controllerName = strUrls[2]; //获取控制器名称
                    //一般情况权限处理--判断 区域和 控制器
                    int exist = userModules.Count(o => o.moduleName == controllerName && o.parentModule.moduleName == areaName);
                    if (exist == 0)
                    {
                        filterContext.HttpContext.Response.Redirect("/Main/NoPermission");
                    }
                    //处理PNR复制功能
                    if (strUrls[3] == "CopyPNR")
                    {
                        if (userModules.Count(o => o.moduleName == "PNRCopy" && o.parentModule.moduleName == controllerName) == 0)
                        {
                            filterContext.HttpContext.Response.Redirect("/Main/NoPermission");
                        }
                    }
                }

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                filterContext.HttpContext.Response.Redirect("/Main/Login");
            }
        }
    }
}
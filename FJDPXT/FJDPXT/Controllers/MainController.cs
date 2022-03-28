using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Common;
using FJDPXT.EntityClass;
using FJDPXT.Models;

namespace FJDPXT.Controllers
{
    public class MainController : Controller
    {
        // GET: Main
        //实例化。把一个类，形象化，通过类的实例，就是对象，来实现类的功能。因为类是抽象的，所以需要实例化对象才能使用类。
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        public ActionResult Index()
        {
            return View();
        }
        //登录页面控制台
        public ActionResult Login()
        {
            string jobNumber = "";//工号
            string password = "";//密码
            bool rememberMe = false;//记住我
            //获取浏览器携带cookie
            HttpCookie cookie = System.Web.HttpContext.Current.Request.Cookies["user"];
            if (cookie != null)
            {
                if (cookie["jobNumber"] != null)
                {
                    jobNumber = System.Web.HttpContext.Current.Server.UrlDecode(cookie["jobNumber"]);
                }
                if(cookie["password"] != null)
                {
                    password = System.Web.HttpContext.Current.Server.UrlDecode(cookie["password"]);
                }
                rememberMe = true;
            }
            ViewBag.jobNumber = jobNumber;
            ViewBag.password = password;
            ViewBag.rememberMe = rememberMe;

            return View();
        }
        //验证码
        public ActionResult CreateValidImage()
        {
            //创建string一个变量validCode=一个有效的代码ValidCodeUtils.使得随机代码为（5）位数
            string validCode = ValidCodeUtils.GetRandomCode(5);
            //创建一个储存工具byte[]，变量=一个有效的代码ValidCodeUtils.创造一张图片来存放一个五位数的代码CreateImage(validCode)
            byte[] validImage = ValidCodeUtils.CreateImage(validCode);
            //将生成 的验证码保存到session，session是一个服务器的站点，服务器为这个验证码开辟了一个空间存放验证码
            Session["validCode"] = validCode;
            //将图片返回到页面,File是文件，因为图片属于文件所有用File，用法(文件名称，"文件类型")，图片，视频，文件等
            return File(validImage,"image/jpeg");
        }
        //接收页面发送过来的数据并进行分析数据
        public ActionResult UserLogin(string jobNumber, string userPassword, string validCode,bool rememberMe)//创建一个变量来接收页面发送过来的数据，renmemberme是判断型所以返回ture或者false
        {
            //接收返回数据，引用创建好一个类，并实例化
            ReturnJson msg = new ReturnJson();
            msg.State = false;
            //创建一个变量等于空作用于对返回验证码的接收
            string sessionValidCode = "";
            //判断如果返回的验证码不等于空的话，将在控制台创建好的储存器Session["validCode"]验证码赋值给sessionValidCode
            if (Session["validCode"]!=null)
            {
                //ToString使用方法是将验证码格式转化
                sessionValidCode = Session["validCode"].ToString();
            }
            //检查用户输入验证码和生成验证码是否相同
            
            validCode = validCode == null ? "" : validCode.Trim();//如果用户输入验证码等于空的话就变成一个空的字符串否则就等于用户输入的验证码去掉空格
            //判断sessionValidCode比较用户输入的验证码StringComparison，并且忽略大小写InvariantCultureIgnoreCase
            if (!sessionValidCode.Equals(validCode,StringComparison.InvariantCultureIgnoreCase))
            {
                //验证码用户的工号和密码是否匹配
                //查询用户数据 Linq
                try
                {
                    //判断用户存不存在
                    //from是SQL代码写法来自tabUser（变量），tabUser在myModel（实例化过后的类）.S_User（数据库中的表名）
                    S_User dbUser = (from tabUser in myModel.S_User
                                         //S_User的数据赋值给了tabUser，tabUser的用户名等于页面发送过来的用户名
                                     where tabUser.jobNumber == jobNumber
                                     //select 查询表tabUser，Single是查询表的一条数据，多条数据写发Tolist。
                                     select tabUser).Single();//如果存在多条数据Single就会出错
                    //对用户输入密码进行加密，引用写好的代码
                    string aesPassword = AESEncryptHelper.Encrypt(userPassword);
                    //将加密后的密码和数据库中进行对比
                    if (aesPassword == dbUser.userPassword)
                    {
                        int loginUserID = dbUser.userID;
                        List<ModuleVo> userModules = (from tabModule in myModel.S_Module
                                                      join tabP in myModel.S_Permission
                                                       on tabModule.moduleID equals tabP.moduleID
                                                      join tabUseType in myModel.S_UserType
                                                       on tabP.UserTypeID equals tabUseType.userTypeID
                                                      join tabUser in myModel.S_User
                                                       on tabUseType.userTypeID equals tabUser.userTypeID
                                                      where tabUser.userID == loginUserID
                                                      select new ModuleVo
                                                      {
                                                          moduleID = tabModule.moduleID,
                                                          moduleName = tabModule.moduleName,
                                                          moduleDescrible = tabModule.moduleDescrible,
                                                          moduleFarID = tabModule.moduleFarID,
                                                          blFun = tabModule.blFun,
                                                          parentModule = (from tabModuleF in myModel.S_Module
                                                                          where tabModuleF.moduleID == tabModule.moduleFarID
                                                                          select tabModuleF).FirstOrDefault()
                                                      }).ToList();
                        //===3-保存用户信息到session,用户ID,用户工号
                        Session["userID"] = dbUser.userID;
                        Session["jobNumber"] = dbUser.jobNumber;
                        Session["userModules"] = userModules;

                        //===4-处理记住我这个功能 使用cookie实现
                        //==4.1-判断是否勾选"记住我"
                        if (rememberMe)
                        {
                            //勾选
                            //实例cookie
                            HttpCookie cookie = new HttpCookie("user");
                            //保存数据到cookie
                            cookie["jobNumber"] = jobNumber;
                            cookie["password"] = userPassword;
                            //设置cookie的有效期 7天.Expires设置cookie的过期代码
                            cookie.Expires = DateTime.Now.AddDays(7);
                            //通过Response把cookie返回给浏览器
                            Response.Cookies.Add(cookie);
                        }
                        else
                        {
                            //未勾选 删除cookie
                            //实例cookie
                            HttpCookie cookie = new HttpCookie("user");
                            //设置cookie的有效期为昨天
                            cookie.Expires = DateTime.Now.AddDays(-1);
                            //通过Response把cookie返回给浏览器
                            Response.Cookies.Add(cookie);
                        }

                        msg.State = true;
                        msg.Text = "登录成功";
                    }
                    else {
                        msg.Text = "登录失败";
                    }

                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "该用户不存在";
                }
            }
            //进行验证码比较后为不正确时执行的代码
            else
            {
                msg.Text = "验证码不正确，请重新输入";
            }
            //返回json格式的数据
            return Json(msg,JsonRequestBehavior.AllowGet);
        }
        public ActionResult Main()
        {
            try
            {
                //将图用户头像更改为用户名。
                //int取用户ID因为id为纯数字,string用户工号是字符串
                int userID = Convert.ToInt32(Session["userID"].ToString());
                //
                string jobNumber = Session["jobNumber"].ToString();

                //传递数据到页面
                //ViewData["jobNumber"] = jobNumber;
                ViewBag.jobNumber = jobNumber;

                return View();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);

                //重定向
                //重定向到登录页面
                //return Redirect(Url.Content("~/Main/Login"));
                //return Redirect("https://www.baidu.com");\
                return RedirectToAction("Login");
            }
        }
        public ActionResult loginOut()
        {
            //移除本次会话保存的所有数据
            Session.Clear();

            return Json(true, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 查询模块权限
        /// </summary>
        /// <returns></returns>
        public ActionResult SelectModularJurisdiction()
        {
            if (Session["userID"] != null)
            {
                int userId = Convert.ToInt32(Session["userID"]);
                //读取权限信息
                var tempModulars = from tabPermission in myModel.S_Permission
                                   join tabModule in myModel.S_Module
                                    on tabPermission.moduleID equals tabModule.moduleID
                                   join tabUser in myModel.S_User
                                    on tabPermission.UserTypeID equals tabUser.userTypeID
                                   where tabUser.userID == userId && tabModule.blFun == false
                                   select new
                                   {
                                       ID = tabModule.moduleID,//模块ID
                                       Name = tabModule.moduleDescrible.Trim()//模块描述
                                   };
                //外连接(左连接)
                var userModulars = (from tbSysModular in myModel.S_Module
                                    join tbTempModulars in tempModulars
                                        on tbSysModular.moduleID equals tbTempModulars.ID into temp
                                    select new
                                    {
                                        ModularID = tbSysModular.moduleID,//模块ID
                                        ModularName = tbSysModular.moduleDescrible.Trim(),//模块名称
                                        ID = temp.FirstOrDefault() != null ? temp.FirstOrDefault().ID : 0//有该模块的权限 ID>0 没有权限ID=0
                                    }).ToList();
                return Json(userModulars, JsonRequestBehavior.AllowGet);
            }
            else
            {
                return Json("", JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult NoPermission()
        {
            return View();
        }
    }
}

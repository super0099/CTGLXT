using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using FJDPXT.Common;


namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class ModifyPasswordController : Controller
    {
        // GET: OthersMaintenance/ModifyPassword
        private int sessID = 0;
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        #region 前置方法
        protected override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            base.OnActionExecuting(filterContext);
            try
            {
                sessID = Convert.ToInt32(Session["userID"].ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                filterContext.Result = Redirect(Url.Content("~/Main/Login"));
            }
        }
        #endregion
        #region 视图
        public ActionResult Index()
        {
            return View();
        }
        #endregion
        #region 确认旧密码是否正确
        public ActionResult CheckedOldPassword(string oldPassword)
        {
            ReturnJson msg = new ReturnJson();

            try
            {
                //根据登录的用户ID查询用户数据
                S_User dbUser = myModel.S_User.Single(o => o.userID == sessID);
                //将用户输入的旧密码加密后和数据库的密码对比
                oldPassword = AESEncryptHelper.Encrypt(oldPassword);
                if (oldPassword == dbUser.userPassword)
                {
                    msg.State = true;
                    msg.Text = "密码正确";
                }
                else
                {
                    msg.Text = "密码错误";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "密码错误";
            }

            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 确认修改密码并重新登录
        public ActionResult updatePassword(string oldPassword, string newPassword)
        {
            ReturnJson msg = new ReturnJson();

            try
            {
                //根据登录的用户ID查询用户数据
                S_User dbUser = myModel.S_User.Single(o => o.userID == sessID);
                //将用户输入的旧密码加密后和数据库的密码对比
                oldPassword = AESEncryptHelper.Encrypt(oldPassword);
                if (oldPassword == dbUser.userPassword)
                {
                    //==修改密码 对新密码进行加密 并修改用户对象的密码
                    dbUser.userPassword = AESEncryptHelper.Encrypt(newPassword);
                    myModel.Entry(dbUser).State = System.Data.Entity.EntityState.Modified;
                    if (myModel.SaveChanges() > 0)
                    {
                        msg.State = true;
                        msg.Text = "修改成功";

                        //清空session 强制重新登录
                        Session.Clear();
                    }
                    else
                    {
                        msg.Text = "密码修改失败";
                    }
                }
                else
                {
                    msg.Text = "旧密码错误";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "旧密码错误";
            }

            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
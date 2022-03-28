using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.Common;
using FJDPXT.EntityClass;
using System.Transactions;
using System.Text.RegularExpressions;

namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class OpenOffNumberController : Controller

    {
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        // GET: OthersMaintenance/OpenOffNumber
        public ActionResult Index()
        {
            try
            {
                int userID = Convert.ToInt32(Session["userID"].ToString());

            }catch (Exception e)
            {
                Console.WriteLine(e);
                return Redirect(Url.Content("~/Main/login"));
            }
            return View();
        }
        //获取下拉框的值
        public ActionResult SelectUserGroupForSelect()
        {
            List<SelectVo> List = (from tabUserGroup in myModel.S_UserGroup
                                      select new SelectVo() {
                                          id=tabUserGroup.userGroupID,
                                          text=tabUserGroup.userGroupNumber
                                      }).ToList();
            return Json(List, JsonRequestBehavior.AllowGet);
        }
        public ActionResult SelectUserTypeForSelect()
        {
            List<SelectVo> List = (from tabUserType in myModel.S_UserType
                                   select new SelectVo()
                                   {
                                       id = tabUserType.userTypeID,
                                       text = tabUserType.userType
                                   }).ToList();
            return Json(List, JsonRequestBehavior.AllowGet);
        }
        public ActionResult InsertUser(S_User user ,HttpPostedFileBase userPicture)
        {
            ReturnJson msg = new ReturnJson();
            //用户组和用户角色判断
            if (user.userGroupID > 0 && user.userTypeID > 0)
            {
                //工号
                if (!string.IsNullOrEmpty(user.jobNumber)&&Regex.IsMatch(user.jobNumber, "^[A-Za-z0-9]{3,10}$"))
                {
                    //用户姓名
                    if (!string.IsNullOrEmpty(user.userName))
                    {
                        //余额
                        if (user.amount != null && user.amount >= 0)
                        {
                            //邮箱
                            if (!string.IsNullOrEmpty(user.userEmail) &&
                                Regex.IsMatch(user.userEmail, "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$"))
                            {
                                //判断工号是否存在
                                int oldCount = myModel.S_User.Count(o => o.jobNumber == user.jobNumber);
                                if (oldCount == 0)
                                {
                                    //开始事务
                                    using (TransactionScope scope = new TransactionScope())
                                    {
                                        try
                                        {
                                            //保存用户头像
                                            //==检查存放用户头像的目录是否存在
                                            if (!System.IO.Directory.Exists(Server.MapPath("~/Document/userPicture/")))
                                            {
                                                //创建目录
                                                System.IO.Directory.CreateDirectory(Server.MapPath("~/Document/userPicture/"));
                                            }
                                            if(userPicture!=null&& userPicture.ContentLength > 0)
                                            {
                                                //获取文件的扩展名称
                                                string imgExtension = System.IO.Path.GetExtension(userPicture.FileName);
                                                //拼接要保存的文件名,可能存在重复的文件名,为了避免重复,在拼接的时候加上当前日期和时间,加上随机编码,再加上文件扩展名
                                                string fileName = DateTime.Now.ToString("yyyyMMddHHmmssffff") + "_" + Guid.NewGuid() + imgExtension;
                                                //拼接文件保存的路径
                                                string filePath = Server.MapPath("~/Document/userPicture/") + fileName;
                                                //保存上传的文件到硬盘
                                                userPicture.SaveAs(filePath);
                                                //文件名称保存到user对象
                                                user.picture = fileName;

                                            }
                                            //==先保存 用户数据
                                            //-对用户密码进行加密
                                            user.userPassword = AESEncryptHelper.Encrypt(user.userPassword);
                                            myModel.S_User.Add(user);
                                            if (myModel.SaveChanges() > 0)
                                            {
                                                //用户数据保存成功
                                                //获取保存后用的ID，因为lin新增数据后数据的主键ID设置到保存所使用的对象中
                                                int userID = user.userID;
                                                S_VirtualAccount virtualAccount = new S_VirtualAccount();
                                                virtualAccount.userID = userID;//设置账号ID
                                                virtualAccount.accountBalance = user.amount;//设置账户余额
                                                virtualAccount.account = string.Format("XNZH{0:000000000}", userID);//设置虚拟账号
                                                myModel.S_VirtualAccount.Add(virtualAccount);
                                                if (myModel.SaveChanges() > 0)
                                                {
                                                    //!!!!!!!!!!!提交事务
                                                    scope.Complete();
                                                    msg.State = true;
                                                    msg.Text = "数据保存成功";
                                                }
                                                else
                                                {
                                                    msg.Text = "数据保存失败";
                                                }
                                            }
                                            else
                                            {
                                                msg.Text = "数据保存失败";
                                            }
                                        }
                                        catch (Exception e)
                                        {
                                            msg.Text = "数据保存失败";
                                        }
                                    }
                                }
                                else
                                {
                                    msg.Text = "该工号已经存在,请检查";
                                }  
                            }
                            else
                            {
                                msg.Text = "E-mail格式不正确,请检查";
                            }
                        }
                        else
                        {
                            msg.Text = "余额只能大于等于0";
                        }
                    }
                    else
                    {
                        msg.Text = "请输入用户姓名";
                    }
                }
                else
                {
                    msg.Text = "工号由3到10位字母或者数字组成，请检查";
                }
            }
            else
            {
                msg.Text = "请选择用户组/用户角色";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
    }
}
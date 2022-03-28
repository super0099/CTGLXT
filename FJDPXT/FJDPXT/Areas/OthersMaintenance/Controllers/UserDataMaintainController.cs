using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Text.RegularExpressions;
using System.Transactions;
using FJDPXT.Common;
using System.IO;
using System.Data;

namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class UserDataMaintainController: Controller
    {
        // GET: OthersMaintenance/UserDataMaintain
        // 其他 -->用户资料维护

        //实例化Model
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        private int sessID=0;
        #region 前置
        //代码意思就是在执行下面方法之前必先经过这些代码
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
        }//
        #endregion
        #region Index.HTML
        public ActionResult Index()
        {
            //清除本页面所使用的session数据
            Session.Remove("nextStartTieckNo");
            Session.Remove("groupUserIdList");
            return View();
        }
        #endregion
        #region UserList.HTML
        public ActionResult UserList(string userGroup)
        {
            //将用户输入的userGroup保存到session中,方便后续使用
            Session["userGroup"] = userGroup;
            return View();
        }
        #endregion
        #region 表格数据显示和表格分页
        public ActionResult SelectUserList(LayuiTablePage layuiTablePage)
        {
            string userGroup = "";
            if (Session["userGroup"] != null)
            {
                userGroup = Session["userGroup"].ToString();
            }
            var varQuery = (from tabUser in myModel.S_User
                                //连接用户表和用户组表
                            join tabUserGroup in myModel.S_UserGroup
                                on tabUser.userGroupID equals tabUserGroup.userGroupID//tabUser外键fk连接tabUserGroup的主键pk
                                                                                      //连接用户表和角色表
                            join tabUserType in myModel.S_UserType
                                on tabUser.userTypeID equals tabUserType.userTypeID//tabUser外键fk连接tabUserType的主键pk
                                                                                   //连接用户表和虚拟账户表
                            join tabVirtualAccount in myModel.S_VirtualAccount
                                on tabUser.userID equals tabVirtualAccount.userID//tabUser主键pk连接tabVirtualAccount的外键fk
                            orderby tabUser.userID//排序
                            select new UserVo()
                            {
                                userID = tabUser.userID,//用户ID
                                userTypeID = tabUser.userTypeID,//角色ID
                                userGroupID = tabUser.userGroupID,//用户组ID
                                jobNumber = tabUser.jobNumber,//工号
                                userName = tabUser.userName,//姓名
                                userEmail = tabUser.userEmail,//邮箱
                                isEnable = tabUser.isEnable,//是否启用
                                userGroupNumber = tabUserGroup.userGroupNumber,//用户组
                                accountBalance = tabVirtualAccount.accountBalance.Value,//账户余额
                                userType = tabUserType.userType,//角色
                                picture = tabUser.picture//用户头像名称

                            });

            if (!string.IsNullOrEmpty(userGroup))
            {
                varQuery = varQuery.Where(o => o.userGroupNumber == userGroup);
            }

            //连表查询
            List<UserVo> listData = varQuery
                                   .Skip(layuiTablePage.GetStartIndex())
                                   .Take(layuiTablePage.limit)
                                   .ToList();
            int totalRows = varQuery.Count();
            LayuiTableData<UserVo> layuiTableData = new LayuiTableData<UserVo>()
            {
                count = totalRows,
                data = listData
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 禁用和启用按钮的代码
        public ActionResult SwitchUserEnable(int userID, bool isEnable)
        {
            //实例化
            ReturnJson msg = new ReturnJson();
            try
            {
                int sessionUserID = Convert.ToInt32(Session["userID"].ToString());
                if (userID != 1)
                {
                    if (userID == sessionUserID)
                    {
                        msg.Text = "不能修改当前登录账号";
                    }
                    else
                    {
                        try
                        {
                            //查询需要 启用/禁用的账户数据
                            S_User dbUser = myModel.S_User.Single(o => o.userID == userID);
                            //启用/禁用用户--本质 修改用户数据的 isEnable字段
                            dbUser.isEnable = isEnable;
                            myModel.Entry(dbUser).State = System.Data.Entity.EntityState.Modified;
                            if (myModel.SaveChanges() > 0)
                            {
                                msg.State = true;
                                msg.Text = isEnable ? "启用" : "禁用" + "用户成功";
                            }
                            else
                            {
                                msg.Text = isEnable ? "启用" : "禁用" + "用户失败";
                            }
                        }
                        catch (Exception e1)
                        {
                            Console.WriteLine(e1);
                            msg.Text = "参数异常";
                        }
                    }
                }
                else
                {
                    msg.Text = "超级管理员不允许被修改";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "未登录无法操作";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 下拉框
        public ActionResult SelectUserTypeForSeelct()
        {
            //SelectVo是创建好的一个下拉框类
            List<SelectVo> UserTypeID = (from tabUserTypeID in myModel.S_UserType
                                         select new SelectVo()
                                         {
                                             id = tabUserTypeID.userTypeID,
                                             text = tabUserTypeID.userType
                                         }).ToList();
            return Json(UserTypeID, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 根据ID获取数据并回填数据
        public ActionResult SelectUserByID(int userID)
        {
            try
            {
                UserVo tabuserbyid = (from tabUserID in myModel.S_User
                                      join tabUserByid in myModel.S_UserType
                                      on tabUserID.userTypeID equals tabUserByid.userTypeID
                                      join tabuserid in myModel.S_UserGroup
                                      on tabUserID.userGroupID equals tabuserid.userGroupID
                                      join TabUserID in myModel.S_VirtualAccount
                                      on tabUserID.userID equals TabUserID.userID
                                      where tabUserID.userID == userID
                                      select new UserVo()
                                      {
                                          userID = tabUserID.userID,
                                          userName = tabUserID.userName,
                                          jobNumber = tabUserID.jobNumber,
                                          userPassword = tabUserID.userPassword,
                                          userEmail = tabUserID.userEmail,
                                          userType = tabUserByid.userType,
                                          userGroupNumber = tabuserid.userGroupNumber,
                                          userTypeID=tabUserByid.userTypeID,
                                          picture= tabUserID.picture,
                                          accountBalance = TabUserID.accountBalance.Value,
                                          userGroupID= tabuserid.userGroupID
                                      }).Single();
                return Json(tabuserbyid, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }
        #endregion
        #region 确定修改数据
        public ActionResult UpdateUser(S_User user,HttpPostedFileBase userPicture)
        {
            ReturnJson msg = new ReturnJson();
            if (user.userID > 0)
            { 
                if (user.userID != sessID)
                {
                    if (user.userTypeID != 0)
                    {
                        if (user.jobNumber != null)
                        {
                            int yhjs = myModel.S_User.Count(o => o.jobNumber == user.jobNumber && o.userID != user.userID);
                            if (yhjs == 0)
                            {
                                if (!string.IsNullOrEmpty(user.userEmail) && Regex.IsMatch(user.userEmail, "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$"))
                                {
                                    if (user.amount != null)
                                    {
                                        if (sessID != 0)
                                        {
                                            using (TransactionScope scope = new TransactionScope())
                                            {
                                                //int useriD = Convert.ToInt32(Session["userID"].ToString());
                                                //msg.Text = "useriD";
                                                try
                                                {
                                                    if (user.userPassword == null)
                                                    {
                                                        string dbuserPassword = (from table in myModel.S_User
                                                                                 //table.userID是数据库的ID和用户输入的ID相等
                                                                                 where table.userID == user.userID
                                                                                 select table.userPassword).Single();
                                                        //user.userPassword = dbuserPassword;
                                                        //string dbuserPassword = myModel.S_User.Single(o => o.userID == user.userID).userPassword;//报错原因是出现两个user对象，一个是页面传过来的一个是创建出来的，但是页面传过来的对象已经被修改而创建的没有被修改
                                                        user.userPassword = dbuserPassword;
                                                    }
                                                    else
                                                    {
                                                        user.userPassword = AESEncryptHelper.Encrypt(user.userPassword);
                                                    }
                                                    //查询出旧的图片
                                                    string oldPicture = (from table in myModel.S_User
                                                                                 //table.userID是数据库的ID和用户输入的ID相等
                                                                             where table.userID == user.userID
                                                                             select table.picture).Single();

                                                    if (userPicture != null && userPicture.ContentLength > 0)
                                                    {
                                                        //图片上传
                                                        if (!System.IO.Directory.Exists(Server.MapPath("~/Document/userPicture/")))
                                                        {
                                                            System.IO.Directory.CreateDirectory(Server.MapPath("~/Document/userPicture/"));
                                                        }
                                                        //图片上传
                                                        //获取文件的扩展名称
                                                        string imgExtension = System.IO.Path.GetExtension(userPicture.FileName);
                                                        //拼接要保存的文件名称,使得该名称独一无二
                                                        string fileName = DateTime.Now.ToString("yyyyMMddHHmmssffff") + "_" + Guid.NewGuid() + imgExtension;
                                                        //拼接文件保存的路径
                                                        string filePath = Server.MapPath("~/Document/userPicture/") + fileName;
                                                        //保存上传的文件夹到硬盘
                                                        userPicture.SaveAs(filePath);
                                                        //文件名称保存到user对象
                                                        user.picture = fileName;

                                                        //判断是否有旧图片，有就删除
                                                        if (!string.IsNullOrEmpty(oldPicture))
                                                        {
                                                            string oldFilePath = Server.MapPath("~/Document/userPicture/") + oldPicture;
                                                            //判断文件是否存在
                                                            if (System.IO.File.Exists(oldFilePath))
                                                            {
                                                                //删除文件
                                                                System.IO.File.Delete(oldFilePath);
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        //图片未上传
                                                        user.picture = oldPicture;
                                                    }
                                                    myModel.Entry(user).State = System.Data.Entity.EntityState.Modified;
                                                    if (myModel.SaveChanges() > 0)
                                                    {
                                                        scope.Complete();
                                                        msg.State = true;
                                                        msg.Text = "修改成功";
                                                    }
                                                    else
                                                    {
                                                        msg.Text = "修改失败";
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                    Console.WriteLine(e);
                                                    msg.Text = "数据异常";
                                                }
                                            }
                                        }
                                        else
                                        {
                                            msg.Text = "未登录，不能修改";
                                        }
                                    }
                                    else
                                    {
                                        msg.Text = "余额数据异常";
                                    }
                                }
                                else
                                {
                                    msg.Text = "邮箱格式不正确";
                                }
                            }
                            else
                            {
                                msg.Text = "该工号已经存在";
                            }
                        }
                        else
                        {
                            msg.Text = "用户姓名不能为空";
                        }
                    }
                    else
                    {
                        msg.Text = "数据异常!";
                    }
                }
                else
                {
                    msg.Text = "当前用户正在登录";
                }
            }
            else
            {
                msg.Text = "数据异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 删除
        public ActionResult DeleteUser(int userID)
        {
            ReturnJson msg = new ReturnJson();            
            try
            {
                if (userID != 1)
                {
                    int sessionID = Convert.ToInt32(Session["userID"].ToString());
                    if (userID != sessionID)
                    {
                        int countPNR = myModel.B_PNR.Count(o => o.operatorID == userID);
                        int countOrder = myModel.B_Order.Count(o => o.operatorID == userID);
                        int countETicket = myModel.B_ETicket.Count(o => o.operatorID == userID);
                        int countFlightChange = myModel.B_FlightChange.Count(o => o.operatorID == userID);
                        int countTicketRefund = myModel.B_TicketRefund.Count(o => o.operatorID == userID);
                        int countETicketChange = myModel.B_ETicketChange.Count(o => o.operatorID == userID);
                        int countETicketInvalid = myModel.B_ETicketInvalid.Count(o => o.operatorID == userID);
                        if (countPNR + countOrder + countETicket + countFlightChange + countTicketRefund + countETicketChange + countETicketInvalid == 0)
                        {
                            using (TransactionScope scope = new TransactionScope())
                            {
                                //查询要删除的用户
                                S_User dbuser = myModel.S_User.Single(o => o.userID == userID);
                                myModel.S_User.Remove(dbuser);
                                if (myModel.SaveChanges ()> 0)
                                {
                                    S_VirtualAccount Account = myModel.S_VirtualAccount.Single(o => o.userID == userID);
                                    myModel.S_VirtualAccount.Remove(Account);
                                    if (myModel.SaveChanges() > 0)
                                    {
                                        scope.Complete();
                                        msg.State = true;
                                        msg.Text = "删除成功";
                                    }
                                    else
                                    {
                                        msg.Text = "删除失败";
                                    }
                                }
                                else
                                {
                                    msg.Text = ("删除失败");
                                }
                            }
                        }
                        else
                        {
                            msg.Text = "正在被使用不能删除";
                        }
                        
                    }
                    else
                    {
                        msg.Text = "不能删除当前登录用户";
                    }
                }
                else
                {
                    msg.Text = "系统默认账户不能删除";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "数据异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 下载模板
        public ActionResult DownImportTemplate()
        {
            //获取模板文件的路径,相对路径
            string templateFilePath = Server.MapPath("~/Document/用户导入模板.xls");
            //判断路径是否存在
            if (System.IO.File.Exists(templateFilePath))
            {
                //获取文件名,在文件路径中提取文件名称,在输入输出的方法中的路径里获取文件名称(文件路径)
                string fileName = System.IO.Path.GetFileName(templateFilePath);
                //以流的形式返回文件
                //返回文件(在输入输出的方法中以流的形式将文件路径,打开方式),文件流固定写法,文件名
                return File(new System.IO.FileStream(templateFilePath,System.IO.FileMode.Open), "application/octet-stream", fileName);
            }
            else
            {
                return Content("导入模板不存在,请联系网站管理人员");
            }
        }
        #endregion
        #region 导入数据
        public ActionResult ImportExcel(HttpPostedFileBase excelFile)
        {
            ReturnJson msg = new ReturnJson();
            try
            {
                //获取文件后缀名
                string fileExtension = System.IO.Path.GetExtension(excelFile.FileName);
                //判断文件后缀
                if (".xls".Equals(fileExtension, StringComparison.CurrentCultureIgnoreCase))
                {
                    //转换成二进制数组
                    //声明一个和文件大小一致的二进制数组
                    byte[] fileBytes = new byte[excelFile.ContentLength];
                    //将上传的文件转成二进制数组
                    //将用户输入的文件用流的方式输入到二进制数组里,从0开始,到数组长度结束
                    excelFile.InputStream.Read(fileBytes, 0, fileBytes.Length);
                    //将二进制数组转为内存流
                    MemoryStream excelMemoryStream = new MemoryStream(fileBytes);
                    //将内存流转为工作簿
                    NPOI.SS.UserModel.IWorkbook workbook = new NPOI.HSSF.UserModel.HSSFWorkbook(excelMemoryStream);
                    //判断是否存在工作表
                    if (workbook.NumberOfSheets > 0)
                    {
                        //获取第一个工作表
                        NPOI.SS.UserModel.ISheet sheet = workbook.GetSheetAt(0);
                        //判断工作表是否有行,PhysicalNumberOfRows物理存在的行，不包含空行，也就是实际存在多少行
                        if (sheet.PhysicalNumberOfRows > 0)
                        {
                            //将数据保存到DataTable中
                            //定义一个DataTable
                            DataTable dtExcel = new DataTable();

                            //获取Excel中的标题行，设置Excel的列名，第二行是标题行索引为1。                            
                            NPOI.SS.UserModel.IRow rowHeader = sheet.GetRow(1);
                            /*
                              FirstCellNum：获取某行第一个单元格下标
                              LastCellNum：获取某行的列数 ！！！！！
                              FirstRowNum：获取第一个实际行的下标
                              LastRowNum：获取最后一个实际行的下标
                            */
                            //获取表格的列数,LastCellNum没有下标
                            int cellCount = rowHeader.LastCellNum;
                            //获取表格的行数
                            int rowCount = sheet.LastRowNum - 1;
                            //创建DataTable中的列                            
                            for (int i = rowHeader.FirstCellNum; i < cellCount; i++)
                            {
                                //通过遍历行中的每一个单元格，获取标题行各个单元格的数据
                                DataColumn dtCol = new DataColumn(rowHeader.GetCell(i).StringCellValue.Trim());
                                //把列添加到DataTable中
                                dtExcel.Columns.Add(dtCol);
                            }
                            //读取Excel中的数据
                            //(sheet.FirstRowNum+2) 第一行是说明行 第二行是标题行，第三行开始才是数据
                            for (int i = (sheet.FirstRowNum+2); i < rowCount; i++)
                            {
                                //获取行
                                NPOI.SS.UserModel.IRow row = sheet.GetRow(i);
                                //DataTable中创建一行
                                DataRow dtRow = dtExcel.NewRow();
                                //遍历行中的列获取数据
                                if (row != null)
                                {
                                    for (int j = row.FirstCellNum ; j < cellCount; j++)
                                    {
                                        if (row.GetCell(j) != null)
                                        {
                                            dtRow[j] = row.GetCell(j).ToString();
                                        }
                                    }
                                }
                                //将一行的数据添加到Datatable
                                dtExcel.Rows.Add(dtRow);
                            }
                            //移除掉DataTable中的空行
                            removeEmptyRow(dtExcel);
                            //===将dataTable中的数据转换为List<S_User>
                            //查询出所有用户组 和用户角色
                            List<S_UserGroup> userGroups = (from tabUserGroup in myModel.S_UserGroup
                                                            select tabUserGroup).ToList();
                            List<S_UserType> userTypes = (from tabUserType in myModel.S_UserType
                                                          select tabUserType).ToList();
                            //存放所有的用户 包括数据库和添加的 --用于判断工号是否重复
                            List<S_User> allUsers = (from tabUser in myModel.S_User
                                                     select tabUser).ToList();
                            //定义存放容器
                            List<S_User> saveUsers = new List<S_User>();
                            //遍历datatable中的数据
                            for (int i = 0; i < dtExcel.Rows.Count; i++)
                            {
                                try
                                {
                                    DataRow dr = dtExcel.Rows[i];
                                    //创建一个S_User实例保存一条用户数据
                                    S_User addUser = new S_User();

                                    //1-用户组号 根据用户组号查询用户组ID
                                    string userGroupNumber = dr["用户组号"].ToString().Trim();
                                    addUser.userGroupID = userGroups.Single(o => o.userGroupNumber == userGroupNumber).userGroupID;
                                    //2-角色
                                    string userType = dr["角色"].ToString().Trim();
                                    addUser.userTypeID = userTypes.Single(o => o.userType == userType).userTypeID;
                                    //3-工号
                                    string jobNumber = dr["工号"].ToString().Trim();
                                    int oldCount = allUsers.Count(o => o.jobNumber == jobNumber);
                                    if (oldCount > 0)
                                    {
                                        msg.Text = "第" + (i + 1) + "条数据的工号:[" + jobNumber + "]重复，请检查";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                    addUser.jobNumber = jobNumber;
                                    //4-姓名
                                    string userName = dr["姓名"].ToString().Trim();
                                    if (string.IsNullOrEmpty(userName))
                                    {
                                        msg.Text = "第" + (i + 1) + "条数据的 姓名 未填写，请检查";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                    addUser.userName = userName;
                                    //4-余额
                                    string amount = dr["余额"].ToString().Trim();
                                    if (string.IsNullOrEmpty(amount) || !Regex.IsMatch(amount, "^\\+?[0-9]{1,6}(\\.[0-9]{1,2})?$"))
                                    {
                                        msg.Text = "第" + (i + 1) + "条数据的余额不正确，请检查";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                    addUser.amount = Convert.ToDecimal(amount);
                                    //5-密码
                                    string userPassword = dr["密码"].ToString().Trim();
                                    addUser.userPassword = AESEncryptHelper.Encrypt(userPassword);
                                    //6-email
                                    string email = dr["email"].ToString().Trim();
                                    if (string.IsNullOrEmpty(email) ||
                                        !Regex.IsMatch(email, "^([a-zA-Z]|[0-9])(\\w|\\-)+@[a-zA-Z0-9]+\\.([a-zA-Z]{2,4})$"))
                                    {

                                        msg.Text = "第" + (i + 1) + "条数据的邮箱格式不正确，请检查";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                    addUser.userEmail = email;
                                    //7-是否开通
                                    string strEnable = dr["是否开通"].ToString().Trim();
                                    if (strEnable != "是" && strEnable != "否")
                                    {
                                        msg.Text = "第" + (i + 1) + "条数据的 是否开通列 值只能填写“是”或者“否”，请检查";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                    addUser.isEnable = (strEnable == "是");

                                    //添加到要保存的列表saveUsers
                                    saveUsers.Add(addUser);
                                    //添加到用于查重的列表allUsers
                                    allUsers.Add(addUser);
                                }
                                catch (Exception e)
                                {
                                    Console.Write(e);
                                    msg.Text = "第" + (i + 1) + "条数据不正确，请检查";
                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                }
                            }
                            //=============进行数据保存
                            if (saveUsers.Count > 0)
                            {
                                using (TransactionScope scope = new TransactionScope())
                                {
                                    try
                                    {
                                        foreach (S_User saveUser in saveUsers)
                                        {
                                            //保存用户数据
                                            myModel.S_User.Add(saveUser);
                                            myModel.SaveChanges();
                                            //获取保存后的用户ID
                                            int tUserId = saveUser.userID;
                                            //===再保存 虚拟账户数据
                                            S_VirtualAccount virtualAccount = new S_VirtualAccount();
                                            virtualAccount.userID = tUserId;//设置用户ID
                                            virtualAccount.accountBalance = saveUser.amount;//设置账户余额
                                                                                            //virtualAccount.account = string.Format("XNZH{0:000000000}", userID);//设置虚拟账号
                                            virtualAccount.account = string.Format("XNZH{0:000000000}", tUserId);//设置虚拟账号

                                            myModel.S_VirtualAccount.Add(virtualAccount);
                                            myModel.SaveChanges();
                                        }
                                        //提交事务
                                        scope.Complete();
                                        msg.State = true;
                                        msg.Text = "数据导入成功，成功导入" + saveUsers.Count() + "条用户数据";
                                    }
                                    catch (Exception e)
                                    {
                                        Console.Write(e);
                                        msg.Text = "数据导入保存失败";
                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                    }
                                }
                            }
                            else
                            {
                                msg.Text = "导入失败,请检查是否填写数据！";
                            }
                        }
                        else
                        {
                            msg.Text = "导入失败,请检查是第一个工作表中是否存在数据！";
                        }
                    }
                    else
                    {
                        msg.Text = "上传的Excel文件中不存在工作表，请检查";
                    }
                }
                else
                {
                    msg.Text = "请上传Excel(.xls)文件";
                }

            }
            catch (Exception e)
            {
                Console.Write(e);
                msg.Text = "上传失败";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 去除datatable空行
        /// </summary>
        /// <param name="dt"></param>
        private void removeEmptyRow(DataTable dt)
        {
            //存放需要移除的DataRow
            List<DataRow> removeList = new List<DataRow>();
            //遍历所有的行
            for (int i = 0; i < dt.Rows.Count; i++)
            {
                bool rowDataIsEmpty = true;//标识是否是空行-默认为空行
                //遍历DataRow的所有列
                for (int j = 0; j < dt.Columns.Count; j++)
                {
                    //判断数据是否为空
                    if (!string.IsNullOrEmpty(dt.Rows[i][j].ToString().Trim()))
                    {
                        rowDataIsEmpty = false;
                    }
                }
                //如果是空行，添加到removeList
                if (rowDataIsEmpty)
                {
                    removeList.Add(dt.Rows[i]);
                }
            }
            //移除掉空行
            for (int i = 0; i < removeList.Count; i++)
            {
                dt.Rows.Remove(removeList[i]);
            }
        }
        #endregion
    }
}
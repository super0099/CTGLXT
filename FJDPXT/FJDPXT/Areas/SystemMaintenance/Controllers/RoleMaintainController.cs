using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Transactions;

namespace FJDPXT.Areas.SystemMaintenance.Controllers
{
    public class RoleMaintainController : Controller
    {
        // GET: SystemMaintenance/RoleMaintain
        private int loginUserID = 0;
        protected override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            base.OnActionExecuting(filterContext);
            try
            {
                loginUserID = Convert.ToInt32(Session["userID"].ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                filterContext.Result = Redirect(Url.Content("~/Main/Login"));//重定向到登录
            }
        }
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        #region 角色查询
        public ActionResult Index()
        {
            return View();
        }
        /// <summary>
        /// 分页查询角色数据
        /// </summary>
        /// <param name="layuiTablePage">layuiTable分页信息</param>
        /// <returns></returns>
        public ActionResult SelectUserTypeInfo(LayuiTablePage layuiTablePage)
        {
            //分页查询数据
            List<S_UserType> listUserTypes = myModel.S_UserType
                .OrderBy(a => a.userTypeID)//根据角色ID排序
                .Skip(layuiTablePage.GetStartIndex())//跳过前面页数的数据
                .Take(layuiTablePage.limit)//查询本页数据的条数
                .ToList();//返回List集合
            //查询机场数据的总共条数
            int intTotalRow = myModel.S_UserType.Count();

            //准备Layui Table所需的数据格式
            LayuiTableData<S_UserType> layuiTableData = new LayuiTableData<S_UserType>()
            {
                data = listUserTypes,
                count = intTotalRow
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion
        /// <summary>
        /// 新增 页面
        /// </summary>
        /// <returns></returns>
        #region 新增角色与权限
        /// <summary>
        /// 内部类
        /// </summary>
        public class ModuleID
        {
            public int moduleID { get; set; }

        }
        /// <summary>
        /// 新增 页面
        /// </summary>
        /// <returns></returns>
        public ActionResult InsertRole()
        {
            List<S_Module> moduleList = myModel.S_Module.ToList();
            ViewBag.moduleList = moduleList;
            return View();
        }
        /// <summary>
        /// 新增角色 和 权限
        /// </summary>
        /// <param name="modUserType">角色数据</param>
        /// <param name="listModuleID">权限模块ID list</param>
        /// <returns></returns>
        public ActionResult InsertPermission(S_UserType modUserType, List<ModuleID> listModuleID)
        {
            ReturnJson msg = new ReturnJson();
            int addCount = 0;//用于记录新增成功的数据的条数            

            //开启事务 - 涉及多表操作，需要开启事务
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {
                    //判断页面传递的数据不为空
                    if (!string.IsNullOrEmpty(modUserType.userType) && !string.IsNullOrEmpty(modUserType.description))
                    {
                        //判断角色是否重复
                        int oldUserTypeCount = myModel.S_UserType.Count(m => m.userType == modUserType.userType.Trim());
                        if (oldUserTypeCount > 0)
                        {
                            msg.Text = "角色已经存在！";
                        }
                        else
                        {
                            //1、新增角色
                            myModel.S_UserType.Add(modUserType);
                            if (myModel.SaveChanges() > 0)
                            {
                                //获取角色ID
                                int UserTypeID = modUserType.userTypeID;
                                //2、遍历生成选择的模块对象
                                List<S_Permission> addPermissions = new List<S_Permission>();
                                foreach (ModuleID item in listModuleID)
                                {
                                    S_Permission modPer = new S_Permission()
                                    {
                                        moduleID = item.moduleID,//模块ID
                                        UserTypeID = UserTypeID,//用户角色ID
                                        isEnable = true//启用
                                    };
                                    addPermissions.Add(modPer);
                                }
                                myModel.S_Permission.AddRange(addPermissions);
                                addCount = myModel.SaveChanges();

                                if (listModuleID.Count == addCount)
                                {
                                    //提交事务
                                    scope.Complete();
                                    msg.State = true;
                                    msg.Text = "角色新增成功！";
                                }
                                else
                                {
                                    msg.Text = "角色新增失败！";
                                }
                            }
                            else
                            {
                                msg.Text = "新增数据异常！";
                            }
                        }
                    }
                    else
                    {
                        msg.Text = "用户类型或用户描述不能为空！";
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "添加角色失败";
                }
            }

            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 修改角色和权限
        /// <summary>
        /// 修改角色页面
        /// </summary>
        /// <param name="userTypeID"></param>
        /// <returns></returns>
        public ActionResult UpdateRole(int userTypeID)
        {
            List<S_Module> moduleList = myModel.S_Module.ToList();

            ViewBag.moduleList = moduleList;
            ViewBag.userTypeID = userTypeID;
            return View();
        }
        public ActionResult SelectUserTypeById(int userTypeID)
        {
            //根据用户类型ID查询 角色
            S_UserType userType = myModel.S_UserType.Where(o => o.userTypeID == userTypeID).Single();
            //根据用户类型ID查询 该用户的权限
            List<PermissionVo> listpermission = (from tabPermission in myModel.S_Permission
                                                 join tabUserType in myModel.S_UserType
                                                    on tabPermission.UserTypeID equals tabUserType.userTypeID
                                                 where tabUserType.userTypeID == userTypeID
                                                 select new PermissionVo
                                                 {
                                                     userType = tabUserType.userType,
                                                     description = tabUserType.description,
                                                     permissionID = tabPermission.permissionID,
                                                     UserTypeID = tabPermission.UserTypeID,
                                                     moduleID = tabPermission.moduleID,
                                                     isEnable = tabPermission.isEnable,
                                                 }).ToList();
            //使用JSON返回多个对象
            return Json(new { userType = userType, listPermission = listpermission }, JsonRequestBehavior.AllowGet);
        }

        public ActionResult UpdatePermission(S_UserType modUserType, List<ModuleID> listModuleID)
        {
            ReturnJson msg = new ReturnJson();

            int addCount = 0;//记录新增成功的数据条数
            int removeCount = 0;//记录移除成功的数据条数
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {
                    //获取UserTypeID
                    int userTypeID = modUserType.userTypeID;

                    //1-修改角色表
                    myModel.Entry(modUserType).State = System.Data.Entity.EntityState.Modified;
                    if (myModel.SaveChanges() > 0)
                    {
                        //查询出数据库中 该用户角色的的模块ID
                        List<ModuleID> oldModuleIDs = (from tabPermission in myModel.S_Permission
                                                       where tabPermission.UserTypeID == userTypeID
                                                       select new ModuleID
                                                       {
                                                           moduleID = tabPermission.moduleID
                                                       }).ToList();
                        //这里面应该有3中情况：
                        //①需要新增的（数据库里面没有的） listModuleID中有，而oldModuleIDs中没有的
                        //②需要删除的 listModuleID中没有，oldModuleIDs中有的
                        //③保持不变的 listModuleID中有的，oldModuleIDs中也有的

                        //1-先查找保持不变的   listModuleID 和 oldModuleIDs的交集
                        List<ModuleID> existList = listModuleID.Intersect(oldModuleIDs, new ModuleIDEqualityComparer()).ToList();
                        //2-新增的 
                        List<ModuleID> addList = listModuleID.Except(existList, new ModuleIDEqualityComparer()).ToList();
                        //3-删除的
                        List<ModuleID> removeList = oldModuleIDs.Except(existList, new ModuleIDEqualityComparer()).ToList();

                        //先删除removeList中的数据
                        if (removeList.Count > 0)
                        {
                            //获取出需要删除的moduleID
                            List<int> removeIDs = removeList.Select(o => o.moduleID).ToList();
                            //查询出需要删除的 S_Permission信息
                            List<S_Permission> removePermissions = myModel.S_Permission.Where(o => removeIDs.Contains(o.moduleID) && o.UserTypeID == userTypeID).ToList();
                            //删除数据
                            myModel.S_Permission.RemoveRange(removePermissions);
                            //更新到数据库 记录被删除的行数
                            removeCount = myModel.SaveChanges();
                        }
                        //新增需要 增加的
                        if (addList.Count > 0)
                        {
                            //存放要新增到S_Permission
                            List<S_Permission> addPermissions = new List<S_Permission>();
                            //遍历addList 创建需要保存的S_Permission对象
                            foreach (ModuleID item in addList)
                            {
                                S_Permission modPer = new S_Permission()
                                {
                                    moduleID = item.moduleID,
                                    UserTypeID = userTypeID,
                                    isEnable = true
                                };
                                addPermissions.Add(modPer);
                            }
                            //新增数据到数据库 并 记录新增的行数
                            myModel.S_Permission.AddRange(addPermissions);
                            addCount = myModel.SaveChanges();
                        }

                        if ((removeList.Count + addList.Count) == (removeCount + addCount))
                        {
                            //提交事务
                            scope.Complete();
                            msg.State = true;
                            msg.Text = "授权成功！";
                        }
                        else
                        {
                            msg.Text = "授权失败！";
                        }
                    }
                    else
                    {
                        msg.Text = "授权失败！";
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "数据异常授权失败！";
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        public class ModuleIDEqualityComparer : IEqualityComparer<ModuleID>
        {
            /// <summary>
            /// 确定指定的对象是否相等。
            /// </summary>
            /// <param name="x">第一个类型为 ModuleID 的对象</param>
            /// <param name="y">第二个类型为 ModuleID 的对象</param>
            /// <returns></returns>
            public bool Equals(ModuleID x, ModuleID y)
            {
                return x.moduleID == y.moduleID;
            }
            /// <summary>
            /// 返回指定对象的哈希代码。
            /// </summary>
            /// <param name="obj"></param>
            /// <returns></returns>
            public int GetHashCode(ModuleID obj)
            {
                if (obj == null)
                {
                    return 0;
                }
                else
                {
                    return obj.ToString().GetHashCode();
                }
            }
        }
        #endregion

        #region 删除角色
        /// <summary>
        /// 删除角色 
        /// </summary>
        /// <param name="userTypeID">角色ID</param>
        /// <returns></returns>
        public ActionResult DeletePermission(int userTypeID)
        {
            ReturnJson msg = new ReturnJson();
            int removeCount = 0;//用于记录删除的数据条数
            //开启事务
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {
                    //1、判断是否有用户正在使用当前用户角色
                    var intUseUserCount = myModel.S_User.Count(o => o.userTypeID == userTypeID);
                    if (intUseUserCount == 0)
                    {
                        //1、删除角色数据
                        S_UserType userTypeInfor = myModel.S_UserType.Where(m => m.userTypeID == userTypeID).Single();
                        myModel.S_UserType.Remove(userTypeInfor);
                        //2、批量删除模块
                        //查询模块列表
                        List<S_Permission> removeList = myModel.S_Permission.Where(m => m.UserTypeID == userTypeID).ToList();
                        //执行删除 
                        myModel.S_Permission.RemoveRange(removeList);
                        //保存到数据库 记录受影响行数
                        removeCount = myModel.SaveChanges();
                        //判断是否删除成功 +1 是因为删除S_UserType也有一条
                        if ((removeList.Count + 1) == removeCount)
                        {
                            //提交事务
                            scope.Complete();
                            msg.State = true;
                            msg.Text = "角色已删除！";
                        }
                        else
                        {
                            msg.Text = "角色删除失败！";
                        }
                    }
                    else
                    {
                        msg.Text = "该用户角色存在使用用户，不能删除！";
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "数据异常角色删除失败！";
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
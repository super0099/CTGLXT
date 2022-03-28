using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;


namespace FJDPXT.Areas.SystemMaintenance.Controllers
{
    public class TicketNumMaintainController : Controller
    {
        private int SessionIDs = 0;
        FJDPXTEntities1 myModles = new FJDPXTEntities1();
        protected override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            base.OnActionExecuting(filterContext);
            try
            {
                SessionIDs = Convert.ToInt32(Session["userID"].ToString());
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                filterContext.Result = Redirect(Url.Content("~/Main/Login"));
            }
        }

        // GET: SystemMaintenance/TicketNumMaintain
        public ActionResult Index()
        {
            return View();
        }
        #region 表格渲染和递归
        public ActionResult SelectTicket(LayuiTablePage layuiTablePage)
        {
            //查询出当前用登录的用户
            S_User loginUser = myModles.S_User.Single(o => o.userID == SessionIDs);
            //登录用户所在用户组ID
            int userGroupID = loginUser.userGroupID;
            //获取登录用户所在用户组的子用户组
            List<S_UserGroup> childUsetGroup = getChildUserGroup(userGroupID).ToList();
            //获取出用户组的用户组ID
            List<int> userGroupIDs = new List<int>();
            foreach (S_UserGroup userGroup in childUsetGroup)
            {
                userGroupIDs.Add(userGroup.userGroupID);//把用户组ID添加到List中
            }
            //获取childUsetGroup包含的用户的ID
            List<int> userIDs = (from tabUser in myModles.S_User
                                 where userGroupIDs.Contains(tabUser.userGroupID)
                                 select tabUser.userID).ToList();
            //把当前登录的用户组ID添加到list
            userIDs.Add(SessionIDs);

            var query = from tabTicket in myModles.S_Ticket
                        join tabUser in myModles.S_User on tabTicket.userID equals tabUser.userID
                        join tabUserGroup in myModles.S_UserGroup on tabUser.userGroupID equals tabUserGroup.userGroupID
                        orderby tabTicket.ticketID
                        where userIDs.Contains(tabUser.userID)
                        select new TecketVo()
                        {
                            ticketID = tabTicket.ticketID,
                            ticketDate = tabTicket.ticketDate,
                            startTicketNo = tabTicket.startTicketNo,
                            endTicketNo = tabTicket.endTicketNo,
                            currentTicketNo = tabTicket.currentTicketNo,
                            userID = tabTicket.userID,
                            isEnable = tabTicket.isEnable,
                            //
                            jobNumber = tabUser.jobNumber,
                            userName = tabUser.userName,
                            userGroupNumber = tabUserGroup.userGroupNumber
                        };

            //获取总行数
            int totalRows = query.Count();

            //分页查询
            List<TecketVo> listData = query
                .Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();

            //准备layui table所需的数据
            LayuiTableData<TecketVo> layuiTableData = new LayuiTableData<TecketVo>()
            {
                count = totalRows,
                data = listData
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }

        private IEnumerable<S_UserGroup> getChildUserGroup(int superiorUserGroupID)
        {
            var query = from tabUseGroup in myModles.S_UserGroup
                        where tabUseGroup.superiorUserGroupID == superiorUserGroupID
                        select tabUseGroup;
            return query.ToList().Concat(query.ToList().SelectMany(o => getChildUserGroup(o.userGroupID)));
        }
        #endregion
        public ActionResult SwitchTitcketEnable(int ticketID,bool isEnable)
        {
            ReturnJson msg = new ReturnJson();
            try
            {
                //查询出需要启用或禁用的票号
                S_Ticket dbTicket = myModles.S_Ticket.Single(o => o.ticketID == ticketID);
                //修改isEnable
                dbTicket.isEnable = isEnable;
                if (myModles.SaveChanges() > 0)
                {
                    msg.State = true;
                    msg.Text = (isEnable ? "启用" : "禁用") + "票号信息成功";
                }
                else
                {
                    msg.Text = (isEnable ? "启用" : "禁用") + "票号信息成功";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "参数异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        public ActionResult DeleteTicket(int ticketID)
        {
            ReturnJson msg = new ReturnJson();
            try
            {
                //查询出需要启用或禁用的票号
                S_Ticket dbTicket = myModles.S_Ticket.Single(o => o.ticketID == ticketID);
                //判断
                string startTicketNo = "E781-"+dbTicket.startTicketNo;
                int count = (from tabETicket in myModles.B_ETicket
                             where tabETicket.ticketNo == startTicketNo
                             select tabETicket).Count();
                if (count == 0)
                {
                    //删除
                    myModles.S_Ticket.Remove(dbTicket);
                    if (myModles.SaveChanges() > 0)
                    {
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
                    msg.Text = "数据正在使用";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "参数异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
    }
}
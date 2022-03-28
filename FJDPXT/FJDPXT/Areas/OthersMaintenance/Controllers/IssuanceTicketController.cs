using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;


namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class IssuanceTicketController : Controller
    {
        // GET: OthersMaintenance/IssuanceTicket
        //其他 --> 票证下发

        //实例化model
        FJDPXTEntities1 myModel = new FJDPXTEntities1();

        private int loginUserID = 0;
        #region 前置方法
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
        #endregion
        #region 视图
        public ActionResult Index()
        {
            return View();
        }
        #endregion
        #region 递归查询出所有用户组
        public ActionResult SelectUser()
        {
            try
            {
                //==查询出用户所在用户组ID
                int loginUserGroupID = (from tabUser in myModel.S_User
                                        where tabUser.userID == loginUserID
                                        select tabUser.userGroupID).Single();
                //查询用户所在用户组ID所有子用户组 
                List<S_UserGroup> childGroups = GetChildUserGroup(loginUserGroupID).ToList();
                //==查询出用户组的用户组ID List
                List<int> listUserGroupID = (from tabUserGroup in childGroups
                                             select tabUserGroup.userGroupID).ToList();
                //===根据UserGroupID list查询出对应用户,不包含当前用户
                List<SelectVo> list = (from tabUser in myModel.S_User
                                       where listUserGroupID.Contains(tabUser.userGroupID)
                                       select new SelectVo()
                                       {
                                           id = tabUser.userID,
                                           text = tabUser.jobNumber + " - " + tabUser.userName
                                       }).ToList();

                //查询出当前用户
                List<SelectVo> loginUser = (from tabUser in myModel.S_User
                                            where tabUser.userID == loginUserID
                                            select new SelectVo()
                                            {
                                                id = tabUser.userID,
                                                text = tabUser.jobNumber + " - " + tabUser.userName
                                            }).ToList();
                //把当前用户添加到列表中
                list.AddRange(loginUser);
                //对list进行排序
                list = list.OrderBy(o => o.id).ToList();
                //将用户列表保存到session中，供列表查询是使用
                List<int> groupUserIdList = new List<int>();
                foreach (SelectVo vo in list)
                {
                    groupUserIdList.Add(vo.id);
                }
                Session["groupUserIdList"] = groupUserIdList;

                return Json(list, JsonRequestBehavior.AllowGet);

            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }

            return null;
        }


        private IEnumerable<S_UserGroup> GetChildUserGroup(int superiorUserGroupID)
        {
            var query = from tabUseGroup in myModel.S_UserGroup
                        where tabUseGroup.superiorUserGroupID == superiorUserGroupID
                        select tabUseGroup;//查询整个表格
            //query 查询出的对象列表.concat进行合并()里的表格.SelectMany选择更多或者说遍历。()里也是一个对象的数据
            // 根据当前用户组的父级用户组ID去查询当前的用户组ID.然后把当前用户组ID作为父级用户组ID去查询用户组ID循环遍历
            IEnumerable<S_UserGroup> childChild = query.ToList().SelectMany(o => GetChildUserGroup(o.userGroupID));
            return query.ToList().Concat(childChild);

        }
        #endregion
        #region 发票表格渲染
        public ActionResult GetStartTicketNo()
        {
            try
            {
                //查询所有S_Ticket数据，并且根据ID进行排序，倒叙
                S_Ticket lastTicket = (from tabTicket in myModel.S_Ticket
                                       orderby tabTicket.ticketID descending
                                       select tabTicket).FirstOrDefault();
                int nextStartTieckNo = 0;
                if (lastTicket != null)
                {
                    //获取列表中的票号属性endTicketNo
                    string strEndTicketNo = lastTicket.endTicketNo;
                    //把字符串转成数字
                    int endTicketNo = Convert.ToInt32(strEndTicketNo);
                    //下发的开始票号
                    nextStartTieckNo = endTicketNo + 1;
                }
                //本次开始的票号  //PadLeft 返回一个指定长度字符串，如果原字符串长度不足，就在左边填充指定的字符
                string strStartTicketNo = "E781-" + nextStartTieckNo.ToString().PadLeft(10, '0');
                Session["nextStartTieckNo"] = nextStartTieckNo;
                return Json(strStartTicketNo, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            return Json("", JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 取值下拉框并返回对应的数据
        //UserID获取下拉框的值，如果UserID=0，查询所有数据，等于1查询1对应的数据以此类推
        public ActionResult SelectTicket(LayuiTablePage layuiTablePage, int UserID)
        {
            var query = from tabTicket in myModel.S_Ticket
                            //变量来自myModel.S_User 根据tabTicket.userID的外键连接tabUser.userID主键
                        join tabUser in myModel.S_User on tabTicket.userID equals tabUser.userID
                        //变量来自myModel.S_UserGroup 根据tabUser.userGroupID的外键连接tabUserGroup.userGroupID主键
                        join tabUserGroup in myModel.S_UserGroup on tabUser.userGroupID equals tabUserGroup.userGroupID
                        orderby tabTicket.ticketID//根据ID进行排序
                        select new TecketVo()
                        {
                            //来自myModel.S_Ticket的数据
                            ticketID = tabTicket.ticketID,
                            ticketDate = tabTicket.ticketDate,
                            startTicketNo = tabTicket.startTicketNo,
                            endTicketNo = tabTicket.endTicketNo,
                            currentTicketNo = tabTicket.currentTicketNo,
                            userID = tabTicket.userID,
                            //来自myModel.S_User的数据
                            jobNumber = tabUser.jobNumber,
                            userName = tabUser.userName,
                            //来自myModel.S_UserGroup的数据
                            userGroupNumber = tabUserGroup.userGroupNumber
                        };

            //userID 为0时查询下拉框中所有的用户（自己和下级）
            if (UserID == 0)
            {
                List<int> groupUserIdList = Session["groupUserIdList"] as List<int>;
                query = query.Where(o => groupUserIdList.Contains(o.userID.Value));
            }
            else
            {
                //不为0时查询指定的用户
                query = query.Where(o => o.userID == UserID);
            }

            //总行数
            int totalRows = query.Count();
            //分页查询
            List<TecketVo> listData = query
                .Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();


            LayuiTableData<TecketVo> layuiTableData = new LayuiTableData<TecketVo>()
            {
                count = totalRows,
                data = listData
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 下发发票
        public ActionResult InsetTicket(int UserID,int Votes)
        {
            ReturnJson msg = new ReturnJson();
            if (UserID>0)
            {
                if (Votes>0)
                {
                    try
                    {
                        //获取到下发票号的开始值，根据开始的值计算出当前票号和结束票号
                        int StartTieckNo = Convert.ToInt32(Session["nextStartTieckNo"].ToString());
                        //实例化表格
                        S_Ticket saveTicket = new S_Ticket();
                        //下发用户
                        saveTicket.userID = UserID;
                        //下发日期
                        saveTicket.ticketDate = DateTime.Now;
                        //开始票号
                        saveTicket.startTicketNo= StartTieckNo.ToString().PadLeft(10, '0');
                        //当前票号
                        saveTicket.currentTicketNo = saveTicket.startTicketNo;
                        //结束票号
                        saveTicket.endTicketNo = (StartTieckNo + Votes - 1).ToString().PadLeft(10,'0');
                        //是否启用
                        saveTicket.isEnable = true;
                        //保存到数据库
                        myModel.S_Ticket.Add(saveTicket);

                        if (myModel.SaveChanges()> 0)
                        { 
                            Session.Remove("nextStartTieckNo");
                            msg.State = true;
                            msg.Text = "下发成功";
                        }
                        else
                        {
                            msg.Text = "下发失败";
                        }

                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e);
                        msg.Text = "下发数据异常";
                    }
                }
                else
                {
                    msg.Text = "下发票数大于0";
                }
            }
            else
            {
                msg.Text = "请选择下发用户";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
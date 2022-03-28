using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;

namespace FJDPXT.Areas.StatisticalManagement.Controllers
{
    public class VirAccountQueryController : Controller
    {
        // GET: StatisticalManagement/VirAccountQuery
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
        //统计管理-->虚拟账户查询
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        public ActionResult Index()
        {
            return View();
        }
        /// <summary>
        /// 查询虚拟账户信息
        /// </summary>
        /// <param name="layuiTablePage">分页</param>
        /// <param name="account">账户</param>
        /// <returns></returns>
        public ActionResult SelectVirAccountQuery(LayuiTablePage layuiTablePage, string userName, string account)
        {
            var list = from tabVirtualAccount in myModel.S_VirtualAccount//虚拟账户                    
                       join tabUser in myModel.S_User on tabVirtualAccount.userID equals tabUser.userID//用户
                       select new TransactionRecordVo
                       {
                           virtualAccountID = tabVirtualAccount.virtualAccountID,//虚拟账户ID
                           userID = tabUser.userID,//用户ID
                           userName = tabUser.userName,//用户姓名
                           account = tabVirtualAccount.account,//账户                         
                           accountBalance = tabVirtualAccount.accountBalance,//账户余额
                       };
            //1、用户姓名
            if (!string.IsNullOrEmpty(userName))
            {
                list = list.Where(m => m.userName.Contains(userName));
            }
            //2、账户
            if (!string.IsNullOrEmpty(account))
            {
                list = list.Where(m => m.account.Contains(account));
            }
            //分页查询机场数据
            List<TransactionRecordVo> listTransactionRecord = list.OrderBy(m => m.virtualAccountID)
                .Skip(layuiTablePage.GetStartIndex()).Take(layuiTablePage.limit).ToList();
            //机场数据总条数
            int intTotalRow = list.Count();
            //分页返回的数据
            LayuiTableData<TransactionRecordVo> layuiTableData = new LayuiTableData<TransactionRecordVo>
            {
                data = listTransactionRecord,
                count = intTotalRow
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);

        }
    }
}
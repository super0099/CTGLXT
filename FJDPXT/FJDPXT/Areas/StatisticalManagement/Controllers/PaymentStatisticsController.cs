using FJDPXT.EntityClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FJDPXT.Areas.StatisticalManagement.Controllers
{
    public class PaymentStatisticsController : Controller
    {
        // GET: StatisticalManagement/PaymentStatistics
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
        //统计管理-->支付统计
        Models.FJDPXTEntities1 myModel = new Models.FJDPXTEntities1();
        /// <summary>
        /// 1 支付统计页面
        /// </summary>
        /// <returns></returns>
        public ActionResult Index()
        {
            return View();
        }
        /// <summary>
        /// 2 查询支付统计的数据
        /// </summary>
        /// <param name="layuiTablePage">分页</param>
        /// <param name="startEndDate">时间段</param>
        /// <param name="userName">用户名</param>
        /// <param name="account">账号</param>
        /// <param name="transactionType">交易类型</param>
        /// <returns></returns>
        public ActionResult SelectPaymentStatistics(LayuiTablePage layuiTablePage, string startEndDate, string userName, string account, int transactionType)
        {
            var list = from tabTransactionRecord in myModel.B_TransactionRecord//交易记录
                       join tabVirtualAccount in myModel.S_VirtualAccount on tabTransactionRecord.virtualAccountID equals tabVirtualAccount.virtualAccountID//虚拟账户
                       join tabUser in myModel.S_User on tabTransactionRecord.userID equals tabUser.userID//用户
                       select new TransactionRecordVo
                       {
                           transactionRecordID = tabTransactionRecord.transactionRecordID,//交易记录ID
                           userID = tabUser.userID,//用户ID
                           userName = tabUser.userName,//用户姓名
                           account = tabVirtualAccount.account,//账户                         
                           transactionType = tabTransactionRecord.transactionType.Value,//交易类型
                           transactionMoney = tabTransactionRecord.transactionMoney,//交易金额
                           transactionTime = tabTransactionRecord.transactionTime,//交易时间
                           transactionTimeStr = tabTransactionRecord.transactionTime.ToString()//交易时间(字符串)
                       };
            //1、判断是否选择时间段
            if (!string.IsNullOrEmpty(startEndDate))
            {
                startEndDate = startEndDate.Replace(" - ", "~");//切割成两个日期
                string[] strs = startEndDate.Split('~');//根据 " - "分割字符串
                if (strs.Length == 2)
                {
                    DateTime dtStart = Convert.ToDateTime(strs[0]);
                    DateTime dtEnd = Convert.ToDateTime(strs[1]);
                    list = list.Where(o => o.transactionTime >= dtStart && o.transactionTime <= dtEnd);
                }
            }
            //2、用户姓名
            if (!string.IsNullOrEmpty(userName))
            {
                list = list.Where(m => m.userName.Contains(userName));
            }
            //3、账户
            if (!string.IsNullOrEmpty(account))
            {
                list = list.Where(m => m.account.Contains(account));
            }
            //4、交易类型
            if (transactionType >= 0)//交易类型  0：支出，1：收入
            {
                list = list.Where(m => m.transactionType == transactionType);
            }

            //分页查询机场数据
            List<TransactionRecordVo> listTransactionRecord = list.OrderBy(m => m.transactionRecordID)
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
using FJDPXT.EntityClass;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Mvc;

namespace FJDPXT.Areas.StatisticalManagement.Controllers
{
    public class TDQstatisticsController : Controller
    {
        // GET: StatisticalManagement/TDQstatistics
        //统计管理-->运输数据查询统计
        Models.FJDPXTEntities1 myModel = new Models.FJDPXTEntities1();
        private int userID;
        //复写父类的该方法。执行控制器中的方法之前先执行该方法。从而实现过滤的功能。
        protected override void OnActionExecuting(ActionExecutingContext filterContext)
        {
            base.OnActionExecuting(filterContext);
            //验证用户登录
            if (Session["userID"] == null)
            {
                //没有登录，重定向 ,,不会执行后续的方法，而是直接跳转到登录页面
                filterContext.Result = Redirect(@Url.Content("~/Main/Login"));
            }
            else
            {
                userID = (int)Session["userID"];
            }
        }
        public ActionResult Index()
        {
            Session.Remove("startTime");
            Session.Remove("endTime");
            Session.Remove("flightCode");
            Session.Remove("userGroup");
            return View();
        }

        [HttpGet]
        public ActionResult DataPage()
        {
            return View();
        }

        /// <summary>
        /// 传输数据
        /// </summary>
        /// <param name="startTime">开始时间</param>
        /// <param name="endTime">结束时间</param>
        /// <param name="flightCode">航班号</param>
        /// <param name="userGroup">用户组</param>
        /// <returns></returns>
        [HttpPost]
        public ActionResult DataPage(string startTime, string endTime, string flightCode, string userGroup)
        {
            //TempData["startTime"] = startTime; //可以跨方法使用，但是有效值只有一次
            //TempData["endTime"] = endTime;
            //TempData["flightCode"] = flightCode;
            //TempData["userGroup"] = userGroup;
            //ViewBag.startTime = startTime;

            Session["startTime"] = startTime;
            Session["endTime"] = endTime;
            Session["flightCode"] = flightCode;
            Session["userGroup"] = userGroup;
            return View();
        }
        /// <summary>
        /// 数据的查询
        /// </summary>
        /// <param name="layuiTablePage"></param>
        /// <returns></returns>
        public ActionResult SelectTDQstatistics(LayuiTablePage layuiTablePage)
        {
            string UserGroup = Session["userGroup"].ToString(),
                   StartTime = Session["startTime"].ToString(),
                   EndTime = Session["endTime"].ToString(),
                   FlightCode = Session["flightCode"].ToString();
            var list = (from tabETicket in myModel.B_ETicket
                        join tabPNRPassenger in myModel.B_PNRPassenger on tabETicket.PNRPassengerID equals tabPNRPassenger.PNRPassengerID
                        join tabCertificatesType in myModel.S_CertificatesType on tabPNRPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                        join tabCabinType in myModel.S_CabinType on tabETicket.cabinTypeID equals tabCabinType.cabinTypeID
                        join tabUser in myModel.S_User on tabETicket.operatorID equals tabUser.userID
                        join tabUserGroup in myModel.S_UserGroup on tabUser.userGroupID equals tabUserGroup.userGroupID
                        join tabFlight in myModel.S_Flight on tabETicket.flightID equals tabFlight.flightID
                        join tabOrange in myModel.S_Airport on tabFlight.orangeID equals tabOrange.airportID
                        join tabDestination in myModel.S_Airport on tabFlight.destinationID equals tabDestination.airportID
                        select new PaymentStatisticsVo
                        {
                            passengerName = tabPNRPassenger.passengerName,
                            userGroupNumber = tabUserGroup.userGroupNumber,
                            flightCode = tabFlight.flightCode,
                            flightDate = tabFlight.flightDate.ToString(),
                            strDepartureTime = tabFlight.departureTime.ToString(),
                            orangeCityName = tabOrange.cityName,
                            destinationCityName = tabDestination.cityName,
                            cabinTypeCode = tabCabinType.cabinTypeCode,
                            certificatesType = tabCertificatesType.certificatesType,
                            certificatesCode = tabPNRPassenger.certificatesCode,
                            strOperatingTtime = tabETicket.tickingTime.ToString(),
                            operTime = tabETicket.tickingTime
                        }).ToList();
            if (!string.IsNullOrEmpty(UserGroup))
            {
                list = list.Where(m => m.userGroupNumber == UserGroup).ToList();
            }
            if (!string.IsNullOrEmpty(FlightCode))
            {
                list = list.Where(m => m.flightCode == FlightCode).ToList();
            }
            if (!string.IsNullOrEmpty(StartTime))
            {
                var startTime = Convert.ToDateTime(StartTime);
                list = list.Where(m => m.operTime >= startTime).ToList();
            }
            if (!string.IsNullOrEmpty(EndTime))
            {
                var endTime = Convert.ToDateTime(EndTime);
                list = list.Where(m => m.operTime <= endTime).ToList();
            }

            //分页查询机场数据
            List<PaymentStatisticsVo> listPayment = list.OrderBy(m => m.operTime)
                .Skip(layuiTablePage.GetStartIndex()).Take(layuiTablePage.limit).ToList();
            //机场数据总条数
            int intTotalRow = list.Count();
            //分页返回的数据
            LayuiTableData<PaymentStatisticsVo> layuiTableData = new LayuiTableData<PaymentStatisticsVo>
            {
                data = listPayment,
                count = intTotalRow
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
namespace FJDPXT.Areas.StatisticalManagement.Controllers
{
    public class SalesReportStatisticsController : Controller
    {
        // GET: StatisticalManagement/SalesReportStatistics
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
        //统计管理-->销售报告统计
        Models.FJDPXTEntities1 myModel = new Models.FJDPXTEntities1();
        public ActionResult Index()
        {
            //下拉框数据查询--机型
            List<S_PlanType> listPlanType = (from tabPlanType in myModel.S_PlanType
                                             select tabPlanType).ToList();
            //下拉框数据查询--机场
            List<S_Airport> listAirport = (from tabAirport in myModel.S_Airport
                                           select tabAirport).ToList();
            //数据传递到视图层
            ViewBag.planTypeInfors = listPlanType;
            ViewBag.airportInfors = listAirport;
            return View();
        }/// <summary>
         /// 航班信息
         /// </summary>
         /// <param name="layuiTablePage">分页实体</param>
         /// <param name="flightCode">航班号</param>
         /// <param name="planTypeID">机型ID</param>
         /// <param name="flightDateStr">时间段</param>
         /// <param name="orangeId">出发城市ID</param>
         /// <param name="destinationId">到达城市ID</param>
         /// <returns></returns>
         public ActionResult seleteFlights(LayuiTablePage layuiTablePage,string flightCode,int planTypeID,string startEndDate,int orangeId,int destinationId)
        {
            var listFlight = from tabFlight in myModel.S_Flight//航班
                             join tabAirportOrange in myModel.S_Airport on tabFlight.orangeID equals tabAirportOrange.airportID//起飞机场
                             join tabAirportDestination in myModel.S_Airport on tabFlight.destinationID equals tabAirportDestination.airportID//降落机场
                             join tabPlanType in myModel.S_PlanType on tabFlight.planTypeID equals tabPlanType.planTypeID//机型
                             orderby tabFlight.flightID descending//倒序
                             select new ETicketVo
                             {
                                 flightID = tabFlight.flightID,
                                 planTypeID = tabPlanType.planTypeID,
                                 flightDate = tabFlight.flightDate,
                                 orangerID = tabAirportOrange.airportID,
                                 destinationID = tabAirportDestination.airportID,

                                 flightCode = tabFlight.flightCode,//航班号  
                                 planTypeName = tabPlanType.planTypeName,//机型名称
                                 flightDateStr = tabFlight.flightDate.ToString(),//起飞日期                        
                                 departureTime = tabFlight.departureTime.ToString(),//起飞时间  
                                 orangeName = tabAirportOrange.airportName + tabAirportOrange.airportCode,//出发城市
                                 destinationName = tabAirportDestination.airportName + tabAirportDestination.airportCode,//到达城市                   
                             };
            //航班号
            if (!string.IsNullOrEmpty(flightCode))
            {
                listFlight = listFlight.Where(o => o.flightCode.Contains(flightCode));
            }
            //机型
            if (planTypeID > 0)
            {
                listFlight = listFlight.Where(m => m.planTypeID == planTypeID);
            }
            //判断是否选择时间段
            if (!string.IsNullOrEmpty(startEndDate))
            {
                startEndDate = startEndDate.Replace(" - ", "~");
                string[] strs = startEndDate.Split('~');
                if (strs.Length == 2)
                {
                    DateTime dtStart = Convert.ToDateTime(strs[0]);
                    DateTime dtEnd = Convert.ToDateTime(strs[1]);
                    listFlight = listFlight.Where(o => o.flightDate >= dtStart &&o.flightDate<= dtEnd);
                }
            }
            //起飞机场ID
            if (orangeId > 0)
            {
                listFlight = listFlight.Where(o => o.orangerID == orangeId);
            }
            //到达机场ID
            if (destinationId > 0)
            {
                listFlight = listFlight.Where(o => o.destinationID == destinationId);
            }
            //获取总数
            var intTotalRow = listFlight.Count();

            //分页
            List<ETicketVo> list = listFlight
                                   .Skip(layuiTablePage.GetStartIndex())
                                   .Take(layuiTablePage.limit)
                                   .ToList();

            //构建返回的数据
            LayuiTableData<ETicketVo> layuiTableData = new LayuiTableData<ETicketVo>()
            {
                data= list,
                count= intTotalRow
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        /// <summary>
        /// 根据航班ID查询电子客票信息
        /// </summary>
        /// <param name="layuiTablePage">分页</param>
        /// <param name="flightID">航班ID</param>
        /// <returns></returns>
        public ActionResult selectOrderByFlightId(LayuiTablePage layuiTablePage, int flightID)
        {
            var listETicket = from tabPNRTicket in myModel.B_PNRTicketing
                              join tabSegment in myModel.B_PNRSegment on tabPNRTicket.PNRSegmentID equals tabSegment.PNRSegmentID
                              join tabFlight in myModel.S_Flight on tabSegment.flightID equals tabFlight.flightID
                              join tabOrange in myModel.S_Airport on tabFlight.orangeID equals tabOrange.airportID
                              join tabDestination in myModel.S_Airport on tabFlight.destinationID equals tabDestination.airportID
                              join tabFlightCabin in myModel.S_FlightCabin on tabSegment.flightCabinID equals tabFlightCabin.flightCabinID
                              join tabCabinType in myModel.S_CabinType on tabSegment.cabinTypeID equals tabCabinType.cabinTypeID
                              join tabPassenger in myModel.B_PNRPassenger on tabPNRTicket.PNRPassengerID equals tabPassenger.PNRPassengerID
                              join tabOrder in myModel.B_Order on tabPNRTicket.orderID equals tabOrder.orderID
                              join tabPNR in myModel.B_PNR on tabPNRTicket.PNRID equals tabPNR.PNRID
                              join tabETicket in myModel.B_ETicket on tabPNRTicket.ETicketID equals tabETicket.ETicketID
                              join tabUser in myModel.S_User on tabETicket.operatorID equals tabUser.userID
                              join tabETicketStatus in myModel.S_ETicketStatus on tabETicket.eTicketStatusID equals tabETicketStatus.eTicketStatusID
                              join tabInvoiceStatus in myModel.S_InvoiceStatus on tabETicket.invoiceStatusID equals tabInvoiceStatus.invoiceStatusID
                              orderby tabETicket.ETicketID descending
                              where tabFlight.flightID == flightID
                              select new ETicketVo
                              {
                                  ETicketID = tabETicket.ETicketID,
                                  PNRSegmentID = tabSegment.PNRSegmentID,//航段ID
                                  ticketNo = tabETicket.ticketNo,//票号 
                                  PNRNo = tabPNR.PNRNo,//PNR编号      
                                  orderNo = tabOrder.orderNo,//订单编号 
                                  passengerNo = tabPassenger.passengerNo.Value,//旅客编号                                     
                                  passengerName = tabPassenger.passengerName,//旅客姓名                                                              
                                  cabinTypeName = tabCabinType.cabinTypeName,//舱位等级
                                  ticketPrice = tabETicket.ticketPrice,//票价
                                  totalPrice = tabOrder.totalPrice.Value,//总价
                                  agencyFee = tabOrder.agencyFee.Value,//代理费
                                  payMoney = tabOrder.payMoney.Value,//实付金额                                  
                                  eTicketStatus = tabETicketStatus.eTicketStatusName,//票联状态
                                  EnglishName = tabETicketStatus.EnglishName,//票联状态
                                  invoiceStatus = tabInvoiceStatus.invoiceStatus,//发票状态                                 
                                  TicketingTime = tabETicket.tickingTime.ToString(),//出票时间
                                  userName = tabUser.userName,//营业员
                              };
            //获取总数
            var intTotalRow = listETicket.Count();
            List<ETicketVo> list = listETicket
                                      .Skip(layuiTablePage.GetStartIndex())
                                      .Take(layuiTablePage.limit)
                                      .ToList();

            //调用分页封装类
            LayuiTableData<ETicketVo> layuiTableData = new LayuiTableData<ETicketVo>();
            layuiTableData.count = intTotalRow;
            layuiTableData.data = list;
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
    }
}
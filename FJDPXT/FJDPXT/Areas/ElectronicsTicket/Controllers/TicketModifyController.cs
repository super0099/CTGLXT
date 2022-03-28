using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Transactions;

namespace FJDPXT.Areas.ElectronicsTicket.Controllers
{
    public class TicketModifyController : Controller
    {
        // GET: ElectronicsTicket/TicketModify      

        FJDPXTEntities1 myModels = new FJDPXTEntities1();

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
        #region 票证数据页面
        public ActionResult TicketModify()
        {
            //下拉框机场名称查询
            List<S_Airport> dbAirport = (from tabAirport in myModels.S_Airport
                                         select tabAirport).ToList();

            //下拉框数据查询，证件类型
            List<S_CertificatesType> dbCertificatesType = (from tabCertificatesType in myModels.S_CertificatesType
                                                           select tabCertificatesType).ToList();

            ViewBag.dbAirport = dbAirport;
            ViewBag.dbCertificatesType = dbCertificatesType;
            return View();
        }
        #endregion

        #region 数据查询页面
        public ActionResult SelectTickets(LayuiTablePage layuiTablePage,string flightDate,int? orangeAirportId,int? destinationAirportId
                                          ,string PNRNo, string ticketNo,string flightCode,string StarTime, string EndTime,int? certificatesTypeID,string certificatesCode)
        {
            //数据查询
            var listTicket = from tbPNRTicket in myModels.B_PNRTicketing
                             join tbETicket in myModels.B_ETicket on tbPNRTicket.ETicketID equals tbETicket.ETicketID
                             join tbUser in myModels.S_User on tbETicket.operatorID equals tbUser.userID
                             join tbPNR in myModels.B_PNR on tbPNRTicket.PNRID equals tbPNR.PNRID
                             join tbPNRPassenger in myModels.B_PNRPassenger on tbPNRTicket.PNRPassengerID equals tbPNRPassenger.PNRPassengerID
                             join tbPassengerType in myModels.S_PassengerType on tbPNRPassenger.passengerTypeID equals tbPassengerType.passengerTypeID
                             join tbFlight in myModels.S_Flight on tbETicket.flightID equals tbFlight.flightID
                             join tbOrange in myModels.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                             join tbDestination in myModels.S_Airport on tbFlight.destinationID equals tbDestination.airportID
                             join tbCabinType in myModels.S_CabinType on tbETicket.cabinTypeID equals tbCabinType.cabinTypeID
                             where tbFlight.flightDate <= DateTime.Now|| tbFlight.flightDate >= DateTime.Now
                             select new ETicketVo
                             {
                                 ticketNo = tbETicket.ticketNo,//票号
                                 flightCode = tbFlight.flightCode,//航班号
                                 PNRNo = tbPNR.PNRNo,//PNR编号
                                 TicketingTime = tbPNRTicket.TicketingTime.ToString(),//出票时间
                                 JobNumber = tbUser.jobNumber,//工号
                                 cabinTypeName = tbCabinType.cabinTypeName,//舱位等级
                                 passengerName = tbPNRPassenger.passengerName,//旅客姓名
                                 certificatesCode = tbPNRPassenger.certificatesCode,//证件号
                                 passengerType = tbPassengerType.passengerType,//旅客类型
                                 ETicketID = tbETicket.ETicketID,//电子客票ID 
                                 //多条件查询
                                 flightDate = tbFlight.flightDate,//航班日期
                                 orangerID = tbOrange.airportID,//起飞机场ID
                                 destinationID = tbDestination.airportID,//到达机场ID
                                 certificatesTypeID = tbPNRPassenger.certificatesTypeID.Value,//证件类型ID
                                 PNRID = tbPNR.PNRID
                             };
            //数据验证，查询
            //1日期
            if (!string.IsNullOrEmpty(flightDate))
            {
                DateTime dtFlightDate = Convert.ToDateTime(flightDate);
                listTicket = listTicket.Where(o => o.flightDate == dtFlightDate);
            }
            //1起飞机场
            if (orangeAirportId > 0)
            {
                listTicket = listTicket.Where(o => o.orangerID == orangeAirportId);

            }
            //1降落机场
            if (destinationAirportId > 0)
            {
                listTicket = listTicket.Where(o => o.destinationID == destinationAirportId);
            }
            //2PNR编号
            if (!string.IsNullOrEmpty(PNRNo))
            {
                listTicket = listTicket.Where(o => o.PNRNo.Equals(PNRNo));
            }
            //3票号
            if (!string.IsNullOrEmpty(ticketNo))
            {
                ticketNo = "E781-" + ticketNo;
                listTicket = listTicket.Where(o => o.ticketNo.Equals(ticketNo));
            }
            //4航班号
            if (!string.IsNullOrEmpty(flightCode))
            {
                flightCode = flightCode.ToUpper();
                listTicket = listTicket.Where(o => o.flightCode.Equals(flightCode));
            }
            //4起飞时间
            if (!string.IsNullOrEmpty(StarTime))
            {
                DateTime dbStarTime = Convert.ToDateTime(StarTime);
                listTicket = listTicket.Where(o => o.flightDate == dbStarTime);
            }
            //4降落时间
            if (!string.IsNullOrEmpty(EndTime))
            {
                DateTime dbEndTime = Convert.ToDateTime(EndTime);
                listTicket = listTicket.Where(o => o.flightDate == dbEndTime);
            }
            //5证件类型
            if (certificatesTypeID > 0)
            {
                listTicket = listTicket.Where(o => o.certificatesTypeID == certificatesTypeID);
            }
            //证件号码
            if (!string.IsNullOrEmpty(certificatesCode))
            {
                listTicket = listTicket.Where(o => o.certificatesCode.Equals(certificatesCode));
            }
            //计算总条数
            int totalCount = listTicket.Count();
            //分页
            List<ETicketVo> dtTicket = listTicket
                                      .OrderByDescending(o => o.ETicketID)
                                      .Skip(layuiTablePage.GetStartIndex())
                                      .Take(layuiTablePage.limit)
                                      .ToList();
            //构建返回数据
            LayuiTableData<ETicketVo> layuiTableData = new LayuiTableData<ETicketVo>()
            {
                count = totalCount,
                data = dtTicket
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 票号查询
        public ActionResult showTicketInfor(int ETicketID)
        {
            ETicketVo ticketInfor = (from tbPNRTicket in myModels.B_PNRTicketing
                                     join tbETicket in myModels.B_ETicket on tbPNRTicket.ETicketID equals tbETicket.ETicketID
                                     join tbFlight in myModels.S_Flight on tbETicket.flightID equals tbFlight.flightID
                                     join tbOrange in myModels.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                                     join tbDestination in myModels.S_Airport on tbFlight.destinationID equals tbDestination.airportID
                                     join tbCabinType in myModels.S_CabinType on tbETicket.cabinTypeID equals tbCabinType.cabinTypeID
                                     join tbPassenger in myModels.B_PNRPassenger on tbPNRTicket.PNRPassengerID equals tbPassenger.PNRPassengerID
                                     join tbUser in myModels.S_User on tbETicket.operatorID equals tbUser.userID
                                     join tbETicketStatus in myModels.S_ETicketStatus on tbETicket.eTicketStatusID equals tbETicketStatus.eTicketStatusID
                                     join tbInvoiceStatus in myModels.S_InvoiceStatus on tbETicket.invoiceStatusID equals tbInvoiceStatus.invoiceStatusID
                                     //航班更改
                                     join tbPNR in myModels.B_PNR on tbPNRTicket.PNRID equals tbPNR.PNRID
                                     join tbSegment in myModels.B_PNRSegment on tbPNRTicket.PNRSegmentID equals tbSegment.PNRSegmentID
                                     orderby tbETicket.ETicketID descending
                                     where tbETicket.ETicketID == ETicketID
                                     select new ETicketVo
                                     {
                                         ETicketID = tbETicket.ETicketID,
                                         orangeCity = tbOrange.cityName,//出发城市
                                         destinationCity = tbDestination.cityName,//到达城市
                                         orangeName = tbOrange.airportName,//出发机场
                                         destinationName = tbDestination.airportName,//到达机场
                                         TicketingTime = tbETicket.tickingTime.ToString(),//出票时间
                                         passengerName = tbPassenger.passengerName,//旅客姓名
                                         passengerNo = tbPassenger.passengerNo.Value,//旅客编号
                                         userName = tbUser.userName,//营业员
                                         flightCode = tbFlight.flightCode,//航班号
                                         cabinTypeName = tbCabinType.cabinTypeName,//舱位等级
                                         flightDateStr = tbFlight.flightDate.ToString(),//航班日期
                                         departureTime = tbFlight.departureTime.ToString(),//出发时间
                                         ticketPrice = tbETicket.ticketPrice,//票价
                                         ticketNo = tbETicket.ticketNo,//票号
                                         eTicketStatus = tbETicketStatus.eTicketStatusName,//票联状态
                                         EnglishName = tbETicketStatus.EnglishName,//票联状态
                                         invoiceStatus = tbInvoiceStatus.invoiceStatus,//发票状态
                                         //航班更改
                                         PNRID = tbPNR.PNRID,
                                         PNRSegmentID = tbSegment.PNRSegmentID,
                                         PNRTicketingID = tbPNRTicket.PNRTicketingID
                                     }).SingleOrDefault();

            //生成作废单号
            DateTime dtToday = DateTime.Now.Date;
            //明天
            DateTime dtNextDay = dtToday.AddDays(1);
            int countNum = (from dbEticketInvalid in myModels.B_ETicketInvalid
                            where dbEticketInvalid.invalidTime > dtToday && dbEticketInvalid.invalidTime < dtNextDay
                            select dbEticketInvalid).Count();
            //作废单
            string InvalidCode = "ZF-" + dtToday.ToString("yyyyMMdd") + (countNum + 1).ToString("0000");
            ViewBag.InvalidCode = InvalidCode;

            ViewBag.ticketInfor = ticketInfor;

            return View();
        }
        #endregion

        #region T4联打印
        public ActionResult doPrint(int eTicketID)
        {
            //只需要返回invoiceStatusID，所以用Single，查询多条List
            ReturnJson msg = new ReturnJson();
            try
            {
                B_ETicket dbETicket = myModels.B_ETicket.Single(o => o.ETicketID == eTicketID);

                dbETicket.invoiceStatusID = 2;
                myModels.Entry(dbETicket).State = System.Data.Entity.EntityState.Modified;
                if (myModels.SaveChanges() > 0)
                {
                    msg.State = true;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "数据异常,打印失败";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 确认修改
        public ActionResult doPrintChange(int eTicketID)
        {
            ReturnJson msg = new ReturnJson();

            using(TransactionScope scope = new TransactionScope())
            {
                try
                {
                    B_ETicket dbETicket = myModels.B_ETicket.Single(o => o.ETicketID == eTicketID);

                    //票号生成，查询出可以使用票号的票号段
                    List<S_Ticket> ticketInfor = (from tbTicket in myModels.S_Ticket
                                                  where tbTicket.userID == loginUserID && tbTicket.currentTicketNo.CompareTo(tbTicket.endTicketNo) <= 0//当前票号小于结束票号
                                                  select tbTicket).ToList();

                    //获取第一个可用的票号段
                    S_Ticket ticket = ticketInfor[0];
                    //创建票号
                    string ticketNo = "";
                    //获取当前票号
                    ticketNo = ticket.currentTicketNo;
                    //将票号转成整数
                    int curTicketNo = Convert.ToInt32(ticket.currentTicketNo);
                    //当前票号加一等于新的票号
                    int nextTicketNo = curTicketNo + 1;
                    //转成字符串
                    string strNextTicketNo = nextTicketNo.ToString("0000000000");
                    //把新的票号赋值给当前票号
                    ticket.currentTicketNo = strNextTicketNo;
                    myModels.Entry(ticket).State = System.Data.Entity.EntityState.Modified;
                    myModels.SaveChanges();
                    //修改电子客票表
                    dbETicket.eTicketStatusID = 6;//6是打印换开状态
                    dbETicket.ticketNo = "E781-" + ticketNo;
                    myModels.Entry(dbETicket).State = System.Data.Entity.EntityState.Modified;
                    if (myModels.SaveChanges() > 0)
                    {
                        scope.Complete();
                        msg.State = true;
                        msg.Object = dbETicket.ticketNo;//把当前票号信息返回到页面作为提示
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "数据异常";
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 更改航班
        //根据flightCabinID航班舱位ID去查询出需要更改的航班信息
        //
        //
        public ActionResult doChangeFlight(int flightCabinID, int PNRID, int PNRSegmentID, int eTicketID,
             int PNRTicketingID, int voluntaryType, string strChangeReason)
        {
            ReturnJson rtMsg = new ReturnJson();
            try
            {
                //查询出当前更改的航班信息
                S_FlightCabin flightCabin = myModels.S_FlightCabin.Single(f => f.flightCabinID == flightCabinID);

                S_Flight flightInfor = myModels.S_Flight.Single(f => f.flightID == flightCabin.flightID);

                //查询出修改前的航段所选航班舱位&航班信息
                B_PNRSegment dbSegment = myModels.B_PNRSegment.Single(s => s.PNRSegmentID == PNRSegmentID);
                int dbFlightID = dbSegment.flightID.Value;
                S_Flight oldFlight = myModels.S_Flight.Single(m => m.flightID == dbFlightID);

                //查询原出票组表数据
                B_PNRTicketing dbPNRTicketing = myModels.B_PNRTicketing.Single(m => m.PNRTicketingID == PNRTicketingID);

                //判断是否与原航班信息一样，是则不需要更改
                if (dbFlightID == flightInfor.flightID && oldFlight.flightDate == flightInfor.flightDate)
                {
                    rtMsg.Text = "选择的航班与原来的航班一样，不需要修改！";
                    return Json(rtMsg, JsonRequestBehavior.AllowGet);
                }

                //查询出当前PNR中所有的航段航班信息，根据航班时间排序
                List<S_Flight> dbFlightInfors = (from tbSegment in myModels.B_PNRSegment
                                                 join tbFlight in myModels.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                 orderby tbFlight.flightDate, tbFlight.departureTime
                                                 where tbSegment.PNRID == PNRID && tbSegment.PNRSegmentID != PNRSegmentID && tbSegment.invalid == true
                                                 select tbFlight).ToList();

                //遍历数据库中当前PNR中所有的航段航班信息
                foreach (S_Flight dbFlight in dbFlightInfors)
                {
                    if (dbFlight.flightDate == flightInfor.flightDate)
                    {
                        //不允许选择同一天的同一个航班
                        if (dbFlight.flightCode == flightInfor.flightCode)
                        {
                            rtMsg.Text = "与当前PNR中的航班重复，请重新选择！";
                            return Json(rtMsg, JsonRequestBehavior.AllowGet);
                        }
                        //同一天的航班,如果到达时间<更改航班的起飞时间，判断时间间隔
                        if (dbFlight.arrivalTime < flightInfor.departureTime)
                        {
                            //(到达时间+60分钟)>更改航班的出发时间
                            if ((dbFlight.arrivalTime.Value.TotalMinutes + 60) > flightInfor.departureTime.Value.TotalMinutes)
                            {
                                rtMsg.Text = "出发时间与当前PNR中前一段航班间隔时间太短，请重新选择！";
                                return Json(rtMsg, JsonRequestBehavior.AllowGet);
                            }
                        }
                        //同一天的航班，如果起飞时间>更改航班的到达时间，判断时间间隔
                        if (flightInfor.arrivalTime < dbFlight.departureTime)
                        {
                            //（更改航班到达时间+60）> 起飞时间
                            if ((flightInfor.arrivalTime.Value.TotalMinutes + 60) > dbFlight.departureTime.Value.TotalMinutes)
                            {
                                rtMsg.Text = "到达时间与当前PNR中下一段航班间隔时间太短，请重新选择！";
                                return Json(rtMsg, JsonRequestBehavior.AllowGet);
                            }
                        }
                        //同一天的航班，更改的航班是否与原有航班时间重叠
                        if ((flightInfor.departureTime >= dbFlight.departureTime && flightInfor.departureTime <= dbFlight.arrivalTime) ||
                            (flightInfor.arrivalTime >= dbFlight.departureTime && flightInfor.arrivalTime <= dbFlight.arrivalTime) ||
                            (flightInfor.departureTime <= dbFlight.departureTime && flightInfor.arrivalTime >= dbFlight.arrivalTime))
                        {
                            rtMsg.Text = "选择的航班与原有航班时间冲突，请重新选择！";
                            return Json(rtMsg, JsonRequestBehavior.AllowGet);
                        }

                    }
                }

                //修改航班
                //开启事务
                using (TransactionScope scope = new TransactionScope())
                {
                    //1.查询出原航段对应的航班舱位信息
                    S_FlightCabin dbFlightCabin = myModels.S_FlightCabin.Single(f => f.flightCabinID == dbSegment.flightCabinID);
                    //释放座位
                    dbFlightCabin.sellSeatNum = dbFlightCabin.sellSeatNum - 1;
                    //保存修改
                    myModels.Entry(dbFlightCabin).State = System.Data.Entity.EntityState.Modified;
                    myModels.SaveChanges();

                    //2.原航段表数据作废
                    dbSegment.segmentType = 2;//航段类型：电子客票变更产生航段
                    dbSegment.invalid = false;
                    myModels.Entry(dbSegment).State = System.Data.Entity.EntityState.Modified;
                    myModels.SaveChanges();
                    //获取原航段数据
                    int? oldSegmentNo = dbSegment.segmentNo;
                    int? oldBookSeatInfo = dbSegment.bookSeatInfo;


                    //3.修改原电子客票表
                    //查询出对应的电子客票
                    B_ETicket dbETicket = myModels.B_ETicket.Single(e => e.ETicketID == eTicketID);
                    //修改发票状态
                    dbETicket.invoiceStatusID = 5;//发票状态改为5-变更结果
                    //保存修改
                    myModels.Entry(dbETicket).State = System.Data.Entity.EntityState.Modified;
                    myModels.SaveChanges();

                    //4.新增航段表
                    B_PNRSegment newSegment = new B_PNRSegment()
                    {
                        PNRID = PNRID,//PNRID
                        segmentNo = oldSegmentNo,//航段编号，与原航段编号一致
                        flightID = flightCabin.flightID,//更改后的航班ID
                        cabinTypeID = flightCabin.cabinTypeID,//更改后的舱位等级ID
                        flightCabinID = flightCabin.flightCabinID,//更改后的航班舱位ID
                        bookSeatNum = 1,//订座数
                        bookSeatInfo = oldBookSeatInfo,//定座情况
                        segmentType = 0,
                        invalid = true
                    };
                    //保存新增
                    myModels.B_PNRSegment.Add(newSegment);
                    myModels.SaveChanges();
                    //获取新航段ID
                    int newSegmentID = newSegment.PNRSegmentID;

                    //5.为当前PNR有效航段重新编排航段号
                    List<B_PNRSegment> newSegmentList = (from tbSegment in myModels.B_PNRSegment
                                                         join tbFlight in myModels.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                         where tbSegment.PNRID == PNRID && tbSegment.invalid == true
                                                         orderby tbFlight.flightDate, tbFlight.departureTime
                                                         select tbSegment).ToList();

                    //重新编排航段编号
                    for (int i = 0; i < newSegmentList.Count; i++)
                    {
                        int segmentId = newSegmentList[i].PNRSegmentID;
                        //查询出航段信息
                        B_PNRSegment segment = myModels.B_PNRSegment.Single(m => m.PNRSegmentID == segmentId);
                        if (segment != null)
                        {
                            segment.segmentNo = i + 1;
                            //保存修改
                            myModels.Entry(segment).State = System.Data.Entity.EntityState.Modified;
                            myModels.SaveChanges();
                        }
                    }

                    //6.为修改后的PNR航段做出票操作
                    //查询当前用户的虚拟账户信息，判断余额是否充足
                    S_VirtualAccount dbVirtualAccount = myModels.S_VirtualAccount.Single(n => n.userID == loginUserID);

                    if (dbVirtualAccount.accountBalance >= flightCabin.cabinPrice)
                    {
                        //6.1 生成订单
                        //生成订单号（查询当天已生成的订单，编号+1）
                        DateTime dtToday = DateTime.Now.Date;
                        DateTime dtNextDay = dtToday.AddDays(1);
                        int nowOrderNum = (from tbOrder in myModels.B_Order
                                           where tbOrder.payTime >= dtToday && tbOrder.payTime < dtNextDay
                                           select tbOrder).Count();

                        string orderNo = "781" + dtToday.ToString("yyyyMMdd") + (nowOrderNum + 1).ToString("00000");

                        decimal? agencyFee = flightCabin.cabinPrice * Convert.ToDecimal(0.03);//代理费
                        decimal? payMoney = flightCabin.cabinPrice - agencyFee;//支付价格

                        //生成订单数据
                        B_Order order = new B_Order()
                        {
                            orderNo = orderNo,
                            PNRID = PNRID,
                            operatorID = loginUserID,
                            totalPrice = flightCabin.cabinPrice,
                            agencyFee = agencyFee,
                            payMoney = payMoney,
                            orderStatus = 2,
                            payTime = DateTime.Now,
                        };
                        //保存到数据库
                        myModels.B_Order.Add(order);
                        myModels.SaveChanges();
                        //获取新订单ID
                        int newOrderID = order.orderID;

                        //6.2 为新增电子客票表，生成票号
                        string ticketNo = "";
                        //查询出当前用户的可用票号数
                        List<S_Ticket> dbTicket = (from tbTicket in myModels.S_Ticket
                                                   where tbTicket.userID == loginUserID && tbTicket.currentTicketNo.CompareTo(tbTicket.endTicketNo) <= 0
                                                   select tbTicket).ToList();

                        //获取第一个可用票号段的票号信息
                        S_Ticket ticket = dbTicket[0];

                        ticketNo = ticket.currentTicketNo;
                        //计算下一个票号
                        int curTicketNo = Convert.ToInt32(ticket.currentTicketNo);
                        int nextTicketNo = curTicketNo + 1;
                        string strNextTicketNo = nextTicketNo.ToString("0000000000");
                        //修改票号表
                        ticket.currentTicketNo = strNextTicketNo;
                        myModels.Entry(ticket).State = System.Data.Entity.EntityState.Modified;
                        myModels.SaveChanges();

                        DateTime dtNow = DateTime.Now;
                        B_ETicket newTIcket = new B_ETicket()
                        {
                            PNRPassengerID = dbPNRTicketing.PNRPassengerID,
                            oderID = newOrderID,
                            flightID = flightCabin.flightID,
                            ticketNo = "E781-" + ticketNo,
                            tickingTime = dtNow,
                            ticketPrice = flightCabin.cabinPrice,
                            payTypeID = 1,
                            operatorID = loginUserID,
                            operatingTtime = dtNow,
                            eTicketStatusID = 1,
                            invoiceStatusID = 1,
                            cabinTypeID = flightCabin.cabinTypeID,
                            flightCabinID = flightCabin.flightCabinID
                        };
                        //保存新增
                        myModels.B_ETicket.Add(newTIcket);
                        myModels.SaveChanges();
                        //获取新电子客票ID
                        int newETicketID = newTIcket.ETicketID;

                        //6.4 新增出票组表
                        B_PNRTicketing pnrTicket = new B_PNRTicketing()
                        {
                            PNRID = PNRID,
                            PNRPassengerID = dbPNRTicketing.PNRPassengerID,
                            PNRSegmentID = newSegmentID,
                            ETicketID = newETicketID,
                            TicketingTime = dtNow,
                            orderID = newOrderID
                        };
                        //保存到数据库
                        myModels.B_PNRTicketing.Add(pnrTicket);
                        myModels.SaveChanges();

                        //6.5 修改当前用户虚拟账户余额(退还更改前航班的费用，支付更改后航班费用)
                        dbVirtualAccount.accountBalance = dbVirtualAccount.accountBalance + dbETicket.ticketPrice - payMoney;
                        //保存修改
                        myModels.Entry(dbVirtualAccount).State = System.Data.Entity.EntityState.Modified;
                        myModels.SaveChanges();

                        //6.6 新增虚拟账户的交易记录(退款记录+支付记录)
                        List<B_TransactionRecord> listTransactionRecord = new List<B_TransactionRecord>();
                        //退款记录
                        listTransactionRecord.Add(new B_TransactionRecord()
                        {
                            virtualAccountID = dbVirtualAccount.virtualAccountID,
                            transactionType = 1,
                            transactionMoney = dbETicket.ticketPrice,
                            transactionTime = DateTime.Now,
                            userID = loginUserID
                        });
                        //支付记录
                        listTransactionRecord.Add(new B_TransactionRecord()
                        {
                            virtualAccountID = dbVirtualAccount.virtualAccountID,
                            transactionType = 0,
                            transactionMoney = payMoney,
                            transactionTime = DateTime.Now,
                            userID = loginUserID
                        });
                        myModels.B_TransactionRecord.AddRange(listTransactionRecord);
                        myModels.SaveChanges();

                        //7.新增航班变更记录表
                        B_FlightChange newFlightChange = new B_FlightChange()
                        {
                            ETicketID = eTicketID,
                            oldPNRSegmentID = PNRSegmentID,
                            newPNRSegmentID = newSegmentID,
                            operatorID = loginUserID,
                            operatingTtime = DateTime.Now,
                            isVoluntary = voluntaryType == 1 ? true : false,
                            changeReason = strChangeReason
                        };
                        //保存数据
                        myModels.B_FlightChange.Add(newFlightChange);
                        myModels.SaveChanges();

                        //提交事务
                        scope.Complete();
                        //返回数据
                        rtMsg.State = true;
                        rtMsg.Text = "航班更改成功！";
                    }
                    else
                    {
                        rtMsg.Text = "当前用户余额不足，无法出票，航班更改失败！";
                        return Json(rtMsg, JsonRequestBehavior.AllowGet);
                    }

                }
            }
            catch (Exception e)
            {
                Console.Write(e);
                rtMsg.Text = "数据异常";
            }

            return Json(rtMsg, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 作废
        public ActionResult Invalid(int eTicketID,decimal Iprice,decimal decAgencyFee,decimal decActualRefunfMoney,string strInvalidCode)
        {
            ReturnJson msg = new ReturnJson();
            //1.将原电子客票表数据作废
            //2.新增电子客票作废表数据
            //3.作废后，退款到虚拟账户
            //4.退费后，为当前虚拟账户新增交易记录
            try
            {
                using(TransactionScope scope = new TransactionScope())
                {
                    //1.查询出原电子客票，修改票证状态
                    B_ETicket dbETicket = (from tabEticket in myModels.B_ETicket
                                           where tabEticket.ETicketID == eTicketID
                                           select tabEticket).Single();

                    dbETicket.eTicketStatusID = 4;
                    myModels.Entry(dbETicket).State = System.Data.Entity.EntityState.Modified;
                    if (myModels.SaveChanges() > 0)
                    {
                        B_ETicketInvalid dbETicketInvalid = new B_ETicketInvalid()
                        {
                            ETicketID = eTicketID,
                            agencyFee = decAgencyFee,
                            actualRefunfMoney = decActualRefunfMoney,
                            InvalidCode = strInvalidCode,
                            operatorID = loginUserID,
                            invalidTime = DateTime.Now
                        };
                        myModels.B_ETicketInvalid.Add(dbETicketInvalid);
                        myModels.SaveChanges();

                        //3.修改虚拟账户余额(+支付金额，代理费为代理商退款给用户时扣除的费用，当前系统使用者为代理商)
                        S_VirtualAccount dbVirtualAccount = (from tabVirtualAccount in myModels.S_VirtualAccount
                                                             where tabVirtualAccount.userID == loginUserID
                                                             select tabVirtualAccount).Single();

                        //
                        dbVirtualAccount.accountBalance += Iprice;
                        myModels.Entry(dbVirtualAccount).State = System.Data.Entity.EntityState.Modified;
                        myModels.SaveChanges();

                        //新增交易记录
                        B_TransactionRecord dbTransactionRecord = new B_TransactionRecord()
                        {
                            virtualAccountID = dbVirtualAccount.virtualAccountID,//虚拟账户ID
                            transactionType = 1,//交易类型-收入
                            transactionMoney = Iprice,//交易金额
                            transactionTime = DateTime.Now,//交易时间
                            userID = loginUserID//用户ID
                        };

                        myModels.B_TransactionRecord.Add(dbTransactionRecord);
                        myModels.SaveChanges();
                        //提交事务
                        scope.Complete();
                        //返回数据
                        msg.State = true;
                        msg.Text = "作废成功！";
                    }
                    else
                    {
                        msg.Text = "作废失败！";
                    }
                }
            }
            catch (Exception e)
            {
                Console.Write(e);
                msg.Text = "数据异常，作废失败！";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 退票
        public ActionResult Refund(int eTicketID, decimal decRefundMoney, decimal decRefundAgencyFee,
            decimal decActualRefunfMoney,int isVoluntary,string strChangeReason)
        {
            ReturnJson msg = new ReturnJson();
            //1.将原电子客票表客票状态改为退票
            //2.新增电子客票退票表数据
            //3.退票后，退款到虚拟账户
            //4.退费后，为当前虚拟账户新增交易记录
            try
            {
                using(TransactionScope scope = new TransactionScope())
                {
                    B_ETicket dbETicket = (from tabETicket in myModels.B_ETicket
                                           where tabETicket.ETicketID == eTicketID
                                           select tabETicket).Single();

                    dbETicket.eTicketStatusID = 3;//票联状态ID(1：可供使用 2：打印换开  3：退票  4:已作废 )
                    myModels.Entry(dbETicket).State = System.Data.Entity.EntityState.Modified;
                    if (myModels.SaveChanges() > 0)
                    {
                        //2.新增退票表数据
                        bool Voluntary = isVoluntary == 1 ? true : false;
                        B_TicketRefund dbTicketRefund = new B_TicketRefund()
                        {
                            ETicketID=eTicketID,
                            refundMoney= decRefundMoney,
                            refundAgencyFee= decRefundAgencyFee,
                            actualRefunfMoney= decActualRefunfMoney,
                            operatorID = loginUserID,
                            operatingTtime=DateTime.Now,
                            isVoluntary = Voluntary,
                            changeReason = strChangeReason
                        };
                        myModels.B_TicketRefund.Add(dbTicketRefund);
                        myModels.SaveChanges();

                        //3.修改虚拟账户余额(+退款金额，代理费为代理商退款给用户时扣除的费用，当前系统使用者为代理商)
                        S_VirtualAccount dbVirtualAccount = (from tabVirtualAccount in myModels.S_VirtualAccount
                                                             where tabVirtualAccount.userID == loginUserID
                                                             select tabVirtualAccount).Single();

                        dbVirtualAccount.accountBalance += decRefundMoney;
                        myModels.Entry(dbVirtualAccount).State = System.Data.Entity.EntityState.Modified;
                        myModels.SaveChanges();

                        //4.当前虚拟账户，新增交易记录
                        B_TransactionRecord dbTransactionRecord = new B_TransactionRecord()
                        {
                            virtualAccountID = dbVirtualAccount.virtualAccountID,//虚拟账户ID
                            transactionType = 1,//交易类型-收入
                            transactionMoney = decRefundMoney,//交易金额
                            transactionTime = DateTime.Now,//交易时间
                            userID = loginUserID//用户ID
                        };
                        myModels.B_TransactionRecord.Add(dbTransactionRecord);
                        myModels.SaveChanges();

                        scope.Complete();
                        msg.State = true;
                        msg.Text = "退票成功！";
                    }
                    else
                    {
                        msg.Text = "退票失败！";
                    }
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

        #region 

        #endregion
    }
}
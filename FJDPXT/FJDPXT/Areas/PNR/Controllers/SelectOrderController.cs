using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;

namespace FJDPXT.Areas.PNR.Controllers
{
    public class SelectOrderController : Controller
    {
        // GET: PNR/SelectOrder
        FJDPXTEntities1 myModle = new FJDPXTEntities1();
        
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
        #region 订单查询页面
        public ActionResult SelectOrder()
        {
            return View();
        }
        #endregion

        #region 订单查询数据
        //需要查询四张表，订单表B_Order，PNR表B_PNR，用户表S_User，用户组表S_UserGroup,int?允许为空
        public ActionResult doSelectOrder(LayuiTablePage layuiTablePage ,string orderNum,int? userGroupID,string jobNumber, string startDate,string endDate, string PNRNo)
        {
            var linq = from tabOrder in myModle.B_Order
                       join tabPNR in myModle.B_PNR on tabOrder.PNRID equals tabPNR.PNRID
                       join tabUser in myModle.S_User on tabOrder.operatorID equals tabUser.userID
                       join tabUserGroup in myModle.S_UserGroup on tabUser.userGroupID equals tabUserGroup.userGroupID
                       orderby tabOrder.orderID descending//依据订单ID进行倒叙
                       select new OrderVo
                       {
                           orderID =tabOrder.orderID,//订单ID
                           orderNo = tabOrder.orderNo,//订单编号
                           PNRID = tabPNR.PNRID,//PNRID
                           PNRNo = tabPNR.PNRNo,//PNR编号
                           totalPrice = tabOrder.totalPrice,//总票价
                           agencyFee = tabOrder.agencyFee,//代理费
                           payMoney = tabOrder.payMoney,//支付金额
                           userGroupNumber = tabUserGroup.userGroupNumber,// 用户组号
                           jobNumber = tabUser.jobNumber,// 工号
                           payTime = tabOrder.payTime,//支付时间
                           //数据筛选
                           userGroupID = tabUserGroup.userGroupID, // 用户组ID                           
                       };

            //判断查询一用户传过来的input标签数据
            if (!string.IsNullOrEmpty(orderNum))
            {
                linq = linq.Where(o => o.orderID.Equals(orderNum.Trim()));//Equals相等，Contains模糊查询
            }
            //判断用户组ID
            if (userGroupID > 0)
            {
                linq = linq.Where(m => m.userGroupID == userGroupID);
            }
            //工号
            if (!string.IsNullOrEmpty(jobNumber))
            {
                linq = linq.Where(m => m.jobNumber.Equals(jobNumber.Trim()));//==,Contains
            }
            //开始时间
            if (!string.IsNullOrEmpty(startDate))
            {
                DateTime dtStartDate = Convert.ToDateTime(startDate);
                linq = linq.Where(m => m.payTime >= dtStartDate);
            }
            //结束时间
            if (!string.IsNullOrEmpty(endDate))
            {
                DateTime dtEndDate = Convert.ToDateTime(endDate);
                linq = linq.Where(m => m.payTime <= dtEndDate);
            }
            //PNR编号
            if (!string.IsNullOrEmpty(PNRNo))
            {
                linq = linq.Where(m => m.PNRNo.Equals(PNRNo.Trim()));//==,Contains
            }






            //计算总条数
            int totalRow = linq.Count();

            //分页
            List<OrderVo> listOrder = linq
                                      .Skip(layuiTablePage.GetStartIndex())
                                      .Take(layuiTablePage.limit)
                                      .ToList();

            //构建传到视图的数据
            LayuiTableData<OrderVo> layuiTableDate = new LayuiTableData<OrderVo>()
            {
                count = totalRow,
                data = listOrder
            };
                                      
            return Json(layuiTableDate, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 递归查询用户组信息
        public ActionResult selectUserGroup()
        {
            //声明一个存放用户组信息的列表
            List<SelectVo> listUsergroup = new List<SelectVo>();

            //查询当前用户所在的用户组
            List<SelectVo> curUserGroup = (from tbUserGroup in myModle.S_UserGroup
                                           join tbUser in myModle.S_User on tbUserGroup.userGroupID equals tbUser.userGroupID
                                           where tbUser.userID == loginUserID
                                           select new SelectVo
                                           {
                                               id = tbUserGroup.userGroupID,
                                               text = tbUserGroup.userGroupNumber
                                           }).ToList();
            //将当前用户所在用户组放进用户组信息列表
            listUsergroup.AddRange(curUserGroup);

            //遍历当前用户组信息
            foreach (SelectVo userGroup in curUserGroup)
            {
                //递归查询所有下级用户组
                List<S_UserGroup> getChildUserGroup = GetChildGroup(userGroup.id).ToList();
                //将返回的下级用户组的信息构建成指定返回格式
                List<SelectVo> childUserGroup = (from tbChildGroup in getChildUserGroup
                                                 select new SelectVo
                                                 {
                                                     id = tbChildGroup.userGroupID,
                                                     text = tbChildGroup.userGroupNumber
                                                 }).ToList();
                //将下级用户组信息放进用户组信息列表
                listUsergroup.AddRange(childUserGroup);
            }
            return Json(listUsergroup, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 递归查询下用户组
        /// </summary>
        /// <param name="superiorUserGroupID">上级用户组ID</param>
        /// <returns></returns>
        private IEnumerable<S_UserGroup> GetChildGroup(int superiorUserGroupID)
        {
            var childUserGroup = from tbUserGroup in myModle.S_UserGroup
                                 where tbUserGroup.superiorUserGroupID == superiorUserGroupID
                                 select tbUserGroup;

            return childUserGroup.ToList().Concat(childUserGroup.ToList().SelectMany(m => GetChildGroup(m.userGroupID)));
        }
        #endregion

        #region 订单显示页面
        public ActionResult OrderInfoShow(int orderID)
        {
            try
            {
                OrderVo dbOrderVo = (from tabOrder in myModle.B_Order
                                     join tabPNR in myModle.B_PNR on tabOrder.PNRID equals tabPNR.PNRID
                                     join tabUser in myModle.S_User on tabOrder.operatorID equals tabUser.userID
                                     join tabUserGroup in myModle.S_UserGroup on tabUser.userGroupID equals tabUserGroup.userGroupID
                                     join tabVirtualAccount in myModle.S_VirtualAccount on tabUser.userID equals tabVirtualAccount.userID
                                     where tabOrder.orderID == orderID
                                     select new OrderVo
                                     {
                                         orderNo=tabOrder.orderNo,//订单号
                                         userGroupNumber = tabUserGroup.userGroupNumber,//用户组号
                                         account = tabVirtualAccount.account,//支付账号
                                         jobNumber = tabUser.jobNumber,//工号
                                         PNRNo = tabPNR.PNRNo,//PNR编号
                                         payMoney = tabOrder.payMoney,//支付金额
                                         remark = tabOrder.remark,//备注
                                         payTime = tabOrder.payTime,//支付时间
                                         PNRID = tabOrder.PNRID
                                     }).Single();

                //查询订单对应的票号信息
                List<PNRTicketingVo> listPNRTicking = (from tbPNRTicketing in myModle.B_PNRTicketing
                                                       join tbPassenger in myModle.B_PNRPassenger on tbPNRTicketing.PNRPassengerID equals tbPassenger.PNRPassengerID
                                                       join tbSegment in myModle.B_PNRSegment on tbPNRTicketing.PNRSegmentID equals tbSegment.PNRSegmentID
                                                       join tbFlight in myModle.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                       join tbOrange in myModle.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                                                       join tbDestination in myModle.S_Airport on tbFlight.destinationID equals tbDestination.airportID
                                                       join tbCabinType in myModle.S_CabinType on tbSegment.cabinTypeID equals tbCabinType.cabinTypeID
                                                       join tbETicket in myModle.B_ETicket on tbPNRTicketing.ETicketID equals tbETicket.ETicketID
                                                       where tbPNRTicketing.orderID == orderID
                                                       select new PNRTicketingVo
                                                       {
                                                           passengerNo = tbPassenger.passengerNo.Value,//旅客编号
                                                           passengerName = tbPassenger.passengerName,//旅客姓名
                                                           segmentNo = tbSegment.segmentNo.Value,//航段号
                                                           flightCode = tbFlight.flightCode,//航班编号
                                                           orangeCity = tbOrange.cityName,//起飞城市名称
                                                           destinationCity = tbDestination.cityName,//到达城市名称
                                                           departureTime = tbFlight.departureTime.Value,//起飞时间
                                                           arrivalTime = tbFlight.arrivalTime.Value,//到达时间
                                                           cabinTypeCode = tbCabinType.cabinTypeCode,//舱位等级
                                                           ticketNo = tbETicket.ticketNo//票号
                                                       }).ToList();

                //将数据返回页面
                ViewBag.dbOrderVo = dbOrderVo;
                ViewBag.listPNRTicking = listPNRTicking;

                return View();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                //重定向
                return RedirectToAction("SelectOrder");
            }            
        }
        #endregion

        #region PNR显示
        public ActionResult openPNRInfoShow(int PNRID)
        {
            //查询PNR相关信息
            try
            {
                //查询PNR信息 -- 联系人信息、出票组信息
                PNRvo pnrInfo = (from tabPnr in myModle.B_PNR
                                 where tabPnr.PNRID == PNRID
                                 select new PNRvo
                                 {
                                     PNRID = tabPnr.PNRID, //PNRID
                                     PNRNo = tabPnr.PNRNo, //PNR编号
                                     contactName = tabPnr.contactName, //联系人姓名
                                     contactPhone = tabPnr.contactPhone, //联系人电话/手机
                                     TicketingInfo = tabPnr.TicketingInfo, //出票信息
                                     PNRStatus = tabPnr.PNRStatus, //PNR状态
                                     operatorID = tabPnr.operatorID, //操作人ID-用户ID
                                     createTime = tabPnr.createTime //创建时间
                                 }).Single();
                //!!!查询 PNR 旅客组信息    !!!左连接!!!
                List<PassengerVo> passengerInfos = (from tabPassenger in myModle.B_PNRPassenger
                                                    join tabPassengerType in myModle.S_PassengerType on tabPassenger.passengerTypeID equals tabPassengerType.passengerTypeID
                                                    join tabCertificatesType in myModle.S_CertificatesType on tabPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                                                    join tabFrequentPassenger in myModle.S_FrequentPassenger on tabPassenger.frequentPassengerID equals tabFrequentPassenger.frequentPassengerID into tiom
                                                    from trim in tiom.DefaultIfEmpty()
                                                    where tabPassenger.PNRID == PNRID
                                                    select new PassengerVo()
                                                    {
                                                        PNRPassengerID = tabPassenger.PNRPassengerID, //pnr旅客ID
                                                        PNRID = tabPassenger.PNRID, //PNRID 
                                                        passengerNo = tabPassenger.passengerNo, //旅客编号
                                                        passengerName = tabPassenger.passengerName, //旅客姓名
                                                        passengerTypeID = tabPassenger.passengerTypeID, //旅客类型ID
                                                        certificatesTypeID = tabPassenger.certificatesTypeID, //旅客证件类型ID
                                                        certificatesCode = tabPassenger.certificatesCode, //旅客证件号
                                                        frequentPassengerID = tabPassenger.frequentPassengerID, //常旅客ID
                                                                                                                //扩展字段
                                                        passengerType = tabPassengerType.passengerType, //旅客类型
                                                        certificatesType = tabCertificatesType.certificatesType, //旅客证件类型
                                                        frequentPassengerNo = trim!=null?trim.frequentPassengerNo:string.Empty//常旅客号
                                                    }).ToList();
                //查询PNR航班信息
                List<FlightCabinInfo> flightCabinInfos = (from tabPnrSegment in myModle.B_PNRSegment
                                                          join tabFlightCabin in myModle.S_FlightCabin on tabPnrSegment.flightCabinID equals tabFlightCabin.flightCabinID
                                                          join tabFlight in myModle.S_Flight on tabFlightCabin.flightID equals tabFlight.flightID
                                                          join tabCabinType in myModle.S_CabinType on tabFlightCabin.cabinTypeID equals tabCabinType.cabinTypeID
                                                          join tabOrangeAirport in myModle.S_Airport on tabFlight.orangeID equals tabOrangeAirport.airportID
                                                          join tabDestinationAirport in myModle.S_Airport on tabFlight.destinationID equals tabDestinationAirport.airportID
                                                          where tabPnrSegment.PNRID == PNRID && tabPnrSegment.invalid == true
                                                          select new FlightCabinInfo
                                                          {
                                                              flightID = tabFlight.flightID, //航班ID
                                                              flightCode = tabFlight.flightCode, //航班号
                                                              orangeID = tabFlight.orangeID, //始发地ID
                                                              destinationID = tabFlight.destinationID, //目的地ID
                                                              departureTime = tabFlight.departureTime, //起飞时间
                                                              arrivalTime = tabFlight.arrivalTime, //到达时间
                                                              planTypeID = tabFlight.planTypeID, //飞机类型ID
                                                              flightDate = tabFlight.flightDate, //航班时间
                                                              standardPrice = tabFlight.standardPrice, //标准时间
                                                                                                       //扩展字段
                                                              cabinTypeID = tabFlightCabin.cabinTypeID.Value, //舱位类型ID
                                                              cabinTypeCode = tabCabinType.cabinTypeCode, //舱位类型编号
                                                              cabinTypeName = tabCabinType.cabinTypeName, //舱位类型名称
                                                              cabinPrice = tabFlightCabin.cabinPrice.Value, //舱位价格
                                                              seatNum = tabPnrSegment.bookSeatNum.Value, //座位数
                                                              orangeCity = tabOrangeAirport.cityName, //始发地城市
                                                              destinationCity = tabDestinationAirport.cityName, //目的地城市
                                                              bookSeatInfo = tabPnrSegment.bookSeatInfo.Value, //订座状态
                                                              PNRSegmentID = tabPnrSegment.PNRSegmentID, //PNR航段信息
                                                              flightCabinID = tabFlightCabin.flightCabinID, //航班舱位ID
                                                              segmentNo = tabPnrSegment.segmentNo.Value, //航段号
                                                              segmentType = tabPnrSegment.segmentType, //航段类型
                                                          }
                    ).ToList();

                //查询其他PNR信息
                List<B_PNROtherInfo> pnrOtherInfos = (from tabPnrOtherInfo in myModle.B_PNROtherInfo
                                                      where tabPnrOtherInfo.PNRID == PNRID
                                                      select tabPnrOtherInfo).ToList();

                //查询出票信息
                List<PNRTicketingVo> pnrTicketings = (from tabTicketing in myModle.B_PNRTicketing
                                                      join tabPassenger in myModle.B_PNRPassenger on tabTicketing.PNRPassengerID equals tabPassenger.PNRPassengerID
                                                      join tabSegment in myModle.B_PNRSegment on tabTicketing.PNRSegmentID equals tabSegment.PNRSegmentID
                                                      join tabETicket in myModle.B_ETicket on tabTicketing.ETicketID equals tabETicket.ETicketID
                                                      where tabTicketing.PNRID == PNRID
                                                      orderby tabSegment.segmentNo, tabPassenger.passengerNo
                                                      select new PNRTicketingVo
                                                      {
                                                          PNRTicketingID = tabTicketing.PNRTicketingID, //PNR出票ID
                                                          PNRID = tabTicketing.PNRID, //PNRID
                                                          PNRPassengerID = tabTicketing.PNRPassengerID, //PNR旅客ID
                                                          PNRSegmentID = tabTicketing.PNRSegmentID, //PNR航段ID
                                                          ETicketID = tabTicketing.ETicketID, //电子客票ID
                                                          TicketingTime = tabTicketing.TicketingTime, //出票时间
                                                                                                      //扩展字段
                                                          passengerNo = tabPassenger.passengerNo.Value, //PNR旅客编号
                                                          segmentNo = tabSegment.segmentNo.Value, //PNR航段编号
                                                          ticketNo = tabETicket.ticketNo //电子客票号
                                                      }).ToList();


                //将数据传递到页面
                ViewBag.pnrInfo = pnrInfo;//pnr基本信息（pnr编号、联系人、出票等信息）
                ViewBag.passengerInfos = passengerInfos;//pnr旅客信息
                ViewBag.flightCabinInfos = flightCabinInfos;//pnr航班舱位信息
                ViewBag.pnrOtherInfos = pnrOtherInfos;//pnr其他信息
                ViewBag.pnrTicketings = pnrTicketings;//pnr出票信息

                return View();
            }
            catch (Exception e)
            {
                //出现异常 重定向到订单查询页面
                Console.WriteLine(e);
                return RedirectToAction("Index");
            }
        }
        #endregion
    }
}
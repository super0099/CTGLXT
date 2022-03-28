using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;

namespace FJDPXT.Areas.PNR.Controllers
{
    public class StrokeDisplayController : Controller
    {
        // GET: PNR/StrokeDisplay
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
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
        #region 行程显示
        public ActionResult StrokeDisplay()
        {
            return View();
        }
        
        
        public ActionResult SelectPNRIDByNo(string PNRNo)
        {
            ReturnJson msg = new ReturnJson();
            try
            {
                PNRNo = PNRNo.Trim().ToUpper();
                B_PNR dbPNR = myModel.B_PNR.Single(o => o.PNRNo == PNRNo);
                //判断PNR状态。0是已经取消，1是还未出票
                if (dbPNR.PNRStatus == 0)
                {
                    msg.Text = "该PNR已经被取消！无法打印行程单";
                }
                if(dbPNR.PNRStatus == 1)
                {
                    msg.Text = "该PNR还未出票！无法打印行程单";
                }
                if(dbPNR.PNRStatus == 2||dbPNR.PNRStatus==3)
                {
                    msg.State = true;
                    msg.Object = dbPNR.PNRID;
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "数据异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        public ActionResult checkFlight(int PNRID)
        {
            try
            {
                //查询PNR
                B_PNR pnr = myModel.B_PNR.Single(o => o.PNRID == PNRID);
                //查询旅客列表
                List<B_PNRPassenger> dbPassenger = (from tabPassenger in myModel.B_PNRPassenger
                                                    where tabPassenger.PNRID == PNRID
                                                    select tabPassenger).ToList();
                //将数据反回到页面
                ViewBag.pnr = pnr;
                ViewBag.dbPassenger = dbPassenger;
                return View();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return RedirectToAction("StrokeDisplay");
            }
        }

        public ActionResult selectPassengerETicket(int passengerID)
        {

            //查询对应的客票信息
            List<PNRTicketingVo> listPNRTicketing = (from tbPNRTicketing in myModel.B_PNRTicketing
                                                     join tbETicketing in myModel.B_ETicket on tbPNRTicketing.ETicketID equals tbETicketing.ETicketID
                                                     join tbPNRPassenger in myModel.B_PNRPassenger on tbPNRTicketing.PNRPassengerID equals tbPNRPassenger.PNRPassengerID
                                                     join tbSegment in myModel.B_PNRSegment on tbPNRTicketing.PNRSegmentID equals tbSegment.PNRSegmentID
                                                     join tbFlight in myModel.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                     join tbOrange in myModel.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                                                     join tbDestination in myModel.S_Airport on tbFlight.destinationID equals tbDestination.airportID
                                                     join tbCabinTpe in myModel.S_CabinType on tbSegment.cabinTypeID equals tbCabinTpe.cabinTypeID
                                                     where tbPNRPassenger.PNRPassengerID == passengerID
                                                     select new PNRTicketingVo
                                                     {
                                                         ticketNo = tbETicketing.ticketNo,//票号
                                                         cabinTypeCode = tbCabinTpe.cabinTypeCode,//舱位等级
                                                         flightCode = tbFlight.flightCode,//航班编号
                                                         flightDate = tbFlight.flightDate.Value,//航班日期
                                                         orangePinyin = tbOrange.pinyinName.ToUpper(),//起飞城市拼音
                                                         orangeCity = tbOrange.cityName,//起飞城市
                                                         orangeCode = tbOrange.airportCode,//起飞机场编码
                                                         departureTime = tbFlight.departureTime.Value,//起飞时间
                                                         destinationPinyin = tbDestination.pinyinName.ToUpper(),//达到城市拼音
                                                         destinationCity = tbDestination.cityName,//到达城市
                                                         destinationCode = tbDestination.airportCode,//到达机场编码
                                                         arrivalTime = tbFlight.arrivalTime.Value//到达时间
                                                     }).ToList();

            return Json(listPNRTicketing, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
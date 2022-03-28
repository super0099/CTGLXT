using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Transactions;//事务
using FJDPXT.Common;

namespace FJDPXT.Areas.PNR.Controllers
{
    public class PNRQueryController : Controller
    {
        FJDPXTEntities1 myModle = new FJDPXTEntities1();
        // GET: PNR/PNRQuery
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
        public ActionResult PNRQuery()
        {
            
            return View();
        }

        #region PNR查询页面
        public ActionResult SelectPNR(LayuiTablePage layuiTablePage, string PNRNo, string passengerName, string flightCode, string flightDate)
        {
            //用变量代替连表查询的结果。tbPassenger来自数据库的B_PNRPassenger旅客组表
            var tempPNRInfor = from tbPassenger in myModle.B_PNRPassenger
                                   //tabPNR来自数据库的B_PNR表
                               join tabPNR in myModle.B_PNR
                               //用tbPassenger表的主键去连接tabPNR表的外键
                                 on tbPassenger.PNRID equals tabPNR.PNRID
                               //tabSegment来自数据库的B_PNRSegment航段表
                               join tabSegment in myModle.B_PNRSegment
                               //用tbPassenger的外键去连接tabSegment的外键
                                 on tbPassenger.PNRID equals tabSegment.PNRID
                               //tabFlight来自数据库的S_Flight航班表
                               join tabFlight in myModle.S_Flight
                               //用tabSegment的外键去连接tabFlight的主键
                                 on tabSegment.flightID equals tabFlight.flightID
                               //查询新的数据
                               select new
                               {
                                   PNRID = tabPNR.PNRID,//PNRID
                                   PNRNo = tabPNR.PNRNo,//PNR编号
                                   bookSeatInfo = tabSegment.bookSeatInfo,//订座情况
                                   flightDate = tabFlight.flightDate,//航班日期
                                   PNRStatus = tabPNR.PNRStatus,//PNR状态
                                   flightCode = tabFlight.flightCode,//航班号
                                   passengerName = tbPassenger.passengerName,//旅客姓名
                               };
            //筛选数据PNR编号,给上面查询出来的数据添加一个条件
            if (!string.IsNullOrEmpty(PNRNo))
            {
                //tempPNRInfor = tempPNRInfor.Where(p => p.PNRNo == PNRNo);
                //模糊查询
                tempPNRInfor = tempPNRInfor.Where(p => p.PNRNo.Contains(PNRNo));
            }
            //筛选数据旅客姓名
            if (!string.IsNullOrEmpty(passengerName))
            {
                tempPNRInfor = tempPNRInfor.Where(p => p.passengerName == passengerName);
            }
            //筛选数据航班号
            if (!string.IsNullOrEmpty(flightCode))
            {
                tempPNRInfor = tempPNRInfor.Where(p => p.flightCode == flightCode);
            }
            //筛选数据航班日期
            if (!string.IsNullOrEmpty(flightDate))
            {
                DateTime FlightDate = Convert.ToDateTime(flightDate);
                tempPNRInfor = tempPNRInfor.Where(p => p.flightDate == FlightDate);
            }




            //根据PNRID分组查询
            //用变量代替分组排序的结果。tbPNRInfor来自连表查询出来的结果tempPNRInfor，也就是上面的表格
            //目的减少相同的PNRID数据的显示，PNRID姓名相同的会合并显示
            var listPNRInfor = from tbPNRInfor in tempPNRInfor
                                   //根据tbPNRInfor的PNRID进行降序排序
                               orderby tbPNRInfor.PNRID descending
                               //根据tbPNRInfor的PNRID进行分组，PNRID相同的在一组.然后放在into新的临时表格中tabPNR
                               group tbPNRInfor by tbPNRInfor.PNRID into tabPNR
                               select new PNRvo
                               {
                                   //key就是tabPNR根据tbPNRInfor.PNRID分组的值，也就是可以用Key来代替tbPNRInfor.PNRID。
                                   PNRID = tabPNR.Key,//根据PNRID的不同划分不同的组
                                   //FirstOrDefault返回序列中的第一个元素，也就是说如果tabPNR这个临时表格中有多条PNRID相同的数据中，只返回第一条数据。这里拿的是列表中flightCode属性相同的拿第一条
                                   flightCode = tabPNR.FirstOrDefault().flightCode,//航班号
                                   //这里拿的是列表中PNRNo属性相同的拿第一条
                                   PNRNo = tabPNR.FirstOrDefault().PNRNo,//PNR编号
                                   //这里拿的是列表中bookSeatInfo属性相同的拿第一条
                                   PNRStatus = tabPNR.FirstOrDefault().PNRStatus,//订座情况
                                   //这里拿的是列表中flightDate属性相同的拿第一条
                                   flightDate = tabPNR.FirstOrDefault().flightDate,//航班日期
                                   //获取旅客信息。可以嵌套查询，子可以使用父的数据，但是父不能使用子的数据
                                   Passenger = (from tabpassenger in myModle.B_PNRPassenger
                                                where tabpassenger.PNRID == tabPNR.Key
                                                select tabpassenger).ToList()
                               };
            
            //计算总条数
            int count = listPNRInfor.Count();

            //分页
            List<PNRvo> listPNR = listPNRInfor
                .OrderByDescending(o => o.PNRID)
                //系统要跳过的多少条数据
                .Skip(layuiTablePage.GetStartIndex())
                //分页大小
                .Take(layuiTablePage.limit)
                .ToList();
            //构建返回的数据
            LayuiTableData<PNRvo> layuiTableData = new LayuiTableData<PNRvo>()
            {
                count = count,
                data = listPNR
            };
            
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region PNR功能显示页面
        public ActionResult PnrInfoShow(int PNRID)
        {
            //返回ID到页面
            ViewBag.PNRID = PNRID;
            return View();
        }
        #endregion

        #region 查询PNR数据
        public ActionResult SelectPNRInfo(int PNRID)
        {
            try
            {
                //1. PNR基本信息：编号，状态，联系人信息，出票组信息
                PNRvo pnrInfor = (from tbPNR in myModle.B_PNR
                                  where tbPNR.PNRID == PNRID
                                  select new PNRvo
                                  {
                                      PNRID = tbPNR.PNRID,
                                      PNRNo = tbPNR.PNRNo,
                                      contactName = tbPNR.contactName,
                                      contactPhone = tbPNR.contactPhone,
                                      TicketingInfo = tbPNR.TicketingInfo,
                                      PNRStatus = tbPNR.PNRStatus
                                  }).Single();


                //连表查询旅客各种信息，左连接
                List<PassengerVo> listPassengerInfor = (from tabPassenger in myModle.B_PNRPassenger
                                                        join tabPassengerType in myModle.S_PassengerType
                                                          on tabPassenger.passengerTypeID equals tabPassengerType.passengerTypeID
                                                        join tabCertificatesType in myModle.S_CertificatesType
                                                          on tabPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                                                        join tabFrequentPassenger in myModle.S_FrequentPassenger on tabPassenger.frequentPassengerID equals tabFrequentPassenger.frequentPassengerID
                                                        into temp
                                                        from tbFrequentPassengers in temp.DefaultIfEmpty()
                                                        where tabPassenger.PNRID == PNRID
                                                        select new PassengerVo()
                                                        {
                                                            PNRPassengerID = tabPassenger.PNRPassengerID,
                                                            passengerName = tabPassenger.passengerName,
                                                            passengerTypeID = tabPassenger.passengerTypeID,
                                                            passengerType = tabPassengerType.passengerType,
                                                            certificatesTypeID = tabPassenger.certificatesTypeID,
                                                            certificatesType = tabCertificatesType.certificatesType,
                                                            certificatesCode = tabPassenger.certificatesCode,
                                                            //三目表达式，判断frequentPassengerNo如果不等于空就原输出，等于空的话就返回空的字符串
                                                            frequentPassengerNo = tbFrequentPassengers!=null? tbFrequentPassengers.frequentPassengerNo:string.Empty
                                                        }).ToList();



                //航段航班舱位信息
                List<FlightCabinInfo> listFlightcabinInfor = (from tbSegment in myModle.B_PNRSegment
                                                              join tbFlight in myModle.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                              join tbOrange in myModle.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                                                              join tbDesdination in myModle.S_Airport on tbFlight.destinationID equals tbDesdination.airportID
                                                              join tbCabinType in myModle.S_CabinType on tbSegment.cabinTypeID equals tbCabinType.cabinTypeID
                                                              join tbFlightCabin in myModle.S_FlightCabin on tbSegment.flightCabinID equals tbFlightCabin.flightCabinID
                                                              orderby tbFlight.flightDate, tbFlight.departureTime
                                                              where tbSegment.PNRID == PNRID
                                                              select new FlightCabinInfo
                                                              {
                                                                  segmentNo = tbSegment.segmentNo.Value,
                                                                  flightCode = tbFlight.flightCode,
                                                                  orangeCity = tbOrange.cityName,//始发地城市
                                                                  destinationCity = tbDesdination.cityName,//目的地城市
                                                                  departureTime = tbFlight.departureTime,
                                                                  arrivalTime = tbFlight.arrivalTime,
                                                                  flightDate = tbFlight.flightDate,
                                                                  cabinTypeCode = tbCabinType.cabinTypeCode,
                                                                  cabinTypeName = tbCabinType.cabinTypeName,
                                                                  cabinPrice = tbFlightCabin.cabinPrice.Value,
                                                                  seatNum = tbSegment.bookSeatNum.Value,
                                                                  segmentType = tbSegment.segmentType,
                                                                  PNRSegmentID = tbSegment.PNRSegmentID
                                                              }).ToList();
                

                //4.其他信息
                List<B_PNROtherInfo> listOtherInfor = myModle.B_PNROtherInfo.Where(o => o.PNRID == PNRID).ToList();

                //5.出票信息
                List<PNRTicketingVo> listPNRTicking = (from tbPNRTicking in myModle.B_PNRTicketing
                                                       join tbPassenger in myModle.B_PNRPassenger on tbPNRTicking.PNRPassengerID equals tbPassenger.PNRPassengerID
                                                       join tbSegment in myModle.B_PNRSegment on tbPNRTicking.PNRSegmentID equals tbSegment.PNRSegmentID
                                                       join tbETicket in myModle.B_ETicket on tbPNRTicking.ETicketID equals tbETicket.ETicketID
                                                       where tbPNRTicking.PNRID == PNRID
                                                       select new PNRTicketingVo
                                                       {
                                                           ticketNo = tbETicket.ticketNo,
                                                           segmentNo = tbSegment.segmentNo.Value,
                                                           passengerNo = tbPassenger.passengerNo.Value
                                                       }).ToList();

                //组装数据
                return Json(new {
                    pnrInfors = pnrInfor,
                    PassengerInfors = listPassengerInfor,
                    FlightcabinInfors = listFlightcabinInfor,
                    OtherInfors = listOtherInfor,
                    PNRTickings = listPNRTicking
                }, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            return Json("数据异常", JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region PNR复制
        public ActionResult CopyPNR(int PNRID)
        {
            ViewBag.PNRID = PNRID;
            return View();
        }
        #endregion
        #region PNR取消
        public ActionResult cancelPNR(int PNRID)
        {
            ReturnJson msg = new ReturnJson();
            //开始事务
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {
                    //查询B_PNR表单返回单挑数据
                    B_PNR pnrInfor = myModle.B_PNR.Single(o => o.PNRID == PNRID);
                    //判断状态是否等于1，只有等于1的状态才能修改
                    if (pnrInfor.PNRStatus == 1)
                    {
                        //修改PNR状态
                        pnrInfor.PNRStatus = 0;
                        //改变数据库里pnrInfor的状态
                        myModle.Entry(pnrInfor).State = System.Data.Entity.EntityState.Modified;
                        myModle.SaveChanges();

                        //查询航段表
                        List<B_PNRSegment> listSegment = (from tabSegment in myModle.B_PNRSegment
                                                          where tabSegment.PNRID == PNRID
                                                          select tabSegment).ToList();
                        foreach(B_PNRSegment segment in listSegment)
                        {
                            //修改订座情况
                            segment.bookSeatInfo = 0;
                            //改变数据库里pnrInfor的状态
                            myModle.Entry(segment).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();

                            //修改对应的航班舱位信息
                            S_FlightCabin flightCabin = (from tabflightCabin in myModle.S_FlightCabin
                                                         where tabflightCabin.flightCabinID == segment.flightCabinID
                                                         select tabflightCabin).Single();
                            //卖出去的座位数因为取消了订单，所以卖出去的座位数要减去取消的座位数
                            flightCabin.sellSeatNum = flightCabin.sellSeatNum - segment.bookSeatNum;
                            myModle.Entry(flightCabin).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }
                        //提交事务
                        scope.Complete();
                        //返回成功信息
                        msg.State = true;
                        msg.Text = "修改成功";
                    }
                    else
                    {
                        if (pnrInfor.PNRStatus == 0)
                        {
                            msg.Text = "该PNR已经取消，无法再次取消!";
                        }
                        if (pnrInfor.PNRStatus == 2)
                        {
                            msg.Text = "该PNR已经部分出票，无法取消!";
                        }
                        if (pnrInfor.PNRStatus == 3)
                        {
                            msg.Text = "该PNR已经全部出票，无法取消!";
                        }                        
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
        #region PNR分离
        public ActionResult splitPNR(int PNRID)
        {
            ViewBag.PNRID = PNRID;

            return View();
        }

        public ActionResult doSplitPNR(int PNRID, List<int> pnrPassengerID)
        {
            ReturnJson msg = new ReturnJson();
            foreach (int pnrPassengerId in pnrPassengerID)
            {
                if (pnrPassengerId == 0)
                {
                    msg.Text = "没有需要分离的旅客！";
                    return Json(msg, JsonRequestBehavior.AllowGet);
                }
            }
            //开始事务
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {
                    //查询PNR表数据
                    B_PNR oldPnrInfor = myModle.B_PNR.Single(o => o.PNRID == PNRID);

                    //查询航段信息
                    List<B_PNRSegment> oldSegmentInfor = (from tbSegment in myModle.B_PNRSegment
                                                          where tbSegment.PNRID == PNRID
                                                          select tbSegment).ToList();
                    //PNR旅客信息
                    List<B_PNRPassenger> oldPassengerInfor = (from tbPNRPassenger in myModle.B_PNRPassenger
                                                              where tbPNRPassenger.PNRID == PNRID
                                                              select tbPNRPassenger).ToList();


                    //判断PNR状态,只有状态为一才能进行操作
                    if (oldPnrInfor.PNRStatus == 1)
                    {
                        //1.新增PNR数据
                        //生成新的PNR编号
                        //创建好的一个类
                        string pnrNo = PNRCodeHelper.CreatePNR();
                        //new对象
                        B_PNR newPNR = new B_PNR()
                        {
                            PNRNo = pnrNo,
                            contactName = oldPnrInfor.contactName,//
                            contactPhone = oldPnrInfor.contactPhone,
                            TicketingInfo = oldPnrInfor.TicketingInfo,
                            PNRStatus = 1,//生成订座情况为一的数据
                            operatorID = loginUserID,//操作人等于现在登录的用户
                            createTime = DateTime.Now//创建时间等于现在的时间
                        };
                        //保存新增
                        myModle.B_PNR.Add(newPNR);
                        myModle.SaveChanges();
                        //获取新增的PNRID，作用于
                        var newPNRID = newPNR.PNRID;

                        //2. 航段表，先修改再新增因为
                        List<B_PNRSegment> newPNRSegment = new List<B_PNRSegment>();
                        foreach (B_PNRSegment segmentInfor in oldSegmentInfor)
                        {
                            //订座数等于传过来的ID，勾选多少就有多少ID
                            segmentInfor.bookSeatNum -= pnrPassengerID.Count;
                            //保存修改
                            myModle.Entry(segmentInfor).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();

                            //新增航段信息，表对象
                            B_PNRSegment pnrSegment = new B_PNRSegment()
                            {
                                PNRID = newPNRID,
                                segmentNo = segmentInfor.segmentNo,
                                flightID = segmentInfor.flightID,
                                cabinTypeID = segmentInfor.cabinTypeID,
                                flightCabinID = segmentInfor.flightCabinID,
                                bookSeatInfo = segmentInfor.bookSeatInfo,
                                bookSeatNum = pnrPassengerID.Count,
                                segmentType = segmentInfor.segmentType,
                                invalid = true
                            };

                            newPNRSegment.Add(pnrSegment);
                        }

                        //保存新增
                        myModle.B_PNRSegment.AddRange(newPNRSegment);
                        myModle.SaveChanges();

                        //3.旅客表
                        foreach (B_PNRPassenger passenger in oldPassengerInfor)
                        {
                            //Contains包含
                            if (pnrPassengerID.Contains(passenger.PNRPassengerID))
                            {
                                passenger.PNRID = newPNRID;

                                //保存修改
                                myModle.Entry(passenger).State = System.Data.Entity.EntityState.Modified;
                                
                            }
                        }

                        if (myModle.SaveChanges() > 0)
                        {
                            //提交事务
                            scope.Complete();

                            //返回成功的信息
                            msg.State = true;
                            msg.Text = "PNR分离成功！";
                        }
                    }
                    else
                    {
                        if (oldPnrInfor.PNRStatus == 0)
                        {
                            msg.Text = "该PNR已经取消，无法分离";
                        }

                        if (oldPnrInfor.PNRStatus == 2)
                        {
                            msg.Text = "该PNR已经部分出票，无法分离";
                        }

                        if (oldPnrInfor.PNRStatus == 3)
                        {
                            msg.Text = "该PNR已经全部出票，无法分离";
                        }
                    }
                }
                catch (Exception e)
                {
                    Console.Write(e);
                    msg.Text = "数据异常，PNR分离失败！";
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        #endregion
        #region PNR添加旅客
        /// <summary>
        /// 添加旅客页面数据查询
        /// </summary>
        /// <param name="PNRID"></param>
        /// <returns></returns>
        public ActionResult addPassengerPage(int PNRID)
        {
            //还可以添加多少旅客
            int maxpaseengerNum = 0;
            //查询出当前PNR已有乘客人数
            int  countNum = (from tabPassenger in myModle.B_PNRPassenger
                            where tabPassenger.PNRID == PNRID
                            select tabPassenger).Count();
            //因为当前最多人数是9人。当前航段旅客人数+现在最多新增的人数=9，9-航段人数=可以添加的旅客人数maxpaseengerNum
            maxpaseengerNum = 9 - countNum;

            //查询航班舱位信息，判断舱位是否足够
            List<S_FlightCabin> flightcabins = (from tbSegment in myModle.B_PNRSegment
                                                join tbFlightCabin in myModle.S_FlightCabin on tbSegment.flightCabinID equals tbFlightCabin.flightCabinID
                                                where tbSegment.PNRID == PNRID
                                                select tbFlightCabin).ToList();
            //循环查询出来的数组
            foreach (S_FlightCabin flightcabin in flightcabins)
            {
                //如果还可以添加多少旅客>（总座位数-卖出去的座位数）或者说剩余的座位数
                if (maxpaseengerNum > (flightcabin.seatNum - flightcabin.sellSeatNum))
                {
                    //
                    maxpaseengerNum = flightcabin.seatNum.Value - flightcabin.sellSeatNum.Value;
                }
            }

            //下拉框数据查询-旅客类型
            List<S_PassengerType> passengerType = (from tbPassengerType in myModle.S_PassengerType select tbPassengerType).ToList();
            //下拉框数据查询-证件类型
            List<S_CertificatesType> certificatesType = (from tbCertificatesType in myModle.S_CertificatesType select tbCertificatesType).ToList();

            //传递数据
            ViewBag.maxNum = maxpaseengerNum;
            ViewBag.PNRID = PNRID;
            ViewBag.passengerType = passengerType;
            ViewBag.certificatesType = certificatesType;


            return View();
        }
        /// <summary>
        /// 添加旅客表单提交
        /// </summary>
        /// <param name="PNRID"></param>
        /// <returns></returns>
        public ActionResult addPNRPassenger(int PNRID,List<PassengerVo> passengerInfors)
        {
            ReturnJson msg = new ReturnJson();
            //判断数据
            try
            {
                B_PNR dbPNR = myModle.B_PNR.Single(o => o.PNRID == PNRID);
                if (dbPNR.PNRStatus == 1)
                {
                    //获取当前PNR的旅客信息,同一个订单不存在两个相同的身份证号的旅客，方法223
                    List<B_PNRPassenger> dbPassengers = (from tbPassenger in myModle.B_PNRPassenger
                                                         where tbPassenger.PNRID == PNRID
                                                         select tbPassenger).ToList();
                    //循环用户传过来的数据信息，创建好的一个类来接收页面传过来的数组，并且循环这个数组里的数据
                    foreach (PassengerVo paseenger in passengerInfors)
                    {
                        //判断名字是否为空，并返回数据到页面
                        if (string.IsNullOrEmpty(paseenger.passengerName))
                        {
                            //Format 替换数据
                            msg.Text = string.Format("旅客 [{0}] 的姓名不能为空！", paseenger.passengerNo);
                            return Json(msg, JsonRequestBehavior.AllowGet);
                        }
                        //如果证件类型为“身份证”
                        if (paseenger.certificatesTypeID == 1)//成年人ID为1
                        {
                            //=检查身份证，IdCardHelper检查身份证创建的一个类方法，CheckIdCard()里面放需要验证的东西，不合理就执行方法
                            if (!IdCardHelper.CheckIdCard(paseenger.certificatesCode))
                            {
                                msg.Text = string.Format("旅客 【{0}】 的身份证号输入不正确，请重新输入！", paseenger.passengerNo);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                            // =检查乘客类型选择（成人、儿童）与身份证是否相符合
                            //IdCardHelper检查身份证创建的一个类方法，GetAgeByIdCard()把身份证输入到括号里可以计算出年龄
                            int age = IdCardHelper.GetAgeByIdCard(paseenger.certificatesCode);
                            if (paseenger.passengerTypeID == 1 && age < 18) //成人，如果成人年龄小于18,提示
                            {
                                msg.Text = string.Format("旅客 【{0}】 的身份证年龄不满18周岁，与所选的“成人”不符合！", paseenger.passengerNo);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                            //选择儿童但是身份证大于18
                            if (paseenger.passengerTypeID == 2 && age >= 18)
                            {
                                msg.Text = string.Format("旅客 【{0}】 的身份证年龄已满18周岁，与所选的“儿童”不符合！", paseenger.passengerNo);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }

                            //=检查在本PNR数据库中旅客列表中是否已经存在该身份证号，配合上面创建的查询方法223
                            foreach (B_PNRPassenger dbPassenger in dbPassengers)
                            {
                                if (dbPassenger.certificatesTypeID == 1 && dbPassenger.certificatesCode.Trim() == paseenger.certificatesCode.Trim())
                                {
                                    msg.Text = string.Format("添加的旅客与当前PNR中其他旅客的身份证相同，请检查！");
                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                }
                            }
                        }


                        //验证常旅客号，判断常旅客号不为空
                        if (!string.IsNullOrEmpty(paseenger.frequentPassengerNo))
                        {
                            try
                            {
                                //根据常旅客号查询常旅客信息，查询出数据库存在的常旅客号
                                S_FrequentPassenger frequentPassenger = (from tbFrequentPassenger in myModle.S_FrequentPassenger
                                                                         where tbFrequentPassenger.frequentPassengerNo == paseenger.frequentPassengerNo
                                                                         select tbFrequentPassenger).Single();

                                //判断常旅客号是否正确，判断用户常旅客号和旅客姓名是否一致
                                if (frequentPassenger.frequentPassengerName != paseenger.passengerName ||
                                    frequentPassenger.certificatesCode != paseenger.certificatesCode)
                                {
                                    msg.Text = string.Format("旅客 【{0}】 姓名和填写的常旅客信息不匹配，请检查。", paseenger.passengerNo);
                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                }
                                //如果传过来的常旅客号和数据库的一致，就进行赋值
                                paseenger.frequentPassengerID = frequentPassenger.frequentPassengerID;
                            }
                            catch (Exception e)
                            {
                                Console.Write(e);
                                msg.Text = "输入的常旅客号不存在，请检查！";
                            }
                        }
                    }
                    //检查新增的旅客身份证号是否相同，两层循环，判断传过来的数据是否存在相同的旅客身份证
                    for (int i = 0; i < passengerInfors.Count; i++)
                    {
                        for (int j = i + 1; j < passengerInfors.Count; j++)
                        {
                            if (passengerInfors[i].certificatesTypeID == 1 &&
                                passengerInfors[j].certificatesTypeID == 1 &&
                                passengerInfors[i].certificatesCode == passengerInfors[j].certificatesCode)
                            {
                                msg.Text = string.Format("第【{0}】个旅客和第【{1}】个旅客的身份证号相同，请检查!", passengerInfors[i].passengerNo, passengerInfors[j].passengerNo);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                        }
                    }
                    //开始事务
                    using (TransactionScope scope = new TransactionScope())
                    {
                        //1.查询航班舱位信息，座位数相对增加或减
                        List<S_FlightCabin> listFlightCabin = (from tbSegment in myModle.B_PNRSegment
                                                               join tbFlightCabin in myModle.S_FlightCabin on tbSegment.flightCabinID equals tbFlightCabin.flightCabinID
                                                               where tbSegment.PNRID == PNRID
                                                               select tbFlightCabin).ToList();
                        foreach (S_FlightCabin flightCabin in listFlightCabin)
                        {
                            //座位数-卖出去的座位数是否大于或等于页面传过来的用户数，飞机没有站票
                            if ((flightCabin.seatNum - flightCabin.sellSeatNum) >= passengerInfors.Count)
                            {
                                //修改数据，如果座位数足够就加上用户需要的座位数
                                flightCabin.sellSeatNum += passengerInfors.Count;

                                //保存修改
                                myModle.Entry(flightCabin).State = System.Data.Entity.EntityState.Modified;
                                myModle.SaveChanges();
                            }
                            else
                            {
                                msg.Text = "航班余票数不足，订座失败！";
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                        }
                        //2.航段表，修改订座的信息，因为多了页面传过来的信息，所以修改订座数
                        List<B_PNRSegment> listSegment = (from tbSegment in myModle.B_PNRSegment
                                                          where tbSegment.PNRID == PNRID
                                                          select tbSegment).ToList();

                        foreach (B_PNRSegment segment in listSegment)
                        {
                            //修改订座数
                            segment.bookSeatNum += passengerInfors.Count;
                            //保存
                            myModle.Entry(segment).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }

                        //3.新增旅客信息
                        //容器
                        List<B_PNRPassenger> listPassengers = new List<B_PNRPassenger>();
                        var passengerNo = dbPassengers.Count + 1;
                        foreach (PassengerVo pnrPassenger in passengerInfors)
                        {
                            B_PNRPassenger tbPassenger = new B_PNRPassenger()
                            {
                                PNRID = PNRID,
                                passengerNo = passengerNo,
                                passengerName = pnrPassenger.passengerName,
                                passengerTypeID = pnrPassenger.passengerTypeID,
                                certificatesTypeID = pnrPassenger.certificatesTypeID,
                                certificatesCode = pnrPassenger.certificatesCode,
                                frequentPassengerID = pnrPassenger.frequentPassengerID
                            };
                            listPassengers.Add(tbPassenger);
                            passengerNo++;
                        }

                        //保存到数据库
                        myModle.B_PNRPassenger.AddRange(listPassengers);
                        myModle.SaveChanges();
                        //提交事务
                        scope.Complete();

                        //返回数据
                        msg.State = true;
                        msg.Text = "添加旅客成功！";
                    }
                }                 
                else
                {
                    msg.Text = "该订单状态不能添加旅客";
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
        #region 修改姓名
        public ActionResult EditPassengerName(int PNRID)
        {
            ViewBag.PNRID = PNRID;
            return View();
        }

        public ActionResult updatePassengerName(int PNRID,List<B_PNRPassenger> passengerInfors)
        {
            ReturnJson msg = new ReturnJson();
            using (TransactionScope scope = new TransactionScope())
            {
                try
                {

                    //查询数据库旅客信息
                    List<B_PNRPassenger> dbPassengers = (from tbPasssnger in myModle.B_PNRPassenger
                                                         where tbPasssnger.PNRID == PNRID
                                                         select tbPasssnger).ToList();

                    foreach (B_PNRPassenger dbPassenger in dbPassengers)
                    {
                        foreach (B_PNRPassenger passengerInfor in passengerInfors)
                        {
                            //判断数据库旅客姓名与页面专递过来的旅客姓名是否一致,旅客ID相等的前提下
                            if (dbPassenger.PNRPassengerID == passengerInfor.PNRPassengerID &&
                                dbPassenger.passengerName != passengerInfor.passengerName)
                            {
                                //判断页面旅客的姓名是否为空
                                if (!string.IsNullOrEmpty(passengerInfor.passengerName))
                                {
                                    //修改旅客表数据
                                    dbPassenger.passengerName = passengerInfor.passengerName;
                                    //保存修改
                                    myModle.Entry(dbPassenger).State = System.Data.Entity.EntityState.Modified;
                                    myModle.SaveChanges();
                                }
                                else
                                {
                                    msg.Text = string.Format("第【{0}】位旅客的姓名为空，修改失败，请检查！", dbPassenger.passengerNo);
                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                }
                            }
                        }
                    }                    
                    //提交事务
                    scope.Complete();
                    //返回数据
                    msg.State = true;
                    msg.Text = "旅客姓名修改成功！";                    
                }
                catch (Exception e)
                {
                    Console.Write(e);
                    msg.Text = "数据异常！";
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 删除旅客
        /// <summary>
        /// 页面
        /// </summary>
        /// <param name="PNRID"></param>
        /// <returns></returns>
        public ActionResult RemovePassenger(int PNRID)
        {
            
            ViewBag.PNRID = PNRID;
            return View();
        }
        public ActionResult deletePassenger(int PNRID,List<int> passengerIds)
        {
            ReturnJson msg = new ReturnJson();

            try
            {      
                //查询当前PNR中所有旅客
                int passengerNum = myModle.B_PNRPassenger.Count(o => o.PNRID == PNRID);
                if (passengerNum > passengerIds.Count)
                {
                    using (TransactionScope scope = new TransactionScope())
                    {
                        //查询出需要删除的旅客信息
                        List<B_PNRPassenger> dbPassenger = (from tabPassenger in myModle.B_PNRPassenger
                                                                //用户传过来的passengerIds中包含（数据库中的PNRPassengerID）
                                                            where passengerIds.Contains(tabPassenger.PNRPassengerID)
                                                            select tabPassenger).ToList();
                        //删除旅客信息
                        myModle.B_PNRPassenger.RemoveRange(dbPassenger);
                        myModle.SaveChanges();

                        //重新编排旅客编号
                        List<B_PNRPassenger> passengerInfor = (from tabpassenger in myModle.B_PNRPassenger
                                                               where tabpassenger.PNRID == PNRID
                                                               select tabpassenger).ToList();
                        for (int i = 0; i < passengerInfor.Count; i++)
                        {
                            passengerInfor[i].passengerNo = i + 1;
                            //保存修改
                            myModle.Entry(passengerInfor[i]).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }

                        //释放座位
                        List<S_FlightCabin> listFlightCabin = (from tabSegment in myModle.B_PNRSegment
                                                               join tabFlightCabin in myModle.S_FlightCabin on tabSegment.flightCabinID equals tabFlightCabin.flightCabinID
                                                               where tabSegment.PNRID == PNRID
                                                               select tabFlightCabin).ToList();
                        foreach (S_FlightCabin listFlightCabins in listFlightCabin)
                        {
                            //座位数减等勾选的数量
                            listFlightCabins.sellSeatNum -= passengerIds.Count;
                            //保存修改
                            myModle.Entry(listFlightCabins).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }
                        //修改航段表信息
                        List<B_PNRSegment> dbPNRSegment = (from tabPNRSegment in myModle.B_PNRSegment
                                                           where tabPNRSegment.PNRID == PNRID
                                                           select tabPNRSegment).ToList();
                        foreach (B_PNRSegment dbPNRSegments in dbPNRSegment)
                        {
                            dbPNRSegments.bookSeatNum -= passengerIds.Count;
                            myModle.Entry(dbPNRSegments).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }
                        //提交事务
                        scope.Complete();
                        msg.State = true;
                        msg.Text = "删除成功";
                    }
                }
                else
                {
                    msg.Text = "不能删除当前PNR中全部旅客信息";
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
        #region 添加航段
        public ActionResult AddSegment(List<int> flightCabinIDs, int PNRID, List<S_Flight> flightList)
        {
            ReturnJson rtMsg = new ReturnJson();

            try
            {
                //查询当前PNR的旅客人数
                int passengerNum = myModle.B_PNRPassenger.Count(m => m.PNRID == PNRID);


                //开启事务
                using (TransactionScope scope = new TransactionScope())
                {
                    List<B_PNRSegment> listSegment = new List<B_PNRSegment>();

                    List<S_Flight> dbFlight = (from tabFlight in myModle.S_Flight
                                               join tabSegment in myModle.B_PNRSegment on tabFlight.flightID equals tabSegment.flightID
                                               where tabSegment.PNRID == PNRID
                                               select tabFlight).ToList();

                    foreach (S_Flight flightLists in flightList)
                    {
                        foreach (S_Flight dbFlights in dbFlight)
                        {
                            if(flightLists.flightCode == dbFlights.flightCode) {
                                rtMsg.Text = "该航班号已经存在，无法添加";
                                return Json(rtMsg, JsonRequestBehavior.AllowGet);
                            }
                        }
                    }

                    for (int i = 0; i < flightCabinIDs.Count; i++)
                    {
                        //获取航班舱位ID
                        int flightCabinId = flightCabinIDs[i];
                        //查询航班舱位信息
                        S_FlightCabin flightCabinInfor = myModle.S_FlightCabin.Single(f => f.flightCabinID == flightCabinId);

                        //判断座位数是否充足
                        if (flightCabinInfor.sellSeatNum + passengerNum <= flightCabinInfor.seatNum)
                        {
                            //修改卖出座位数
                            flightCabinInfor.sellSeatNum += passengerNum;
                            myModle.Entry(flightCabinInfor).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();

                            //新增航段
                            B_PNRSegment segment = new B_PNRSegment()
                            {
                                PNRID = PNRID,
                                segmentNo = (i + 1),
                                flightID = flightCabinInfor.flightID,
                                cabinTypeID = flightCabinInfor.cabinTypeID,
                                flightCabinID = flightCabinInfor.flightCabinID,
                                bookSeatNum = passengerNum,
                                bookSeatInfo = 1,
                                invalid = true
                            };
                            listSegment.Add(segment);
                        }
                        else
                        {
                            rtMsg.Text = "航班余票数不足，添加航段失败！";
                            return Json(rtMsg, JsonRequestBehavior.AllowGet);
                        }
                    }
                    //保存航段表数据
                    myModle.B_PNRSegment.AddRange(listSegment);
                    myModle.SaveChanges();

                    //查询出当前PNR所有的航段信息，按照航班的日期&起飞时间顺序排序
                    List<B_PNRSegment> dbSegment = (from tbSegment in myModle.B_PNRSegment
                                                    join tbFlight in myModle.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                    orderby tbFlight.flightDate, tbFlight.departureTime
                                                    where tbSegment.PNRID == PNRID
                                                    select tbSegment).ToList();

                    //遍历航段信息
                    for (int i = 0; i < dbSegment.Count; i++)
                    {
                        int segmentId = dbSegment[i].PNRSegmentID;
                        B_PNRSegment segmentInfor = myModle.B_PNRSegment.SingleOrDefault(s => s.PNRSegmentID == segmentId);

                        if (segmentInfor != null)
                        {
                            //修改航段号
                            segmentInfor.segmentNo = i + 1;
                            myModle.Entry(segmentInfor).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }
                    }

                    //添加航段完成，提交事务
                    scope.Complete();
                    //返回数据
                    rtMsg.State = true;
                    rtMsg.Text = "添加航段成功！";
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
        #region 删除航段
        public ActionResult removeSegmentPage(int PNRID)
        {
            ViewBag.PNRID = PNRID;

            return View();
        }

        public ActionResult delSegment(int PNRID, List<int> segmentIds)
        {
            ReturnJson rtMsg = new ReturnJson();

            try
            {
                using (TransactionScope scope = new TransactionScope())
                {
                    //查询出需要删除的航段信息
                    List<B_PNRSegment> delSegmentInfor = (from tbSegment in myModle.B_PNRSegment
                                                          where segmentIds.Contains(tbSegment.PNRSegmentID)
                                                          select tbSegment).ToList();

                    //遍历航段信息
                    foreach (B_PNRSegment delSegment in delSegmentInfor)
                    {
                        //查询对应的航班舱位信息
                        S_FlightCabin flightCabin = myModle.S_FlightCabin.Single(f => f.flightCabinID == delSegment.flightCabinID);

                        //修改卖出座位数
                        flightCabin.sellSeatNum -= delSegment.bookSeatNum;
                        myModle.Entry(flightCabin).State = System.Data.Entity.EntityState.Modified;
                        myModle.SaveChanges();
                    }

                    //删除航段信息
                    myModle.B_PNRSegment.RemoveRange(delSegmentInfor);
                    myModle.SaveChanges();

                    //重新编排航段编号
                    List<B_PNRSegment> dbSegment = (from tbSegment in myModle.B_PNRSegment
                                                    join tbFlight in myModle.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                    orderby tbFlight.flightDate, tbFlight.departureTime
                                                    where tbSegment.PNRID == PNRID
                                                    select tbSegment).ToList();

                    for (int i = 0; i < dbSegment.Count; i++)
                    {
                        var segmentId = dbSegment[i].PNRSegmentID;
                        B_PNRSegment segment = myModle.B_PNRSegment.SingleOrDefault(s => s.PNRSegmentID == segmentId);

                        if (segment != null)
                        {
                            segment.segmentNo = i + 1;
                            myModle.Entry(segment).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();
                        }
                    }

                    //提交事务
                    scope.Complete();

                    //返回数据
                    rtMsg.State = true;
                    rtMsg.Text = "删除航段成功！";
                }
            }
            catch (Exception e)
            {
                Console.Write(e);
                rtMsg.Text = "数据异常！";
            }

            return Json(rtMsg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 部分出票
        /// <summary>
        /// 部分出票页面
        /// </summary>
        /// <param name="PNRID"></param>
        /// <returns></returns>
        public ActionResult PartIssuanceTicket(int PNRID)
        {
            try
            {
                //1. PNR基本信息：编号，状态，联系人信息，出票组信息
                PNRvo pnrInfor = (from tbPNR in myModle.B_PNR
                                  where tbPNR.PNRID == PNRID
                                  select new PNRvo
                                  {
                                      PNRID = tbPNR.PNRID,
                                      PNRNo = tbPNR.PNRNo,
                                      contactName = tbPNR.contactName,
                                      contactPhone = tbPNR.contactPhone,
                                      TicketingInfo = tbPNR.TicketingInfo,
                                      PNRStatus = tbPNR.PNRStatus
                                  }).Single();


                //2.旅客信息 -->左连接
                List<PassengerVo> listPassengerInfor = (from tbPassenger in myModle.B_PNRPassenger
                                                        join tbPassngerType in myModle.S_PassengerType on tbPassenger.passengerTypeID equals tbPassngerType.passengerTypeID
                                                        join tbCertificateType in myModle.S_CertificatesType on tbPassenger.certificatesTypeID equals tbCertificateType.certificatesTypeID
                                                        join tbFrequentPassenger in myModle.S_FrequentPassenger on tbPassenger.frequentPassengerID equals tbFrequentPassenger.frequentPassengerID
                                                        into temp
                                                        from tbTemp in temp.DefaultIfEmpty()
                                                        where tbPassenger.PNRID == PNRID
                                                        select new PassengerVo
                                                        {
                                                            PNRPassengerID = tbPassenger.PNRPassengerID,
                                                            passengerName = tbPassenger.passengerName,
                                                            passengerTypeID = tbPassenger.passengerTypeID,
                                                            passengerType = tbPassngerType.passengerType,
                                                            certificatesTypeID = tbPassenger.certificatesTypeID,
                                                            certificatesType = tbCertificateType.certificatesType,
                                                            certificatesCode = tbPassenger.certificatesCode,
                                                            frequentPassengerNo = tbTemp != null ? tbTemp.frequentPassengerNo : string.Empty

                                                        }).ToList();

                //3.航段航班舱位信息
                List<FlightCabinInfo> listFlightcabinInfor = (from tbSegment in myModle.B_PNRSegment
                                                              join tbFlight in myModle.S_Flight on tbSegment.flightID equals tbFlight.flightID
                                                              join tbOrange in myModle.S_Airport on tbFlight.orangeID equals tbOrange.airportID
                                                              join tbDesdination in myModle.S_Airport on tbFlight.destinationID equals tbDesdination.airportID
                                                              join tbCabinType in myModle.S_CabinType on tbSegment.cabinTypeID equals tbCabinType.cabinTypeID
                                                              join tbFlightCabin in myModle.S_FlightCabin on tbSegment.flightCabinID equals tbFlightCabin.flightCabinID
                                                              orderby tbFlight.flightDate, tbFlight.departureTime
                                                              where tbSegment.PNRID == PNRID
                                                              select new FlightCabinInfo
                                                              {
                                                                  segmentNo = tbSegment.segmentNo.Value,
                                                                  flightCode = tbFlight.flightCode,
                                                                  orangeCity = tbOrange.cityName,//始发地城市
                                                                  destinationCity = tbDesdination.cityName,//目的地城市
                                                                  departureTime = tbFlight.departureTime,
                                                                  arrivalTime = tbFlight.arrivalTime,
                                                                  flightDate = tbFlight.flightDate,
                                                                  cabinTypeCode = tbCabinType.cabinTypeCode,
                                                                  cabinTypeName = tbCabinType.cabinTypeName,
                                                                  cabinPrice = tbFlightCabin.cabinPrice.Value,
                                                                  seatNum = tbSegment.bookSeatNum.Value,
                                                                  segmentType = tbSegment.segmentType,
                                                                  //航段删除
                                                                  PNRSegmentID = tbSegment.PNRSegmentID
                                                              }).ToList();

                //4.其他信息
                List<B_PNROtherInfo> listOtherInfor = myModle.B_PNROtherInfo.Where(o => o.PNRID == PNRID).ToList();

                //5.当前用户的账号信息
                VirtualAccountVo virtualAccountVo = (from tbUser in myModle.S_User
                                                     join tbVirtualAccount in myModle.S_VirtualAccount on tbUser.userID equals tbVirtualAccount.userID
                                                     where tbUser.userID == loginUserID
                                                     select new VirtualAccountVo
                                                     {
                                                         account = tbVirtualAccount.account,
                                                         accountBalance = tbVirtualAccount.accountBalance,
                                                         jobNumber = tbUser.jobNumber
                                                     }).Single();

                //6.查询出已经出票的旅客ID，List<int>整形的列表
                List<int> ticketingPassengerIDs = (from tbPNRPassenger in myModle.B_PNRPassenger
                                                   join tbPNRTicketing in myModle.B_PNRTicketing on tbPNRPassenger.PNRPassengerID equals tbPNRTicketing.PNRPassengerID
                                                   where tbPNRPassenger.PNRID == PNRID
                                                   select tbPNRPassenger.PNRPassengerID).ToList();

                //将数据传递到页面
                ViewBag.PNRInfo = pnrInfor;//PNR基本信息
                ViewBag.passengerInfor = listPassengerInfor;//旅客信息
                ViewBag.flightCabinInfor = listFlightcabinInfor;//航段信息
                ViewBag.PNROtherInfor = listOtherInfor;//其他信息
                ViewBag.VirtualAccount = virtualAccountVo;//当前用户虚拟账户
                ViewBag.ticketingPassengerIDs = ticketingPassengerIDs;//已经出票的用户
                ViewBag.PNRID = PNRID;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
            }
            return View();
        }

        public ActionResult doPartIssueTickets(B_Order order,List<int> checkedPassengerIDs,int payType)
        {
            ReturnJson msg = new ReturnJson();
            //判断PNRID不为空，支付金额=总金额-代理费
            if(order.PNRID!=null&& order.payMoney == order.totalPrice - order.agencyFee)
            {
                using(TransactionScope scope = new TransactionScope())
                {
                    try
                    {
                        //查询出已经出票的旅客ID
                        List<int> ticPassengerIDs = (from tabPassenger in myModle.B_PNRPassenger
                                                     join tabPNRTicketing in myModle.B_PNRTicketing on tabPassenger.PNRPassengerID equals tabPNRTicketing.PNRPassengerID
                                                     where tabPassenger.PNRID == order.PNRID
                                                     select tabPassenger.PNRPassengerID).ToList();
                        //查询出未出票旅客ID
                        List<int> noTicPassengerIDs = (from tabPassenger in myModle.B_PNRPassenger
                                                       //根据PNRID去查询，然后又根据查询已经出票的旅客中不包含现在查询出来的旅客
                                                       where tabPassenger.PNRID == order.PNRID && !ticPassengerIDs.Contains(tabPassenger.PNRPassengerID)
                                                       select tabPassenger.PNRPassengerID).ToList();

                        //循环被勾选的旅客
                        foreach (int passengerId in checkedPassengerIDs)
                        {
                            //判断勾选的旅客是否不包含在未出票中，如果是就提示用户勾选的旅客已经是出票状态了
                            if (!noTicPassengerIDs.Contains(passengerId))
                            {
                                msg.Text = "已选列表中部分旅客已经出票，请检查后再试";
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                        }

                        //判断是全部出票还是部分出票,用户勾选的旅客等于未出票旅客，返回true
                        bool isIssuanceAll = checkedPassengerIDs.Count == noTicPassengerIDs.Count;
                        //PNR状态的验证
                        B_PNR pnrInfor = myModle.B_PNR.Single(o => o.PNRID == order.PNRID);
                        //部分出票和已经订票都能执行
                        if (pnrInfor.PNRStatus == 1 || pnrInfor.PNRStatus == 2)
                        {
                            //查询出当前虚拟账户余额
                            S_VirtualAccount virtualAccount = myModle.S_VirtualAccount.Single(p => p.userID == loginUserID);
                            //判断当前虚拟账户余额是否充足
                            if (virtualAccount.accountBalance >= order.payMoney)
                            {
                                //出票1.生成订单2.
                                string orderNum = "";
                                //计算今天已经出票数
                                DateTime dtToday = DateTime.Now.Date;//得出现在的日期和时间
                                DateTime dtNextDay = dtToday.AddDays(1);//加一天
                                int orderCount = (from tabOrder in myModle.B_Order
                                                  //查询条件，支付时间大于或等于今天并且小于明天，简单地说就是今天时间内
                                                  where tabOrder.payTime >= dtToday && tabOrder.payTime < dtNextDay
                                                  select tabOrder).Count();
                                //(orderCount + 1)查询出今天出的票数加一张就是新的一张ToString("00000")，用0来填充
                                orderNum = "781" + dtToday.ToString("yyyyMMdd") + (orderCount + 1).ToString("00000");

                                //
                                order.orderNo =orderNum;
                                order.operatorID = loginUserID;
                                order.orderStatus = 2;
                                order.payTime = DateTime.Now;
                                //保存订单表
                                myModle.B_Order.Add(order);
                                myModle.SaveChanges();
                                //获取订单ID
                                int orderId = order.orderID;
                                //查询出需要出票的旅客信息
                                List<B_PNRPassenger> checkedPassenger = (from tabPassenger in myModle.B_PNRPassenger
                                                                         where tabPassenger.PNRID == order.PNRID && checkedPassengerIDs.Contains(tabPassenger.PNRPassengerID)
                                                                         select tabPassenger).ToList();
                                if (checkedPassenger.Count > 0)
                                {
                                    List<FlightCabinInfo> flightCabinInfor = (from tabSegment in myModle.B_PNRSegment
                                                                              join tabFlight in myModle.S_Flight on tabSegment.flightID equals tabFlight.flightID
                                                                              join tabFlightCabin in myModle.S_FlightCabin on tabSegment.flightCabinID equals tabFlightCabin.flightCabinID
                                                                              join tabCabinType in myModle.S_CabinType on tabSegment.cabinTypeID equals tabCabinType.cabinTypeID
                                                                              where tabSegment.PNRID == order.PNRID && tabSegment.segmentType == 0 && tabSegment.invalid == true
                                                                              select new FlightCabinInfo
                                                                              {
                                                                                  PNRSegmentID = tabSegment.PNRSegmentID,//PNR航段ID
                                                                                  flightID = tabFlight.flightID,//航班ID
                                                                                  cabinTypeID = tabCabinType.cabinTypeID,//舱位类型ID
                                                                                  flightCabinID = tabFlightCabin.flightCabinID,//航班舱位ID
                                                                                  cabinPrice = tabFlightCabin.cabinPrice.Value//价格--舱位价格
                                                                              }).ToList();
                                    //计算本次出票所需的票号数,需要出票的旅客总数*航段数
                                    int needTicketNum = checkedPassenger.Count * flightCabinInfor.Count;
                                    //统计当前用户可用票号
                                    //查询当前用户可用票号段信息
                                    List<S_Ticket> ticketInfors = (from tabTicket in myModle.S_Ticket
                                                                   //用户为当前登录的用户，开始票号和结束票号比较
                                                                   where tabTicket.userID == loginUserID && tabTicket.currentTicketNo.CompareTo(tabTicket.endTicketNo) <= 0
                                                                   select tabTicket).ToList();
                                    List<TicketInfo> listTicket = new List<TicketInfo>();
                                    foreach (S_Ticket ticketInfor in ticketInfors)
                                    {
                                        //获取当前票号和结束票号
                                        string curTicketNo = ticketInfor.currentTicketNo.Trim();
                                        string endTicketNo = ticketInfor.endTicketNo.Trim();
                                        //计算可用的票号
                                        int canUseNum = Convert.ToInt32(endTicketNo) - Convert.ToInt32(curTicketNo) + 1;
                                        TicketInfo ticket = new TicketInfo
                                        {
                                            ticket = ticketInfor,
                                            canUseNum = canUseNum,//可用客票数
                                            useNum = 0,
                                            currentTicketNo=ticketInfor.currentTicketNo//当前客票号等于查询出来的当前客票号
                                        };
                                        listTicket.Add(ticket);//把所需要的数据存放到容器里
                                    }
                                    //判断可用票号是否充足
                                    if (listTicket.Sum(t => t.canUseNum) >= needTicketNum)
                                    {
                                        
                                        foreach (FlightCabinInfo flightCabin in flightCabinInfor)
                                        {
                                            foreach (B_PNRPassenger passenger in checkedPassenger)
                                            {
                                                //出票
                                                //先新增电子客票表，再新增出票组表
                                                //1.电子客票表
                                                //生成票号
                                                string ticketNo = "";

                                                foreach (TicketInfo ticket in listTicket)
                                                {
                                                    if (ticket.canUseNum > 0)
                                                    {
                                                        ticketNo = ticket.currentTicketNo;
                                                        //计算下一张票号数
                                                        //数据类型转换，把票号一大串数字换成整形
                                                        int intTicketNo = Convert.ToInt32(ticketNo);
                                                        //每用一张票号当前的票号就要加一
                                                        int intNextNo = intTicketNo + 1;
                                                        //数据类型转换,把整形数字换成一大串数字
                                                        string strNextNo = intNextNo.ToString("0000000000");
                                                        //可用票号数减一
                                                        ticket.canUseNum--;
                                                        //使用客票数加一
                                                        ticket.useNum++;
                                                        //当前客票号等于转换回来的string类型的客票
                                                        ticket.currentTicketNo = strNextNo;
                                                        break;
                                                    }
                                                }
                                                //获取当前时间
                                                DateTime dtNow = DateTime.Now;
                                                //新增电子客票表,表对象
                                                B_ETicket eTicket = new B_ETicket()
                                                {
                                                    PNRPassengerID=passenger.PNRPassengerID,//旅客ID
                                                    oderID=orderId,//订单id
                                                    flightID = flightCabin.flightID,//航班ID，查询航班表
                                                    ticketNo ="E781-"+ticketNo,//票号，当前票号处理后
                                                    tickingTime = dtNow,//出票时间
                                                    ticketPrice = flightCabin.cabinPrice,//票价，查询航班表
                                                    payTypeID = payType,//付款方式ID，页面传过来
                                                    operatorID = loginUserID,//操作人ID,当前登录用户
                                                    operatingTtime = dtNow,//操作时间，现在时间
                                                    eTicketStatusID = 1,//票联状态ID
                                                    invoiceStatusID = 1,//发票状态ID
                                                    cabinTypeID = flightCabin.cabinTypeID,//舱位等级ID，查询航班表
                                                    flightCabinID = flightCabin.flightCabinID//航班舱位ID，查询航班表
                                                };
                                                //保存到数据库
                                                myModle.B_ETicket.Add(eTicket);
                                                myModle.SaveChanges();

                                                //获取电子客票ID
                                                int eTicketID = eTicket.ETicketID;

                                                //新增出票组表
                                                B_PNRTicketing pnrTicket = new B_PNRTicketing()
                                                {
                                                    PNRID = order.PNRID,//PNRID
                                                    PNRPassengerID = passenger.PNRPassengerID,//旅客ID，查询旅客信息表
                                                    PNRSegmentID = flightCabin.PNRSegmentID,//航段ID，查询航班表
                                                    ETicketID = eTicketID,//电子客票ID
                                                    TicketingTime = dtNow,
                                                    orderID = orderId
                                                };
                                                //保存到数据库
                                                myModle.B_PNRTicketing.Add(pnrTicket);
                                                myModle.SaveChanges();
                                                
                                            }                                            
                                        }
                                        //修改PNR状态，变量返回的是true还是false，三目表达式
                                        pnrInfor.PNRStatus = isIssuanceAll ? 3 : 2;
                                        myModle.Entry(pnrInfor).State = System.Data.Entity.EntityState.Modified;
                                        myModle.SaveChanges();

                                        //修改航段表
                                        List<B_PNRSegment> dbSegment = (from tabSegment in myModle.B_PNRSegment
                                                                        where tabSegment.PNRID == order.PNRID
                                                                        select tabSegment).ToList();

                                        foreach (B_PNRSegment segment in dbSegment)
                                        {
                                            //三目表达式
                                            segment.bookSeatInfo = isIssuanceAll ? 3 : 2;//订座情况

                                            myModle.Entry(segment).State = System.Data.Entity.EntityState.Modified;
                                            myModle.SaveChanges();
                                        }
                                        //修改票号表
                                        foreach (TicketInfo ticketInfor in listTicket)
                                        {
                                            if (ticketInfor.useNum > 0)
                                            {
                                                S_Ticket dbTicket = ticketInfor.ticket;
                                                dbTicket.currentTicketNo = ticketInfor.currentTicketNo;

                                                myModle.Entry(dbTicket).State = System.Data.Entity.EntityState.Modified;
                                                myModle.SaveChanges();
                                            }
                                        }
                                        //修改当前用户账户余额
                                        virtualAccount.accountBalance -= order.payMoney;
                                        myModle.Entry(virtualAccount).State = System.Data.Entity.EntityState.Modified;
                                        myModle.SaveChanges();

                                        //修改当前用户账户余额
                                        B_TransactionRecord transactionRecord = new B_TransactionRecord()
                                        {
                                            virtualAccountID = virtualAccount.virtualAccountID,//虚拟账户ID
                                            transactionType = 0,//交易类型
                                            transactionMoney = order.payMoney,//交易金额
                                            transactionTime = DateTime.Now,//交易时间
                                            userID = loginUserID//操作人
                                        };
                                        myModle.B_TransactionRecord.Add(transactionRecord);
                                        myModle.SaveChanges();
                                        //部分出票操作完成
                                        //提交事务
                                        scope.Complete();

                                        msg.State = true;
                                        msg.Text = "出票成功！";
                                        msg.Object = orderId;
                                    }                                    
                                    else
                                    {
                                        msg.Text = "您所剩余的票号不足，出票失败！";
                                    }
                                }
                            }
                            else
                            {
                                msg.Text = "余额不足";                                
                            }
                        }
                        else
                        {
                            //如果是全部出票的情况下，部分出票也不允许
                            if (pnrInfor.PNRStatus == 3)
                            {
                                msg.Text = string.Format("PNR： {0}已经全部出票，不能重复出票", pnrInfor.PNRNo);
                            }
                            else if (pnrInfor.PNRStatus == 0)
                            {
                                msg.Text = string.Format("PNR： {0}已经取消订座，不能出票", pnrInfor.PNRNo);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e);
                        msg.Text = "数据异常";
                    }
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        #endregion
        #region 其他信息修改
        public ActionResult EditOtherInfo(int PNRID)
        {
            ViewBag.PNRID = PNRID;
            return View();
        }

        public ActionResult updateOtherInfor(string contactName,string contactPhone,string ticketingInfo,List<int> checkedOtherIds,string addOtherInfor,int PNRID)
        {
            ReturnJson msg = new ReturnJson();
            //数据验证
            //判断联系人姓名和电话
            if (!string.IsNullOrEmpty(contactName) && !string.IsNullOrEmpty(contactPhone))
            {
                //判断出票信息不能为空
                if (!string.IsNullOrEmpty(ticketingInfo))
                {
                    //开始事务
                    using(TransactionScope scope = new TransactionScope())
                    {
                        try
                        {
                            //修改联系人姓名，电话，出票信息
                            B_PNR dbPNR = (from tabPNR in myModle.B_PNR
                                           where tabPNR.PNRID == PNRID
                                           select tabPNR).Single();
                            dbPNR.contactName = contactName;
                            dbPNR.contactPhone = contactPhone;
                            dbPNR.TicketingInfo = ticketingInfo;

                            myModle.Entry(dbPNR).State = System.Data.Entity.EntityState.Modified;
                            myModle.SaveChanges();

                            //判断要删除的其他信息不能为空
                            if(checkedOtherIds!=null&& checkedOtherIds.Count != 0)
                            {
                                List<B_PNROtherInfo> dbOtherInfo = (from tabOtherInfo in myModle.B_PNROtherInfo
                                                                        //查询条件，用户传过来的数据中的其他信息包含数据库中PNROtherInfoID
                                                                    where checkedOtherIds.Contains(tabOtherInfo.PNROtherInfoID)
                                                                    select tabOtherInfo).ToList();

                                myModle.B_PNROtherInfo.RemoveRange(dbOtherInfo);
                                myModle.SaveChanges();
                            }

                            //添加其他信息
                            if (!string.IsNullOrEmpty(addOtherInfor))
                            {
                                string[] arrOtherInfor = addOtherInfor.Split(Environment.NewLine.ToCharArray());
                                List<B_PNROtherInfo> listOtherInfor = new List<B_PNROtherInfo>();
                                foreach (string otherInfor in arrOtherInfor)
                                {
                                    listOtherInfor.Add(new B_PNROtherInfo()
                                    {
                                        PNRID = PNRID,
                                        otherInfo = otherInfor
                                    });
                                }

                                myModle.B_PNROtherInfo.AddRange(listOtherInfor);
                                myModle.SaveChanges();
                            }
                            //其他信息修改完成，提交事务
                            scope.Complete();

                            //返回
                            msg.State = true;
                            msg.Text = "其他信息修改成功！";
                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e);
                            msg.Text = "数据异常";
                        }
                    }
                }
                else
                {
                    msg.Text = "出票信息不能为空";
                }
            }
            else
            {
                msg.Text = "联系人姓名和电话不能为空";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using FJDPXT.Common;
using System.Transactions;


namespace FJDPXT.Areas.PNR.Controllers
{
    public class PNRAppointmentController : Controller
    {
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
        // GET: PNR/PNRAppointment
        public ActionResult Index()
        {
            //查询所有的机场数据
            List<S_Airport> airport = myModel.S_Airport.ToList();
            //传递数据到页面
            ViewBag.airports = airport;

            //清除session中关于PNR预订的部分
            Session.Remove("flightCabinIDs");
            Session.Remove("flightCabinInfos");

            return View();
        }
        #region 航班选择
        public ActionResult SelectFlightPage()
        {
            return View();
        }
        /// <summary>
        /// 查询航段-机场iD-日期
        /// </summary>
        /// <param name="segmentNum"></param>
        /// <param name="airports"></param>
        /// <param name="dates"></param>
        /// <returns></returns>
        public ActionResult SelectFlight(int segmentNum,int[] airports,string[] dates)
        {
            //存放查询出的信息，容器14q
            List<SegmentVo> listSegment = new List<SegmentVo>();
            //验证接收到的参数是否正确,航段数为什么等于？，因为两个机场才有一个航段
            if (segmentNum == airports.Length - 1 && segmentNum == dates.Length)
            {
                //循环航段数，查询出需要的数据
                for (int sengmentIndex = 0; sengmentIndex < segmentNum; sengmentIndex++)
                {
                    //存放本航段的数据，容器14w
                    SegmentVo segmentvo = new SegmentVo();
                    //1.本航段的起飞降落机场的iD
                    int orangeID = airports[sengmentIndex];//起飞机场ID
                    int destinationID = airports[sengmentIndex+1];//降落机场ID
                    //2.查询起飞机场和降落机场的数据
                    S_Airport orangeAirport = myModel.S_Airport.Single(o => o.airportID == orangeID);//起飞机场
                    S_Airport destinationAirport = myModel.S_Airport.Single(o => o.airportID == destinationID);//降落机场
                    //2.1设置segmentVo的起飞机场和降落机场信息
                    segmentvo.orangeCityName = orangeAirport.cityName;//起飞机场名称
                    segmentvo.destinationCityName = destinationAirport.cityName;//降落城市名称
                    segmentvo.orangeAirportName = orangeAirport.airportName;//起飞机场名称
                    segmentvo.destinationAirportName = destinationAirport.airportName;//降落机场名称
                    //3设置航段日期
                    segmentvo.strDate = dates[sengmentIndex];
                    DateTime flightDate = Convert.ToDateTime(dates[sengmentIndex]);
                    //4.查询符合该航段条件的航班
                    var query = from tabFlight in myModel.S_Flight
                                join tabOrangeAirport in myModel.S_Airport
                                  on tabFlight.orangeID equals tabOrangeAirport.airportID
                                join tabDestinationAirport in myModel.S_Airport
                                  on tabFlight.destinationID equals tabDestinationAirport.airportID
                                join tabPlanType in myModel.S_PlanType
                                  on tabFlight.planTypeID equals tabPlanType.planTypeID
                                where tabFlight.flightDate == flightDate && tabFlight.orangeID == orangeID && tabFlight.destinationID == destinationID
                                orderby tabFlight.departureTime
                                select new FlightVo()
                                {
                                    flightID = tabFlight.flightID,//航班ID
                                    flightCode = tabFlight.flightCode,//航班号
                                    orangeID = tabFlight.orangeID,//起飞地ID
                                    destinationID = tabFlight.destinationID,//降落地ID
                                    flightDate = tabFlight.flightDate,//起飞日期
                                    departureTime = tabFlight.departureTime,//起飞时间
                                    arrivalTime = tabFlight.arrivalTime,//到达时间
                                    planTypeID = tabFlight.planTypeID,//机型ID
                                    standardPrice = tabFlight.standardPrice,//价格
                                                                            //扩展部分
                                    orangeCityName = tabOrangeAirport.cityName,//起城市场名称
                                    orangeAirportName = tabOrangeAirport.airportName,//起飞机场名称
                                    destinationCityName = tabOrangeAirport.cityName,//降落城市名称
                                    destinationAirportName = tabOrangeAirport.airportName,//降落机场名称
                                    planTypeName = tabPlanType.planTypeName,//飞机机型名称
                                    flightCabins = (from tabFlightCabins in myModel.S_FlightCabin
                                                    join tabCabinType in myModel.S_CabinType
                                                    on tabFlightCabins.cabinTypeID equals tabCabinType.cabinTypeID
                                                    where tabFlightCabins.flightID == tabFlight.flightID
                                                    select new FlightCabinVo()
                                                    {
                                                        flightCabinID = tabFlightCabins.flightCabinID,//航班舱位ID
                                                        flightID = tabFlightCabins.flightID,//航班ID
                                                        cabinTypeID = tabFlightCabins.cabinTypeID,//舱位等级ID
                                                        seatNum = tabFlightCabins.seatNum,//座位数
                                                        cabinPrice = tabFlightCabins.cabinPrice,//价格
                                                        sellSeatNum = tabFlightCabins.sellSeatNum,//售出座位数
                                                        //扩展字段
                                                        cabinTypeCode = tabCabinType.cabinTypeCode,//舱位等级编号
                                                        cabinTypeName = tabCabinType.cabinTypeName//舱位等级名称
                                                    }).ToList()
                                };
                    //判断日期是否是当天的
                    DateTime dtToDay = DateTime.Now.Date;//获取今天的日期
                    TimeSpan tsNow = DateTime.Now.TimeOfDay;//获取现在的时间
                    //判断查询的航班是今天
                    if (flightDate==dtToDay)
                    {
                        query = query.Where(o => o.departureTime > tsNow);
                    }
                    //查询出航段对应的航班数据
                    List<FlightVo> flightList = query.ToList();

                    segmentvo.flightList = flightList;
                    //容器14q
                    listSegment.Add(segmentvo);
                }
            }
            return Json(listSegment,JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 旅客信息录入页面
        public ActionResult EnterInfoPage(int[] flightCabinIDs, int passengerNum )
        {

            //===查询出用户选择的航班舱位数据
            List<FlightCabinInfo> flightCabinInfos = new List<FlightCabinInfo>();
            for (int i = 0; i < flightCabinIDs.Length; i++)
            {
                //取出航班舱位ID                
                int flightCabinID = flightCabinIDs[i];

                //查询航班信息 航班舱位信息
                FlightCabinInfo flightCabinInfo = (from tabFlightCabin in myModel.S_FlightCabin
                                                   join tabFlight in myModel.S_Flight
                                                    on tabFlightCabin.flightID equals tabFlight.flightID
                                                   join tabOrangeAirport in myModel.S_Airport
                                                    on tabFlight.orangeID equals tabOrangeAirport.airportID
                                                   join tabDestinationAirport in myModel.S_Airport
                                                    on tabFlight.destinationID equals tabDestinationAirport.airportID
                                                   join tabCabinType in myModel.S_CabinType
                                                    on tabFlightCabin.cabinTypeID equals tabCabinType.cabinTypeID
                                                   where tabFlightCabin.flightCabinID == flightCabinID
                                                   select new FlightCabinInfo()
                                                   {
                                                       flightID = tabFlight.flightID,//航班ID
                                                       flightCode = tabFlight.flightCode,//航班号
                                                       orangeID = tabFlight.orangeID,//始发地ID
                                                       destinationID = tabFlight.destinationID,//目的地ID
                                                       departureTime = tabFlight.departureTime,//起飞时间
                                                       arrivalTime = tabFlight.arrivalTime,//降落时间
                                                       planTypeID = tabFlight.planTypeID,//飞机类型ID
                                                       flightDate = tabFlight.flightDate,//起飞日期
                                                       standardPrice = tabFlight.standardPrice,//标准价格

                                                       cabinTypeID = tabFlightCabin.cabinTypeID.Value,//舱位类型ID
                                                       cabinTypeCode = tabCabinType.cabinTypeCode,//舱位类型编号
                                                       cabinTypeName = tabCabinType.cabinTypeName,//舱位类型名称
                                                       cabinPrice = tabFlightCabin.cabinPrice.Value,//舱位价格
                                                       seatNum = passengerNum,//预订座位数量 就是乘客数量
                                                       orangeCity = tabOrangeAirport.cityName,//始发地城市名称
                                                       destinationCity = tabDestinationAirport.cityName//目的地城市名称
                                                   }).SingleOrDefault();
                flightCabinInfos.Add(flightCabinInfo);
            }//==查询旅客类型  for 下拉框
            List<S_PassengerType> passengerTypes = myModel.S_PassengerType.ToList();

            //==查询证件类型  for 下拉框
            List<S_CertificatesType> certificatesTypes = myModel.S_CertificatesType.ToList();


            //把数据传递到页面
            ViewBag.flightCabinInfos = flightCabinInfos;//旅客选择航班舱位信息
            ViewBag.passengerNum = passengerNum;//旅客人数
            ViewBag.passengerTypes = passengerTypes;//旅客类型
            ViewBag.certificatesTypes = certificatesTypes;//证件类型

            //==将选择的航班舱位ID保存到session，用于最后保存PNR时数据验证
            Session["flightCabinIDs"] = flightCabinIDs;
            Session["flightCabinInfos"] = flightCabinInfos;

            return View();
        }
        #endregion
        #region 用户信息数据验证
        public ActionResult EnterInfo(List<PassengerVo> passengers,string contactName,string contactPhone, string ticketingInfo, string PNROtherInfo, int[] flightCabinIDs)
        {
            ReturnJson msg = new ReturnJson();
            //数据验证
            //验证旅客所有信息passengers
            if (passengers!=null)
            {
                //遍历所有的用户数据
                for (int i = 0; i < passengers.Count; i++)
                {
                    //旅客姓名
                    if (string.IsNullOrEmpty(passengers[i].passengerName))
                    {
                        msg.Text = string.Format("第{0}个旅客姓名未填写,请检查", i + 1);
                        return Json(msg, JsonRequestBehavior.AllowGet);
                    }
                    //旅客证件号
                    if (string.IsNullOrEmpty(passengers[i].certificatesCode))
                    {
                        msg.Text = string.Format("第{0}个旅客的证件号未填写,请检查", i + 1);
                        return Json(msg, JsonRequestBehavior.AllowGet);
                    }
                    if (passengers[i].certificatesTypeID == 1)
                    {
                        //验证身份证号是否正确，创建好的一个身份证验证方法IdCardHelper
                        if (!IdCardHelper.CheckIdCard(passengers[i].certificatesCode))
                        {
                            msg.Text = string.Format("第{0}个旅客的身份证号不正确,请检查", i + 1);
                            return Json(msg, JsonRequestBehavior.AllowGet);
                        }
                        //根据身份证号获取年龄
                        int passengerAge = IdCardHelper.GetAgeByIdCard(passengers[i].certificatesCode);
                        //判断旅客类型和年龄是否符合
                        //选择的旅客类型为成人,但是身份证年龄小于18
                        if (passengers[i].certificatesTypeID==1&&passengerAge<18)
                        {
                            msg.Text = string.Format("第{0}个旅客的身份证年龄小于18岁,与所选择的'成人'身份不符,请检查", i + 1);
                            return Json(msg, JsonRequestBehavior.AllowGet);
                        }
                        //选择的旅客类型为儿童，但是身份证年龄大于或等于18
                        if(passengers[i].certificatesTypeID == 2 && passengerAge >= 18)
                        {
                            msg.Text = string.Format("第{0}个旅客的身份证年龄大于或等于18岁,与所选择的'儿童'身份不符,请检查", i + 1);
                            return Json(msg, JsonRequestBehavior.AllowGet);
                        }
                        //判断用户输入的身份证号码是否相同
                        for (int j = i + 1; j < passengers.Count; j++)
                        {
                            if (passengers[j].certificatesTypeID == 1 &&
                                passengers[i].certificatesCode.Trim() == passengers[j].certificatesCode.Trim())
                            {
                                msg.Text = string.Format("第{0}个旅客的身份证号与第{1}个旅客的身份证号相同.请检查,请检查", i + 1, j + 1);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                        }
                    }
                    //检查常旅客号
                    if (!string.IsNullOrEmpty(passengers[i].frequentPassengerNo))
                    {
                        try
                        {
                            //解决查询表出现的bug，查询一个数组或者一个列表里的索引值如果是直接o => o.frequentPassengerNo == passengers[i].frequentPassengerNo.Trim();这样写很容易出现错误但是不报错
                            //所以先把索引passengers[i].frequentPassengerNo.Trim()赋值给一个变量frequentPassengerNo再使用变量去查询
                            string frequentPassengerNo = passengers[i].frequentPassengerNo.Trim();
                            //判断常旅客号是否存在
                            S_FrequentPassenger dbFrequentPassenger = myModel.S_FrequentPassenger
                                .Single(o => o.frequentPassengerNo == frequentPassengerNo);
                            //判断常旅客号信息和添加的用户信息是否一致,判断常旅客号里的姓名，身份证，等信息是否和数据库中的一致
                            if (dbFrequentPassenger.frequentPassengerName.Trim() == passengers[i].passengerName.Trim() &&
                                dbFrequentPassenger.certificatesTypeID == passengers[i].certificatesTypeID &&
                                dbFrequentPassenger.certificatesCode.Trim() == passengers[i].certificatesCode.Trim())
                            {
                                //常旅客号信息匹配
                                //检查常旅客号的同时把 常旅客号转成对应的常旅客ID
                                passengers[i].frequentPassengerID = dbFrequentPassenger.frequentPassengerID;
                            }
                            else
                            {
                                msg.Text = string.Format("第{0}个旅客的信息与常旅客信息不匹配,请检查", i + 1);
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }

                        }
                        catch (Exception e)
                        {
                            Console.WriteLine(e);
                            msg.Text = string.Format("第{0}个旅客的常旅客号不存在,请检查", i + 1);
                            return Json(msg, JsonRequestBehavior.AllowGet);
                        }
                    }
                }                
            }
            else
            {
                msg.Text = "旅客信息数据异常";
            }
            //验证flightCabinIDs
            //比较传递的flightCabinIDs和session中的是否一致
            //as C#提供的一个数据转换关键字 ,如果可以转换,就返回转换后的值,如果不能转换就返回null,不会出现异常
            //as 使用时搭配引用数据类型
            int[] sessionflightCabinIDs = Session["flightCabinIDs"] as int[];
            //判断sessionFlightCabinIDs是否有数据
            if(sessionflightCabinIDs == null)
            {
                msg.Text = "数据异常";
                return Json(msg, JsonRequestBehavior.AllowGet);
            }
            //判断 sessionFlightCabinIDs的长度和传递sessionFlightCabinIDs元素个数是否一致
            if (sessionflightCabinIDs.Length!= flightCabinIDs.Length)
            {
                msg.Text = "数据异常";
                return Json(msg, JsonRequestBehavior.AllowGet);
            }
            //判断sessionFlightCabinIDs与传递sessionFlightCabinIDs是否一致
            for (int i = 0; i < sessionflightCabinIDs.Length; i++)
            {
                if(sessionflightCabinIDs[i]!= flightCabinIDs[i])
                {
                    msg.Text = "数据异常";
                    return Json(msg, JsonRequestBehavior.AllowGet);
                }
            }
            //=联系人信息。姓名
            if (string.IsNullOrEmpty(contactName))
            {
                msg.Text = "请填写联系人姓名";
                return Json(msg, JsonRequestBehavior.AllowGet);
            }
            //=联系人信息。电话
            if (string.IsNullOrEmpty(contactPhone))
            {
                msg.Text = "请填写联系人电话";
                return Json(msg, JsonRequestBehavior.AllowGet);
            }

            //保存数据
            //==1-保存 B_PNR 表
            //=1.1-生成PNR编号
            string strPNRNo = PNRCodeHelper.CreatePNR();
            //=1.2-构建要保存的B_PNR对象
            B_PNR savePNR = new B_PNR()
            {
                PNRNo = strPNRNo,//PNR 编号
                contactName= contactName,//联系人姓名
                contactPhone= contactPhone,//联系人电话
                TicketingInfo= ticketingInfo,
                PNRStatus = 1,//1：PNR生成后默认状态，即已经订座（HK）,2:部分出票，3：全部出票，0：取消订座   -1：pnr删除,数据库有提示
                operatorID = loginUserID,//操作人Id 就是当前登录用户ID
                createTime = DateTime.Now//创建时间 现在
            };
            //涉及多表的操作 启用事务
            using (TransactionScope scope=new TransactionScope())
            {
                try
                {
                    //保存B_PNR的信息
                    myModel.B_PNR.Add(savePNR);
                    if (myModel.SaveChanges()>0)
                    {
                        //获取保存后的PNRID
                        int PNRID = savePNR.PNRID;
                        //保存PNR旅客信息
                        //=2.1-把List<PassengerVo>转为 List<B_PNRPassenger>类型,方法的开始用户传过来的信息List<PassengerVo>
                        //容器保存转换好的数据，用循环来转换
                        List<B_PNRPassenger> savePNRPassengers = new List<B_PNRPassenger>();
                        //循环用户传过来的数据
                        foreach (PassengerVo passenger in passengers)
                        {
                            //给上面容器添加新的数据new B_PNRPassenger(){里面是新的数据}
                            savePNRPassengers.Add(new B_PNRPassenger()
                            {
                                PNRID = PNRID,//PNRID
                                passengerNo = passenger.passengerNo,//PNR旅客编号
                                passengerName = passenger.passengerName,///旅客姓名
                                passengerTypeID = passenger.passengerTypeID,//旅客类型Id
                                certificatesTypeID = passenger.certificatesTypeID,//旅客证件类型ID
                                certificatesCode = passenger.certificatesCode,//证件号
                                frequentPassengerID = passenger.frequentPassengerID//常旅客ID
                            });                                
                        }
                        myModel.B_PNRPassenger.AddRange(savePNRPassengers);//Add可能是保存单个数据或者对象，AddRange可能是保存多个数据
                        myModel.SaveChanges();//这里不用if语句是因为如果保存不成功就执行到上一个if方法的else方法内
                        //3-保存PNR航段信息 B_PNRSegment
                        //在保存航段信息的同时 需要修改航班舱位表S_FlightCabin对应数据的售出座位数
                        //-遍历用户选择的航班舱位ID,构建需要保存的PNR航段信息 B_PNRSegment数据 
                        //      以及修改航班舱位表S_FlightCabin对应数据的售出座位数
                        List<B_PNRSegment> savePNRSegments = new List<B_PNRSegment>();
                        //flightCabinIDs用户所选的航班舱位ID
                        for (int i = 0; i < flightCabinIDs.Length; i++)
                        {
                            int flightCbainId = flightCabinIDs[i];
                            try
                            {
                                //根据航班ID，查询出航班舱位数据，S_FlightCabin航班舱位表
                                S_FlightCabin dbFlightCabin = myModel.S_FlightCabin.Single(o => o.flightCabinID == flightCbainId);
                                //判断剩余座位数是否充足，旅客人数<=座位数-售出座位数
                                if (passengers.Count <= dbFlightCabin.seatNum - dbFlightCabin.sellSeatNum)
                                {
                                    //先占座位数,售出座位数加旅客人数
                                     dbFlightCabin.sellSeatNum += passengers.Count;
                                    //保存修改后的航班舱位数据
                                    myModel.Entry(dbFlightCabin).State = System.Data.Entity.EntityState.Modified;
                                    myModel.SaveChanges();
                                    //构建需要保存PNR航段数据
                                    savePNRSegments.Add(new B_PNRSegment()
                                    {
                                        PNRID = PNRID,//PNRID
                                        segmentNo = i + 1,//PNR航段序号
                                        flightID = dbFlightCabin.flightID,//航班ID
                                        cabinTypeID = dbFlightCabin.cabinTypeID,//舱位等级Id
                                        flightCabinID = dbFlightCabin.flightCabinID,//航班舱位ID
                                        bookSeatNum = passengers.Count,//订座数=旅客人数
                                        bookSeatInfo = 1,//订座情况: 1：已预订座位；,2:部分出票，3：全部出票,0：取消订座 
                                        segmentType = 0,//航段类型标识  0:预订 1:换开
                                        invalid = true,//数据有效
                                    });
                                }
                                else
                                {
                                    //剩余座位数
                                    msg.Text = "剩余座位数不足";
                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                }
                            }
                            catch (Exception e)
                            {
                                Console.WriteLine(e);
                                msg.Text = "选择的航班舱位信息异常，请检查";
                                return Json(msg, JsonRequestBehavior.AllowGet);
                            }
                        }
                        //AddRange保存多条数据，AddRange是保存的范围
                        myModel.B_PNRSegment.AddRange(savePNRSegments);
                        myModel.SaveChanges();
                        //保存PNR航段信息到数据库
                        //B_PNROtherInfo是B_PNR其他信息
                        List<B_PNROtherInfo> savePNROtherInfos = new List<B_PNROtherInfo>();
                        //按照换行符分割PNR其他信息
                        //Environment.NewLine代表就是换行符，NewLine是C#定义好的换行符
                        string[] strOtherInfos = PNROtherInfo.Split(Environment.NewLine.ToArray());
                        foreach(string strOtherInfo in strOtherInfos)
                        {
                            if (!string.IsNullOrEmpty(strOtherInfo))//!string.IsNullOrEmpty不为空
                            {
                                savePNROtherInfos.Add(new B_PNROtherInfo()
                                {
                                    PNRID = PNRID,//PNRID
                                    otherInfo = strOtherInfo//其他信息
                                });
                            }
                        }
                        //保存PNR信息到数据库
                        myModel.B_PNROtherInfo.AddRange(savePNROtherInfos);
                        myModel.SaveChanges();
                        //提交事务
                        scope.Complete();

                        //清除session
                        Session.Remove("flightCabinIDs");
                        Session.Remove("flightCabinInfos");

                        // 返回PNR生成成功
                        msg.State = true;
                        msg.Text = "预订成功！请确认订座信息！";
                        msg.Code = PNRID.ToString();//将pnrID返回页面
                    }
                    else
                    {
                        msg.Text = "订座失败";
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
        #region RNR信息确认
        public ActionResult PnrInfoConfirm(int PNRID)
        {
            try
            {
                //查询出PNR信息
                B_PNR pnrInfo = myModel.B_PNR.Single(o => o.PNRID == PNRID);
                //查询PNR旅客信息
                //C#join默认是内连接，就是说两个表格取他们相同的。左连接是左边的表格全部输出，左边有的右边没有，右边就用null代替，右边有的左边没有的就把左边的去掉。右连接和左连接反过来。outer 外连接取两边的所有，左边有的右边没有的，右就用null代替。
                List<PassengerVo> passengers =
                    (from tabPassenger in myModel.B_PNRPassenger
                         //旅客类型
                     join tabPassengerType in myModel.S_PassengerType
                        on tabPassenger.passengerTypeID equals tabPassengerType.passengerTypeID
                     //证件类型
                     join tabCertificatesType in myModel.S_CertificatesType
                        on tabPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                     //常旅客表 !!!!左连接!!!
                     join tabFrequentPassengerA in myModel.S_FrequentPassenger
                        on tabPassenger.frequentPassengerID equals tabFrequentPassengerA.frequentPassengerID
                        into temp
                     from tabFrequentPassenger in temp.DefaultIfEmpty()
                     where tabPassenger.PNRID == PNRID
                     select new PassengerVo()
                     {
                         PNRPassengerID = tabPassenger.PNRPassengerID,//pnr旅客Id
                         PNRID = tabPassenger.PNRID,//PNRID
                         passengerNo = tabPassenger.passengerNo,//pnr旅客编号
                         passengerName = tabPassenger.passengerName,//pnr旅客姓名
                         passengerTypeID = tabPassenger.passengerTypeID,//旅客类型ID
                         certificatesTypeID = tabPassenger.certificatesTypeID,//旅客证件类型ID
                         certificatesCode = tabPassenger.certificatesCode,//证件号
                         frequentPassengerID = tabPassenger.frequentPassengerID,//常旅客Id
                         //
                         passengerType = tabPassengerType.passengerType,//旅客类型
                         certificatesType = tabCertificatesType.certificatesType,//证件类型
                         //左连接表的取值 使用三目运算符，左连接一定要用三目运算符，因为左连接有的值可能为空
                         frequentPassengerNo = tabFrequentPassenger != null ? tabFrequentPassenger.frequentPassengerNo : ""
                     }).ToList();
                //查询PNR航段信息
                List<FlightCabinInfo> flightCabinInfos =
                    (from tabPNRSegment in myModel.B_PNRSegment//B_PNRSegment为航段表
                         //航班舱位表
                     join tabFlightCabin in myModel.S_FlightCabin//S_FlightCabin为航班舱位表
                        //航段表的外建连接航班舱位表的主键
                        on tabPNRSegment.flightCabinID equals tabFlightCabin.flightCabinID
                     //航班表
                     join tabFlight in myModel.S_Flight//S_Flight为航班表
                        //航段表的外建连接航班表的主键
                        on tabPNRSegment.flightID equals tabFlight.flightID
                     //舱位等级表
                     join tabCabinType in myModel.S_CabinType//S_CabinType为舱位等级表
                       //航段表的外建连接舱位等级表的主键
                       on tabPNRSegment.cabinTypeID equals tabCabinType.cabinTypeID
                     //机场表 起飞
                     join tabOrangeAirport in myModel.S_Airport//S_Airport为机场表
                     //航班表的外键去连接机场表的主键，两个不同的建也可以连表查询
                       on tabFlight.orangeID equals tabOrangeAirport.airportID
                     //机场表 降落
                     join tabDestinationAirport in myModel.S_Airport
                     //航班表的外键去连接机场表的主键，两个不同的建也可以连表查询
                       on tabFlight.destinationID equals tabDestinationAirport.airportID
                     //机型表
                     join tabPlanType in myModel.S_PlanType
                        on tabFlight.planTypeID equals tabPlanType.planTypeID
                     //条件
                     where tabPNRSegment.PNRID == PNRID
                     select new FlightCabinInfo()
                     {
                         flightID = tabFlight.flightID,//航班ID
                         flightCode = tabFlight.flightCode,//航班号
                         orangeID = tabFlight.orangeID,//起飞机场ID
                         destinationID = tabFlight.destinationID,//降落机场ID
                         flightDate = tabFlight.flightDate,//航班日期
                         departureTime = tabFlight.departureTime,//起飞时间
                         arrivalTime = tabFlight.arrivalTime,//降落时间
                         planTypeID = tabFlight.planTypeID,//机型ID
                         standardPrice = tabFlight.standardPrice,//标准价格

                         PNRSegmentID = tabPNRSegment.PNRSegmentID,//航段ID
                         segmentNo = tabPNRSegment.segmentNo.Value,//航段号，可能存在空的值所以用value来转换
                         flightCabinID = tabPNRSegment.flightCabinID.Value,//航班舱位ID，可能存在空的值所以用value来转换
                         cabinTypeID = tabPNRSegment.cabinTypeID.Value,//航班舱位类型ID，可能存在空的值所以用value来转换
                         seatNum = tabPNRSegment.bookSeatNum.Value,//订座数量，可能存在空的值所以用value来转换
                         bookSeatInfo = tabPNRSegment.bookSeatInfo.Value,//订座状态，可能存在空的值所以用value来转换
                         segmentType = tabPNRSegment.segmentType,//航段类型
                         //扩展
                         orangeCity = tabOrangeAirport.cityName,//起飞城市
                         destinationCity = tabDestinationAirport.cityName,//降落城市
                         planTypeName = tabPlanType.planTypeName,//机型
                         cabinPrice = tabFlightCabin.cabinPrice.Value,//舱位价格
                         cabinTypeCode = tabCabinType.cabinTypeCode,//舱位等级编号
                         cabinTypeName = tabCabinType.cabinTypeName//舱位等级名称

                     }).ToList();
                //查询其他PNR信息,查询B_PNROtherInfo的表格里所有数据
                List<B_PNROtherInfo> pnrOtherInfos = myModel.B_PNROtherInfo
                    .Where(o => o.PNRID == PNRID)
                    .ToList();


                //查询登录用户对应的虚拟账户信息
                VirtualAccountVo virtualAccount =
                    (from tabVirtualAccount in myModel.S_VirtualAccount
                     join tabUser in myModel.S_User
                        on tabVirtualAccount.userID equals tabUser.userID
                     where tabVirtualAccount.userID == loginUserID//等于现在登录的用户
                     select new VirtualAccountVo()
                     {
                         virtualAccountID = tabVirtualAccount.virtualAccountID,//虚拟账户ID
                         account = tabVirtualAccount.account,//虚拟账号
                         accountBalance = tabVirtualAccount.accountBalance,//虚拟账户余额
                         userID = tabVirtualAccount.userID,//用户ID
                         //扩展
                         jobNumber = tabUser.jobNumber//工号
                     }).Single();



                //将查询出的数据传递到页面
                ViewBag.pnrInfo = pnrInfo;//查询出PNR信息并传到页面
                ViewBag.passengers = passengers;//查询PNR旅客信息并传到页面
                ViewBag.flightCabinInfos = flightCabinInfos;//查询PNR航段信息并传到页面
                ViewBag.pnrOtherInfos = pnrOtherInfos;//查询B_PNR其他信息表格数据并传到页面
                ViewBag.virtualAccount = virtualAccount;//查询登录用户对应的虚拟账户信息并传到页面
                return View();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                //重定向到PNR预定的第一个页面
                return RedirectToAction("Index");
            }           
        }
        #endregion
        #region 出票操作
        /// <summary>
        /// 
        /// </summary>
        /// <param name="order">订单信息</param>
        /// <param name="payType">支付方式</param>
        /// <returns></returns>
        public ActionResult IssueTickets(B_Order order,int payType)
        {
            ReturnJson msg = new ReturnJson();
            //数据验证
            //PNRID不能为0，支付金额=总金额-代理费
            if (order.PNRID != 0 && order.payMoney == order.totalPrice - order.agencyFee)
            {
                //多表保存，开始事务
                using (TransactionScope scope = new TransactionScope())
                {
                    try
                    {
                        //验证PNR状态
                        B_PNR dbPNR = myModel.B_PNR.Single(o => o.PNRID == order.PNRID);
                        //验证PNR状态是否为1，只有状态为1的才能全部出票，1:PNR生成后默认状态，即已经订座（HK），2:全部出票，3:全部
                        if (dbPNR.PNRStatus == 1)
                        {
                            //验证当前登录用户的虚拟账户余额是否充足
                            S_VirtualAccount dbVirtualAccount = myModel.S_VirtualAccount.Single(o => o.userID == loginUserID);
                            //判断虚拟账户余额是否大于等于支付金额
                            if (dbVirtualAccount.accountBalance >= order.payMoney)
                            {
                                //检查PNR旅客数,ToList不支持o => o.PNRID == order.PNRID表达式，需要在条件上添加
                                List<B_PNRPassenger> dbPNRPassenger = myModel.B_PNRPassenger.Where(o => o.PNRID == order.PNRID).ToList();
                                //PNR中的旅客数需要大于0
                                if (dbPNRPassenger.Count > 0)
                                {
                                    //检查PNR下的航段数                                    
                                    List<FlightCabinInfo> flightCabinInfos =
                                        (from tabPNRSegment in myModel.B_PNRSegment
                                             //航班舱位表
                                         join tabFlightCabin in myModel.S_FlightCabin
                                            on tabPNRSegment.flightCabinID equals tabFlightCabin.flightCabinID
                                         //航班表
                                         join tabFlight in myModel.S_Flight
                                            on tabPNRSegment.flightID equals tabFlight.flightID
                                         //舱位等级表
                                         join tabCabinType in myModel.S_CabinType
                                           on tabPNRSegment.cabinTypeID equals tabCabinType.cabinTypeID
                                         //机场表 起飞
                                         join tabOrangeAirport in myModel.S_Airport
                                           on tabFlight.orangeID equals tabOrangeAirport.airportID
                                         //机场表 降落
                                         join tabDestinationAirport in myModel.S_Airport
                                           on tabFlight.destinationID equals tabDestinationAirport.airportID
                                         //机型表
                                         join tabPlanType in myModel.S_PlanType
                                            on tabFlight.planTypeID equals tabPlanType.planTypeID
                                         //条件
                                         where tabPNRSegment.PNRID == order.PNRID
                                         select new FlightCabinInfo()
                                         {
                                             flightID = tabFlight.flightID,//航班ID
                                             flightCode = tabFlight.flightCode,//航班号
                                             orangeID = tabFlight.orangeID,//起飞机场ID
                                             destinationID = tabFlight.destinationID,//降落机场ID
                                             flightDate = tabFlight.flightDate,//航班日期
                                             departureTime = tabFlight.departureTime,//起飞时间
                                             arrivalTime = tabFlight.arrivalTime,//降落时间
                                             planTypeID = tabFlight.planTypeID,//机型ID
                                             standardPrice = tabFlight.standardPrice,//标准价格

                                             PNRSegmentID = tabPNRSegment.PNRSegmentID,//航段ID
                                             segmentNo = tabPNRSegment.segmentNo.Value,//航段号
                                             flightCabinID = tabPNRSegment.flightCabinID.Value,//航班舱位ID
                                             cabinTypeID = tabPNRSegment.cabinTypeID.Value,//航班舱位类型ID
                                             seatNum = tabPNRSegment.bookSeatNum.Value,//订座数量
                                             bookSeatInfo = tabPNRSegment.bookSeatInfo.Value,//订座状态
                                             segmentType = tabPNRSegment.segmentType,//航段类型
                                             //扩展
                                             orangeCity = tabOrangeAirport.cityName,//起飞城市
                                             destinationCity = tabDestinationAirport.cityName,//降落城市
                                             planTypeName = tabPlanType.planTypeName,//机型
                                             cabinPrice = tabFlightCabin.cabinPrice.Value,//舱位价格
                                             cabinTypeCode = tabCabinType.cabinTypeCode,//舱位等级编号
                                             cabinTypeName = tabCabinType.cabinTypeName//舱位等级名称
                                    }).ToList();
                                    //判断PNE航段数
                                    if (flightCabinInfos.Count>0)
                                    {
                                        //判断当前用户的票号数是否大于需要的票号数
                                        //计算需要的票号数
                                        int needTicketNum = dbPNRPassenger.Count * flightCabinInfos.Count;
                                        //查询
                                        List<S_Ticket> dbUserTickets =
                                            (from tabTicket in myModel.S_Ticket
                                             where tabTicket.userID == loginUserID &&//当前登录用户
                                             //CompareTo比较的意思，当前票号比较结束票号，当前票号<结束票号用<0表示,同时告诉控制台我还有票号可出。当前票号>结束票号用>0表示，告诉控制台我已经没有票号可出。当前票号=结束票号用=0表示，告诉控制台我还可以出票号。
                                             tabTicket.currentTicketNo.CompareTo(tabTicket.endTicketNo) <= 0 &&// 当前票号小于等于结束票号
                                             tabTicket.isEnable == true// 票号需要被启用
                                             select tabTicket
                                             ).ToList();

                                        //统计出该用户各个票号段的可用客票信息
                                        //容器
                                        List<TicketInfo> ticketInfos = new List<TicketInfo>();    
                                                                          
                                        foreach (S_Ticket userTicket in dbUserTickets)
                                        {
                                            //获取当前票号和结束票号
                                            string currentTicketNo = userTicket.currentTicketNo;
                                            string endTicketNo = userTicket.endTicketNo;
                                            //计算可用使用的票数，Convert数据类型转换，ToInt32将以数字的字符串转成整数，+1的意思就是就算当前和结束相同还有一张可以使用 
                                            int canUseTicketNum = Convert.ToInt32(endTicketNo) - Convert.ToInt32(currentTicketNo) + 1;

                                            //存放ticketInfos，将{}里的数据保存到上面创建好的容器里
                                            ticketInfos.Add(new TicketInfo()
                                            {
                                                ticket = userTicket,
                                                canUseNum = canUseTicketNum,//可用票数
                                                useNum = 0,//本次使用数 0,目前还没有使用
                                                currentTicketNo = currentTicketNo
                                            });
                                        }
                                        //6.4-计算总的可用票数
                                        int totalCanUseTicketNum = ticketInfos.Sum(o => o.canUseNum);
                                        //6.5- 判断总的可用票数是否大于等于需要的票号数
                                        if (totalCanUseTicketNum >= needTicketNum)
                                        {
                                            //数据保存
                                            DateTime dtNow = DateTime.Now;//当前时间
                                            //保存订单信息
                                            //生成订单编号
                                            string strOrderNo = "";
                                            //查询出当天的订单数
                                            DateTime dtTody = dtNow.Date;//获取当前的日期时间dtNow，Date是获取当天的日期没有时间
                                            DateTime dtNextDay = dtTody.AddDays(1);//在当天的基础上加一天，就是第二天
                                            //查询出今天的订单，大于今天的0.0.0时，小于第二天的0.0.0时就是今天范围
                                            int todayOrderCount = (from tabOrder in myModel.B_Order
                                                                   where tabOrder.payTime > dtTody &&
                                                                         tabOrder.payTime < dtNextDay
                                                                   select tabOrder).Count();
                                            //拼接订单编号
                                            //781订单的前缀，+今天的年月日+（今天的订单数+1），一共五位数，不够用0来凑
                                            strOrderNo = "781" + dtTody.ToString("yyyyMMdd") + (todayOrderCount + 1).ToString("00000");
                                            //设置订单的其他信息
                                            order.orderNo = strOrderNo;//订单编号等于拼接好的订单编号
                                            order.operatorID = loginUserID;//操作人ID等于登录的ID
                                            order.payTime = dtNow;//订单支付时间
                                            order.orderStatus = 1;// 1:未支付，2：订单已经支付；0：订单取消
                                            //保存
                                            myModel.B_Order.Add(order);
                                            if (myModel.SaveChanges() > 0)
                                            {
                                                //获取出订单ID
                                                int savedOrderID = order.orderID;
                                                //2-遍历PNR航段和PNR旅客数据,生成保存电子客票(B_ETicket)信息,
                                                //  再保存PNR出票组信息(B_PNRTicketing)
                                                //2.1-双层循环 遍历PNR航段 和 PNR旅客
                                                foreach (FlightCabinInfo flightCabinInfo in flightCabinInfos)
                                                {
                                                    foreach (B_PNRPassenger passenger in dbPNRPassenger)
                                                    {
                                                        //2.2-获取出电子客票号
                                                        string strTicketNo = "";
                                                        //获取出电子票号
                                                        foreach (TicketInfo ticketInfo in ticketInfos)
                                                        {
                                                            //判断该票号信息是否还有可以使用的票号
                                                            if (ticketInfo.canUseNum > 0)
                                                            {
                                                                //拼接当前客票号
                                                                strTicketNo = "E781-" + ticketInfo.currentTicketNo;
                                                                //获取当前票号,Convert将一个数据类型转成另一个数据类型
                                                                int intTicketNo = Convert.ToInt32(ticketInfo.currentTicketNo);
                                                                //计算下一个客票号
                                                                string nextTicketNo = (intTicketNo + 1).ToString("0000000000");
                                                                //更新ticketInfo的信息
                                                                ticketInfo.currentTicketNo = nextTicketNo;//
                                                                ticketInfo.canUseNum--;//可用票号减一，因为你用了多少就要减多少
                                                                ticketInfo.useNum++;//使用票数+1
                                                                break;
                                                            }
                                                        }
                                                        //判断票号不为空
                                                        if (!string.IsNullOrEmpty(strTicketNo))
                                                        {
                                                            //保存电子客票(B_ETicket)信息
                                                            B_ETicket saveETicket = new B_ETicket()
                                                            {
                                                                PNRPassengerID = passenger.PNRPassengerID,//PNR旅客ID
                                                                oderID = savedOrderID,//订单ID
                                                                flightID = flightCabinInfo.flightID,//航班ID
                                                                flightCabinID = flightCabinInfo.flightCabinID,//航班舱位ID
                                                                cabinTypeID = flightCabinInfo.cabinTypeID,//舱位等级ID
                                                                ticketPrice = flightCabinInfo.cabinPrice,//舱位价格
                                                                ticketNo = strTicketNo,//电子票号
                                                                tickingTime = dtNow,//出票时间
                                                                payTypeID = payType,//付款方式
                                                                operatorID = loginUserID,//操作人ID
                                                                operatingTtime = dtNow,//操作时间
                                                                eTicketStatusID = 1, //1：正常状态 2：换开  3：退票
                                                                invoiceStatusID = 1, //1：未开发票 2：发票已回收 
                                                            };
                                                            myModel.B_ETicket.Add(saveETicket);
                                                            myModel.SaveChanges();
                                                            //获取电子客票后保存的主键ID
                                                            int saveETicketID = saveETicket.ETicketID;
                                                            //判断电子客票的ID是否大于0
                                                            if (saveETicketID > 0)
                                                            {
                                                                //PNR出票组信息(B_PNRTicketing)
                                                                B_PNRTicketing savePNRTicketing = new B_PNRTicketing()
                                                                {
                                                                    PNRID = order.PNRID, //PNRID 
                                                                    PNRPassengerID = passenger.PNRPassengerID, //PNR旅客ID
                                                                    PNRSegmentID = flightCabinInfo.PNRSegmentID, //PNR航段ID
                                                                    ETicketID = saveETicketID, //电子客票ID
                                                                    TicketingTime = dtNow, //出票时间
                                                                    orderID = savedOrderID, //订单ID
                                                                };
                                                                myModel.B_PNRTicketing.Add(savePNRTicketing);
                                                                if (myModel.SaveChanges() > 0)
                                                                {

                                                                }
                                                                else
                                                                {
                                                                    msg.Text = "PNR出票组信息保存失败,出票失败";
                                                                    return Json(msg, JsonRequestBehavior.AllowGet);
                                                                }
                                                            }
                                                            else
                                                            {
                                                                msg.Text = "电子客票号获取失败，出票失败";
                                                                return Json(msg, JsonRequestBehavior.AllowGet);
                                                            }
                                                        }
                                                        else
                                                        {
                                                            msg.Text = "电子客票号获取失败，出票失败";
                                                            return Json(msg, JsonRequestBehavior.AllowGet);
                                                        }
                                                    }
                                                }
                                                //修改PNR状态为3，全部出票
                                                dbPNR.PNRStatus = 3;
                                                //State状态，
                                                myModel.Entry(dbPNR).State = System.Data.Entity.EntityState.Modified;
                                               if (myModel.SaveChanges() > 0)
                                                {
                                                    //修改航段出票信息（状态）为3：全部出票
                                                    //查询出PNR对应的航段信息
                                                    List<B_PNRSegment> dbPNRSegments = myModel.B_PNRSegment
                                                       .Where(o => o.PNRID == order.PNRID)
                                                       .ToList();
                                                    //遍历修改PN航段
                                                    foreach (B_PNRSegment dbPNRSegment in dbPNRSegments)
                                                    {
                                                        //修改订座情况 为 2:已经出票
                                                        dbPNRSegment.bookSeatInfo = 2;
                                                        myModel.Entry(dbPNRSegment).State = System.Data.Entity.EntityState.Modified;
                                                        if (myModel.SaveChanges() < 1)
                                                        {
                                                            msg.Text = "PNR航段订座情况更新失败,出票失败";
                                                            return Json(msg, JsonRequestBehavior.AllowGet);
                                                        }
                                                    }
                                                    //更新用户的票号信息
                                                    //遍历票号信息
                                                    foreach (TicketInfo ticketInfo in ticketInfos)
                                                    {
                                                        //判断该票号信息是否有使用，有使用才更新
                                                        if (ticketInfo.useNum > 0)
                                                        {
                                                            //获取出需要更新的S_Ticket对象
                                                            S_Ticket saveTicket = ticketInfo.ticket;
                                                            //更新saveTicket的当前票号
                                                            saveTicket.currentTicketNo = ticketInfo.currentTicketNo;
                                                            myModel.Entry(saveTicket).State = System.Data.Entity.EntityState.Modified;
                                                            if (myModel.SaveChanges() < 1)
                                                            {
                                                                msg.Text = "票号数据更新失败，出票失败";
                                                                return Json(msg, JsonRequestBehavior.AllowGet);
                                                            }
                                                        }
                                                    }
                                                    //更新当前虚拟账户信息，虚拟账户扣除相应的可用余额
                                                    //虚拟账户表的账户余额=虚拟账户表的账户余额-订单表的支付金额
                                                    //dbVirtualAccount虚拟账户表的变量
                                                    dbVirtualAccount.accountBalance = dbVirtualAccount.accountBalance - order.payMoney;
                                                    //更新虚拟账户表的余额
                                                    myModel.Entry(dbVirtualAccount).State = System.Data.Entity.EntityState.Modified;
                                                    if (myModel.SaveChanges() > 0)
                                                    {
                                                        //虚拟账户的交易记录添加一条数据
                                                        B_TransactionRecord saveTransactionRecord = new B_TransactionRecord()
                                                        {
                                                            //虚拟账户ID=虚拟账户表的虚拟账户ID
                                                            virtualAccountID = dbVirtualAccount.virtualAccountID,//虚拟账户ID
                                                            //交易金额=订单表的支付金额
                                                            transactionMoney = order.payMoney,
                                                            //支付类型=1.交易类型  0：支出，1：收入
                                                            transactionType = 1,//类型：1 收入
                                                            //用户ID=当前登录用户ID
                                                            userID=loginUserID,
                                                        };
                                                        myModel.B_TransactionRecord.Add(saveTransactionRecord);
                                                        if (myModel.SaveChanges() > 0)
                                                        {
                                                            //提交事务
                                                            scope.Complete();
                                                            msg.State = true;
                                                            msg.Text = "出票成功";
                                                            msg.Object = savedOrderID;//把订单Id返回
                                                        }
                                                        else
                                                        {
                                                            msg.Text = "交易记录失败，出票失败";
                                                        }
                                                    }
                                                    else
                                                    {
                                                        msg.Text = "虚拟账户扣款失败";
                                                    }
                                                }
                                                else
                                                {
                                                    msg.Text = "PNR状态失败，出票失败";
                                                }
                                            }
                                            else
                                            {
                                                msg.Text = "订单保存失败";
                                            }
                                        }
                                        else
                                        {
                                            msg.Text = "您没有足够的电子客票,无法出票";
                                        }
                                    }
                                    else
                                    {
                                        msg.Text = "PNR下没有需要出票的航段信息，无法出票";
                                    }
                                }
                                else
                                {
                                    msg.Text = "PNR下没有需要出票的旅客,无法出票";
                                }
                            }
                            else
                            {
                                msg.Text = "余额不足";
                            }
                        }
                        else if (dbPNR.PNRStatus==2)
                        {
                            msg.Text = string.Format("PNR【{0}】已经部分出票，不能在此出票:", dbPNR.PNRNo);
                        }else if (dbPNR.PNRStatus == 3)
                        {
                           msg.Text = string.Format("PNR【{0}】已经出票，不能出票操作:", dbPNR.PNRNo);
                        }else if (dbPNR.PNRStatus == 0)
                        {
                           msg.Text = string.Format("PNR【{0}】已经取消出票，不能出票:", dbPNR.PNRNo);
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e);
                        msg.Text = "数据异常出票失败";
                    }
                }
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region PNR出票结果页面
        public ActionResult IssuanceTicketResult(int orderID)
        {
            try
            {
                //查询出订单信息
                OrderVo order = (from taborder in myModel.B_Order
                                 join tabPNR in myModel.B_PNR
                                   on taborder.PNRID equals tabPNR.PNRID
                                 where taborder.orderID == orderID
                                 select new OrderVo()
                                 {
                                     orderID = taborder.orderID,//订单ID
                                     orderNo = taborder.orderNo,//订单编号
                                     PNRNo = tabPNR.PNRNo//PNR编号
                                 }).Single();
                //查询出旅客信息
                List<ETicketVo> eTicketVo = (from tabETicket in myModel.B_ETicket
                                             join tabOrder in myModel.B_Order
                                                  on tabETicket.oderID equals tabOrder.orderID
                                             join tabPNRPassenger in myModel.B_PNRPassenger
                                               on tabETicket.PNRPassengerID equals tabPNRPassenger.PNRPassengerID
                                             where tabOrder.orderID == orderID
                                             select new ETicketVo()
                                             {
                                                 passengerNo = tabPNRPassenger.passengerNo.Value,//旅客编号
                                                 passengerName = tabPNRPassenger.passengerName,//旅客姓名
                                                 ticketPrice = tabOrder.totalPrice,//票价
                                                 ticketNo = tabETicket.ticketNo,//电子客票号
                                             }).ToList();
                //把数据返回到页面
                ViewBag.order = order;
                ViewBag.eTicketVo = eTicketVo;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return RedirectToAction("Index");
            }
            return View();
        }
        #endregion
    }
}
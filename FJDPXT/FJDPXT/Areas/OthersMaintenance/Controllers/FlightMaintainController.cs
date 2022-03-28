using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Transactions;

namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class FlightMaintainController : Controller
    {
        // GET: OthersMaintenance/FlightMaintain
        //其他 --> 航班维护

        //实例化model
        FJDPXTEntities1 myModel = new FJDPXTEntities1();

        #region 航班维护

        /// <summary>
        /// 航班维护
        /// </summary>
        /// <returns></returns>
        public ActionResult Index()
        {

            List<S_Airport> airports = myModel.S_Airport.ToList();//查询所有机场/城市详细
            List<S_CabinType> cabinTypes = myModel.S_CabinType.OrderByDescending(o => o.discountRate).ToList();//查询出所有的错舱位类型
            List<S_PlanType> planTypes = myModel.S_PlanType.ToList();//查询出所有的飞机机型
            ViewBag.airports = airports;
            ViewBag.cabinTypes = cabinTypes;
            ViewBag.planTypes = planTypes;

            return View();
        }

        /// <summary>
        /// 多条件分页查询航班数据
        /// </summary>
        /// <param name="layuiTablePage">分页信息</param>
        /// <param name="orangeAirportID">航班起飞机场ID</param>
        /// <param name="destinationAirportID">航班降落机场ID</param>
        /// <param name="flightDateLimit">航班起飞日期时间段</param>
        /// <returns></returns>
        public ActionResult SelectFlight(LayuiTablePage layuiTablePage, int? orangeAirportID, int? destinationAirportID, string flightDateLimit)
        {
            var queryLinq = from tabFlight in myModel.S_Flight
                            join tabOrangeAirport in myModel.S_Airport on tabFlight.orangeID equals tabOrangeAirport.airportID
                            join tabDestinationAirport in myModel.S_Airport on tabFlight.destinationID equals tabDestinationAirport.airportID
                            join tabPlanType in myModel.S_PlanType on tabFlight.planTypeID equals tabPlanType.planTypeID
                            select new FlightVo()
                            {
                                flightID = tabFlight.flightID, //航班ID
                                flightCode = tabFlight.flightCode, //航班号
                                orangeID = tabFlight.orangeID, //起飞机场ID
                                destinationID = tabFlight.destinationID, //目的地机场ID
                                flightDate = tabFlight.flightDate, //起飞日期
                                departureTime = tabFlight.departureTime, //起飞时间
                                arrivalTime = tabFlight.arrivalTime, //降落时间
                                planTypeID = tabFlight.planTypeID, //飞机机型ID
                                standardPrice = tabFlight.standardPrice, //标准价格
                                                                         //扩展字段
                                orangeCityName = tabOrangeAirport.cityName, //起飞城市名称
                                orangeAirportName = tabOrangeAirport.airportName, //起飞机场名称
                                destinationCityName = tabDestinationAirport.cityName, //降落城市名称
                                destinationAirportName = tabDestinationAirport.airportName, //降落机场名称
                                planTypeName = tabPlanType.planTypeName, //机型名称
                            };

            //多条件的处理
            //起飞机场id
            if (orangeAirportID != null && orangeAirportID > 0)
            {
                queryLinq = queryLinq.Where(o => o.orangeID == orangeAirportID);
            }
            //降落机场ID
            if (destinationAirportID != null && destinationAirportID > 0)
            {
                queryLinq = queryLinq.Where(o => o.destinationID == destinationAirportID);
            }
            //航班起飞日期时间段
            if (!string.IsNullOrEmpty(flightDateLimit))
            {
                flightDateLimit = flightDateLimit.Replace(" - ", "~");
                //对日期段进行分割
                string[] strDates = flightDateLimit.Split('~');
                if (strDates.Length == 2)
                {
                    //将日期字符串转为Date类型
                    DateTime dtStart = Convert.ToDateTime(strDates[0].Trim());
                    DateTime dtEnd = Convert.ToDateTime(strDates[1].Trim());

                    queryLinq = queryLinq.Where(o => o.flightDate >= dtStart && o.flightDate <= dtEnd);
                }
            }

            //获取数据总行数
            int totalRow = queryLinq.Count();
            //分页查询数据
            List<FlightVo> list = queryLinq
                .OrderByDescending(o => o.flightID)//倒序查询
                .Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();

            //组装layuiTable所需的数据
            LayuiTableData<FlightVo> layuiTableData = new LayuiTableData<FlightVo>()
            {
                count = totalRow,
                data = list
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 根据航班ID查询航班舱位信息
        /// </summary>
        /// <param name="layuiTablePage">分页信息</param>
        /// <param name="flightID">航班ID</param>
        /// <returns></returns>
        public ActionResult SelectFlightCabinByFlightID(LayuiTablePage layuiTablePage, int flightID)
        {
            var queryLinq = from tabFlightCabin in myModel.S_FlightCabin
                            join tabCabinType in myModel.S_CabinType on tabFlightCabin.cabinTypeID equals tabCabinType.cabinTypeID
                            where tabFlightCabin.flightID == flightID
                            select new FlightCabinVo()
                            {
                                flightCabinID = tabFlightCabin.flightCabinID,//航班舱位ID
                                flightID = tabFlightCabin.flightID,//航班ID
                                cabinTypeID = tabFlightCabin.cabinTypeID,//航班舱位类型id
                                seatNum = tabFlightCabin.seatNum,//航班舱位座位数
                                cabinPrice = tabFlightCabin.cabinPrice,//航班舱位价格
                                sellSeatNum = tabFlightCabin.sellSeatNum,//航班售出座位数
                                                                         //扩展
                                cabinTypeCode = tabCabinType.cabinTypeCode,//航班舱位类型编号
                                cabinTypeName = tabCabinType.cabinTypeName//航班舱位类型名称
                            };

            //总条数
            int totalRow = queryLinq.Count();
            //分页查询数据
            List<FlightCabinVo> list = queryLinq
                .OrderBy(o => o.flightCabinID)
                .Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();

            LayuiTableData<FlightCabinVo> layuiTableData = new LayuiTableData<FlightCabinVo>()
            {
                count = totalRow,
                data = list
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }


        /// <summary>
        /// 新增航班
        /// </summary>
        /// <param name="flightInfo">航班信息</param>
        /// <param name="cabinTypeIDs">舱位等级ID</param>
        /// <param name="seatNums">舱位座位数</param>
        /// <param name="cabinPrices">舱位价格</param>
        /// <returns></returns>
        public ActionResult InsertFlight(S_Flight flightInfo, int[] cabinTypeIDs, int[] seatNums, decimal[] cabinPrices)
        {
            ReturnJson msg = new ReturnJson();

            //验证 航班号
            if (!string.IsNullOrEmpty(flightInfo.flightCode))
            {
                //验证 航班标准价格
                if (flightInfo.standardPrice > 0)
                {
                    //验证航班舱位信息是否完整
                    if (cabinTypeIDs.Length == seatNums.Length && seatNums.Length == cabinPrices.Length)
                    {
                        bool bolFlightCabinOk = true;//标记航班舱位验证是否通过
                        //验证
                        for (int i = 0; i < cabinTypeIDs.Length; i++)
                        {
                            if (cabinTypeIDs[i] < 1)
                            {
                                msg.Text = "航班舱位数据验证失败";
                                bolFlightCabinOk = false;
                                break;
                            }
                            if (seatNums[i] > 0 && cabinPrices[i] < 1)
                            {
                                msg.Text = "航班座位数大于0时，航班舱位价格不能为0";
                                bolFlightCabinOk = false;
                                break;
                            }
                        }

                        if (bolFlightCabinOk)
                        {
                            using (TransactionScope scope = new TransactionScope())
                            {
                                try
                                {
                                    //查询出航班选择的飞机机型信息
                                    S_PlanType dbPlanType = myModel.S_PlanType.Single(o => o.planTypeID == flightInfo.planTypeID);

                                    //检查已经分配的座位数是否等于机型座位数
                                    if (seatNums.Sum() == dbPlanType.seatNum)
                                    {
                                        //保存
                                        myModel.S_Flight.Add(flightInfo);
                                        if (myModel.SaveChanges() > 0)
                                        {
                                            int flightID = flightInfo.flightID;//获取保存的航班信息
                                            List<S_FlightCabin> addFlightCabins = new List<S_FlightCabin>();
                                            for (int i = 0; i < cabinTypeIDs.Length; i++)
                                            {
                                                S_FlightCabin addFlightCabin = new S_FlightCabin()
                                                {
                                                    flightID = flightID,//航班号
                                                    cabinTypeID = cabinTypeIDs[i],//舱位类型ID
                                                    seatNum = seatNums[i],//舱位座位数
                                                    cabinPrice = cabinPrices[i],//舱位价格
                                                    sellSeatNum = 0//售出座位数 默认0
                                                };
                                                addFlightCabins.Add(addFlightCabin);
                                            }

                                            //==保存航班舱位想到数据库
                                            myModel.S_FlightCabin.AddRange(addFlightCabins);
                                            if (myModel.SaveChanges() > 0)
                                            {
                                                //提交事务
                                                scope.Complete();

                                                msg.State = true;
                                                msg.Text = "航班信息保存成功";
                                            }
                                            else
                                            {
                                                msg.Text = "航班保存失败";
                                            }
                                        }
                                        else
                                        {
                                            msg.Text = "航班信息保存失败";
                                        }
                                    }
                                    else
                                    {
                                        msg.Text = "分配的舱位座位数之和与航班机型座位数不匹配，请检查";
                                    }
                                }
                                catch (Exception e)
                                {
                                    msg.Text = "请选择航班机型";
                                }
                            }
                        }
                    }
                    else
                    {
                        msg.Text = "请填写完整座位数和舱位价格，如果不设置该舱位，请填写0";
                    }
                }
                else
                {
                    msg.Text = "请设置航班标准价格";
                }
            }
            else
            {
                msg.Text = "请输入航班号";
            }

            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 根据航班ID查询航班 和 航班舱位信息
        /// </summary>
        /// <param name="flightID"></param>
        /// <returns></returns>
        public ActionResult SelectFlightInfoByID(int flightID)
        {
            try
            {
                //查询航班信息
                S_Flight flight = myModel.S_Flight.Single(o => o.flightID == flightID);
                //查询航班舱位信息
                List<S_FlightCabin> flightCabins = myModel.S_FlightCabin.Where(o => o.flightID == flightID).ToList();

                //返回数据
                return Json(new { flight = flight, flightCabins = flightCabins }, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }


        /// <summary>
        /// 修改航班信息
        /// </summary>
        /// <param name="flightInfo">航班信息</param>
        /// <param name="cabinTypeIDs">舱位等级ID</param>
        /// <param name="seatNums">舱位座位数</param>
        /// <param name="cabinPrices">舱位价格</param>
        /// <returns></returns>
        public ActionResult UpdateFlight(S_Flight flightInfo, int[] cabinTypeIDs, int[] seatNums, decimal[] cabinPrices)
        {
            ReturnJson msg = new ReturnJson();

            //验证 航班号
            if (!string.IsNullOrEmpty(flightInfo.flightCode))
            {
                //验证 航班标准价格
                if (flightInfo.standardPrice > 0)
                {
                    //验证航班舱位信息是否完整
                    if (cabinTypeIDs.Length == seatNums.Length && seatNums.Length == cabinPrices.Length)
                    {
                        bool bolFlightCabinOk = true;//标记航班舱位验证是否通过
                        //验证
                        for (int i = 0; i < cabinTypeIDs.Length; i++)
                        {
                            if (cabinTypeIDs[i] < 1)
                            {
                                msg.Text = "航班舱位数据验证失败";
                                bolFlightCabinOk = false;
                                break;
                            }
                            if (seatNums[i] > 0 && cabinPrices[i] < 1)
                            {
                                msg.Text = "航班座位数大于0时，航班舱位价格不能为0";
                                bolFlightCabinOk = false;
                                break;
                            }
                        }

                        if (bolFlightCabinOk)
                        {
                            using (TransactionScope scope = new TransactionScope())
                            {
                                try
                                {
                                    //查询出航班选择的飞机机型信息
                                    S_PlanType dbPlanType = myModel.S_PlanType.Single(o => o.planTypeID == flightInfo.planTypeID);

                                    //检查已经分配的座位数是否等于机型座位数
                                    if (seatNums.Sum() == dbPlanType.seatNum)
                                    {
                                        //保存修改
                                        myModel.Entry(flightInfo).State = System.Data.Entity.EntityState.Modified;
                                        if (myModel.SaveChanges() > 0)
                                        {
                                            int flightID = flightInfo.flightID;//获取航班ID
                                            List<S_FlightCabin> addFlightCabins = new List<S_FlightCabin>();
                                            for (int i = 0; i < cabinTypeIDs.Length; i++)
                                            {
                                                int cabinTypeID = cabinTypeIDs[i];
                                                S_FlightCabin dbFlightCabin = myModel.S_FlightCabin
                                                    .SingleOrDefault(o => o.flightID == flightID && o.cabinTypeID == cabinTypeID);
                                                if (dbFlightCabin != null)
                                                {
                                                    //该航班存在该舱位信息
                                                    //判断修改后的航班舱位 座位数必须大于等于售出座位数
                                                    if (seatNums[i] >= dbFlightCabin.sellSeatNum)
                                                    {
                                                        //修改航班舱位信息
                                                        dbFlightCabin.seatNum = seatNums[i];
                                                        dbFlightCabin.cabinPrice = cabinPrices[i];

                                                        //保存修改
                                                        myModel.Entry(dbFlightCabin).State = System.Data.Entity.EntityState.Modified;
                                                        myModel.SaveChanges();
                                                    }
                                                    else
                                                    {
                                                        msg.Text = "修改后的航班舱位 座位数必须大于等于售出座位数";
                                                        return Json(msg, JsonRequestBehavior.AllowGet);
                                                    }
                                                }
                                                else
                                                {
                                                    //不存在该舱位信息  --添加
                                                    S_FlightCabin addFlightCabin = new S_FlightCabin()
                                                    {
                                                        flightID = flightID,//航班号
                                                        cabinTypeID = cabinTypeIDs[i],//舱位类型ID
                                                        seatNum = seatNums[i],//舱位座位数
                                                        cabinPrice = cabinPrices[i],//舱位价格
                                                        sellSeatNum = 0//售出座位数 默认0
                                                    };
                                                    addFlightCabins.Add(addFlightCabin);
                                                }
                                            }

                                            //==保存航班舱位想到数据库
                                            myModel.S_FlightCabin.AddRange(addFlightCabins);
                                            myModel.SaveChanges();

                                            //提交事务
                                            scope.Complete();

                                            msg.State = true;
                                            msg.Text = "航班信息保存成功";
                                        }
                                        else
                                        {
                                            msg.Text = "航班信息保存失败";
                                        }
                                    }
                                    else
                                    {
                                        msg.Text = "分配的舱位座位数之和与航班机型座位数不匹配，请检查";
                                    }
                                }
                                catch (Exception e)
                                {
                                    msg.Text = "请选择航班机型";
                                }
                            }
                        }
                    }
                    else
                    {
                        msg.Text = "请填写完整座位数和舱位价格，如果不设置该舱位，请填写0";
                    }
                }
                else
                {
                    msg.Text = "请设置航班标准价格";
                }
            }
            else
            {
                msg.Text = "请输入航班号";
            }

            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        /// <summary>
        /// 删除航班信息
        /// </summary>
        /// <param name="flightID"></param>
        /// <returns></returns>
        public ActionResult DeleteFlightInfoByID(int flightID)
        {
            ReturnJson msg = new ReturnJson();

            try
            {
                //查询出需要删除的航班信息
                S_Flight dbFlight = myModel.S_Flight.Single(o => o.flightID == flightID);

                //检查 PNR航段表 电子客票表 中是否有使用该航班
                int countPNRSegment = myModel.B_PNRSegment.Count(o => o.flightID == flightID);
                int countETicket = myModel.B_ETicket.Count(o => o.flightID == flightID);
                //判断是否有使用
                if (countPNRSegment + countETicket == 0)
                {
                    using (TransactionScope scope = new TransactionScope())
                    {
                        //删除航班信息
                        myModel.S_Flight.Remove(dbFlight);

                        //删除航班舱位信息
                        List<S_FlightCabin> dbFlightCabins = myModel.S_FlightCabin.Where(o => o.flightID == flightID).ToList();
                        myModel.S_FlightCabin.RemoveRange(dbFlightCabins);
                        if (myModel.SaveChanges() > 0)
                        {
                            scope.Complete();//提交事务

                            msg.State = true;
                            msg.Text = "航班删除成功";
                        }
                        else
                        {
                            msg.Text = "航班删除失败";
                        }
                    }
                }
                else
                {
                    msg.Text = "该航班已经在使用中，无法删除";
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                msg.Text = "删除失败";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }

        #endregion


        #region 批量生成航班数据(随机 由于生成测试数据)

        /// <summary>
        /// 生成航班数据 批量\随机 测试数据
        /// </summary>
        /// <returns></returns>
        public ActionResult CreateFlightAuto()
        {

            //清除session记录
            Session.Remove("flightCreateResults");
            Session.Remove("insertFlights");

            return View();
        }

        /// <summary>
        /// 查询所有机场数据for xm-select
        /// </summary>
        /// <returns></returns>
        public ActionResult SelectAirportAll()
        {
            List<XmSelectVo> listData = (from tabAirport in myModel.S_Airport
                                         select new XmSelectVo()
                                         {
                                             value = tabAirport.airportID,
                                             name = tabAirport.cityName + " - " + tabAirport.airportName,
                                             //selected=true,//表示是否默认选择
                                             //disabled=true,//禁用
                                         }).ToList();
            return Json(listData, JsonRequestBehavior.AllowGet);

        }


        public ActionResult CreateFlight(int[] selectAirportIds, int createNum, string createDate)
        {
            //查询出所有的机场 -- 为了避免在循环中连接数据库查询
            List<S_Airport> airports = myModel.S_Airport.ToList();

            //定义一个容器 - 存放生成的结果信息
            List<FlightCreateResult> flightCreateResults = new List<FlightCreateResult>();
            //定义一个容器 - 存放使用过的机场ID
            List<int> listAirportId = new List<int>();

            //====两层循环 生成城市之间两两组合
            //=第一层循环 取出第一个机场
            for (int i = 0; i < selectAirportIds.Length; i++)
            {
                // linq lambda表达式都支持 查询 变量(list/集合)
                //获取第一个机场
                //S_Airport airport1 = airports.Single(o => o.airportID == selectAirportIds[i]);
                S_Airport airport1 = (from tabAirpot in airports
                                      where tabAirpot.airportID == selectAirportIds[i]
                                      select tabAirpot).Single();

                //=第二层循环 取出第二个机场
                for (int j = 0; j < selectAirportIds.Length; j++)
                {
                    //获取到第二个机场对象
                    S_Airport airport2 = airports.Single(c => c.airportID == selectAirportIds[j]);

                    //=判断两个城市名称是否相同 如果相同就跳过 避免生成自己到自己的航班 或者城市的A机场到城市的B机场的航班
                    if (airport1.cityName.Trim() == airport2.cityName.Trim())
                    {
                        continue;
                    }

                    //将组合信息保存到生成结果表
                    flightCreateResults.Add(new FlightCreateResult()
                    {
                        orangeID = selectAirportIds[i],//起飞机场ID
                        destinationID = selectAirportIds[j],//到达机场id
                        flightLine = airport1.cityName + "[" + airport1.airportName +
                                     "] ---> " +
                                     airport2.cityName + "[" + airport2.airportName + "]"//航线描述
                    });
                }
                //将机场ID 添加到listAirportId
                listAirportId.Add(selectAirportIds[i]);
            }

            //将日期字符串转成DateTime
            DateTime dtCreateDate = Convert.ToDateTime(createDate);

            //查询出所有的飞机类型
            List<S_PlanType> planTypes = myModel.S_PlanType.ToList();

            //查询出生数据库中已经存在的当天的 起飞地点 降落地点在生成列表的所有 航班
            List<S_Flight> dbFlightList = (from tabFlight in myModel.S_Flight
                                           where tabFlight.flightDate == dtCreateDate &&
                                           listAirportId.Contains(tabFlight.orangeID.Value) &&
                                           listAirportId.Contains(tabFlight.destinationID.Value)
                                           select tabFlight).ToList();

            //容器 - 存放所有的航班 包括 数据库已经有的和 新生产的
            List<S_Flight> totalFlights = new List<S_Flight>();
            //将数据库中今天的航班数据添加到totalFlights
            totalFlights.AddRange(dbFlightList);

            //容器 - 存放新生成的航班
            List<S_Flight> insertFlights = new List<S_Flight>();

            //=================开始生成航班信息=========
            if (flightCreateResults.Count > 0)
            {
                //随机类 用于生成随机数
                Random r = new Random();
                //循环指定的生成次数
                for (int i = 0; i < createNum; i++)
                {
                    //随机生成一条航线的索引  [0,flightCreateResults.Count)
                    int randIndex = r.Next(0, flightCreateResults.Count);

                    //==开始随机生成航班信息
                    // 起飞时间 随机[6,24)小时，随机[0,60)分钟
                    TimeSpan tsStart = new TimeSpan(r.Next(6, 24), r.Next(0, 60), 0);
                    //随机飞行时长 分钟[20,180)
                    TimeSpan tsAdd = new TimeSpan(0, r.Next(20, 181), 0);
                    //降落时间=起飞时间+结束时间
                    TimeSpan tsEnd = tsStart.Add(tsAdd);
                    //判断结束时间大于1天的要减去1天，保留时分秒
                    if (tsEnd.Days > 0)
                    {
                        tsEnd = tsEnd.Subtract(new TimeSpan(1, 0, 0, 0));
                    }

                    //==随机生成航班号 MU+4位数字
                    string flightCode = "MU" + r.Next(1000, 10000);

                    //查询判断 起飞时间和现有相同航线航班间距小于的30分钟的跳过 / 或者有相同航班号的跳过 （这些数据都是同一天的，就不需要判断日期）
                    List<S_Flight> oldFlights = totalFlights.Where(f =>
                        (f.orangeID == flightCreateResults[randIndex].orangeID
                         && f.destinationID == flightCreateResults[randIndex].destinationID
                         && (f.departureTime.Value - tsStart).TotalMinutes < 30
                         && (f.departureTime.Value - tsStart).TotalMinutes > -30) || f.flightCode.Trim() == flightCode).ToList();
                    if (oldFlights.Count == 0)
                    {
                        //没有冲突的数据 创建航班数据

                        //创建航班数据
                        S_Flight addFlight = new S_Flight()
                        {
                            flightCode = flightCode,//航班号
                            orangeID = flightCreateResults[randIndex].orangeID,//起飞机场id
                            destinationID = flightCreateResults[randIndex].destinationID,//降落机场id
                            flightDate = dtCreateDate,//航班日期
                            departureTime = tsStart,//起飞时间
                            arrivalTime = tsEnd,//降落时间
                            planTypeID = planTypes[r.Next(0, planTypes.Count)].planTypeID,//随机机型id
                            standardPrice = r.Next(900, 2001)//随机价格
                        };

                        //添加到新生成的航班list
                        insertFlights.Add(addFlight);
                        //添加到今日总的航班列表 --为了查冲突
                        totalFlights.Add(addFlight);
                        //对应航线生成的航班数+1
                        flightCreateResults[randIndex].createNum++;
                    }
                    else
                    {
                        //起飞时间和现有相同航线航班间距小于的30分钟的跳过 / 或者有相同航班号的跳过 （这些数据都是同一天的，就不需要判断日期）
                    }
                }
            }

            //将航班数据保存到session
            Session["flightCreateResults"] = flightCreateResults;//航班生成情况
            Session["insertFlights"] = insertFlights;//生成的新航班

            //返回生成情况描述
            string strMsg = string.Format("目标生成{0}条航班数据，实际可以生成{1}条不重复的航班数据", createNum, insertFlights.Count);

            return Json(strMsg, JsonRequestBehavior.AllowGet);
        }


        /// <summary>
        /// 查询航班生成情况 for table
        /// </summary>
        /// <returns></returns>
        public ActionResult SelectFlightCreateResults()
        {

            List<FlightCreateResult> flightCreateResults = new List<FlightCreateResult>();
            //从session中获取出航班生成情况
            if (Session["flightCreateResults"] != null)
            {
                flightCreateResults = (List<FlightCreateResult>)Session["flightCreateResults"];
            }
            //通过layuiTable数据格式返回
            LayuiTableData<FlightCreateResult> layuiTableData = new LayuiTableData<FlightCreateResult>()
            {
                count = flightCreateResults.Count,
                data = flightCreateResults
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }



        /// <summary>
        /// 保存生成的航班信息
        /// </summary>
        /// <returns></returns>
        public ActionResult SaveCreateFlight()
        {
            int successNum = 0;//保存成功数
            int failNum = 0;//保存失败数

            //从Session中获取出生成的航班数据
            List<S_Flight> insertFlights = new List<S_Flight>();
            if (Session["insertFlights"] != null)
            {
                insertFlights = (List<S_Flight>)Session["insertFlights"];
            }

            //生成的航班数据是否大于0
            if (insertFlights.Count > 0)
            {
                //查询所有机型信息
                List<S_PlanType> planTypes = myModel.S_PlanType.ToList();

                //查询所有的舱位类型
                List<S_CabinType> cabinTypes = myModel.S_CabinType.ToList();

                //随机类实例
                Random r = new Random();

                //遍历要新增的航班
                foreach (S_Flight insertFlight in insertFlights)
                {
                    //开启事务
                    using (var scope = new TransactionScope())
                    {
                        //==1、新增要保存的航班信息
                        myModel.S_Flight.Add(insertFlight);
                        if (myModel.SaveChanges() > 0)
                        {
                            //航班信息保存成功

                            //==2、生成并保存航班舱位信息
                            int insertFlightId = insertFlight.flightID;//获取到保存后的航班id
                            //=2.1、创建存放航班舱位信息的list
                            List<S_FlightCabin> flightCabins = new List<S_FlightCabin>();
                            //=2.2、查询出航班飞机机型座位数
                            int planSeatNum = planTypes.Single(p => p.planTypeID == insertFlight.planTypeID).seatNum.Value;

                            //=2.3、记录飞机所剩座位数 开始时就是飞机座位数
                            int seatNum = planSeatNum;
                            //=2.4、遍历舱位等级list
                            foreach (S_CabinType cabinType in cabinTypes)
                            {
                                //记录生成的座位数
                                int createSeatNum = 0;
                                //生成时最多座位数限制  如果剩余座位数小于飞机座位数的1/3时，飞机座位数就是最大限制，否则最大限制为飞机座位数的1/3
                                int maxCreateNum = seatNum < (planSeatNum / 3) ? seatNum : planSeatNum / 3;

                                //生成舱位座位数 1、F头等舱一般比较少，生成范围是[0,9），其他舱位等级生成范围[1, maxCreateNum + 1)
                                //createSeatNum = cabinType.cabinTypeName.Trim() == "F"
                                createSeatNum = cabinType.cabinTypeCode.Trim() == "F"
                                    ? r.Next(0, 9)
                                    : r.Next(1, maxCreateNum + 1);

                                //如果生成的座位数为0时，跳过数据保存 （主要是头等舱，部分航班可以没有头等舱）
                                if (createSeatNum == 0)
                                {
                                    continue;
                                }
                                //计算舱位价格=航班标准价格*舱位折扣 先除以10转整数后乘以10是为了得到整十数
                                int cabinPrice = Convert.ToInt32(insertFlight.standardPrice * cabinType.discountRate / 10) * 10;

                                //添加航班舱位信息到list中
                                flightCabins.Add(new S_FlightCabin()
                                {
                                    flightID = insertFlightId,
                                    cabinTypeID = cabinType.cabinTypeID,
                                    seatNum = createSeatNum,
                                    cabinPrice = cabinPrice,
                                    sellSeatNum = 0  //卖出座位数
                                });

                                //更新飞机剩余舱位数
                                seatNum = seatNum - createSeatNum;
                                //如果剩下飞机座位数不足0，结束循环
                                if (seatNum <= 0)
                                {
                                    break;
                                }
                            }
                            //=2.5、保存航班舱位信息list  使用AddRange
                            myModel.S_FlightCabin.AddRange(flightCabins);
                            if (myModel.SaveChanges() == flightCabins.Count)
                            {
                                scope.Complete(); //提交事务，事务操作完成，需要添加事务才能真正保存到数据库
                                successNum++;//成功数++
                            }
                            else
                            {
                                failNum++;//失败数++
                            }
                        }
                        else
                        {
                            //航班信息保存失败
                            failNum++;//失败数++
                        }
                    }
                }
            }
            //清除session记录
            Session.Remove("flightCreateResults");
            Session.Remove("insertFlights");

            //显示航班信息保存情况
            string strMsg = string.Format("需要新增{0}条数据,成功{1}条，失败{2}条。", insertFlights.Count, successNum, failNum);

            return Json(strMsg, JsonRequestBehavior.AllowGet);
        }
        #endregion

    }
}
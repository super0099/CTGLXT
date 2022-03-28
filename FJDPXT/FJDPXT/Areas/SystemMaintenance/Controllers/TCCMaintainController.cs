using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;//引用Modles里面的内容，但是在使用里面的内容之前先把内容实例化FJDPXTEntities1 myModels = new FJDPXTEntities1();
using FJDPXT.EntityClass;
using System.Text.RegularExpressions;

namespace FJDPXT.Areas.SystemMaintenance.Controllers
{
    public class TCCMaintainController : Controller
    {
        // GET: SystemMaintenance/TCCMaintain
        //实例化myModels
        FJDPXTEntities1 myModels = new FJDPXTEntities1();
        public ActionResult Index()
        {
            try
            {
                int userID = Convert.ToInt32(Session["userID"].ToString());

                return View();
            }
            catch (Exception e)
            {
                Console.WriteLine(e);

                //重定向
                //重定向到登录页面
                return Redirect(Url.Content("~/Main/Login"));
            }
        }

        #region 三字码分页
        public ActionResult SelectAirport(LayuiTablePage layuiTablePage)//调用写好的类EntityClass里的LayuiTablePage方法，为改方法添加参数layuiTablePage
        //public ActionResult SelectAirport(int page,int limit)//layui分页会有两个数据，分页大小（limit），当前页数（page）
        {
            //List<>泛型，查询整个表格用泛型，from 后面是变量名称，in是这个变量名来开始引入并实例化的myModels里的S_Airport数据
            List<S_Airport> listData = (from tabAirport in myModels.S_Airport
                                        orderby tabAirport.airportCode//orderby排序
                                        select tabAirport)
                                        .Skip(layuiTablePage.GetStartIndex())//跳过多少条数据
                                                                             //.Skip((page-1)*limit)//还没有封装前用的代码，跳过多少条数据
                                        .Take(layuiTablePage.limit)//要查询多少条数据
                                                                   //.Take(limit)//还没有封装前用的代码，要查询多少条数据
                                        .ToList();

            //查询数据的总条数 Lambda表达式
            //声明一个变量接收表单的总条数
            int totalRows = myModels.S_Airport.Count();
            //totalRows = (from tabAirport in myModel.S_Airport
            //             select tabAirport).Count();

            LayuiTableData<S_Airport> layuiTableData = new LayuiTableData<S_Airport>()//实例化
            {
                count = totalRows,//总条数
                data = listData//数据
            };
            //layuiTableData.count = totalRows;//总条数
            //layuiTableData.data = listData;//数据

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 三字码新增部分
        /// <summary>
        /// 新增机场 
        /// </summary>
        /// <param name="airport">页面提交的机场数据</param>
        /// <returns></returns>
        public ActionResult InserAirport(S_Airport airport)//airport是个变量配合html代码的名字接收用户输入的数据
        {
            ReturnJson msg = new ReturnJson();
            //msg.State = false;

            //===1-验证数据
            //==1.1-验证三字码
            //如果机场三字码不等于空并且三字码长度等于3并且符合正则表达式执行if代码
            if (airport.airportCode != null && airport.airportCode.Length == 3&&Regex.IsMatch(airport.airportCode,"^[A-Z]{3}"))
            {
                //==1.2-机场名称
                //IsNullOrEmpty（）意思是括号内的字符串等于空，在string前加！就是不等于的意思
                if (!string.IsNullOrEmpty(airport.airportName)
                    && Regex.IsMatch(airport.airportName, "^[\u4e00-\u9fa5A-Za-z]+$"))// null ""                    
                {
                    //==1.3-城市名称
                    //IsNullOrEmpty（）意思是括号内的字符串等于空，在string前加！就是不等于的意思
                    if (!string.IsNullOrEmpty(airport.cityName))
                    {
                        //==1.3-城市名称
                        //IsNullOrEmpty（）意思是括号内的字符串等于空，在string前加！就是不等于的意思
                        if (!string.IsNullOrEmpty(airport.pinyinName))
                        {
                            //判断要新增的三字码or机场名称是否存在
                            int oldAirportCount = (from tabAirport in myModels.S_Airport////申明一个变量来自myModels里的数据S_Airport表格
                                                   where tabAirport.airportCode == airport.airportCode //查询的条件是myModels里的三字码是否等于用户输入的三字码或者myModels的机场名称是否等于用户输入的机场名称
                                                   || tabAirport.airportName == airport.airportName
                                                   select tabAirport).Count();//select查询的意思，tabAirport是myModels里的数据，Count（）是所有数据。合起来就是查询myModels里的所有数据
                            //判断查询表中的数据如果存在的话就是不等于0，如果不存在就等于0执行if语句
                            if (oldAirportCount == 0)
                            {
                                //==新增数据到数据库
                                //add需要新增的数据实例
                                //myModels里的S_Airport表格Add新增airport用户输入的数据
                                myModels.S_Airport.Add(airport);
                                //保存新增到数据库
                                if (myModels.SaveChanges() > 0)
                                {
                                    msg.State = true;
                                    msg.Text = "新增成功";
                                }
                                else
                                {
                                    msg.Text = "新增失败";
                                }
                            }
                            else
                            {
                                msg.Text = "已经存在三字码或者机场名称";
                            }
                        }
                        else
                        {
                            msg.Text = "城市拼音名称不能为空";
                        }
                    }
                    else
                    {
                        msg.Text = "城市名不能为空";
                    }
                }
                else
                {
                    msg.Text = "请填写机场名称";
                }
            }
            else
            {
                msg.Text = "三字码只能有三位";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 三字码修改部分
        //修改需要比新增多一条数据获取id，辅助获取机场ID同一列上的数据并执行修改
        //根据ID（主键）查询机场信息
        public ActionResult SelectAirportByID(int airportID)
        {
            try//作用于如果出现异常就会执行catch来解决异常
            {
                //从表格中获取id数据
                S_Airport airport = (from tabAirport in myModels.S_Airport//申明的变量来自myModels里的S_Airport表格数据
                                     where tabAirport.airportID == airportID//条件是S_Airport表格里的id是否等于用户想要获取的id
                                     select tabAirport).Single();//查询整个表格的单条数据
                return Json(airport, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }
        public ActionResult UpdateAirport(S_Airport airport)
        {
            ReturnJson msg = new ReturnJson();
            //验证被修改机场的ID必须大于0，因为机场id不存在等于0或者小于0的
            if (airport.airportID > 0)
            {
                //===1-验证数据
                //==1.1-验证三字码
                //如果机场的的三字码不等于空并且等于3就执行if代码，否则提示"三字码只能有三位"
                if (airport.airportCode != null && airport.airportCode.Length == 3)
                {
                    //==1.2-机场名称
                    //IsNullOrEmpty（）是等于空的字符串意思，但是在strin前加！就是不等于空和airport.airportName!=null一个意思
                    if (!string.IsNullOrEmpty(airport.airportName))// null ""
                    {
                        //==1.3-城市名称
                        //IsNullOrEmpty是等于空的字符串意思，但是在strin前加！就是不等于空和airport.cityName!=null一个意思
                        if (!string.IsNullOrEmpty(airport.cityName))
                        {
                            //==1.3-城市拼音名称
                            //IsNullOrEmpty是等于空的字符串意思，但是在strin前加！就是不等于空和airport.pinyinName!=null一个意思
                            if (!string.IsNullOrEmpty(airport.pinyinName))
                            {
                               //向后台判断要修改的机场数据的三字码or机场名称和其他机场是否有相同的
                                int oldAirportCount = (from tabAirport in myModels.S_Airport//申明一个变量来自myModels里的数据S_Airport
                                                       where tabAirport.airportID!=airport.airportID//查询条件S_Airport的ID不等于用户传过来的ID因为一张表格id是独有的，意思就是说要修改的数据ID必须是表格中存在的
                                                       && (tabAirport.airportCode == airport.airportCode//
                                                       || tabAirport.airportName == airport.airportName)
                                                       select tabAirport).Count();//查询tabAirport里的多条数据
                                //判断查询表中的数据如果存在的话就是不等于0，如果不存在就等于0执行if语句
                                if (oldAirportCount == 0)
                                {
                                    myModels.Entry(airport).State = System.Data.Entity.EntityState.Modified; //标记该条数据被修改
                                    //保存修改后的数据到数据库
                                    if (myModels.SaveChanges() > 0)
                                    {
                                        msg.State = true;
                                        msg.Text = "修改成功";
                                    }
                                    else
                                    {
                                        msg.Text = "修改失败";
                                    }
                                }
                                else
                                {
                                    msg.Text = "要保存的三字码或者机场名称已经存在";
                                }
                            }
                            else
                            {
                                msg.Text = "城市拼音名称不能为空";
                            }
                        }
                        else
                        {
                            msg.Text = "城市名不能为空";
                        }
                    }
                    else
                    {
                        msg.Text = "请填写机场名称";
                    }
                }
                else
                {
                    msg.Text = "三字码只能有三位";
                }
            }
            else
            {
                msg.Text = "参数异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);

        }
        #endregion
        #region 三字码删除部分
        public ActionResult DeleteAirportByID(int airportID)
        {
            ReturnJson msg = new ReturnJson();
            //
            if (airportID > 0)
            {
                try
                {
                    //S_Airport dbAirport = (from tabAirport in myModels.S_Airport
                    //                       where tabAirport.airportID == airportID
                    //                       select tabAirport).Single();
                    S_Airport dbAirport = myModels.S_Airport.Single(o => o.airportID == airportID);
                    int useAirportCount = (from tabFlight in myModels.S_Flight
                                           where tabFlight.orangeID == airportID ||
                                           tabFlight.destinationID == airportID
                                           select tabFlight).Count();
                    if (useAirportCount == 0)
                    {
                        myModels.S_Airport.Remove(dbAirport);
                        if (myModels.SaveChanges() > 0)
                        {
                            msg.State = true;
                            msg.Text = "删除成功";
                        }
                        else
                        {
                            msg.Text = "删除失败";
                        }
                    }
                    else
                    {
                        msg.Text = "该机场数据在被使用中，无法删除";
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    msg.Text = "机场数据不存在";
                };
            }
            else
            {
                msg.Text = "参数异常，无法删除";

            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
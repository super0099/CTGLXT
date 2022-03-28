using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.Text.RegularExpressions;//引入正则表达式

namespace FJDPXT.Areas.SystemMaintenance.Controllers
{
    public class ClassMaintainController : Controller
    {
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        // GET: SystemMaintenance/ClassMaintain
        #region 视图部分
        public ActionResult Index()
        {
            //重定向
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
        #endregion
        #region 分页查询部分
        public ActionResult SelectCabinType(LayuiTablePage layuiTablePage)
        {
            //分页查询 舱位等级
            List<S_CabinType> listData = (from tabCabinType in myModel.S_CabinType
                                          orderby tabCabinType.cabinTypeID
                                          select tabCabinType)
                                        .Skip(layuiTablePage.GetStartIndex())
                                        .Take(layuiTablePage.limit)
                                        .ToList();

            //查询总条数,如果需要查询更多的数据就用long型
            int totalRows = (from tabCabinType in myModel.S_CabinType
                             select tabCabinType).Count();

            //组装layui table所需的数据格式
            LayuiTableData<S_CabinType> layuiTableData = new LayuiTableData<S_CabinType>()
            {
                count = totalRows,//数据总条数
                data = listData,//当前页的数据
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 新增部分
        public ActionResult InsertCabinType(S_CabinType cabinType)
        {
            ReturnJson msg = new ReturnJson();

            //验证数据
            if (!string.IsNullOrEmpty(cabinType.cabinTypeCode)&&cabinType.cabinTypeCode.Length==1
                && Regex.IsMatch(cabinType.cabinTypeCode, "^[A-Z]{1}$"))
            {
                if (!string.IsNullOrEmpty(cabinType.cabinTypeName))
                {
                    if (cabinType.basisPrice!=null&&cabinType.basisPrice>0&&cabinType.basisPrice<10000)
                    {
                        if (cabinType.discountRate != null && cabinType.discountRate > 0)
                        {
                            int oldCount = (from tabCabinType in myModel.S_CabinType
                                            where tabCabinType.cabinTypeCode == cabinType.cabinTypeCode
                                            select tabCabinType).Count();
                            if (oldCount == 0)
                            {
                                myModel.S_CabinType.Add(cabinType);
                                if (myModel.SaveChanges() > 0)
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
                                msg.Text = "舱位编号已经存在，不能重复添加";
                            }
                        }
                        else
                        {
                            msg.Text = "折扣率必须大于0";
                        }
                    }
                    else
                    {
                        msg.Text = "基础价格必须大于0,且小于 100000";
                    }
                }
                else
                {
                    msg.Text = "舱位等级名称数据不正确,请检查";
                }
            }
            else
            {
                msg.Text = "舱位编号数据不正确，请检查";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #region 修改部分
        #region 修改部分辅助ID
        public ActionResult SelectCabinTypeByID(S_CabinType cabinType)
        {
            try
            {
                S_CabinType dbCabinType = (from tabCabinType in myModel.S_CabinType
                                           where tabCabinType.cabinTypeID == cabinType.cabinTypeID
                                           select tabCabinType).Single();
                return Json(dbCabinType, JsonRequestBehavior.AllowGet);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }
        #endregion
        #region 数据验证部分
        public ActionResult UpdateCabinType(S_CabinType cabinType)
        {
            ReturnJson msg = new ReturnJson();
            if (cabinType.cabinTypeID > 0)
            {
                if (!string.IsNullOrEmpty(cabinType.cabinTypeCode) && cabinType.cabinTypeCode.Length == 1 && Regex.IsMatch(cabinType.cabinTypeCode, "^[A-Z]{1}$"))
                {
                    if (!string.IsNullOrEmpty(cabinType.cabinTypeName))
                    {
                        if (cabinType.basisPrice != null && cabinType.basisPrice < 100000)
                        {
                            if (cabinType.discountRate != null && cabinType.discountRate > 0)
                            {
                                int otherCount = (from tabCabinType in myModel.S_CabinType
                                                  where tabCabinType.cabinTypeID != cabinType.cabinTypeID &&
                                                  tabCabinType.cabinTypeCode == cabinType.cabinTypeCode &&
                                                  tabCabinType.cabinTypeName == cabinType.cabinTypeName
                                                  select tabCabinType).Count();
                                if (otherCount == 0)
                                {
                                    //myModel.S_CabinType.Add(cabinType);//向数据库新增才使用的代码，不然数据修改时不会改变原有的数据而是新增一条新的数据
                                    myModel.Entry(cabinType).State = System.Data.Entity.EntityState.Modified;
                                    if (myModel.SaveChanges() > 0)
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
                                    msg.Text = "数据异常";
                                }
                            }
                            else
                            {
                                msg.Text = "舱位折扣率格式错误";
                            }
                        }
                        else
                        {
                            msg.Text = "舱位基础价格错误，请检查！";
                        }
                    }
                    else
                    {
                        msg.Text = "舱位名称错误，请检查！";
                    }
                }
                else
                {
                    msg.Text = "舱位编码错误，请检查！";
                }
            }
            else
            {
                msg.Text = "参数异常，请检查！";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
        #endregion
        #region 删除部分
        public ActionResult DeleteCabinTypeByID(int cabinTypeID)
        {
            ReturnJson msg = new ReturnJson();
            if (cabinTypeID > 0)
            {
                int useInPNRSegment = (from tabCabinType in myModel.B_PNRSegment
                                               where tabCabinType.cabinTypeID == cabinTypeID
                                               select tabCabinType).Count();
                //int useInPNRSegment=myModel.B_PNRSegment.Count(o=>o.tcabinTypeID == cabinTypeID);//是上面方法简写
                int useInFlightCabin = (from tabCabinType in myModel.S_FlightCabin
                                        where tabCabinType.cabinTypeID == cabinTypeID
                                       select tabCabinType).Count();
                if(useInPNRSegment== 0&& useInFlightCabin==0)
                {
                    try
                    {
                        S_CabinType dbCabinType = (from tabCabinType in myModel.S_CabinType
                                                   where tabCabinType.cabinTypeID== cabinTypeID
                                                   select tabCabinType).Single();
                        myModel.S_CabinType.Remove(dbCabinType);
                        if (myModel.SaveChanges()>0)
                        {
                            msg.State = true;
                            msg.Text = "删除成功";
                        }
                        else
                        {
                            msg.Text = "删除失败";
                        }
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine(e);
                        msg.Text = "数据异常,删除失败";
                    }
                }
            }
            else
            {
                msg.Text = "删除异常";
            }
            return Json(msg, JsonRequestBehavior.AllowGet);
        }
        #endregion
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FJDPXT.Models;
using FJDPXT.EntityClass;
using System.IO;

namespace FJDPXT.Areas.OthersMaintenance.Controllers
{
    public class ExportARdataController : Controller
    {
        // GET: OthersMaintenance/
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
        //其他--》导出AR数据
        FJDPXTEntities1 myModel = new FJDPXTEntities1();
        #region 主页面
        public ActionResult Index()
        {
            return View();
        }
        #endregion

        #region 数据分页查询
        public ActionResult SelectARDate(LayuiTablePage layuiTablePage, string startEndDate)
        {
            var query = from tbOrder in myModel.B_Order
                        join tbPNR in myModel.B_PNR on tbOrder.PNRID equals tbPNR.PNRID
                        join tbUser in myModel.S_User on tbOrder.operatorID equals tbUser.userID
                        join tbUserGroup in myModel.S_UserGroup on tbUser.userGroupID equals tbUserGroup.userGroupID
                        orderby tbOrder.orderID
                        select new ExportARdataVo
                        {
                            orderNo = tbOrder.orderNo,
                            payTime = tbOrder.payTime,
                            totalPrice = tbOrder.totalPrice,
                            agencyFee = tbOrder.agencyFee,
                            payMoney = tbOrder.payMoney,
                            userGroup = tbUserGroup.userGroupNumber,
                            jobNumber = tbUser.jobNumber,
                            PNR = tbPNR.PNRNo,
                            orderStatus = tbOrder.orderStatus
                        };
            //判断是否选择时间段
            if (!string.IsNullOrEmpty(startEndDate))
            {
                startEndDate = startEndDate.Replace(" - ", "~");
                string[] strs = startEndDate.Split('~');//根据 " - "分割字符串
                if (strs.Length == 2)
                {
                    DateTime dtStart = Convert.ToDateTime(strs[0]);
                    DateTime dtEnd = Convert.ToDateTime(strs[1]);
                    dtEnd = dtEnd.AddDays(1);
                    query = query.Where(o => o.payTime >= dtStart && o.payTime < dtEnd);
                }
            }
            //查询总行数
            int totalRow = query.Count();

            //分页查询数据
            List<ExportARdataVo> listData = query.Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();

            LayuiTableData<ExportARdataVo> layuiTableData = new LayuiTableData<ExportARdataVo>()
            {
                count = totalRow,
                data = listData
            };

            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region AR导出
        public ActionResult ExportARData(string startEndDate)
        {
            try
            {
                #region 数据查询页面
                var query = from tbOrder in myModel.B_Order
                            join tbPNR in myModel.B_PNR on tbOrder.PNRID equals tbPNR.PNRID
                            join tbUser in myModel.S_User on tbOrder.operatorID equals tbUser.userID
                            join tbUserGroup in myModel.S_UserGroup on tbUser.userGroupID equals tbUserGroup.userGroupID
                            orderby tbOrder.orderID
                            select new ExportARdataVo
                            {
                                orderNo = tbOrder.orderNo,
                                payTime = tbOrder.payTime,
                                totalPrice = tbOrder.totalPrice,
                                agencyFee = tbOrder.agencyFee,
                                payMoney = tbOrder.payMoney,
                                userGroup = tbUserGroup.userGroupNumber,
                                jobNumber = tbUser.jobNumber,
                                PNR = tbPNR.PNRNo,
                                orderStatus = tbOrder.orderStatus
                            };
                //判断是否选择时间段
                if (!string.IsNullOrEmpty(startEndDate))
                {
                    startEndDate = startEndDate.Replace(" - ", "~");
                    string[] strs = startEndDate.Split('~');//根据 " - "分割字符串
                    if (strs.Length == 2)
                    {
                        DateTime dtStart = Convert.ToDateTime(strs[0]);
                        DateTime dtEnd = Convert.ToDateTime(strs[1]);
                        dtEnd = dtEnd.AddDays(1);
                        query = query.Where(o => o.payTime >= dtStart && o.payTime < dtEnd);
                    }
                }

                List<ExportARdataVo> list = query.ToList();
                #endregion

                #region 使用模板的方式导出Excel
                //--读取模板
                //获取模板的文件路径
                string templateFilePath = Server.MapPath("~/Document/ARDataTemplate.xls");
                //判断文件是否存在
                if (!System.IO.File.Exists(templateFilePath))
                {
                    return Content("导出失败，无法找到导出模板，请联系网站管理人员");
                }
                //==2-使用NPOI打开模板Excel
                //=2.1-使用文件打开模板文件
                FileStream fileSteam = new FileStream(templateFilePath, FileMode.Open);
                //2.2-把文件流转为工作簿
                NPOI.HSSF.UserModel.HSSFWorkbook excelBookTemplate = new NPOI.HSSF.UserModel.HSSFWorkbook(fileSteam);

                //==3-打开模板所在第一个工作表
                NPOI.SS.UserModel.ISheet sheet = excelBookTemplate.GetSheetAt(0);
                NPOI.SS.UserModel.ICellStyle style = excelBookTemplate.CreateCellStyle();

                //==4-设置标题，如果筛选时间段不为空就拼接上筛选时间段
                if (!string.IsNullOrEmpty(startEndDate))
                {
                    NPOI.SS.UserModel.IRow rowTitle = sheet.GetRow(0);
                    rowTitle.GetCell(0).SetCellValue("订单数据    " + startEndDate);
                }

                //==5-往模板中填充数据
                //=5.1-设置数据单元格的样式
                //水平垂直居中对齐
                style.Alignment = NPOI.SS.UserModel.HorizontalAlignment.Center;
                style.VerticalAlignment = NPOI.SS.UserModel.VerticalAlignment.Center;
                //设置边框为实线
                style.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
                style.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
                //==5.2-开始填充数据
                int index = 2;//目前这个模板数据开始填充数据的行索引为2
                //遍历查询出的数据 填充
                for (int i = 0; i < list.Count(); i++)
                {
                    NPOI.SS.UserModel.IRow row = sheet.CreateRow(index); ;//给sheet添加一行
                    row.Height = 22 * 20;//设置行高
                    //设置单元格数据
                    row.CreateCell(0).SetCellValue(i + 1);
                    row.CreateCell(1).SetCellValue(list[i].orderNo);
                    row.CreateCell(2).SetCellValue(list[i].payTime.ToString());
                    row.CreateCell(3).SetCellValue(list[i].totalPrice.ToString());
                    row.CreateCell(4).SetCellValue(list[i].agencyFee.ToString());
                    row.CreateCell(5).SetCellValue(list[i].payMoney.ToString());
                    row.CreateCell(6).SetCellValue(list[i].userGroup.ToString());
                    row.CreateCell(7).SetCellValue(list[i].jobNumber.ToString());
                    row.CreateCell(8).SetCellValue(list[i].PNR.ToString());
                    //设置单元格样式
                    for (int j = 0; j < row.Cells.Count; j++)
                    {
                        row.GetCell(j).CellStyle = style;
                    }
                    index++;
                }

                //以流的方式返回
                string fileName = "订单信息" + DateTime.Now.ToString("yyyy-MM-dd-HH-mm-ss-ffff") + ".xls";
                //把Excel转化为流，输出
                MemoryStream BookStream = new MemoryStream();//定义内存流
                excelBookTemplate.Write(BookStream);//将工作薄写入内存流
                BookStream.Seek(0, SeekOrigin.Begin);//输出之前调用Seek（偏移量，游标位置）方法：获取文件流的长度
                return File(BookStream, "application/vnd.ms-excel", fileName); // 文件类型/文件名称/

                #endregion
            }
            catch (Exception e)
            {
                Console.Write(e);
                return Content("数据导出异常");
            }
        }
        #endregion
    }
}
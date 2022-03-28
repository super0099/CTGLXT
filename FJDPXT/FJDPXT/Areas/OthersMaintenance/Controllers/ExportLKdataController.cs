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
    public class ExportLKdataController : Controller
    {
        // GET: OthersMaintenance/ExportLKdata
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
        FJDPXTEntities1 myModels = new FJDPXTEntities1();
        //其他===导出旅客数据
        #region 主页面
        public ActionResult Index()
        {
            return View();
        }
        #endregion

        #region 数据分页查询和多条件查询
        public ActionResult SelectPassengerData(LayuiTablePage layuiTablePage, string startEndDate)
        {
            //查询表格需要的数据
            var query = from tabPNRPassenger in myModels.B_PNRPassenger
                        join tabPassengerType in myModels.S_PassengerType on tabPNRPassenger.passengerTypeID equals tabPassengerType.passengerTypeID
                        join tabCertificatesType in myModels.S_CertificatesType on tabPNRPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                        join tabPNR in myModels.B_PNR on tabPNRPassenger.PNRID equals tabPNR.PNRID
                        orderby tabPNRPassenger.PNRPassengerID
                        select new PassengerVo()
                        {
                            passengerName = tabPNRPassenger.passengerName,
                            passengerType = tabPassengerType.passengerType,
                            certificatesType = tabCertificatesType.certificatesType,
                            certificatesCode = tabPNRPassenger.certificatesCode,
                            contactName = tabPNR.contactName,
                            contactPhone = tabPNR.contactPhone,
                            createTime = tabPNR.createTime.Value
                        };
            //判断是否选择时间段
            if (!string.IsNullOrEmpty(startEndDate))
            {
                startEndDate = startEndDate.Replace(" - ", "~");//字符串替换
                string[] strs = startEndDate.Split('~');//分割字符串放进数组里，然后根据数组索引来获取对应的值
                if (strs.Length == 2)
                {
                    DateTime deStart = Convert.ToDateTime(strs[0]);//获取第一个分割处理的字符串
                    DateTime dtEnd = Convert.ToDateTime(strs[1]);//获取第二个分割处理的字符串
                    dtEnd = dtEnd.AddDays(1);//获取到最后时间段，并且给最后时间段添加一天，作用是使得查询出最后时间段里的所有数据
                    //如果用户选择了时间段，就必须根据时间段进行查询
                    query = query.Where(o => o.createTime >= deStart && o.createTime < dtEnd);
                }
            }

            //计算总条数，分页
            int totalRow = query.Count();

            //分页查询
            List<PassengerVo> listData = query.Skip(layuiTablePage.GetStartIndex())
                .Take(layuiTablePage.limit)
                .ToList();

            //构建需要返回的数据
            LayuiTableData<PassengerVo> layuiTableData = new LayuiTableData<PassengerVo>()
            {
                count = totalRow,
                data = listData
            };
            return Json(layuiTableData, JsonRequestBehavior.AllowGet);
        }
        #endregion

        #region 旅客数据导出
        public ActionResult ExportLKDATA(string startEndDate)
        {
            try
            {
                //查询表格需要的数据
                var query = from tabPNRPassenger in myModels.B_PNRPassenger
                            join tabPassengerType in myModels.S_PassengerType on tabPNRPassenger.passengerTypeID equals tabPassengerType.passengerTypeID
                            join tabCertificatesType in myModels.S_CertificatesType on tabPNRPassenger.certificatesTypeID equals tabCertificatesType.certificatesTypeID
                            join tabPNR in myModels.B_PNR on tabPNRPassenger.PNRID equals tabPNR.PNRID
                            orderby tabPNRPassenger.PNRPassengerID
                            select new PassengerVo()
                            {
                                passengerName = tabPNRPassenger.passengerName,
                                passengerType = tabPassengerType.passengerType,
                                certificatesType = tabCertificatesType.certificatesType,
                                certificatesCode = tabPNRPassenger.certificatesCode,
                                contactName = tabPNR.contactName,
                                contactPhone = tabPNR.contactPhone,
                                createTime = tabPNR.createTime.Value
                            };
                //判断是否选择时间段
                if (!string.IsNullOrEmpty(startEndDate))
                {
                    startEndDate = startEndDate.Replace(" - ", "~");//字符串替换
                    string[] strs = startEndDate.Split('~');//分割字符串放进数组里，然后根据数组索引来获取对应的值
                    if (strs.Length == 2)
                    {
                        DateTime deStart = Convert.ToDateTime(strs[0]);//获取第一个分割处理的字符串
                        DateTime dtEnd = Convert.ToDateTime(strs[1]);//获取第二个分割处理的字符串
                        dtEnd = dtEnd.AddDays(1);//获取到最后时间段，并且给最后时间段添加一天，作用是使得查询出最后时间段里的所有数据
                                                 //如果用户选择了时间段，就必须根据时间段进行查询
                        query = query.Where(o => o.createTime >= deStart && o.createTime < dtEnd);
                    }
                }
                List<PassengerVo> list = query.ToList();

                #region NPOI导出Excel
                //=============================分部=======================================
                //1.创建工作簿->创建工作表(设置工作表名字)->创建行(设置行高)->创建单元格(标题单元格)->合并标题单元格
                //2.
                //=============================分部=======================================
                //1-创建工作簿
                NPOI.HSSF.UserModel.HSSFWorkbook workbook = new NPOI.HSSF.UserModel.HSSFWorkbook();
                //2-创建工作表,从创建好的工作簿中创建工作表workbook.CreateSheet();，括号内可以写表格的名字
                NPOI.SS.UserModel.ISheet sheet1 = workbook.CreateSheet();
                //更改工作表的名字SetSheetName(0,"旅客信息")，0为索引因为一个工作簿内存在多个工作表，""为工作表的名字
                workbook.SetSheetName(0,"旅客信息");

                //3-设置表标题
                //3-1、创建行
                NPOI.SS.UserModel.IRow rowTitle = sheet1.CreateRow(0);//下标
                rowTitle.HeightInPoints = 35;//行高 HeightInPoints的单位是点，而Height的单位是1/20个点，所以Height的值永远是HeightInPoints的20倍
                //3-2创建单元格
                NPOI.SS.UserModel.ICell cell0 = rowTitle.CreateCell(0);
                //3-3单元格设置值
                string strTitle = "旅客数据";
                if (!string.IsNullOrEmpty(startEndDate))
                {
                    strTitle += "" + startEndDate;
                }
                cell0.SetCellValue(strTitle);
                //3-4合并单元格,(0,0,0,6)四个参数，分别代表开始合并的横，结束的横，开始合并的列，结束的列
                sheet1.AddMergedRegion(new NPOI.SS.Util.CellRangeAddress(0,0,0,6));
                //3-5设置单元格样式,给工作簿设置样式,//水平居中,//垂直居中
                NPOI.SS.UserModel.ICellStyle cellStyle_Title = workbook.CreateCellStyle();
                cellStyle_Title.Alignment = NPOI.SS.UserModel.HorizontalAlignment.Center;//水平居中
                cellStyle_Title.VerticalAlignment = NPOI.SS.UserModel.VerticalAlignment.Center;//垂直居中
                NPOI.SS.UserModel.IFont font_title = workbook.CreateFont();//声明字体
                font_title.Color = NPOI.HSSF.Util.HSSFColor.Blue.Index;//设置字体颜色
                font_title.Boldweight = (short)NPOI.SS.UserModel.FontBoldWeight.Bold;//字体加粗
                font_title.FontHeightInPoints = 18;

                //上面都是声明，下面才是赋值
                //给单元格设置字体，声明好的字体
                cellStyle_Title.SetFont(font_title);
                cell0.CellStyle = cellStyle_Title;//设置单元格样式

                //==4.设置表头
                NPOI.SS.UserModel.IRow row1 = sheet1.CreateRow(1);
                row1.Height = 22 * 20;

                row1.CreateCell(0).SetCellValue("序号");
                row1.CreateCell(1).SetCellValue("旅客姓名");
                row1.CreateCell(2).SetCellValue("旅客类型");
                row1.CreateCell(3).SetCellValue("证件类型");
                row1.CreateCell(4).SetCellValue("证件号码");
                row1.CreateCell(5).SetCellValue("联系人姓名");
                row1.CreateCell(6).SetCellValue("联系人电话");

                //=4.3创建表头样式
                NPOI.SS.UserModel.ICellStyle cellStyle_header = workbook.CreateCellStyle();//声明样式
                cellStyle_header.Alignment = NPOI.SS.UserModel.HorizontalAlignment.Center;//水平居中
                cellStyle_header.VerticalAlignment = NPOI.SS.UserModel.VerticalAlignment.Center;//垂直居中
                //设置背景颜色
                cellStyle_header.FillPattern = NPOI.SS.UserModel.FillPattern.SolidForeground;//设置表头行背景颜色
                cellStyle_header.FillForegroundColor = NPOI.HSSF.Util.HSSFColor.Aqua.Index;

                //设置边框线为实线
                cellStyle_header.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
                cellStyle_header.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
                cellStyle_header.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
                cellStyle_header.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
                //设置字体
                NPOI.SS.UserModel.IFont font_header = workbook.CreateFont();//声明字体
                font_header.Boldweight = (short)NPOI.SS.UserModel.FontBoldWeight.Bold;//加粗
                font_header.FontHeightInPoints = 10;//字体大小
                cellStyle_header.SetFont(font_header);//加入单元格
                //给单元格设置样式 循环
                for (int i = 0; i <row1.Cells.Count; i++)
                {
                    row1.GetCell(i).CellStyle = cellStyle_header;
                }
                //==5-遍历查询到的数据，设置表格数据
                //=5.1-创建数据内部部分 单元格样式
                NPOI.SS.UserModel.ICellStyle cellstyle_value = workbook.CreateCellStyle();//声明样式
                cellstyle_value.Alignment = NPOI.SS.UserModel.HorizontalAlignment.Center;//水平居中
                cellstyle_value.VerticalAlignment = NPOI.SS.UserModel.VerticalAlignment.Center;//垂直居中
                //设置边框线为实线
                cellstyle_value.BorderLeft = NPOI.SS.UserModel.BorderStyle.Thin;
                cellstyle_value.BorderTop = NPOI.SS.UserModel.BorderStyle.Thin;
                cellstyle_value.BorderRight = NPOI.SS.UserModel.BorderStyle.Thin;
                cellstyle_value.BorderBottom = NPOI.SS.UserModel.BorderStyle.Thin;
                //5.2遍历数据，创建数据部分行列
                for (int i = 0; i <list.Count; i++)
                {
                    //5.2.1-创建行,标题和表头行占了两行，所以数据是从第三行开始
                    NPOI.SS.UserModel.IRow row = sheet1.CreateRow(2 + i);
                    row.Height = 22 * 20;//设置行高
                    //5.2.2-创建列，并设置值
                    row.CreateCell(0).SetCellValue(i + 1);//第一列，序号
                    row.CreateCell(1).SetCellValue(list[i].passengerName);//第二列，旅客姓名
                    row.CreateCell(2).SetCellValue(list[i].passengerType);//第三列，旅客类型
                    row.CreateCell(3).SetCellValue(list[i].certificatesType);//第四列，证件类型
                    row.CreateCell(4).SetCellValue(list[i].certificatesCode);//第五列，证件号
                    row.CreateCell(5).SetCellValue(list[i].contactName);//第六列，联系人姓名
                    row.CreateCell(6).SetCellValue(list[i].contactPhone);//第七列，联系人电话
                    //5.2.3-给每个单元格添加样式
                    for (int j = 0; j <row.Cells.Count; j++)
                    {
                        row.GetCell(j).CellStyle = cellstyle_value;
                    }
                }
                //==6-设置列宽为自动适应
                for (int i = 0; i <sheet1.GetRow(1).Cells.Count; i++)
                {
                    sheet1.AutoSizeColumn(i);
                    sheet1.SetColumnWidth(i, sheet1.GetColumnWidth(i) * 17 / 10);
                }
                //=7-把创建好的Excel输出到浏览器
                string fileName = "旅客信息" + DateTime.Now.ToString("yyy-MM-dd-HH-mm-ss-ffff") + ".xls";
                //把Excel转化为流输出
                MemoryStream BookStream = new MemoryStream();//定义流
                workbook.Write(BookStream);//将工作薄写入流
                BookStream.Seek(0, SeekOrigin.Begin);//输出之前调用Seek（偏移量，游标位置）
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
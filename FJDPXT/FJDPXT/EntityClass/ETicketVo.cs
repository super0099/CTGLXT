using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class ETicketVo : B_ETicket
    {
        /// <summary>
        /// PNR旅客编号
        /// </summary>
        public int passengerNo { get; set; }

        /// <summary>
        /// 旅客姓名
        /// </summary>
        public string passengerName { get; set; }
        /// <summary>
        /// 航班号
        /// </summary>
        public string flightCode { get; set; }
        /// <summary>
        /// PNR编号
        /// </summary>
        public string PNRNo { get; set; }

        /// <summary>
        /// PNRID
        /// </summary>
        public int PNRID { get; set; }

        /// <summary>
        ///出票日期
        /// </summary>
        private string ticketingTime;
        public string TicketingTime
        {
            get
            {
                return ticketingTime;
            }
            set
            {
                try
                {
                    ticketingTime = Convert.ToDateTime(value).ToString("yyyy-MM-dd");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    ticketingTime = value;
                }
            }
        }
        /// <summary>
        /// 工号
        /// </summary>
        public string JobNumber { get; set; }
        /// <summary>
        /// 舱位等级
        /// </summary>
        public string cabinTypeName { get; set; }
        /// <summary>
        /// 证件号
        /// </summary>
        public string certificatesCode { get; set; }
        /// <summary>
        /// 旅客类型
        /// </summary>
        public string passengerType { get; set; }
        /// <summary>
        /// 航班日期
        /// </summary>
        public Nullable<System.DateTime> flightDate { get; set; }
        /// <summary>
        /// 起飞日期
        /// </summary>
        private string FlightDate;
        public string flightDateStr
        {
            get
            {
                return FlightDate;
            }
            set
            {
                try
                {
                    FlightDate = Convert.ToDateTime(value).ToString("yyyy-MM-dd");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    FlightDate = value;
                }
            }
        }

        /// <summary>
        ///起飞时间
        /// </summary>
        private string DepartureTime;
        public string departureTime
        {
            get
            {
                return DepartureTime;
            }
            set
            {
                try
                {
                    DepartureTime = Convert.ToDateTime(value).ToString("HH:mm");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    DepartureTime = value;
                }
            }
        }

        public int orangerID { get; set; }
        public int destinationID { get; set; }
        public int certificatesTypeID { get; set; }
        /// <summary>
        /// 出发城市
        /// </summary>
        public string orangeCity { get; set; }
        /// <summary>
        /// 出发机场
        /// </summary>
        public string orangeName { get; set; }
        /// <summary>
        /// 到达城市
        /// </summary>
        public string destinationCity { get; set; }
        /// <summary>
        /// 到达机场
        /// </summary>
        public string destinationName { get; set; }
        /// <summary>
        /// 用户姓名
        /// </summary>
        public string userName { get; set; }

        /// <summary>
        /// 票联状态
        /// </summary>
        public string eTicketStatus { get; set; }
        public string EnglishName { get; set; }
        /// <summary>
        /// 发票状态
        /// </summary>
        public string invoiceStatus { get; set; }
        /// <summary>
        /// 航段ID
        /// </summary>
        public int PNRSegmentID { get; set; }
        /// <summary>
        /// 出票组ID
        /// </summary>
        public int PNRTicketingID { get; set; }


        /// <summary>
        /// 机型名称
        /// </summary>
        public string planTypeName { get; set; }



        /// <summary>
        /// 机型ID
        /// </summary>
        public int planTypeID { get; set; }


        /// <summary>
        /// 订单编号
        /// </summary>
        public string orderNo { get; set; }

        // <summary>
        /// 代理费
        /// </summary>
        public decimal agencyFee { get; set; }
        /// <summary>
        /// 总价
        /// </summary>
        public decimal totalPrice { get; set; }
        /// <summary>
        /// 实付金额
        /// </summary>
        public decimal payMoney { get; set; }
    }
}
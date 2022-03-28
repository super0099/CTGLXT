using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class PNRTicketingVo: B_PNRTicketing
    {
        /// <summary>
        /// 航段编号
        /// </summary>
        public int segmentNo { get; set; }

        /// <summary>
        /// 旅客编号
        /// </summary>
        public int passengerNo { get; set; }

        /// <summary>
        /// 电子客票号
        /// </summary>
        public string ticketNo { get; set; }


        /// <summary>
        /// 旅客姓名
        /// </summary>
        public string passengerName { get; set; }

        /// <summary>
        /// 航班日期
        /// </summary>
        public DateTime flightDate { get; set; }

        /// <summary>
        /// 航班号
        /// </summary>
        public string flightCode { get; set; }

        /// <summary>
        /// 起飞城市名称
        /// </summary>
        public string orangeCity { get; set; }

        /// <summary>
        /// 起飞城市的编号
        /// </summary>
        public string orangeCode { get; set; }

        /// <summary>
        /// 起飞城市拼音
        /// </summary>
        public string orangePinyin { get; set; }

        /// <summary>
        /// 到达城市名称
        /// </summary>
        public string destinationCity { get; set; }

        /// <summary>
        /// 到达城市编号
        /// </summary>
        public string destinationCode { get; set; }

        /// <summary>
        /// 到达城市拼音
        /// </summary>
        public string destinationPinyin { get; set; }

        /// <summary>
        /// 起飞时间
        /// </summary>
        public TimeSpan departureTime { get; set; }

        /// <summary>
        /// 到达时间
        /// </summary>
        public TimeSpan arrivalTime { get; set; }

        /// <summary>
        /// 舱位等级编号
        /// </summary>
        public string cabinTypeCode { get; set; }

        /// <summary>
        /// 舱位等级名称
        /// </summary>
        public string cabinTypeName { get; set; }
    }
}

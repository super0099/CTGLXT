using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class FlightCabinInfo : S_Flight//自建类，继承S_航班表
    {
        /// <summary>
        /// PNR航段ID
        /// </summary>
        public int PNRSegmentID { get; set; }

        /// <summary>
        /// 航段号
        /// </summary>
        public int segmentNo { get; set; }

        /// <summary>
        /// 航班舱位ID
        /// </summary>
        public int flightCabinID { get; set; }

        /// <summary>
        /// 起飞城市
        /// </summary>
        public string orangeCity { get; set; }

        /// <summary>
        /// 目的地城市
        /// </summary>
        public string destinationCity { get; set; }

        /// <summary>
        /// 舱位类型ID
        /// </summary>
        public int cabinTypeID { get; set; }

        /// <summary>
        /// 舱位类型名称
        /// </summary>
        public string cabinTypeName { get; set; }

        /// <summary>
        /// 舱位类型编号
        /// </summary>
        public string cabinTypeCode { get; set; }

        /// <summary>
        /// 价格--舱位价格
        /// </summary>
        public decimal cabinPrice { get; set; }

        /// <summary>
        /// 座位数量(订座数据量)
        /// </summary>
        public int seatNum { get; set; }
        /// <summary>
        /// 机型名称
        /// </summary>
        public string planTypeName { get; set; }
        /// <summary>
        /// 订座情况
        /// </summary>
        public int bookSeatInfo { get; set; }
        public string bookSeatInfoStr
        {
            get
            {
                string strVal = "";
                switch (bookSeatInfo)
                {
                    case 0:
                        strVal = "取消订座";
                        break;
                    case 1:
                        strVal = "已经订座";
                        break;
                    case 2:
                        strVal = "已经出票";
                        break;
                }
                return strVal;
            }
        }
        //==航段类型
        public int segmentType { get; set; }

        public string segmentTypeStr
        {
            get
            {
                if (segmentType == 1)
                {
                    return "客票换开";
                }
                else if (segmentType == 0)
                {
                    return "预订";
                }
                else if (segmentType == 2)
                {
                    return "已作废";
                }
                return "";
            }
        }
    }
}
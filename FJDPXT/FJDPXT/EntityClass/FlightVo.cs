using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class FlightVo: S_Flight//自建类，继承S_航班表
    {
        /// <summary>
        /// 始发地/起飞地城市名称
        /// </summary>
        public string orangeCityName { get; set; }

        /// <summary>
        /// 始发地/起飞地 机场名称
        /// </summary>
        public string orangeAirportName { get; set; }

        /// <summary>
        /// 目的地 城市名称
        /// </summary>
        public string destinationCityName { get; set; }

        /// <summary>
        /// 目的地 机场名称
        /// </summary>
        public string destinationAirportName { get; set; }

        /// <summary>
        /// 机型名称
        /// </summary>
        public string planTypeName { get; set; }


        /// <summary>
        /// 航班舱位信息列表
        /// </summary>
        public List<FlightCabinVo> flightCabins { get; set; }
    }
}
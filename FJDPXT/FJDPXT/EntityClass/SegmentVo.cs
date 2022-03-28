using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    public class SegmentVo//自建类
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
        /// 日期
        /// </summary>
        public string strDate { get; set; }

        /// <summary>
        /// 航段航班信息
        /// </summary>
        public List<FlightVo> flightList { get; set; }//一共有三级关系，这是最外的类包含（FlightVo.cs是第二个类（FlightCabinVo.cs第三个类））
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    public class FlightCreateResult
    {
        /// <summary>
        /// 起飞机场ID
        /// </summary>
        public int orangeID { get; set; }

        /// <summary>
        /// 降落机场ID
        /// </summary>
        public int destinationID { get; set; }

        /// <summary>
        /// 存放航线信息 用于返回页面显示
        /// </summary>
        public string flightLine { get; set; }

        /// <summary>
        /// 用于存放该航线生成的航班数  返回页面显示
        /// </summary>
        public int createNum { get; set; }
    }
}
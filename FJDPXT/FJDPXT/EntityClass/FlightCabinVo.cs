using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class FlightCabinVo:S_FlightCabin
    {
        /// <summary>
        ///  舱位等级等级编码
        /// </summary>
        public string cabinTypeCode { get; set; }

        /// <summary>
        /// 舱位等级等级名称
        /// </summary>
        public string cabinTypeName { get; set; }
    }
}
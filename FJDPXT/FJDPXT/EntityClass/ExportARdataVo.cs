using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    public class ExportARdataVo : Models.B_Order
    {
        //用户组号
        public string userGroup { get; set; }
        //用户工号
        public string jobNumber { get; set; }
        //PNR
        public string PNR { get; set; }


        public string payTimeStr
        {
            get
            {
                return this.payTime.Value.ToString("yyyy-MM-dd HH:mm:ss");
            }
        }
    }
}
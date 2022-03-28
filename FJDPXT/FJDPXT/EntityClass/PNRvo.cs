using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class PNRvo:B_PNR
    {
        public List<B_PNRPassenger> Passenger { get; set; }//PNR旅客信息 列表
        public string flightCode { get; set; }//航班号
        public DateTime? flightDate { get; set; }//航班日期
        public string strPNRStatus//PNR状态
        {
            get
            {
                switch (this.PNRStatus.Value)
                {
                    case 0:
                        return "已经取消";
                    case 1:
                        return "已经订座";
                    case 2:
                        return "部分出票";
                    case 3:
                        return "全部出票";
                    default:
                        return "";
                }
            }
        }
        
    }
}
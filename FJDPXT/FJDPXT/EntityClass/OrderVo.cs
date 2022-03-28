using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class OrderVo : B_Order
    {
        /// <summary>
        /// PNR编号
        /// </summary>
        public string PNRNo { get; set; }
        /// <summary>
        /// 用户组号
        /// </summary>
        public string userGroupNumber { get; set; }
        /// <summary>
        /// 工号
        /// </summary>
        public string jobNumber { get; set; }
        /// <summary>
        /// 用户组ID
        /// </summary>
        public int userGroupID { get; set; }
        /// <summary>
        /// 支付账号
        /// </summary>
        public string account { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        

    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class VirtualAccountVo: S_VirtualAccount
    {
        /// <summary>
        /// 用户工号
        /// </summary>
        public string jobNumber { get; set; }
    }
}
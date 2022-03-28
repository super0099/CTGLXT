using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class TicketInfo
    {
        /// <summary>
        /// 存放客票信息对象
        /// </summary>
        public S_Ticket ticket { get; set; }

        /// <summary>
        /// 该客票信息可用使用的客票数
        /// </summary>
        public int canUseNum { get; set; }

        /// <summary>
        /// 本次使用的客票数
        /// </summary>
        public int useNum { get; set; }

        /// <summary>
        /// 当前的客票号
        /// </summary>
        public string currentTicketNo { get; set; }
    }
}
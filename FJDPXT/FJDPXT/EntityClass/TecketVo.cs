using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class TecketVo : S_Ticket
    {
        //拼接开始票号字符串
        public string strStartTicketNo
        {
            get
            {
                return "E781-" + this.startTicketNo;
            }
        }

        public string strEndTicketNo
        {
            get
            {
                return "E781-" + this.endTicketNo;
            }
        }


        public string strCurrentTicketNo
        {
            get
            {
                return "E781-" + this.currentTicketNo;
            }
        }

        public string strTicketDate
        {
            get
            {
                return this.ticketDate.Value.ToString("yyyy-MM-dd HH:mm:ss");
            }
        }


        /// <summary>
        /// 用户姓名
        /// </summary>
        public string userName { get; set; }
        /// <summary>
        /// 用户工号
        /// </summary>
        public string jobNumber { get; set; }

        /// <summary>
        /// 用户组号
        /// </summary>
        public string userGroupNumber { get; set; }
    }
    
}
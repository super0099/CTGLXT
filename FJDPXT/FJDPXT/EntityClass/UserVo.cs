using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class UserVo:S_User
    {
        //扩展---角色
        public string userType { get; set; }
        //扩展---用户组号
        public string userGroupNumber { get; set; }
        //扩展---用户余额
        public decimal accountBalance { get; set; }
        
       
    }
}
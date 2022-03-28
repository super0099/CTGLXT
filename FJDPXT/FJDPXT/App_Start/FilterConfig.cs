using FJDPXT.Filter;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FJDPXT
{
    public class FilterConfig
    {
        /// <summary>
        /// 全局过滤器
        /// </summary>
        /// <param name="filters"></param>
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            //全局默认的过滤器-错误处理 
            filters.Add(new HandleErrorAttribute());
            //添加自定义 权限过滤器
            filters.Add(new PermissionFilter());
        }
    }
}
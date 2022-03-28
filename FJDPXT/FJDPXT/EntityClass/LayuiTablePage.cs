using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    //layui table的分页信息
    public class LayuiTablePage
   {    /// <summary>
        /// 当前页码
        /// </summary>
        public int page { get; set; }//1开始算起
        /// <summary>
        /// 分页大小
        /// </summary>
        public int limit { get; set; }//假如一页有五条，开始第一条索引为0，那么这一页有0 1 2 3 4
        /// <summary>
        /// 获取要跳过的数据条数(同时也是要查询的数据的开始索引)
        /// 开始的索引
        /// </summary>
        /// <returns></returns>
        public int GetStartIndex()
        {
            return (page - 1) * limit;//第一页：(1-1)*5,开始索引值为0.第二页：(2-1)*5，开始索引值为5
        }
        /// <summary>
        /// 分页数据结束位置的索引
        /// </summary>
        /// <returns></returns>
        public int GetEndIndex()
        {
            return page * limit - 1;
        }
    }
}
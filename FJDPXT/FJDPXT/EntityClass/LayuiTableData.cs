using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    /// <summary>
    /// layui绑定表格数据
    /// </summary>
    /// <typeparam name="T"></typeparam>
    public class LayuiTableData<T>
    {
        /// <summary>
        /// 状态
        /// </summary>
        public int code { get; set; }

        /// <summary>
        /// 消息
        /// </summary>
        public string msg { get; set; }

        /// <summary>
        /// 数据总条数
        /// </summary>
        public int count { get; set; }

        /// <summary>
        /// 数据
        /// </summary>
        public List<T> data { get; set; }
    }
}
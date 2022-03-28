using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.EntityClass
{
    public class XmSelectVo
    {
        /// <summary>
        /// 下拉框option的 文本
        /// </summary>
        public string name { get; set; }


        /// <summary>
        /// 下拉框option的 value值
        /// </summary>
        public int value { get; set; }

        /// <summary>
        /// 是否选择
        /// </summary>
        public bool selected { get; set; }
        /// <summary>
        /// 是否禁用
        /// </summary>
        public bool disabled { get; set; }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using FJDPXT.Models;

namespace FJDPXT.EntityClass
{
    public class TransactionRecordVo : B_TransactionRecord
    {
        /// <summary>
        /// 账户
        /// </summary>
        public string account { get; set; }
        /// <summary>
        /// 用户姓名
        /// </summary>
        public string userName { get; set; }

        /// <summary>
        /// 交易类型  
        /// </summary>
        private int _transactionType;

        public int transactionType
        {
            get { return _transactionType; }
            set
            {
                _transactionType = value;
                switch (_transactionType)
                {
                    case 0:
                        _transactionTypeStr = "支出";
                        break;
                    case 1:
                        _transactionTypeStr = "收入";
                        break;
                }
            }
        }

        private string _transactionTypeStr;
        public string transactionTypeStr
        {
            get { return _transactionTypeStr; }
        }

        /// <summary>
        /// 交易时间
        /// </summary>        
        private string TransactionTimeStr;
        public string transactionTimeStr
        {
            get
            {
                return TransactionTimeStr;
            }
            set
            {
                try
                {
                    TransactionTimeStr = Convert.ToDateTime(value).ToString("yyyy/MM/dd HH:mm:ss");
                }
                catch (Exception e)
                {
                    Console.WriteLine(e);
                    TransactionTimeStr = value;
                }
            }
        }

        /// <summary>
        /// 账户余额
        /// </summary>
        public Nullable<decimal> accountBalance { get; set; }

    }
}
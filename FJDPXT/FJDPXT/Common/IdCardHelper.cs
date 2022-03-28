using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace FJDPXT.Common
{
    public class IdCardHelper
    {
        /// <summary>  
        /// 验证身份证合理性
        /// </summary>
        /// <param name="idCard"></param>
        /// <returns></returns>  
        public static bool CheckIdCard(string idCard)
        {
            //判断是否为空
            if (string.IsNullOrEmpty(idCard))
            {
                return false;
            }
            switch (idCard.Length)
            {
                case 18:
                    {
                        bool check = CheckIdCard18(idCard);
                        return check;
                    }
                case 15:
                    {
                        bool check = CheckIdCard15(idCard);
                        return check;
                    }
                default:
                    return false;
            }
        }

        /// <summary>
        /// 根据身份证获取年龄
        /// </summary>
        /// <param name="idCard"></param>
        /// <returns></returns>
        public static int GetAgeByIdCard(string idCard)
        {
            int age = 0;
            if (CheckIdCard(idCard))
            {
                var subStr = string.Empty;
                switch (idCard.Length)
                {
                    case 18:
                        subStr = idCard.Substring(6, 8).Insert(4, "-").Insert(7, "-");
                        break;
                    case 15:
                        subStr = ("19" + idCard.Substring(6, 6)).Insert(4, "-").Insert(7, "-");
                        break;
                }
                TimeSpan ts = DateTime.Now.Subtract(Convert.ToDateTime(subStr));
                age = ts.Days / 365;
            }
            return age;
        }



        #region 私有方法

        /// <summary>  
        /// 18位身份证号码验证  
        /// </summary>  
        private static bool CheckIdCard18(string idNumber)
        {
            long n;
            if (long.TryParse(idNumber.Remove(17), out n) == false
                || n < Math.Pow(10, 16) || long.TryParse(idNumber.Replace('x', '0').Replace('X', '0'), out n) == false)
            {
                return false;//数字验证  
            }
            string address = "11x22x35x44x53x12x23x36x45x54x13x31x37x46x61x14x32x41x50x62x15x33x42x51x63x21x34x43x52x64x65x71x81x82x91";
            if (address.IndexOf(idNumber.Remove(2), StringComparison.Ordinal) == -1)
            {
                return false;//省份验证  
            }
            string birth = idNumber.Substring(6, 8).Insert(6, "-").Insert(4, "-");
            DateTime time;
            if (DateTime.TryParse(birth, out time) == false)
            {
                return false;//生日验证  
            }
            string[] arrVarifyCode = ("1,0,x,9,8,7,6,5,4,3,2").Split(',');
            string[] wi = ("7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2").Split(',');
            char[] ai = idNumber.Remove(17).ToCharArray();
            int sum = 0;
            for (int i = 0; i < 17; i++)
            {
                sum += int.Parse(wi[i]) * int.Parse(ai[i].ToString());
            }
            int y;
            Math.DivRem(sum, 11, out y);
            if (arrVarifyCode[y] != idNumber.Substring(17, 1).ToLower())
            {
                return false;//校验码验证  
            }
            return true;//符合GB11643-1999标准  
        }


        /// <summary>  
        /// 15位身份证号码验证  
        /// </summary>  
        private static bool CheckIdCard15(string idNumber)
        {
            long n;
            if (long.TryParse(idNumber, out n) == false || n < Math.Pow(10, 14))
            {
                return false;//数字验证  
            }
            string address = "11x22x35x44x53x12x23x36x45x54x13x31x37x46x61x14x32x41x50x62x15x33x42x51x63x21x34x43x52x64x65x71x81x82x91";
            if (address.IndexOf(idNumber.Remove(2), StringComparison.Ordinal) == -1)
            {
                return false;//省份验证  
            }
            string birth = idNumber.Substring(6, 6).Insert(4, "-").Insert(2, "-");
            DateTime time;
            if (DateTime.TryParse(birth, out time) == false)
            {
                return false;//生日验证  
            }
            return true;
        }

        #endregion
    }
}
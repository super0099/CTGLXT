using System;

namespace FJDPXT.EntityClass
{
    public class PaymentStatisticsVo
    {
        /// <summary>
        /// 乘客名称
        /// </summary>
        public string passengerName { get; set; }
        /// <summary>
        /// 用户组
        /// </summary>
        public string userGroupNumber { get; set; }
        /// <summary>
        /// 航班号
        /// </summary>
        public string flightCode { get; set; }
        /// <summary>
        /// 起飞日期
        /// </summary>
        public string flightDate { get; set; }
        /// <summary>
        /// 起飞时间
        /// </summary>
        /// <summary>
        /// 起飞时间
        /// </summary>
        //public string departureTime { get; set; }
        private string departureTime;
        /// <summary>
        /// 起飞时间
        /// </summary>
        public string strDepartureTime
        {
            get
            {
                try
                {
                    departureTime = Convert.ToDateTime(departureTime).ToString("HH:mm");
                    return departureTime;
                }
                catch (Exception)
                {
                    return departureTime;
                }
            }
            set
            {
                departureTime = value;
            }
        }

        /// <summary>
        /// 飞机起飞城市
        /// </summary>
        public string orangeCityName { get; set; }
        /// <summary>
        /// 飞机到达城市
        /// </summary>
        public string destinationCityName { get; set; }
        /// <summary>
        /// 舱位等级
        /// </summary>
        public string cabinTypeCode { get; set; }
        /// <summary>
        /// 证件类型名称
        /// </summary>
        public string certificatesType { get; set; }
        /// <summary>
        /// 证件编号
        /// </summary>
        public string certificatesCode { get; set; }
        /// <summary>
        /// 出票日期
        /// </summary>
        private string operatingTtime;
        public string strOperatingTtime
        {
            get
            {
                try
                {
                    operatingTtime = Convert.ToDateTime(operatingTtime).ToString("yyyy/MM/dd HH:mm:ss");
                    return operatingTtime;
                }
                catch (Exception)
                {
                    return operatingTtime;
                }
            }
            set
            {
                operatingTtime = value;
            }
        }

        public DateTime? operTime { get; set; }
    }
}
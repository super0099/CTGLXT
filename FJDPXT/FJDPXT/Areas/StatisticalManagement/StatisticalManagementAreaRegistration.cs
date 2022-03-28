using System.Web.Mvc;

namespace FJDPXT.Areas.StatisticalManagement
{
    public class StatisticalManagementAreaRegistration : AreaRegistration 
    {
        public override string AreaName 
        {
            get 
            {
                return "StatisticalManagement";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context) 
        {
            context.MapRoute(
                "StatisticalManagement_default",
                "StatisticalManagement/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
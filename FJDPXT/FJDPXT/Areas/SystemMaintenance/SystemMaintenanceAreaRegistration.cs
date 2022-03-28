using System.Web.Mvc;

namespace FJDPXT.Areas.SystemMaintenance
{
    public class SystemMaintenanceAreaRegistration : AreaRegistration 
    {
        public override string AreaName 
        {
            get 
            {
                return "SystemMaintenance";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context) 
        {
            context.MapRoute(
                "SystemMaintenance_default",
                "SystemMaintenance/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
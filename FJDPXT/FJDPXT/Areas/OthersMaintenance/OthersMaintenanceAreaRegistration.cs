using System.Web.Mvc;

namespace FJDPXT.Areas.OthersMaintenance
{
    public class OthersMaintenanceAreaRegistration : AreaRegistration 
    {
        public override string AreaName 
        {
            get 
            {
                return "OthersMaintenance";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context) 
        {
            context.MapRoute(
                "OthersMaintenance_default",
                "OthersMaintenance/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
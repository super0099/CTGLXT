using System.Web.Mvc;

namespace FJDPXT.Areas.PNR
{
    public class PNRAreaRegistration : AreaRegistration 
    {
        public override string AreaName 
        {
            get 
            {
                return "PNR";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context) 
        {
            context.MapRoute(
                "PNR_default",
                "PNR/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
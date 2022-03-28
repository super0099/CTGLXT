using System.Web.Mvc;

namespace FJDPXT.Areas.ElectronicsTicket
{
    public class ElectronicsTicketAreaRegistration : AreaRegistration 
    {
        public override string AreaName 
        {
            get 
            {
                return "ElectronicsTicket";
            }
        }

        public override void RegisterArea(AreaRegistrationContext context) 
        {
            context.MapRoute(
                "ElectronicsTicket_default",
                "ElectronicsTicket/{controller}/{action}/{id}",
                new { action = "Index", id = UrlParameter.Optional }
            );
        }
    }
}
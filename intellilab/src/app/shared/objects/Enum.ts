enum dashboardRouteDestinations { none = 100, inv_expired, inv_stock, imm_reminder, quotes_expired, quotes_expireSoon, SOP_toApprove, KIT_toReview, KIT_toApprove }

export class Enum {
  public static getDashboardRouteDestinations() {
     return dashboardRouteDestinations;
   }
}

package com.internal.service.somruinternal.tasks;

import com.internal.service.somruinternal.model2.OrderItemV2;
import com.internal.service.somruinternal.model2.SettingV2;
import com.internal.service.somruinternal.model2.SettingValueV2;
import com.internal.service.somruinternal.dto.EmailDetail;
import com.internal.service.somruinternal.repository2.OrderItemRepositoryV2;
import com.internal.service.somruinternal.repository2.SettingRepositoryV2;
import com.internal.service.somruinternal.utils.IPConfig;
import com.internal.service.somruinternal.utils.SendEmailUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * Produces an html report of Orders with no existing ETA which is emailed
 * to the purchasing agent as defined in the settings table with page = orderItem
 * and type = orderagentemail
 * @author peter
 */
@Component
public class OrdersWithNoETAReportTask implements SomruTask {

    @Autowired
    OrderItemRepositoryV2 orderItemRepo;
    
    @Autowired
    SettingRepositoryV2 settingRepo;
    
    @Autowired
    SendEmailUtil sendEmailUtil;

    @Value( "${angular.server.port}" )
    private String angularPort;

    @Value( "${angular.server.protocol}" )
    private String angularProtocol;

    private final static Logger LOGGER = LoggerFactory.getLogger(OrdersWithNoETAReportTask.class);

    @Override
    public void run() {
        LOGGER.info("OrdersWithNoETAReportTask starting");
        String reportContent = this.buildHtmlReport();
        LOGGER.info(String.format( "Retrieved html for report - content length is %d characters", reportContent.length() ));
        if ( reportContent.length() > 0 ) {
            SettingV2 aSetting = settingRepo.getSettingsByName("orderAgentEmail");
            if ( aSetting == null ) {
                LOGGER.error("Expecting to find 1 setting with name = 'orderAgentEmail' but found 0");
                return;
            }
            List<SettingValueV2> settingValues = aSetting.getSettingValues();
            String[] emailDistList = new String[settingValues.size()];
            for ( int i = 0 ; i < settingValues.size() ; i++ )
            {
                emailDistList[i] = settingValues.get(i).getValue();
            }
            String[] ccDistList = {}; // empty
            LOGGER.info("Retrieved report - sending email now");
            EmailDetail ed = new EmailDetail(
                 emailDistList,
                 ccDistList, // cc list empty
                 "Order Items no eta notification", // subject
                 reportContent,
                 "text/html"
            );
            try {
                sendEmailUtil.sendEmail(ed);
            }
            catch ( Exception e) {
                LOGGER.error( "OrdersWithNoETAReportTask failed sendEmailUtil.sendEmail", e);
            }
        }
        else {
            LOGGER.info("No report data retrieved - skipping email step. All done of this task for today");
        }
    }

    private String buildHtmlReport() {
        
        String result = "";
        List<OrderItemV2> orderList = orderItemRepo.loadOrderedItemsNoETA();
        StringBuilder sb = new StringBuilder("");
        if ( orderList.size() > 0 ) {
            sb.append( "<h2>The following ordered items were created with no ETA.</h2>");
            sb.append( "<p>Please follow up with the supplier.</p>");
            int rowCount = 0;
            for (OrderItemV2 orderItem : orderList) {
                
                String angularURL = String.format("%s%s:%s/%s/%d",
                  angularProtocol, IPConfig.getServerIP(), angularPort, "main/orders", orderItem.getOrderItemId());
                
                if ( rowCount == 0 ) {
                    // see thymeleaf mailTemplate for def of class TFTable
                    sb.append( "<table class='TFtable'><tr class='TFtable_ODD'>" );
                    sb.append( "<th>Order Item Name</th><th>Requested By</th><th>Requested Date</th><th>Status</th>");
                    sb.append( "</tr>");
                }
                String rowClass = (rowCount % 2 == 0) ? "TFtable_EVEN" : "TFtable_ODD"; 
                String requestUserName = "Unknown";
                if ( orderItem.getRequestUser() != null ) 
                    requestUserName = orderItem.getRequestUser().getName();
                sb.append( 
                    String.format( "<tr class='%s'><th>%s, %s</th><th>%s</th><th>%s</th><th>%s</th></tr>",
                        rowClass,
                        String.format( "<a href='%s'>%s</a>", angularURL, orderItem.getName()),
                        orderItem.getCatalogNumber(),
                        requestUserName,
                        orderItem.getRequestTime(),
                        orderItem.getStatus() )
                );
                rowCount++;
            }

            LOGGER.info(String.format("OrdersWithNoETAReport : %s", result));

        }
        return result;
    }

}

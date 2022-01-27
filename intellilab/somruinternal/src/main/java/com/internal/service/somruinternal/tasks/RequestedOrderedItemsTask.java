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
import java.util.Date;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * Produces an html report of Requested Ordered Items created in the last 24 hrs.
 * @author peter
 */
@Component
public class RequestedOrderedItemsTask implements SomruTask {

    @Autowired
    OrderItemRepositoryV2 orderItemRepo;
    
    @Autowired
    SettingRepositoryV2 settingsRepo;
    
    @Autowired
    SendEmailUtil sendEmailUtil;

    @Value( "${angular.server.port}" )
    private String angularPort;

    @Value( "${angular.server.protocol}" )
    private String angularProtocol;
 
    private String emailCSV;

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestedOrderedItemsTask.class);

    @Override
    public void run() {
        LOGGER.info("RquestedOrderedItemsTask starting - loading recipients");
        this.emailCSV = this.getReportRecipientsCSV();
        if ( this.emailCSV.length() == 0 ) {
            LOGGER.info("No recipients configured under setting 'orderedItemsApproverEmails'");
            return;    
        }
        else {
            LOGGER.info(String.format("Recipient List : %s", this.emailCSV ));
        }
        String reportContent = this.buildHtmlReport();
        LOGGER.info(String.format( "Retrieved html for report - content length is %d characters", reportContent.length() ));
        if ( reportContent.length() > 0 ) {

            // this report is a temporary measure and because of this, the report recipients are simply hard coded here
            String[] emailDistList = emailCSV.split(",");
            String[] ccDistList = {}; // empty
            LOGGER.info("Retrieved report - sending email now");
            EmailDetail ed = new EmailDetail(
                 emailDistList,
                 ccDistList, // cc list empty
                 "Requested Ordered Items", // subject
                 reportContent,
                 "text/html"
            );
            try {
                sendEmailUtil.sendEmail(ed);
            }
            catch ( Exception e) {
                LOGGER.error( "RquestedOrderedItemsTask failed sendEmailUtil.sendEmail", e);
            }
        }
        else {
            LOGGER.info("No report data retrieved - skipping email step. All done of this task for today");
        }
    }
    
    private String getReportRecipientsCSV() {
        StringBuilder csv = new StringBuilder("");
        SettingV2 aSetting = settingsRepo.getSettingsByName("orderedItemsApproverEmails");
        if ( aSetting != null ) {
            List<SettingValueV2> listOfSettingValues = aSetting.getSettingValues();
            for (SettingValueV2 aSettingValue : listOfSettingValues) {
                csv.append(aSettingValue.getValue());
                csv.append( ",");
            }
        }
        return csv.toString();    
    }

    private String buildHtmlReport() {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date dateOneDayAgo = calendar.getTime();
        
        List<OrderItemV2> orderList = orderItemRepo.loadRequestedOrderCreatedSince(dateOneDayAgo);
        StringBuilder sb = new StringBuilder("");
        if ( orderList.size() > 0 ) {
            sb.append( "<h2>The following requested items have been created in the past 24 hours and are awaiting approval.</h2>");
            sb.append( "<p>Please notify staff of your approval.</p>");
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
            //TODO Temporary comment out
//            context.setVariable("message", sb.toString());
//            result = templateEngine.process("mailTemplate", context);
            LOGGER.info(String.format("RquestedOrderedItemsTask : %s", result));
        }
        return result;
    }
}

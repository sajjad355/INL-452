package com.internal.service.somruinternal.tasks;


import com.internal.service.somruinternal.model2.EquipmentV2;
import com.internal.service.somruinternal.dto.EmailDetail;
import com.internal.service.somruinternal.repository2.EquipmentRepositoryV2;
import com.internal.service.somruinternal.utils.SendEmailUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * notify the last person who has edited the Equipment record of the need to perform maintenance, calibration or both
 * to any piece of equipment where maintenance or calibration date is past due
 * @author peter
 */
@Component
public class OutofDateEquipmentTask implements SomruTask {
    
    @Autowired
    EquipmentRepositoryV2 equipmentRepo;
    
    @Autowired
    SendEmailUtil sendEmailUtil;

    @Value( "${angular.server.port}" )
    private String angularPort;

    @Value( "${angular.server.protocol}" )
    private String angularProtocol;

    private final static Logger LOGGER = LoggerFactory.getLogger(LateOrdersTask.class);
    
    @Override
    public void run() {
        LOGGER.info("OutofDateEquipmentTask starting");
        String reportContent = this.buildHtmlReport();
        LOGGER.info(String.format( "Retrieved html for report - content length is %d characters", reportContent.length() ));
        
        
    }
    
    private String buildHtmlReport() {
        String result = "";
        List<EquipmentV2> equipmentList = equipmentRepo.loadMaintenanceOrCalibrateDue();
        StringBuilder sb = new StringBuilder("");
        
        return result;
    }
    
    

    
}

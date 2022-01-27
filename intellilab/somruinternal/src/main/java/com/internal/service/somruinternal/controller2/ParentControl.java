package com.internal.service.somruinternal.controller2;


import com.internal.service.somruinternal.repository2.UserHistoryRepositoryV2;
import com.internal.service.somruinternal.model2.UserHistoryV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public abstract class ParentControl {
    
    @Autowired
    UserHistoryRepositoryV2 userHistoryRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ParentControl.class);
    
    @Transactional(rollbackFor=Exception.class)
    protected void saveUserHistory(String username, String component, String activity, String description) {
        LOGGER.info( "saveUserHistory" );
        UserHistoryV2 userHistory = new UserHistoryV2();
        userHistory.setActivity(activity);
        userHistory.setActivityTime(new java.util.Date( System.currentTimeMillis()));
        userHistory.setComponent(component);
        StringBuilder sb = new StringBuilder( description );
        sb.append( String.format( " Activity Timestamp: %s", getCurrentFormattedTimeStamp() ));
        userHistory.setDescription(sb.toString());
        userHistory.setUsername(username);
        LOGGER.info( String.format( "UserHistory Details : %s", userHistory) );
        userHistoryRepo.save(userHistory);
        LOGGER.info("User History save complete");
    }
    
    private String getCurrentFormattedTimeStamp() {
        java.util.Date now = new java.util.Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return sdf.format(now);
    }
}

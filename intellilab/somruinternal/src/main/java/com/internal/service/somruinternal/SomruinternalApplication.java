package com.internal.service.somruinternal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.internal.service.somruinternal.tasks.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SomruinternalApplication {

    private final static Logger LOGGER = LoggerFactory.getLogger(SomruinternalApplication.class);

    @Autowired
    OrdersWithNoETAReportTask taskOne;

    @Autowired
    LateOrdersTask taskTwo;

    @Autowired
    RequestedOrderedItemsTask taskThree;

    public static final int DAY_IN_MS = 86400000;

    public static void main(String[] args) {
        SpringApplication.run(SomruinternalApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(64000);
        loggingFilter.setIncludeHeaders(true);
        return loggingFilter;
    }

    // Put any tasks needing daily execution in here - sending of email reports etc
    @Scheduled(fixedRate = SomruinternalApplication.DAY_IN_MS)
    public void runDailyTasks() {
        LOGGER.info("Begin daily scheduled tasks");
        // add your daily task to the array below
        SomruTask tasks[] = {
            taskOne,
            taskTwo,
            taskThree
        };
        for (SomruTask task : tasks) {
            try {
                task.run();
            }
            catch ( Exception e ) {
                LOGGER.error("Fail to run task", e );
                // try the next one....
            }
        }
    }





}

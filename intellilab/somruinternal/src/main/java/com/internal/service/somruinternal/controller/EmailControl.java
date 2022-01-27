package com.internal.service.somruinternal.controller;

import com.internal.service.somruinternal.utils.SendEmailUtil;
import com.internal.service.somruinternal.dto.EmailDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.mail.MessagingException;


@RestController
@RequestMapping("/email")
public class EmailControl {
        
    @Autowired
    SendEmailUtil sendEmailUtil;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailControl.class);
    
    @PutMapping("/sendemail/")
    public void sendEmail(@Valid @RequestBody EmailDetail emailDetail) throws MessagingException {
        LOGGER.info( String.format( "SendEmailUtil received a request to sendMail - details %s", emailDetail) );
        try {
            sendEmailUtil.sendEmail( emailDetail);
        }
        catch ( Exception e ) {
            LOGGER.error( "EmailControl failed to sendEmail", e);
        }    
    }
    
}

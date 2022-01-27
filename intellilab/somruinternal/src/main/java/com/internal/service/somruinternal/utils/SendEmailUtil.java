package com.internal.service.somruinternal.utils;


import com.internal.service.somruinternal.dto.EmailDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendEmailUtil {

    @Autowired
    public JavaMailSender javaMailSender;

    @Autowired
    MailProperties mailProperties;

    @Value("${angular.server.port}")
    private String angularPort;

    @Value("${angular.server.protocol}")
    private String angularURLProtocol;

    @Value( "${angular.server.ip}" )
    private String serverIP;

    private final static Logger LOGGER = LoggerFactory.getLogger(SendEmailUtil.class);

    public void sendEmail(EmailDetail emailDetail) throws MessagingException {
        LOGGER.info( String.format( "SendEmailUtil received a request to sendMail - details %s", emailDetail) );
        boolean sendAsHtml = "text/html".equalsIgnoreCase(emailDetail.getMimeType());
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(emailDetail.getEmailAddressList());
            messageHelper.setCc(emailDetail.getEmailCcList());
            messageHelper.setSubject(emailDetail.getSubject());
            messageHelper.setText(emailDetail.getEmailContent(), sendAsHtml);
        };
        javaMailSender.send(messagePreparator);
    }

    public void sendResetPasswordEmail( String userEmail, String information) throws MessagingException {

        String angularURL = String.format("%s%s:%s/%s/%s",
                angularURLProtocol, serverIP, angularPort, "resetpassword", information);
        String resetpasswordsubject;
        String resetpasswordcontent;
        resetpasswordsubject = "Password Reset Request For Aegyris-IntelliLab Account";
        resetpasswordcontent
          = "<p>Hello,</p><p>A password reset request has been submitted for your <a href=\"mailto:" + userEmail + "\" target=\"_blank\">" + userEmail + "</a> " +
          "Aegyris-IntelliLab-account. Click the link below to proceed with the reset process.</p>"
          + "<a href=\"" + angularURL + "\"" + ">Reset Password</a><br><br>If you did not request to reset your password, you can ignore this email.<br><br>Thank You," +
          "<br><br>Your Somru BioScience Team";
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true);
        helper.setTo(userEmail);
        helper.setSubject(resetpasswordsubject);
        helper.setText(resetpasswordcontent, true);
        javaMailSender.send(mail);

    }



}

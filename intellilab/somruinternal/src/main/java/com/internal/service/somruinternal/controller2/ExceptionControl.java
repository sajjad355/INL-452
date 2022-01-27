package com.internal.service.somruinternal.controller2;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.dto.EmailDetail;
import com.internal.service.somruinternal.dto.SubmitExceptionInput;
import com.internal.service.somruinternal.error.ErrorMessage;
import com.internal.service.somruinternal.utils.SendEmailUtil;

@RestController
@RequestMapping("/exception")
public class ExceptionControl {

	@Autowired
	SendEmailUtil sendEmailUtil;

	@Value("${somru.mail.address.support}")
	private String supportMailAddress;

	@PostMapping("/submit")
	public void submitException(@RequestBody ErrorMessage inputException) {

		String[] emailDistList = { supportMailAddress };
		String[] ccDistList = {};

		StringBuilder reportContent = new StringBuilder();
		reportContent.append("Tag: " + inputException.getTag() + "\n\n");
		reportContent.append("Timestamp: " + inputException.getTimestamp() + "\n\n");
		reportContent.append("HTTP Status: " + inputException.getStatus() + "\n\n");
		reportContent.append("Error: " + inputException.getError() + "\n\n");
		reportContent.append("Message: " + inputException.getMessage() + "\n\n");
		reportContent.append("Request URI path: " + inputException.getPath() + "\n\n");

		EmailDetail emailDetail = new EmailDetail(emailDistList, ccDistList, "User exception report",
				reportContent.toString(), "text/html");
		try {
			sendEmailUtil.sendEmail(emailDetail);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

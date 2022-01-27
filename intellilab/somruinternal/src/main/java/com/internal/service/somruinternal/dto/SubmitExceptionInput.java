package com.internal.service.somruinternal.dto;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.internal.service.somruinternal.error.ApiSubError;

public class SubmitExceptionInput {

	private String status;
	
//  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	
	private String message;
	
	private String debugMessage;
	
	private String sugestion;
	
	private String errorCode;
	
	private String userInput;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public String getSugestion() {
		return sugestion;
	}

	public void setSugestion(String sugestion) {
		this.sugestion = sugestion;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getUserInput() {
		return userInput;
	}

	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
}

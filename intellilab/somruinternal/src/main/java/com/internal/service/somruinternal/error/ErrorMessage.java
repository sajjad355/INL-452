package com.internal.service.somruinternal.error;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpStatus;

public class ErrorMessage {

	private String tag;
	private Date timestamp; // "2021-05-27T10:42:09.601+00:00",
	private Integer status; // 404,
	private String error; // "Not Found",
//	private String trace; //
	private String message; // "No message available",
	private String path;
	
	public ErrorMessage() {
	}
	
	public ErrorMessage (HttpStatus status, Exception ex, String path) {
		if (status == null) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		this.tag = generateTag();
		this.timestamp = new Date();
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = ex.getMessage();
		this.path = path;
	}
	
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
//	public String getTrace() {
//		return trace;
//	}
//	public void setTrace(String trace) {
//		this.trace = trace;
//	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	private String generateTag() {
		Calendar calendar = Calendar.getInstance();  
		String tag = "ERR" + calendar.get(Calendar.MINUTE) + calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND) + (new Random().nextInt(899) + 100);
		return tag;
	}
}

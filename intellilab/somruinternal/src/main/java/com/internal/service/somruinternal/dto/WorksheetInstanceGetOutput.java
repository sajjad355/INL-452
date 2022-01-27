package com.internal.service.somruinternal.dto;

import java.util.List;

public class WorksheetInstanceGetOutput {
	private String message;
	private List<WorksheetInstanceSummary> summaries;

	public WorksheetInstanceGetOutput() {
		super();
	}

	public WorksheetInstanceGetOutput(String message, List<WorksheetInstanceSummary> summaries) {
		super();
		this.message = message;
		this.summaries = summaries;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<WorksheetInstanceSummary> getSummaries() {
		return summaries;
	}

	public void setSummaries(List<WorksheetInstanceSummary> summaries) {
		this.summaries = summaries;
	}

}

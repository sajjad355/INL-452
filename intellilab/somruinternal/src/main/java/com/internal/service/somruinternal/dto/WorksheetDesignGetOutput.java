package com.internal.service.somruinternal.dto;

import java.util.List;

public class WorksheetDesignGetOutput {
	private String message;
	private List<WorksheetSummary> summaries;

	public WorksheetDesignGetOutput() {
		super();
	}

	public WorksheetDesignGetOutput(String message, List<WorksheetSummary> summaries) {
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

	public List<WorksheetSummary> getSummaries() {
		return summaries;
	}

	public void setSummaries(List<WorksheetSummary> summaries) {
		this.summaries = summaries;
	}

}

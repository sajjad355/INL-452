package com.internal.service.somruinternal.dto;

import java.util.List;

public class AssignAnalystOutput {

	private String message;

	private List<Assignment> assignments;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

}

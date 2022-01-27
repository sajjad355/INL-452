package com.internal.service.somruinternal.dto;

public class AssignAnalystInput {
	
	private long analysts[];
	
	private long templateId;

	public long[] getAnalysts() {
		return analysts;
	}

	public void setAnalysts(long[] analysts) {
		this.analysts = analysts;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	
	

}

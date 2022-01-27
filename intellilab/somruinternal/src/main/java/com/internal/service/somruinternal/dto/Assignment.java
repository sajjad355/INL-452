package com.internal.service.somruinternal.dto;

public class Assignment {

	private String wsNum;
	
	private Integer exeNum;	

	private long templateId;

	private String templateName;

	private String studyNum;

	private String scientist;

	private String exType;

	private String exObjective;

	private long assignDate;

	public String getWsNum() {
		return wsNum;
	}

	public void setWsNum(String wsNum) {
		this.wsNum = wsNum;
	}

	public Integer getExeNum() {
		return exeNum;
	}

	public void setExeNum(Integer exeNum) {
		this.exeNum = exeNum;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(String studyNum) {
		this.studyNum = studyNum;
	}

	public String getScientist() {
		return scientist;
	}

	public void setScientist(String scientist) {
		this.scientist = scientist;
	}

	public String getExType() {
		return exType;
	}

	public void setExType(String exType) {
		this.exType = exType;
	}

	public String getExObjective() {
		return exObjective;
	}

	public void setExObjective(String exObjective) {
		this.exObjective = exObjective;
	}

	public long getAssignDate() {
		return assignDate;
	}

	public void setAssignDate(long assignDate) {
		this.assignDate = assignDate;
	}

}

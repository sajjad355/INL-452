package com.internal.service.somruinternal.dto;

public class WorksheetInstanceSummary {
	private String wsNum;
	private Integer exeNum;	
	private String worksheetName;
	private long worksheetId;
	private long templateId;
	private long analystId;
	private String analystName;
	private Long reviewerId;
	private String reviewerName;
	private Long startDate;
	private Long saveDate;
	private Long completionDate;
	private Long reviewDate;
	private String status;
	private Integer exVersion;
	private String exType;
	private String exObjective;
	private String studyNum;

	public WorksheetInstanceSummary() {
		super();
	}

	public WorksheetInstanceSummary(String wsNum, Integer exeNum, String worksheetName, long worksheetId, long templateId, long analystId,
			String analystName, Long reviewerId, String reviewerName, Long startDate, Long saveDate,
			Long completionDate, Long reviewDate, String status, Integer exVersion, String exType, String exObjective, String studyNum) {
		super();
		this.wsNum = wsNum;
		this.exeNum = exeNum;
		this.worksheetName = worksheetName;
		this.worksheetId = worksheetId;
		this.templateId = templateId;
		this.analystId = analystId;
		this.analystName = analystName;
		this.reviewerId = reviewerId;
		this.reviewerName = reviewerName;
		this.startDate = startDate;
		this.saveDate = saveDate;
		this.completionDate = completionDate;
		this.reviewDate = reviewDate;
		this.status = status;
		this.exVersion = exVersion;
		this.exType = exType;
		this.exObjective = exObjective;
		this.studyNum = studyNum;
	}

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

	public String getWorksheetName() {
		return worksheetName;
	}

	public void setWorksheetName(String worksheetName) {
		this.worksheetName = worksheetName;
	}

	public long getWorksheetId() {
		return worksheetId;
	}

	public void setWorksheetId(long worksheetId) {
		this.worksheetId = worksheetId;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public long getAnalystId() {
		return analystId;
	}

	public void setAnalystId(long analystId) {
		this.analystId = analystId;
	}

	public String getAnalystName() {
		return analystName;
	}

	public void setAnalystName(String analystName) {
		this.analystName = analystName;
	}

	public Long getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(Long reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getReviewerName() {
		return reviewerName;
	}

	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Long saveDate) {
		this.saveDate = saveDate;
	}

	public Long getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Long completionDate) {
		this.completionDate = completionDate;
	}

	public Long getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Long reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(String studyNum) {
		this.studyNum = studyNum;
	}

	public Integer getExVersion() {
		return exVersion;
	}

	public void setExVersion(Integer exVersion) {
		this.exVersion = exVersion;
	}
}

package com.internal.service.somruinternal.dto;

public class WorksheetSummary {
	private String wsNum;
	private Integer exeNum;	
	private long templateId;
	private String templateName;
	private Long parentId;
	private long userId;
	private String scientist;
	private long creationDate;
	private Long saveDate;
	private Long finalizationDate;
	private int finalized;
	private long version;
	private String exType;
	private String exObjective;
	private String studyNum;

	public WorksheetSummary() {
		super();
	}

	public WorksheetSummary(String wsNum, Integer exeNum, long templateId, String templateName, Long parentId, long userID, long dateCreated,
			Long dateFinalized, Long dateSaved, int finalized, String scientist, long version, String exType,
			String exObjective, String studyNum) {
		super();
		this.wsNum = wsNum;
		this.exeNum = exeNum;
		this.templateId = templateId;
		this.templateName = templateName;
		this.userId = userID;
		this.parentId = parentId;
		this.creationDate = dateCreated;
		this.finalizationDate = dateFinalized;
		this.saveDate = dateSaved;
		this.finalized = finalized;
		this.scientist = scientist;
		this.version = version;
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


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userID) {
		this.userId = userID;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long dateCreated) {
		this.creationDate = dateCreated;
	}

	public Long getFinalizationDate() {
		return finalizationDate;
	}

	public void setFinalizationDate(Long dateFinalized) {
		this.finalizationDate = dateFinalized;
	}

	public Long getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(Long dateSaved) {
		this.saveDate = dateSaved;
	}

	public int getFinalized() {
		return finalized;
	}

	public void setFinalized(int finalized) {
		this.finalized = finalized;
	}

	public String getScientist() {
		return scientist;
	}

	public void setScientist(String scientist) {
		this.scientist = scientist;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
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

}

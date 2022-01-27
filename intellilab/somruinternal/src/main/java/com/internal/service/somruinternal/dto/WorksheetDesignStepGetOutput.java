package com.internal.service.somruinternal.dto;

import java.util.List;
import java.util.Set;

import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.WorksheetStep;

public class WorksheetDesignStepGetOutput {
	private String message;

	private String wsNum;
	private Integer exeNum;	
	private Long parentId;
	private String scientist;
	private long userId;
	private String templateName;
	private long creationDate;
	private long saveDate;
	private Long finalizationDate;
	private int finalized;
	private long version;
	private String exType;
	private String exObjective;
	private String studyNum;
	private String designComment;

	private List<WorksheetStep> steps;

	private List<PlateMap> files;

	public WorksheetDesignStepGetOutput() {
		super();
	}

	public WorksheetDesignStepGetOutput(String message, String wsNum, Integer exeNum, Long parentId, long userId, String templateName,
			String scientist, long creationDate, long saveDate, Long finalizationDate, int finalized, long version,
			String exType, String exObjective, String studyNum, List<WorksheetStep> steps, List<PlateMap> files) {
		super();
		this.message = message;
		this.wsNum = wsNum;
		this.exeNum = exeNum;
		this.parentId = parentId;
		this.userId = userId;
		this.templateName = templateName;
		this.scientist = scientist;
		this.creationDate = creationDate;
		this.saveDate = saveDate;
		this.finalizationDate = finalizationDate;
		this.finalized = finalized;
		this.version = version;
		this.exType = exType;
		this.exObjective = exObjective;
		this.studyNum = studyNum;
		this.steps = steps;
		this.files = files;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getScientist() {
		return scientist;
	}

	public void setScientist(String scientist) {
		this.scientist = scientist;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getSaveDate() {
		return saveDate;
	}

	public void setSaveDate(long saveDate) {
		this.saveDate = saveDate;
	}

	public Long getFinalizationDate() {
		return finalizationDate;
	}

	public void setFinalizationDate(Long finalizationDate) {
		this.finalizationDate = finalizationDate;
	}

	public int getFinalized() {
		return finalized;
	}

	public void setFinalized(int finalized) {
		this.finalized = finalized;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<WorksheetStep> getSteps() {
		return steps;
	}

	public void setSteps(List<WorksheetStep> steps) {
		this.steps = steps;
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

	public String getStudyNum() {
		return studyNum;
	}

	public void setStudyNum(String studyNum) {
		this.studyNum = studyNum;
	}

	public String getExObjective() {
		return exObjective;
	}

	public void setExObjective(String exObjective) {
		this.exObjective = exObjective;
	}

	public List<PlateMap> getFiles() {
		return files;
	}

	public void setFiles(List<PlateMap> files) {
		this.files = files;
	}

	public String getDesignComment() {
		return designComment;
	}

	public void setDesignComment(String designComment) {
		this.designComment = designComment;
	}

}

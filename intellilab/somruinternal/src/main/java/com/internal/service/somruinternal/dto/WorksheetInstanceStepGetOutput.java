package com.internal.service.somruinternal.dto;

import java.util.List;
import java.util.Set;

import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.Result;
import com.internal.service.somruinternal.model2.WorksheetStep;

public class WorksheetInstanceStepGetOutput {
	private String message;

	private String worksheetName;
	private Long worksheetId;
	private Long templateId;
	private Long analystId;
	private String analystName;
	private Long reviewerId;
	private String reviewerName;
	private Long startDate;
	private Long saveDate;
	private Long completionDate;
	private Long reviewDate;
	private String status;
	private String overallComment;
    private Integer exVersion;
	private String exType;
	private String exObjective;
	private String studyNum;
	private String designComment;
	
	private List<WorksheetStep> steps;
	
	private List<PlateMap> files;
	
	private List<Result> results;

	public WorksheetInstanceStepGetOutput() {
		super();
	}

	public WorksheetInstanceStepGetOutput(String message, String worksheetName, Long worksheetId, Long templateId,
			Long analystId, String analystName, Long reviewerId, String reviewerName, Long startDate, Long saveDate,
			Long completionDate, Long reviewDate, String status, List<WorksheetStep> steps, String overallComment, Integer exVersion, String exType,
			String exObjective, String studyNum, List<PlateMap> files, List<Result> results, String designComment) {
		super();
		this.message = message;
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
		this.steps = steps;
		this.overallComment = overallComment;
		this.exVersion = exVersion;
		this.exType = exType;
		this.exObjective = exObjective;
		this.studyNum = studyNum;
		this.files = files;
		this.results = results;
		this.designComment = designComment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getWorksheetName() {
		return worksheetName;
	}

	public void setWorksheetName(String worksheetName) {
		this.worksheetName = worksheetName;
	}

	public Long getWorksheetId() {
		return worksheetId;
	}

	public void setWorksheetId(Long worksheetId) {
		this.worksheetId = worksheetId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public Long getAnalystId() {
		return analystId;
	}

	public void setAnalystId(Long analystId) {
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

	public List<WorksheetStep> getSteps() {
		return steps;
	}

	public void setSteps(List<WorksheetStep> steps) {
		this.steps = steps;
	}

	public String getOverallComment() {
		return overallComment;
	}

	public void setOverallComment(String overallComment) {
		this.overallComment = overallComment;
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

	public List<PlateMap> getFiles() {
		return files;
	}

	public void setFiles(List<PlateMap> files) {
		this.files = files;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getDesignComment() {
		return designComment;
	}

	public void setDesignComment(String designComment) {
		this.designComment = designComment;
	}

	public Integer getExVersion() {
		return exVersion;
	}

	public void setExVersion(Integer exVersion) {
		this.exVersion = exVersion;
	}

}

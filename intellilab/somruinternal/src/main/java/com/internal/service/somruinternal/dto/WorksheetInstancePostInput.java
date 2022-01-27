package com.internal.service.somruinternal.dto;

import java.util.ArrayList;
import java.util.List;

import com.internal.service.somruinternal.model2.Result;
import com.internal.service.somruinternal.model2.WorksheetStep;

public class WorksheetInstancePostInput {

    private long worksheetId;
    private long templateId;
    private Long analystId;
    private Long reviewerId;
    private Long startDate;
    private Long saveDate;
    private Long completionDate;
    private Long reviewDate;
    private String status;
    private String overallComment;
    private String exType;
    private String exObjective;
    private String studyNum;
    private String designComment;

    private List<WorksheetStep> worksheetSteps = new ArrayList<>();

    private Result[] results;

    public WorksheetInstancePostInput() {
        super();
    }

    public WorksheetInstancePostInput(long worksheetId, long templateId, long analystId, long reviewerId,
            Long startDate, Long saveDate, Long completionDate, Long reviewDate, String status, String overallComment,
            String exType, String exObjective, String studyNum, List<WorksheetStep> worksheetSteps, Result[] results) {
        super();
        this.worksheetId = worksheetId;
        this.templateId = templateId;
        this.analystId = analystId;
        this.reviewerId = reviewerId;
        this.startDate = startDate;
        this.saveDate = saveDate;
        this.completionDate = completionDate;
        this.reviewDate = reviewDate;
        this.status = status;
        this.overallComment = overallComment;
        this.exType = exType;
        this.exObjective = exObjective;
        this.worksheetSteps = worksheetSteps;
        this.results = results;
        this.studyNum = studyNum;
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

    public Long getAnalystId() {
        return analystId;
    }

    public void setAnalystId(Long analystId) {
        this.analystId = analystId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
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

    public List<WorksheetStep> getSteps() {
        return worksheetSteps;
    }

    public void setSteps(List<WorksheetStep> worksheetSteps) {
        this.worksheetSteps = worksheetSteps;
    }

    public Result[] getResults() {
        return results;
    }

    public void setResults(Result[] results) {
        this.results = results;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

    public String getDesignComment() {
        return designComment;
    }

    public void setDesignComment(String designComment) {
        this.designComment = designComment;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("worksheetId=");
        buff.append(this.worksheetId);
        buff.append(", templateId=");
        buff.append(templateId);
        buff.append(",analystId=");
        buff.append(analystId);
        buff.append(",reviewerId=");
        buff.append(reviewerId);
        buff.append(",startDate=");
        buff.append(startDate);
        buff.append(",saveDate=");
        buff.append(saveDate);
        buff.append(",completionDate=");
        buff.append(completionDate);
        buff.append(",reviewDate=");
        buff.append(reviewDate);
        buff.append(",status=");
        buff.append(status);
        buff.append(",overallComment=");
        buff.append(overallComment);
        buff.append(",exType=");
        buff.append(exType);
        buff.append(",exObjective=");
        buff.append(exObjective);
        buff.append(",studyNum=");
        buff.append(studyNum);
        buff.append("\n");
        buff.append(",worksheetSteps follow:\n");
        for (WorksheetStep worksheetStep : worksheetSteps) {
            buff.append(worksheetStep.toString());
        }
        buff.append(",results follow:\n");
        for (Result result : results) {
            buff.append(result.toString());
        }
        return buff.toString();
    }

    public List<WorksheetStep> getWorksheetSteps() {
        return worksheetSteps;
    }

    public void setWorksheetSteps(List<WorksheetStep> worksheetSteps) {
        this.worksheetSteps = worksheetSteps;
    }

}

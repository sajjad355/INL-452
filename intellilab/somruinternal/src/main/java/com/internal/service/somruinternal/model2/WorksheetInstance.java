package com.internal.service.somruinternal.model2;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ws_instance_worksheet")
public class WorksheetInstance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long dbid;

	@Column(nullable = false)
	private long startDate;

	@Column
	private Long saveDate;

	@Column
	private Long completionDate;

	@Column
	private Long reviewDate;

	@Column(nullable = false)
	private String status;

	@Column
	private String overallComment;

	@Column(nullable = false)
	private Integer exVersion;

	@Column(nullable = false)
	private String exType;

	@Column(nullable = false)
	private String exObjective;
	
	@Column(nullable = false)
	private String studyNum;

	@Column(nullable = true)
	private String designComment;

	@OneToMany(mappedBy = "worksheet", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	private Set<WorksheetStep> worksheetSteps = new HashSet<>();

	// User Analyst:
	@ManyToOne
	@JoinColumn(name = "fk_analyst_id")
	private UserV2 analyst;

	// User Analyst:
	@ManyToOne
	@JoinColumn(nullable = true, name = "fk_reviewer_id")
	private UserV2 reviewer;

	// Result:
	@OneToMany(mappedBy = "worksheet")
	private List<Result> results = new ArrayList<Result>();

	// Worksheet Design:
	@ManyToOne
	@JoinColumn(name = "fk_design_id")
	private WorksheetDesign worksheetDesign;
	
	public WorksheetInstance() {
		super();
	}

	public WorksheetInstance(long dbid, long startDate, Long saveDate, Long completionDate, Long reviewDate,
			String status, UserV2 analyst, UserV2 reviewer, WorksheetDesign worksheetDesign, String overallComment,
			String exType, String exObjective, String studyNum) {
		super();
		this.dbid = dbid;
		this.startDate = startDate;
		this.saveDate = saveDate;
		this.completionDate = completionDate;
		this.reviewDate = reviewDate;
		this.status = status;
		this.analyst = analyst;
		this.reviewer = reviewer;
		this.worksheetDesign = worksheetDesign;
		this.overallComment = overallComment;
		this.exType = exType;
		this.exObjective = exObjective;
		this.studyNum = studyNum;
	}

	public long getDbid() {
		return dbid;
	}

	public void setDbid(long dbid) {
		this.dbid = dbid;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
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

	public Set<WorksheetStep> getSteps() {
		return worksheetSteps;
	}

	public void setSteps(Set<WorksheetStep> worksheetSteps) {
		this.worksheetSteps = worksheetSteps;
	}

	public UserV2 getAnalyst() {
		return analyst;
	}

	public void setAnalyst(UserV2 analyst) {
		this.analyst = analyst;
	}

	public UserV2 getReviewer() {
		return reviewer;
	}

	public void setReviewer(UserV2 reviewer) {
		this.reviewer = reviewer;
	}

	public WorksheetDesign getWorksheetDesign() {
		return worksheetDesign;
	}

	public void setWorksheetDesign(WorksheetDesign worksheetDesign) {
		this.worksheetDesign = worksheetDesign;
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

	public Set<WorksheetStep> getWorksheetSteps() {
		return worksheetSteps;
	}

	public void setWorksheetSteps(Set<WorksheetStep> worksheetSteps) {
		this.worksheetSteps = worksheetSteps;
	}
        
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("WorksheetInstance: dbid=");
            builder.append( dbid );
            builder.append(",startDate=");
            builder.append( startDate );
            builder.append(",saveDate=");
            builder.append( saveDate );
            builder.append(",completionDate=");
            builder.append( completionDate );
            builder.append(",reviewDate=");
            builder.append( reviewDate );
            builder.append(",status=");
            builder.append( status );
            builder.append(",overallComment=");
            builder.append( overallComment );
            builder.append(",exType=");
            builder.append( exType );
            builder.append(",exObjective=");
            builder.append( exObjective );
            builder.append(",studyNum=");
            builder.append( studyNum );
            builder.append(",designComment=");
            builder.append( designComment );
            builder.append(",analyst=");
            builder.append( analyst );
            builder.append(",reviewer=");
            builder.append( reviewer );
            
            return builder.toString();
        }
}
package com.internal.service.somruinternal.dto;

import java.util.ArrayList;
import java.util.List;

import com.internal.service.somruinternal.model2.PlateMap;
import com.internal.service.somruinternal.model2.StopStep;
import com.internal.service.somruinternal.model2.WorksheetStep;

public class WorksheetDesignPostInput {

	private String wsNum;
	private Integer exeNum;
    private Long templateId;
    private String templateName;
    private long creationDate;
    private Long finalizationDate;
    private long saveDate;
    private int finalized;
    private String scientist;
    private Long userId;
    private Long parentId;
    private Long version;
    private String exType;
    private String exObjective;
    private String studyNum;
    private String designComment;

    private List<WorksheetStep> steps = new ArrayList<>();

    private PlateMap[] files;

    public WorksheetDesignPostInput() {
        super();
    }

    public WorksheetDesignPostInput(long dbid, String templateName, long dateCreated, Long dateFinalized,
            long dateSaved, int finalized, String scientist, long userID, long parentID, Long version, String exType, String exObjective, String studyNum, List<WorksheetStep> steps, PlateMap[] files) {
        super();
        this.templateId = dbid;
        this.templateName = templateName;
        this.creationDate = dateCreated;
        this.finalizationDate = dateFinalized;
        this.saveDate = dateSaved;
        this.finalized = finalized;
        this.scientist = scientist;
        this.userId = userID;
        this.parentId = parentID;
        this.version = version;
        this.exType = exType;
        this.exObjective = exObjective;
        this.studyNum = studyNum;
        this.steps = steps;
        this.files = files;
    }

    public List<WorksheetStep> getSteps() {
        return steps;
    }

    public void setSteps(List<WorksheetStep> steps) {
        this.steps = steps;
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

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long dbid) {
        this.templateId = dbid;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentID) {
        this.parentId = parentID;
    }

    public long getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(long dateSaved) {
        this.saveDate = dateSaved;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

    public PlateMap[] getFiles() {
        return files;
    }

    public void setFiles(PlateMap[] files) {
        this.files = files;
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
        StringBuilder builder = new StringBuilder();
        builder.append("WorksheetDesignPostInput: templateId=");
        builder.append( templateId );
        builder.append(",templateName=");
        builder.append( templateName );
        builder.append(",creationDate=");
        builder.append( creationDate );
        builder.append(",finalizationDate=");
        builder.append( finalizationDate );
        builder.append(",saveDate=");
        builder.append( saveDate );
        builder.append(",finalized=");
        builder.append( finalized );
        builder.append(",scientist=");
        builder.append( scientist );
        builder.append(",userId=");
        builder.append( userId );
        builder.append(",parentId=");
        builder.append( parentId );
        builder.append(",version=");
        builder.append( version );
        builder.append(",exType=");
        builder.append( exType );
        builder.append(",exObjective=");
        builder.append( exObjective );
        builder.append(",studyNum=");
        builder.append( studyNum );
        builder.append( "Steps follow:\n");
        for (WorksheetStep step : steps) {
           builder.append( step.toString() ); 
        }
        builder.append( "Platemaps follow:\n");
        for (PlateMap plateMap : files) {
           builder.append( plateMap.toString() ); 
        }
        return builder.toString();
    }
}

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_design_worksheet")
public class WorksheetDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column(nullable = false)
    private String wsNum;

    @Column(nullable = false)
    private Integer exeNum;

    @Column(nullable = false)
    private String templateName;

    @Column(nullable = false)
    private long dateCreated;

    @Column(nullable = true)
    private Long dateFinalized;

    @Column(nullable = true)
    private long dateSaved;

    @Column(nullable = false)
    private int finalized;

    @Column(nullable = false)
    private String scientist;

    @Column
    private String editedBy;

    @Column(nullable = false)
    private long version;

    @Column(nullable = false)
    private String exType;

    @Column(nullable = false)
    private String exObjective;

    @Column(nullable = false)
    private String studyNum;
    
    @Column(nullable = true)
    private String designComment;

    // Plate Map:
    @OneToMany(mappedBy = "worksheet", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<PlateMap> plateMap = new ArrayList<PlateMap>();

    // User:
    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private UserV2 user;
    
	@OneToMany(mappedBy = "template", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Set<WorksheetStep> worksheetSteps = new HashSet<>();

    @OneToMany(mappedBy = "worksheetDesign")
    @JsonIgnore
    private List<AnalystWorksheetDesign> analystWorksheetDesignList = new ArrayList<AnalystWorksheetDesign>();

    // Self-reference:
    @ManyToOne
    @JoinColumn(name = "fk_parent_id")
    private WorksheetDesign worksheetParent;

    @OneToMany(mappedBy = "worksheetParent")
    private List<WorksheetDesign> worksheetChildren = new ArrayList<WorksheetDesign>();

    // Worksheet Instance:
    @OneToMany(mappedBy = "worksheetDesign")
    private List<WorksheetInstance> worksheetInstances = new ArrayList<WorksheetInstance>();

	public WorksheetDesign() {
        super();
    }

    public WorksheetDesign(long dbid, String templateName, long dateCreated, Long dateFinalized, long dateSaved,
            int finalized, String scientist, String exType, String exObjective, String studyNum) {
        super();
        this.dbid = dbid;
        this.templateName = templateName;
        this.dateCreated = dateCreated;
        this.dateFinalized = dateFinalized;
        this.dateSaved = dateSaved;
        this.finalized = finalized;
        this.scientist = scientist;
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

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDateFinalized() {
        return dateFinalized;
    }

    public void setDateFinalized(Long dateFinalized) {
        this.dateFinalized = dateFinalized;
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

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public UserV2 getUser() {
        return user;
    }

    public void setUser(UserV2 user) {
        this.user = user;
    }

    public WorksheetDesign getWorksheetParent() {
        return worksheetParent;
    }

    public void setWorksheetParent(WorksheetDesign worksheetParent) {
        this.worksheetParent = worksheetParent;
    }

    public long getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(long dateSaved) {
        this.dateSaved = dateSaved;
    }

    public List<WorksheetDesign> getWorksheetChildren() {
        return worksheetChildren;
    }

    public void setWorksheetChildren(List<WorksheetDesign> worksheetChildren) {
        this.worksheetChildren = worksheetChildren;
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

    public List<WorksheetInstance> getWorksheetInstances() {
        return worksheetInstances;
    }

    public void setWorksheetInstances(List<WorksheetInstance> worksheetInstances) {
        this.worksheetInstances = worksheetInstances;
    }

    public List<PlateMap> getPlateMap() {
        return plateMap;
    }

    public void setPlateMap(List<PlateMap> plateMap) {
        this.plateMap = plateMap;
    }

    public String getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(String studyNum) {
        this.studyNum = studyNum;
    }

	public List<AnalystWorksheetDesign> getAnalystWorksheetDesignList() {
		return analystWorksheetDesignList;
	}

	public void setAnalystWorksheetDesignList(List<AnalystWorksheetDesign> analystWorksheetDesignList) {
		this.analystWorksheetDesignList = analystWorksheetDesignList;
	}

	public Set<WorksheetStep> getWorksheetSteps() {
		return worksheetSteps;
	}

	public void setWorksheetSteps(Set<WorksheetStep> worksheetSteps) {
		this.worksheetSteps = worksheetSteps;
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
        builder.append("WorksheetDesign: dbid=");
        builder.append( dbid );
        builder.append(",templateName=");
        builder.append( templateName );
        builder.append(",dateCreated=");
        builder.append( dateCreated );
        builder.append(",dateFinalized=");
        builder.append( dateFinalized );
        builder.append(",dateSaved=");
        builder.append( dateSaved );
        builder.append(",finalized=");
        builder.append( finalized );
        builder.append(",scientist=");
        builder.append( scientist );
        builder.append(",editedBy=");
        builder.append( editedBy );
        builder.append(",version=");
        builder.append( version );
        builder.append(",version=");
        builder.append( version );
        builder.append(",exType=");
        builder.append( exType );
        builder.append(",exObjective=");
        builder.append( exObjective );
        builder.append(",studyNum=");
        builder.append( studyNum );
        builder.append(",user=");
        builder.append( user );
        builder.append( "\n" );
        builder.append( "Stop Steps follow:\n");
        builder.append( "PlateMaps follow:\n");
        for (PlateMap aPlateMap : plateMap) {
           builder.append( aPlateMap.toString() ); 
        }
        return builder.toString();
    }

}

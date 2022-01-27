package com.internal.service.somruinternal.model2;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type", visible = true)
@JsonSubTypes({ @JsonSubTypes.Type(value = StopStep.class, name = "stop"),
		@JsonSubTypes.Type(value = WashStep.class, name = "wash"),
		@JsonSubTypes.Type(value = CoatingStep.class, name = "coating"),
		@JsonSubTypes.Type(value = BlockingStep.class, name = "blocking"),
		@JsonSubTypes.Type(value = CaptureStep.class, name = "capture"),
		@JsonSubTypes.Type(value = TestStep.class, name = "test"),
		@JsonSubTypes.Type(value = DetectionStep.class, name = "detection"),
		@JsonSubTypes.Type(value = SubstrateStep.class, name = "substrate"),
		@JsonSubTypes.Type(value = CalibratorPreparationStep.class, name = "calibrator preparation"),
		@JsonSubTypes.Type(value = QcPreparationStep.class, name = "qc preparation"),
		@JsonSubTypes.Type(value = SampleDilutionStep.class, name = "sample dilution"),
		@JsonSubTypes.Type(value = MrdStep.class, name = "mrd"),

		@JsonSubTypes.Type(value = StopStepInstance.class, name = "stop instance"),
		@JsonSubTypes.Type(value = WashStepInstance.class, name = "wash instance"),
		@JsonSubTypes.Type(value = CoatingStepInstance.class, name = "coating instance"),
		@JsonSubTypes.Type(value = BlockingStepInstance.class, name = "blocking instance"),
		@JsonSubTypes.Type(value = CaptureStepInstance.class, name = "capture instance"),
		@JsonSubTypes.Type(value = TestStepInstance.class, name = "test instance"),
		@JsonSubTypes.Type(value = DetectionStepInstance.class, name = "detection instance"),
		@JsonSubTypes.Type(value = SubstrateStepInstance.class, name = "substrate instance"),
		@JsonSubTypes.Type(value = CalibratorPreparationStepInstance.class, name = "calibrator preparation instance"),
		@JsonSubTypes.Type(value = QcPreparationStepInstance.class, name = "qc preparation instance"),
		@JsonSubTypes.Type(value = SampleDilutionStepInstance.class, name = "sample dilution instance"),
		@JsonSubTypes.Type(value = MrdStepInstance.class, name = "mrd instance") })

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ws_step")
public abstract class WorksheetStep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dbid;
	
	private String type;

	private long position;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ws_instance_step_equipment_map", 
                   joinColumns = @JoinColumn(name = "step_id"), 
                   inverseJoinColumns = @JoinColumn(name = "equipment_id"))
	@JsonProperty("equipments")
	private List<EquipmentV2> equipments;

	@ManyToOne
	@JoinColumn(name = "fk_worksheet_instance_id")
	@JsonIgnore
	private WorksheetInstance worksheet;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "fk_worksheet_template_id")
	@JsonIgnore
	private WorksheetDesign template;

	@Column
	private String editedBy;

	@Column(nullable = false)
	private long dispenseValue;

	@Column(nullable = false)
	private String dispenseUnit;

	//TODO check whether it should be in individual steps or common here
//	@Column(nullable = false)
	private int timer;

	@Column
	private String experimentComment;

	@Column
	private String reviewComment;

	@Column(nullable = false)
	private int isExcluded;

	@Column
	private String bufferId;

	@Column
	private String deviationDetails;

	@Column
	private String severity;

	@Column
	private Long startDate;

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public List<EquipmentV2> getEquipments() {
		return equipments;
	}

	public void setEquipments(List<EquipmentV2> equipments) {
		this.equipments = equipments;
	}

	public WorksheetInstance getWorksheet() {
		return worksheet;
	}

	public void setWorksheet(WorksheetInstance worksheet) {
		this.worksheet = worksheet;
	}

	public String getEditedBy() {
		return editedBy;
	}

	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}

	public long getDispenseValue() {
		return dispenseValue;
	}

	public void setDispenseValue(long dispenseValue) {
		this.dispenseValue = dispenseValue;
	}

	public String getDispenseUnit() {
		return dispenseUnit;
	}

	public void setDispenseUnit(String dispenseUnit) {
		this.dispenseUnit = dispenseUnit;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public String getExperimentComment() {
		return experimentComment;
	}

	public void setExperimentComment(String experimentComment) {
		this.experimentComment = experimentComment;
	}

	public String getReviewComment() {
		return reviewComment;
	}

	public void setReviewComment(String reviewComment) {
		this.reviewComment = reviewComment;
	}

	public int getIsExcluded() {
		return isExcluded;
	}

	public void setIsExcluded(int isExcluded) {
		this.isExcluded = isExcluded;
	}

	public String getBufferId() {
		return bufferId;
	}

	public void setBufferId(String bufferId) {
		this.bufferId = bufferId;
	}

	public String getDeviationDetails() {
		return deviationDetails;
	}

	public void setDeviationDetails(String deviationDetails) {
		this.deviationDetails = deviationDetails;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public WorksheetDesign getTemplate() {
		return template;
	}

	public void setTemplate(WorksheetDesign template) {
		this.template = template;
	}
}

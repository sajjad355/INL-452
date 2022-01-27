package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_instance_incubation_timer")
public class IncubationInstanceTimer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dbid;

	@Column
	private Long timeValueSet;

	@Column
	private String timeUnitSet;

	@Column
	private Long timerStartDate;

	@Column
	private Long timerValue;

	@Column
	private boolean complete;

	@Column
	private String formattedTime;
	
    @Column
    private Boolean overdue = false;
	
	@JsonIgnore
	@OneToOne(mappedBy = "timer")
    private IncubationInstanceSubStep incubationInstanceSubStep;

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public Long getTimeValueSet() {
		return timeValueSet;
	}

	public void setTimeValueSet(Long timeValueSet) {
		this.timeValueSet = timeValueSet;
	}

	public String getTimeUnitSet() {
		return timeUnitSet;
	}

	public void setTimeUnitSet(String timeUnitSet) {
		this.timeUnitSet = timeUnitSet;
	}

	public Long getTimerStartDate() {
		return timerStartDate;
	}

	public void setTimerStartDate(Long timerStartDate) {
		this.timerStartDate = timerStartDate;
	}

	public Long getTimerValue() {
		return timerValue;
	}

	public void setTimerValue(Long timerValue) {
		this.timerValue = timerValue;
	}

	public boolean getComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getFormattedTime() {
		return formattedTime;
	}

	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}

	public IncubationInstanceSubStep getIncubationInstanceSubStep() {
		return incubationInstanceSubStep;
	}

	public void setIncubationInstanceSubStep(IncubationInstanceSubStep incubationInstanceSubStep) {
		this.incubationInstanceSubStep = incubationInstanceSubStep;
	}

	public Boolean isOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}
	
	
}

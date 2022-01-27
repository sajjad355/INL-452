package com.internal.service.somruinternal.model2;

import com.internal.service.somruinternal.model2.DetectionStepInstance;
import com.internal.service.somruinternal.model2.CoatingStepInstance;
import com.internal.service.somruinternal.model2.CaptureStepInstance;
import com.internal.service.somruinternal.model2.BlockingStepInstance;

import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ws_instance_incubation_substep")
public class IncubationInstanceSubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;
    
    
    @Column
    Integer position;
    
    // Blocking step:
    @ManyToOne
    @JoinColumn(name = "fk_blocking_step_id")
    @JsonIgnore
    private BlockingStepInstance blockingStep;

    // Capture step:
    @ManyToOne
    @JoinColumn(name = "fk_capture_step_id")
    @JsonIgnore
    private CaptureStepInstance captureStep;

    // Coating step:
    @ManyToOne
    @JoinColumn(name = "fk_coating_step_id")
    @JsonIgnore
    private CoatingStepInstance coatingStep;

    // Detection step:
    @ManyToOne
    @JoinColumn(name = "fk_detection_step_id")
    @JsonIgnore
    private DetectionStepInstance detectionStep;

    // Substrate step:
    @ManyToOne
    @JoinColumn(name = "fk_substrate_step_id")
    @JsonIgnore
    private SubstrateStepInstance substrateStep;

    // Test step:
    @ManyToOne
    @JoinColumn(name = "fk_test_step_id")
    @JsonIgnore
    private TestStepInstance testStep;


    @Column(nullable = false)
    private String incubateAtValue;

    @Column(nullable = false)
    private long incubateShakingValue;

    @Column(nullable = false)
    private String incubateTimeUnit;

    @Column(nullable = false)
    private long incubateTimeValue;

    @Column(nullable = false)
    private long incubateIndex;

    @Column(nullable = false)
    private int valid;

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
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "timer_id", referencedColumnName = "dbid")
    private IncubationInstanceTimer timer;

    public IncubationInstanceSubStep() {
        super();
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getIncubateAtValue() {
        return incubateAtValue;
    }

    public void setIncubateAtValue(String incubateAtValue) {
        this.incubateAtValue = incubateAtValue;
    }

    public long getIncubateShakingValue() {
        return incubateShakingValue;
    }

    public void setIncubateShakingValue(long incubateShakingValue) {
        this.incubateShakingValue = incubateShakingValue;
    }

    public String getIncubateTimeUnit() {
        return incubateTimeUnit;
    }

    public void setIncubateTimeUnit(String incubateTimeUnit) {
        this.incubateTimeUnit = incubateTimeUnit;
    }

    public long getIncubateTimeValue() {
        return incubateTimeValue;
    }

    public void setIncubateTimeValue(long incubateTimeValue) {
        this.incubateTimeValue = incubateTimeValue;
    }

    public long getIncubateIndex() {
        return incubateIndex;
    }

    public void setIncubateIndex(long incubateIndex) {
        this.incubateIndex = incubateIndex;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
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

    public BlockingStepInstance getBlockingStep() {
        return blockingStep;
    }

    public void setBlockingStep(BlockingStepInstance blockingStep) {
        this.blockingStep = blockingStep;
    }

    public CaptureStepInstance getCaptureStep() {
        return captureStep;
    }

    public void setCaptureStep(CaptureStepInstance captureStep) {
        this.captureStep = captureStep;
    }

    public CoatingStepInstance getCoatingStep() {
        return coatingStep;
    }

    public void setCoatingStep(CoatingStepInstance coatingStep) {
        this.coatingStep = coatingStep;
    }

    public DetectionStepInstance getDetectionStep() {
        return detectionStep;
    }

    public void setDetectionStep(DetectionStepInstance detectionStep) {
        this.detectionStep = detectionStep;
    }

    public SubstrateStepInstance getSubstrateStep() {
        return substrateStep;
    }

    public void setSubstrateStep(SubstrateStepInstance substrateStep) {
        this.substrateStep = substrateStep;
    }

    public TestStepInstance getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStepInstance testStep) {
        this.testStep = testStep;
    }

	public IncubationInstanceTimer getTimer() {
		return timer;
	}

	public void setTimer(IncubationInstanceTimer timer) {
		this.timer = timer;
	}

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("IncubationInstanceSubStep: dbid=");
        buff.append(dbid);
        buff.append(",incubateAtValue=");
        buff.append(incubateAtValue);
        buff.append(",incubateShakingValue=");
        buff.append(incubateShakingValue);
        buff.append(",incubateTimeUnit=");
        buff.append(incubateTimeUnit);
        buff.append(",incubateTimeValue=");
        buff.append(incubateTimeValue);
        buff.append(",incubateIndex=");
        buff.append(incubateIndex);
        buff.append(",valid=");
        buff.append(valid);
        buff.append(",timeValueSet=");
        buff.append(timeValueSet);
        buff.append(",timeUnitSet=");
        buff.append(timeUnitSet);
        buff.append(",timerStartDate=");
        buff.append(timerStartDate);
        buff.append(",timerValue=");
        buff.append(timerValue);
        buff.append(",complete=");
        buff.append(complete);
        buff.append(",formattedTime=");
        buff.append(formattedTime);
        if ( blockingStep != null ) {
            buff.append(",blockingStepId FK=");
            buff.append(blockingStep.getDbid());
        }
        if ( captureStep != null ) {
            buff.append(",captureStepId FK=");
            buff.append(captureStep.getDbid());
        }
        if ( coatingStep != null ) {
            buff.append(",coatingStepId FK=");
            buff.append(coatingStep.getDbid());
        }
        if ( detectionStep != null ) {
            buff.append(",detectionStepId FK=");
            buff.append(detectionStep.getDbid());
        }
        if ( substrateStep != null ) {
            buff.append(",substrateStepId FK=");
            buff.append(substrateStep.getDbid());
        }
        if ( testStep != null ) {
            buff.append(",testStepId FK=");
            buff.append(testStep.getDbid());
        }
        buff.append("\n");
        return buff.toString();

    }

	public Boolean isOverdue() {
		return overdue;
	}

	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

}

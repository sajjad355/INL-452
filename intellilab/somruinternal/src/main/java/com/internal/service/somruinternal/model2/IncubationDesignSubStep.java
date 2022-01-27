package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_design_incubation_substep")
public class IncubationDesignSubStep {

    // Blocking step:
    @ManyToOne
    @JoinColumn(name = "fk_blocking_step_id")
    @JsonIgnore
    private BlockingStep blockingStep;

    // Capture step:
    @ManyToOne
    @JoinColumn(name = "fk_capture_step_id")
    @JsonIgnore
    private CaptureStep captureStep;

    // Coating step:
    @ManyToOne
    @JoinColumn(name = "fk_coating_step_id")
    @JsonIgnore
    private CoatingStep coatingStep;

    // Detection step:
    @ManyToOne
    @JoinColumn(name = "fk_detection_step_id")
    @JsonIgnore
    private DetectionStep detectionStep;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column
    private Integer position;
    
    // Substrate step:
    @ManyToOne
    @JoinColumn(name = "fk_substrate_step_id")
    @JsonIgnore
    private SubstrateStep substrateStep;

    // Test step:
    @ManyToOne
    @JoinColumn(name = "fk_test_step_id")
    @JsonIgnore
    private TestStep testStep;

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
    private Long timer;

    @Column
    private Integer complete;

    @Column
    private String formattedTime;

    public IncubationDesignSubStep() {
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

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public BlockingStep getBlockingStep() {
        return blockingStep;
    }

    public void setBlockingStep(BlockingStep blockingStep) {
        this.blockingStep = blockingStep;
    }

    public CaptureStep getCaptureStep() {
        return captureStep;
    }

    public void setCaptureStep(CaptureStep captureStep) {
        this.captureStep = captureStep;
    }

    public CoatingStep getCoatingStep() {
        return coatingStep;
    }

    public void setCoatingStep(CoatingStep coatingStep) {
        this.coatingStep = coatingStep;
    }

    public DetectionStep getDetectionStep() {
        return detectionStep;
    }

    public void setDetectionStep(DetectionStep detectionStep) {
        this.detectionStep = detectionStep;
    }

    public SubstrateStep getSubstrateStep() {
        return substrateStep;
    }

    public void setSubstrateStep(SubstrateStep substrateStep) {
        this.substrateStep = substrateStep;
    }

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }
    
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("IncubationDesignSubStep: dbid=");
        buff.append( dbid );
        buff.append(",incubateAtValue=");
        buff.append( incubateAtValue );
        buff.append(",incubateShakingValue=");
        buff.append( incubateShakingValue );
        buff.append(",incubateTimeUnit=");
        buff.append( incubateTimeUnit );
        buff.append(",incubateTimeValue=");
        buff.append( incubateTimeValue );
        buff.append(",incubateIndex=");
        buff.append( incubateIndex );
        buff.append(",valid=");
        buff.append( valid );
        buff.append(",timeValueSet=");
        buff.append( timeValueSet );
        buff.append(",timeUnitSet=");
        buff.append( timeUnitSet );
        buff.append(",timerStartDate=");
        buff.append( timerStartDate );
        buff.append(",timer=");
        buff.append( timer );
        buff.append(",complete=");
        buff.append( complete );
        buff.append(",formattedTime=");
        buff.append( formattedTime );
        buff.append( "\n");
        return buff.toString();
           
    }

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
}

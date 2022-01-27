package com.internal.service.somruinternal.model2;

import com.internal.service.somruinternal.model2.DilutionDesignSubStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.CaptureStep;
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
@Table(name = "ws_design_material_substep")
public class MaterialDesignSubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column
    private Integer position;
    
    @Column
    private String buffer;

    @Column
    private String material;

    @Column
    private long materialId;

    @Column
    private double stockConcentration;

    @Column
    private String stockConcentrationUnit;

    @Column
    private double finalConcentration;

    @Column
    private String finalConcentrationUnit;

    @Column
    private String supplier;

//     Dilution Sub-SubStep:
    @OneToMany(mappedBy = "materialStep", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<DilutionDesignSubStep> subStepArray = new ArrayList<DilutionDesignSubStep>();

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

    // Test step:
    @ManyToOne
    @JoinColumn(name = "fk_test_step_id")
    @JsonIgnore
    private TestStep testStep;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public double getStockConcentration() {
        return stockConcentration;
    }

    public void setStockConcentration(double stockConcentration) {
        this.stockConcentration = stockConcentration;
    }

    public String getStockConcentrationUnit() {
        return stockConcentrationUnit;
    }

    public void setStockConcentrationUnit(String stockConcentrationUnit) {
        this.stockConcentrationUnit = stockConcentrationUnit;
    }

    public double getFinalConcentration() {
        return finalConcentration;
    }

    public void setFinalConcentration(double finalConcentration) {
        this.finalConcentration = finalConcentration;
    }

    public String getFinalConcentrationUnit() {
        return finalConcentrationUnit;
    }

    public void setFinalConcentrationUnit(String finalConcentrationUnit) {
        this.finalConcentrationUnit = finalConcentrationUnit;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public List<DilutionDesignSubStep> getSubStepArray() {
        return subStepArray;
    }

    public void setSubStepArray(List<DilutionDesignSubStep> subStepArray) {
        this.subStepArray = subStepArray;
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

    public TestStep getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStep testStep) {
        this.testStep = testStep;
    }
    
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("MaterialDesignSubStep: dbid=");
        buff.append(dbid);
        buff.append(",buffer=");
        buff.append(buffer);
        buff.append(",material=");
        buff.append(material);
        buff.append(",materialId=");
        buff.append(materialId);
        buff.append(",stockConcentration=");
        buff.append(stockConcentration);
        buff.append(",stockConcentrationUnit=");
        buff.append(stockConcentrationUnit);
        buff.append(",finalConcentration=");
        buff.append(finalConcentration);
        buff.append(",finalConcentrationUnit=");
        buff.append(finalConcentrationUnit);
        buff.append(",supplier=");
        buff.append(supplier);
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
        if ( testStep != null ) {
            buff.append(",testStepId FK=");
            buff.append(testStep.getDbid());
        }
        buff.append( "DilutionDesignSubStep follow:\n");
//        for (DilutionDesignSubStep  dilutionSubStep : subStepArray ) {
//            buff.append( dilutionSubStep.toString());
//        }
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

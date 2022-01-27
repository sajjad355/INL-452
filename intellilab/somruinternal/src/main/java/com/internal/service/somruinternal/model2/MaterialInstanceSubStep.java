package com.internal.service.somruinternal.model2;

import com.internal.service.somruinternal.model2.DilutionInstanceSubStep;
import com.internal.service.somruinternal.model2.DetectionStepInstance;
import com.internal.service.somruinternal.model2.CoatingStepInstance;
import com.internal.service.somruinternal.model2.CaptureStepInstance;
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_instance_material_substep")
public class MaterialInstanceSubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;
    
    @Column
    Integer position;

    @Column
    private String buffer;

    @Column
    private Double bufferVolume_sys;

    @Column
    private String material;

    @Column
    private Long materialId;

    @Column
    private String materialLot_user;

    @Column
    private Double materialVolume_sys;

    @Column
    private Double stockConcentration;

    @Column
    private String stockConcentrationUnit;

    @Column
    private Double finalConcentration;

    @Column
    private String finalConcentrationUnit;

    @Column
    private String supplier;

    @Column
    private Double volume_user;

    @Column
    private String volume_userUnit;

    // Dilution Sub-SubStep:
    @OneToMany(mappedBy = "materialStep", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<DilutionInstanceSubStep> subStepArray = new ArrayList<DilutionInstanceSubStep>();

    // Detection step:
    @ManyToOne
    @JoinColumn(name = "fk_detection_step_id")
    @JsonIgnore
    private DetectionStepInstance detectionStep;

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

    // Test step:
    @ManyToOne
    @JoinColumn(name = "fk_test_step_id")
    @JsonIgnore
    private TestStepInstance testStep;

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

    public Double getBufferVolume_sys() {
        return bufferVolume_sys;
    }

    public void setBufferVolume_sys(Double bufferVolume_sys) {
        this.bufferVolume_sys = bufferVolume_sys;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialLot_user() {
        return materialLot_user;
    }

    public void setMaterialLot_user(String materialLot_user) {
        this.materialLot_user = materialLot_user;
    }

    public Double getMaterialVolume_sys() {
        return materialVolume_sys;
    }

    public void setMaterialVolume_sys(Double materialVolume_sys) {
        this.materialVolume_sys = materialVolume_sys;
    }

    public Double getStockConcentration() {
        return stockConcentration;
    }

    public void setStockConcentration(Double stockConcentration) {
        this.stockConcentration = stockConcentration;
    }

    public String getStockConcentrationUnit() {
        return stockConcentrationUnit;
    }

    public void setStockConcentrationUnit(String stockConcentrationUnit) {
        this.stockConcentrationUnit = stockConcentrationUnit;
    }

    public Double getFinalConcentration() {
        return finalConcentration;
    }

    public void setFinalConcentration(Double finalConcentration) {
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

    public Double getVolume_user() {
        return volume_user;
    }

    public void setVolume_user(Double volume_user) {
        this.volume_user = volume_user;
    }

    public String getVolume_userUnit() {
        return volume_userUnit;
    }

    public void setVolume_userUnit(String volume_userUnit) {
        this.volume_userUnit = volume_userUnit;
    }

    public List<DilutionInstanceSubStep> getSubStepArray() {
        return subStepArray;
    }

    public void setSubStepArray(List<DilutionInstanceSubStep> subStepArray) {
        this.subStepArray = subStepArray;
    }

    public DetectionStepInstance getDetectionStep() {
        return detectionStep;
    }

    public void setDetectionStep(DetectionStepInstance detectionStep) {
        this.detectionStep = detectionStep;
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

    public TestStepInstance getTestStep() {
        return testStep;
    }

    public void setTestStep(TestStepInstance testStep) {
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
        buff.append(",bufferVolume_sys=");
        buff.append(bufferVolume_sys);
        buff.append(",materialLot_user=");
        buff.append(materialLot_user);
        buff.append(",volume_user=");
        buff.append(volume_user);
        buff.append(",volume_userUnit=");
        buff.append(volume_userUnit);
        
        
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
        buff.append( "DilutionInstanceSubStep follow:\n");
        for (DilutionInstanceSubStep  dilutionSubStep : subStepArray ) {
            buff.append( dilutionSubStep.toString());
        }
        buff.append("\n");
        return buff.toString();
    }

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
    
   
}

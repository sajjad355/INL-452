package com.internal.service.somruinternal.model2;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ws_instance_detection_step")
public class DetectionStepInstance extends WorksheetStep {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

	@Column(nullable = false)
    private String detectionBuffer;

    @Column(nullable = false)
    private String detectionBufferSupplier;

    @Column
    private String detectionBufferLot_user;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "detectionStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialInstanceSubStep> subStepArray = new ArrayList<MaterialInstanceSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "detectionStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationInstanceSubStep> incubationArray = new ArrayList<IncubationInstanceSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("DetectionStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",detectionBuffer=");
        buff.append( detectionBuffer );
        buff.append(",detectionBufferSupplier=");
        buff.append( detectionBufferSupplier );
        buff.append(",detectionBufferLot_user=");
        buff.append( detectionBufferLot_user );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationInstanceSubStep follow:\n");
        for (IncubationInstanceSubStep incubationDesignSubStep : incubationArray) {
            buff.append( incubationDesignSubStep.toString());
        }
        buff.append( "MaterialInstanceSubStep follow:\n");
        for (MaterialInstanceSubStep  materialSubStep : subStepArray ) {
            buff.append( materialSubStep.toString());
        }
        buff.append( "\n");
        return buff.toString();
    }

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getDetectionBuffer() {
		return detectionBuffer;
	}

	public void setDetectionBuffer(String detectionBuffer) {
		this.detectionBuffer = detectionBuffer;
	}

	public String getDetectionBufferSupplier() {
		return detectionBufferSupplier;
	}

	public void setDetectionBufferSupplier(String detectionBufferSupplier) {
		this.detectionBufferSupplier = detectionBufferSupplier;
	}

	public String getDetectionBufferLot_user() {
		return detectionBufferLot_user;
	}

	public void setDetectionBufferLot_user(String detectionBufferLot_user) {
		this.detectionBufferLot_user = detectionBufferLot_user;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public List<MaterialInstanceSubStep> getSubStepArray() {
		return subStepArray;
	}

	public void setSubStepArray(List<MaterialInstanceSubStep> subStepArray) {
		this.subStepArray = subStepArray;
	}

	public List<IncubationInstanceSubStep> getIncubationArray() {
		return incubationArray;
	}

	public void setIncubationArray(List<IncubationInstanceSubStep> incubationArray) {
		this.incubationArray = incubationArray;
	}

	public boolean isDispenseCheck() {
		return dispenseCheck;
	}

	public void setDispenseCheck(boolean dispenseCheck) {
		this.dispenseCheck = dispenseCheck;
	}
}

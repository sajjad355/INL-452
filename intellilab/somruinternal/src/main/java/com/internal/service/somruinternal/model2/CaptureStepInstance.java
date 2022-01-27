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
@Table(name = "ws_instance_capture_step")
public class CaptureStepInstance extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String captureBuffer;

    @Column(nullable = false)
    private String captureBufferSupplier;

    @Column
    private String captureBufferLot_user;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "captureStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialInstanceSubStep> subStepArray = new ArrayList<MaterialInstanceSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "captureStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationInstanceSubStep> incubationArray = new ArrayList<IncubationInstanceSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("CaptureStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",captureBuffer=");
        buff.append( captureBuffer );
        buff.append(",captureBufferSupplier=");
        buff.append( captureBufferSupplier );
        buff.append(",captureBufferLot_user=");
        buff.append( captureBufferLot_user );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationDesignSubSteps follow:\n");
        for (IncubationInstanceSubStep incubationSubStep : incubationArray) {
            buff.append( incubationSubStep.toString());
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

	public String getCaptureBuffer() {
		return captureBuffer;
	}

	public void setCaptureBuffer(String captureBuffer) {
		this.captureBuffer = captureBuffer;
	}

	public String getCaptureBufferSupplier() {
		return captureBufferSupplier;
	}

	public void setCaptureBufferSupplier(String captureBufferSupplier) {
		this.captureBufferSupplier = captureBufferSupplier;
	}

	public String getCaptureBufferLot_user() {
		return captureBufferLot_user;
	}

	public void setCaptureBufferLot_user(String captureBufferLot_user) {
		this.captureBufferLot_user = captureBufferLot_user;
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

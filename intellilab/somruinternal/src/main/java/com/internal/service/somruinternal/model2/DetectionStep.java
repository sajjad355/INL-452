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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ws_design_detection_step")
public class DetectionStep extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String detectionBuffer;

    @Column(nullable = false)
    private String detectionBufferSupplier;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "detectionStep", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<MaterialDesignSubStep> subStepArray = new ArrayList<MaterialDesignSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "detectionStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationDesignSubStep> incubationArray = new ArrayList<IncubationDesignSubStep>();
  
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("DetectionStep: dbid=");
        buff.append( dbid );
        buff.append(",detectionBuffer=");
        buff.append( detectionBuffer );
        buff.append(",detectionBufferSupplier=");
        buff.append( detectionBufferSupplier );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationDesignSubSteps follow:\n");
        for (IncubationDesignSubStep incubationDesignSubStep : incubationArray) {
            buff.append( incubationDesignSubStep.toString());
        }
        buff.append( "MaterialDesignSubSteps follow:\n");
        for (MaterialDesignSubStep  materialSubStep : subStepArray ) {
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

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public List<MaterialDesignSubStep> getSubStepArray() {
		return subStepArray;
	}

	public void setSubStepArray(List<MaterialDesignSubStep> subStepArray) {
		this.subStepArray = subStepArray;
	}

	public List<IncubationDesignSubStep> getIncubationArray() {
		return incubationArray;
	}

	public void setIncubationArray(List<IncubationDesignSubStep> incubationArray) {
		this.incubationArray = incubationArray;
	}

	public boolean isDispenseCheck() {
		return dispenseCheck;
	}

	public void setDispenseCheck(boolean dispenseCheck) {
		this.dispenseCheck = dispenseCheck;
	}

}

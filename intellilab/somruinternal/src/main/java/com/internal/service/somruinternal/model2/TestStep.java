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
@Table(name = "ws_design_test_step")
public class TestStep extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String sampleBuffer;

    @Column(nullable = false)
    private String sampleBufferSupplier;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "testStep", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<MaterialDesignSubStep> subStepArray = new ArrayList<MaterialDesignSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "testStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationDesignSubStep> incubationArray = new ArrayList<IncubationDesignSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("TestStep: dbid=");
        buff.append( dbid );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",sampleBuffer=");
        buff.append( sampleBuffer );
        buff.append(",sampleBufferSupplier=");
        buff.append( sampleBufferSupplier );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationDesignSubStep follow:\n");
        for (IncubationDesignSubStep incubationSubStep : incubationArray) {
           buff.append( incubationSubStep.toString());
        }
        buff.append( "\n");
        buff.append( "MaterialDesignSubStep follow:\n");
        for (MaterialDesignSubStep materialSubStep : subStepArray) {
           buff.append( materialSubStep.toString());
        }
        return buff.toString();
    }

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getSampleBuffer() {
		return sampleBuffer;
	}

	public void setSampleBuffer(String sampleBuffer) {
		this.sampleBuffer = sampleBuffer;
	}

	public String getSampleBufferSupplier() {
		return sampleBufferSupplier;
	}

	public void setSampleBufferSupplier(String sampleBufferSupplier) {
		this.sampleBufferSupplier = sampleBufferSupplier;
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

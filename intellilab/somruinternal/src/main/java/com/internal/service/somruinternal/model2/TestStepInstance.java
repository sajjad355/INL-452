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
@Table(name = "ws_instance_test_step")
public class TestStepInstance extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String sampleBuffer;

    @Column(nullable = false)
    private String sampleBufferSupplier;

    @Column
    private String sampleBufferLot_user;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "testStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialInstanceSubStep> subStepArray = new ArrayList<MaterialInstanceSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "testStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationInstanceSubStep> incubationArray = new ArrayList<IncubationInstanceSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("TestStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",sampleBuffer=");
        buff.append( sampleBuffer );
        buff.append(",sampleBufferSupplier=");
        buff.append( sampleBufferSupplier );
        buff.append(",sampleBufferLot_user=");
        buff.append( sampleBufferLot_user );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationInstanceSubStep follow:\n");
        for (IncubationInstanceSubStep incubationSubStep : incubationArray) {
           buff.append( incubationSubStep.toString());
        }
        buff.append( "\n");
        buff.append( "MaterialInstanceSubStep follow:\n");
        for (MaterialInstanceSubStep materialSubStep : subStepArray) {
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


	public String getSampleBufferLot_user() {
		return sampleBufferLot_user;
	}


	public void setSampleBufferLot_user(String sampleBufferLot_user) {
		this.sampleBufferLot_user = sampleBufferLot_user;
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

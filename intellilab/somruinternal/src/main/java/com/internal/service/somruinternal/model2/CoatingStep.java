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
@Table(name = "ws_design_coating_step")
public class CoatingStep extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String coatingBuffer;

    @Column(nullable = false)
    private String coatingBufferSupplier;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "coatingStep", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<MaterialDesignSubStep> subStepArray = new ArrayList<MaterialDesignSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "coatingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationDesignSubStep> incubationArray = new ArrayList<IncubationDesignSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("CaptureStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",coatingBuffer=");
        buff.append( coatingBuffer );
        buff.append(",coatingBufferSupplier=");
        buff.append( coatingBufferSupplier );
        buff.append( "\n");
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "IncubationDesignSubStep follow:\n");
        for (IncubationDesignSubStep incubationSubStep : incubationArray) {
           buff.append( incubationSubStep.toString());
        }
        buff.append( "MaterialDesignSubStep follow:\n");
        for (MaterialDesignSubStep materialSubStep : subStepArray) {
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

	public String getCoatingBuffer() {
		return coatingBuffer;
	}

	public void setCoatingBuffer(String coatingBuffer) {
		this.coatingBuffer = coatingBuffer;
	}

	public String getCoatingBufferSupplier() {
		return coatingBufferSupplier;
	}

	public void setCoatingBufferSupplier(String coatingBufferSupplier) {
		this.coatingBufferSupplier = coatingBufferSupplier;
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

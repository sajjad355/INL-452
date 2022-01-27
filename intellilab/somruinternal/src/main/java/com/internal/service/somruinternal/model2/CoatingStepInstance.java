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
@Table(name = "ws_instance_coating_step")
public class CoatingStepInstance extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String coatingBuffer;

    @Column(nullable = false)
    private String coatingBufferSupplier;

    @Column
    private String coatingBufferLot_user;

    @Column
    private String materialId;

    // Material SubStep:
    @OneToMany(mappedBy = "coatingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MaterialInstanceSubStep> subStepArray = new ArrayList<MaterialInstanceSubStep>();

    // Incubation SubStep:
    @OneToMany(mappedBy = "coatingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationInstanceSubStep> incubationArray = new ArrayList<IncubationInstanceSubStep>();
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("CoatingStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",coatingBuffer=");
        buff.append( coatingBuffer );
        buff.append(",coatingBufferSupplier=");
        buff.append( coatingBufferSupplier );        
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationInstanceSubStep follow:\n");
        for (IncubationInstanceSubStep incubationSubStep : incubationArray) {
           buff.append( incubationSubStep.toString());
        }
        buff.append( "MaterialInstanceSubStep follow:\n");
        for (MaterialInstanceSubStep materialSubStep : subStepArray) {
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

	public String getCoatingBufferLot_user() {
		return coatingBufferLot_user;
	}

	public void setCoatingBufferLot_user(String coatingBufferLot_user) {
		this.coatingBufferLot_user = coatingBufferLot_user;
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

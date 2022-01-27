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
@Table(name = "ws_design_substrate_step")
public class SubstrateStep extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String substrate;

    @Column(nullable = false)
    private String substrateSupplier;

    @Column
    private String materialId;

    // Incubation SubStep:
    @OneToMany(mappedBy = "substrateStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationDesignSubStep> incubationArray = new ArrayList<IncubationDesignSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("SubstrateStep: dbid=");
        buff.append( dbid );
        buff.append(",substrate=");
        buff.append( substrate );
        buff.append(",substrateSupplier=");
        buff.append( substrateSupplier );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append( "\n");
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "IncubationDesignSubStep follow:\n");
        for (IncubationDesignSubStep incubationSubStep : incubationArray) {
           buff.append( incubationSubStep.toString());
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

	public String getSubstrate() {
		return substrate;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public String getSubstrateSupplier() {
		return substrateSupplier;
	}

	public void setSubstrateSupplier(String substrateSupplier) {
		this.substrateSupplier = substrateSupplier;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
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

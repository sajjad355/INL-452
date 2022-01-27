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
@Table(name = "ws_design_blocking_step")
public class BlockingStep extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String blockingBuffer;

    @Column(nullable = false)
    private String blockingBufferSupplier;

    @Column
    private String materialId;

    // Incubation SubStep:
    @OneToMany(mappedBy = "blockingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationDesignSubStep> incubationArray = new ArrayList<IncubationDesignSubStep>();
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;
    
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("BlockingStep: dbid=");
        buff.append( dbid );
        buff.append(",blockingBuffer=");
        buff.append( blockingBuffer );
        buff.append(",blockingBufferSupplier=");
        buff.append( blockingBufferSupplier );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationDesignSubSteps follow:\n");
        for (IncubationDesignSubStep incubationDesignSubStep : incubationArray) {
           buff.append( incubationDesignSubStep.toString());
        }
        return buff.toString();
    }

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getBlockingBuffer() {
		return blockingBuffer;
	}

	public void setBlockingBuffer(String blockingBuffer) {
		this.blockingBuffer = blockingBuffer;
	}

	public String getBlockingBufferSupplier() {
		return blockingBufferSupplier;
	}

	public void setBlockingBufferSupplier(String blockingBufferSupplier) {
		this.blockingBufferSupplier = blockingBufferSupplier;
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

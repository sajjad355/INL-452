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
@Table(name = "ws_instance_blocking_step")
public class BlockingStepInstance extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String blockingBuffer;

    @Column(nullable = false)
    private String blockingBufferSupplier;

    @Column
    private String blockingBufferLot_user;

    // Incubation SubStep:
    @OneToMany(mappedBy = "blockingStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncubationInstanceSubStep> incubationArray = new ArrayList<IncubationInstanceSubStep>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("BlockingStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",blockingBuffer=");
        buff.append( blockingBuffer );
        buff.append(",blockingBufferLot_user=");
        buff.append( blockingBufferLot_user );
        buff.append(",blockingBufferSupplier=");
        buff.append( blockingBufferSupplier );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");
        buff.append( "IncubationInstanceSubStep follow:\n");
        for (IncubationInstanceSubStep incubationInstanceSubStep : incubationArray) {
           buff.append( incubationInstanceSubStep.toString());
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


	public String getBlockingBufferLot_user() {
		return blockingBufferLot_user;
	}


	public void setBlockingBufferLot_user(String blockingBufferLot_user) {
		this.blockingBufferLot_user = blockingBufferLot_user;
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

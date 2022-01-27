package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ws_instance_wash_step")
public class WashStepInstance extends WorksheetStep {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String washingBuffer;

    @Column(nullable = false)
    private String washingBufferSupplier;

    @Column
    private String washingBufferLot_user;

    @Column(nullable = false)
    private long washTime;

    @Column(nullable = false)
    private long washValue;

    @Column(nullable = false)
    private String washUnit;

    @Column
    private String pbst;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("WashStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",washingBuffer=");
        buff.append( washingBuffer );
        buff.append(",washingBufferSupplier=");
        buff.append( washingBufferSupplier );
        buff.append(",washingBufferLot_user=");
        buff.append( washingBufferLot_user );      
        buff.append(",washTime=");
        buff.append( washTime );
        buff.append(",washValue=");
        buff.append( washValue );
        buff.append(",washUnit=");
        buff.append( washUnit );
        buff.append(",dispenseCheck=");
        buff.append( dispenseCheck );
        buff.append( "\n");    
        return buff.toString();
    }

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getWashingBuffer() {
		return washingBuffer;
	}

	public void setWashingBuffer(String washingBuffer) {
		this.washingBuffer = washingBuffer;
	}

	public String getWashingBufferSupplier() {
		return washingBufferSupplier;
	}

	public void setWashingBufferSupplier(String washingBufferSupplier) {
		this.washingBufferSupplier = washingBufferSupplier;
	}

	public String getWashingBufferLot_user() {
		return washingBufferLot_user;
	}

	public void setWashingBufferLot_user(String washingBufferLot_user) {
		this.washingBufferLot_user = washingBufferLot_user;
	}

	public long getWashTime() {
		return washTime;
	}

	public void setWashTime(long washTime) {
		this.washTime = washTime;
	}

	public long getWashValue() {
		return washValue;
	}

	public void setWashValue(long washValue) {
		this.washValue = washValue;
	}

	public String getWashUnit() {
		return washUnit;
	}

	public void setWashUnit(String washUnit) {
		this.washUnit = washUnit;
	}

	public String getPbst() {
		return pbst;
	}

	public void setPbst(String pbst) {
		this.pbst = pbst;
	}

	public boolean isDispenseCheck() {
		return dispenseCheck;
	}

	public void setDispenseCheck(boolean dispenseCheck) {
		this.dispenseCheck = dispenseCheck;
	}
}

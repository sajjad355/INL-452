package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ws_instance_stop_step")
public class StopStepInstance extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    @Column(nullable = false)
    private String stopSolution;

    @Column(nullable = false)
    private String stopSolutionSupplier;

    @Column(nullable = false)
    private long readAtValue_1;

    @Column(nullable = false)
    private long readAtValue_2;

    @Column
    private String stopSolutionLot_user;

    @Column
    private String materialId;
    
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean dispenseCheck;

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("StopStepInstance: dbid=");
        buff.append( dbid );
        buff.append(",stopSolution=");
        buff.append( stopSolution );
        buff.append(",stopSolutionSupplier=");
        buff.append( stopSolutionSupplier );
        buff.append(",readAtValue_1=");
        buff.append( readAtValue_1 );
        buff.append(",readAtValue_2=");
        buff.append( readAtValue_2 );
        buff.append(",stopSolutionLot_user=");
        buff.append( stopSolutionLot_user );
        buff.append(",materialId=");
        buff.append( materialId );
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

	public String getStopSolution() {
		return stopSolution;
	}

	public void setStopSolution(String stopSolution) {
		this.stopSolution = stopSolution;
	}

	public String getStopSolutionSupplier() {
		return stopSolutionSupplier;
	}

	public void setStopSolutionSupplier(String stopSolutionSupplier) {
		this.stopSolutionSupplier = stopSolutionSupplier;
	}

	public long getReadAtValue_1() {
		return readAtValue_1;
	}

	public void setReadAtValue_1(long readAtValue_1) {
		this.readAtValue_1 = readAtValue_1;
	}

	public long getReadAtValue_2() {
		return readAtValue_2;
	}

	public void setReadAtValue_2(long readAtValue_2) {
		this.readAtValue_2 = readAtValue_2;
	}

	public String getStopSolutionLot_user() {
		return stopSolutionLot_user;
	}

	public void setStopSolutionLot_user(String stopSolutionLot_user) {
		this.stopSolutionLot_user = stopSolutionLot_user;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public boolean isDispenseCheck() {
		return dispenseCheck;
	}

	public void setDispenseCheck(boolean dispenseCheck) {
		this.dispenseCheck = dispenseCheck;
	}

}

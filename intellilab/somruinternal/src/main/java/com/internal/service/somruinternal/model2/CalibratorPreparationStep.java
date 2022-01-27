package com.internal.service.somruinternal.model2;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ws_design_calibrator_preparation_step")
public class CalibratorPreparationStep  extends WorksheetStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    private String materialId;

    private String material;
    
    private String supplier;
    
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("CalibratorPreparationStep: dbid=");
        buff.append( dbid );
        buff.append(",materialId=");
        buff.append( materialId );
        buff.append(",material=");
        buff.append( material );
        buff.append(",supplier=");
        buff.append( supplier );
        buff.append( "\n");    
        return buff.toString();
    }

	public Long getDbid() {
		return dbid;
	}

	public void setDbid(Long dbid) {
		this.dbid = dbid;
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
}

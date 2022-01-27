package com.internal.service.somruinternal.model2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ws_instance_dilution_substep")
public class DilutionInstanceSubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;
    
    @Column
    Integer position;

    @Column
    private String buffer;

    @Column
    private Double bufferVolume_sys;

    @Column
    private Double finalConcentration;

    @Column
    private String finalConcentrationUnit;

    @Column
    private Double materialVolume_sys;

    @Column
    private Double stockConcentration;

    @Column
    private String stockConcentrationUnit;

    @Column
    private Double volume_user;

    @Column
    private String volume_userUnit;

    @ManyToOne
    @JoinColumn(name = "fk_material_instance_substep_id")
    @JsonIgnore
    private MaterialInstanceSubStep materialStep;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public Double getBufferVolume_sys() {
        return bufferVolume_sys;
    }

    public void setBufferVolume_sys(Double bufferVolume_sys) {
        this.bufferVolume_sys = bufferVolume_sys;
    }

    public Double getFinalConcentration() {
        return finalConcentration;
    }

    public void setFinalConcentration(Double finalConcentration) {
        this.finalConcentration = finalConcentration;
    }

    public String getFinalConcentrationUnit() {
        return finalConcentrationUnit;
    }

    public void setFinalConcentrationUnit(String finalConcentrationUnit) {
        this.finalConcentrationUnit = finalConcentrationUnit;
    }

    public Double getMaterialVolume_sys() {
        return materialVolume_sys;
    }

    public void setMaterialVolume_sys(Double materialVolume_sys) {
        this.materialVolume_sys = materialVolume_sys;
    }

    public Double getStockConcentration() {
        return stockConcentration;
    }

    public void setStockConcentration(Double stockConcentration) {
        this.stockConcentration = stockConcentration;
    }

    public String getStockConcentrationUnit() {
        return stockConcentrationUnit;
    }

    public void setStockConcentrationUnit(String stockConcentrationUnit) {
        this.stockConcentrationUnit = stockConcentrationUnit;
    }

    public Double getVolume_user() {
        return volume_user;
    }

    public void setVolume_user(Double volume_user) {
        this.volume_user = volume_user;
    }

    public String getVolume_userUnit() {
        return volume_userUnit;
    }

    public void setVolume_userUnit(String volume_userUnit) {
        this.volume_userUnit = volume_userUnit;
    }

    public MaterialInstanceSubStep getMaterialStep() {
        return materialStep;
    }

    public void setMaterialStep(MaterialInstanceSubStep materialStep) {
        this.materialStep = materialStep;
    }
    
     @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("DilutionInstanceSubStep: dbid=");
        buff.append( dbid );
        buff.append(",buffer=");
        buff.append( buffer );
        buff.append(",bufferVolume_sys=");
        buff.append( bufferVolume_sys );
        buff.append(",materialVolume_sys=");
        buff.append( materialVolume_sys );
        buff.append(",volume_user=");
        buff.append( volume_user );
        buff.append(",volume_userUnit=");
        buff.append( volume_userUnit );
        buff.append(",finalConcentration=");
        buff.append( finalConcentration );
        buff.append(",finalConcentrationUnit=");
        buff.append( finalConcentrationUnit );
        buff.append(",stockConcentration=");
        buff.append( stockConcentration );
        buff.append(",stockConcentrationUnit=");
        buff.append( stockConcentrationUnit );
        buff.append( "\n");
        return buff.toString();
    }

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
    
}

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
@Table(name = "ws_design_dilution_substep")
public class DilutionDesignSubStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column
    private Integer position;
    
    @Column
    private String buffer;

    @Column
    private double finalConcentration;

    @Column
    private String finalConcentrationUnit;

    @Column
    private double stockConcentration;

    @Column
    private String stockConcentrationUnit;

    @ManyToOne
    @JoinColumn(name = "fk_material_design_substep_id")
    @JsonIgnore
    private MaterialDesignSubStep materialStep;

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

    public double getFinalConcentration() {
        return finalConcentration;
    }

    public void setFinalConcentration(double finalConcentration) {
        this.finalConcentration = finalConcentration;
    }

    public String getFinalConcentrationUnit() {
        return finalConcentrationUnit;
    }

    public void setFinalConcentrationUnit(String finalConcentrationUnit) {
        this.finalConcentrationUnit = finalConcentrationUnit;
    }

    public double getStockConcentration() {
        return stockConcentration;
    }

    public void setStockConcentration(double stockConcentration) {
        this.stockConcentration = stockConcentration;
    }

    public String getStockConcentrationUnit() {
        return stockConcentrationUnit;
    }

    public void setStockConcentrationUnit(String stockConcentrationUnit) {
        this.stockConcentrationUnit = stockConcentrationUnit;
    }

    public MaterialDesignSubStep getMaterialStep() {
        return materialStep;
    }

    public void setMaterialStep(MaterialDesignSubStep materialStep) {
        this.materialStep = materialStep;
    }
    
    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append("DilutionDesignSubStep: dbid=");
        buff.append( dbid );
        buff.append(",buffer=");
        buff.append( buffer );
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

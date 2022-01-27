package com.internal.service.somruinternal.model2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "il_injection")
public class InjectionV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long injectionId;

    @ManyToOne(optional=false)
    @JoinColumn(name = "chicken_id")
    private ChickenV2 chicken;

    @Column(nullable = true,length=50)
    private String adjuvant;

    @Column(nullable = true)
    private Date injectionDate;

    @Column(nullable = true)
    private int daysAfterImmune;

    @Column(nullable = true)
    private double drugAmount;

    @Column(nullable = true,length=50)
    private String unit;

    @Column(nullable = true,length=50)
    private String bloodTiter;

    @Column(nullable = true,length=50)
    private String status;

    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;

    public InjectionV2() {
        super();
    }


    /**
     * @return the injectionId
     */
    public long getInjectionId() {
        return injectionId;
    }

    /**
     * @param injectionId the injectionId to set
     */
    public void setInjectionId(long injectionId) {
        this.injectionId = injectionId;
    }

    /**
     * @return the chicken
     */
    public ChickenV2 getChicken() {
        return chicken;
    }

    /**
     * @param chicken the chicken to set
     */
    public void setChicken(ChickenV2 chicken) {
        this.chicken = chicken;
    }

    /**
     * @return the adjuvant
     */
    public String getAdjuvant() {
        return adjuvant;
    }

    /**
     * @param adjuvant the adjuvant to set
     */
    public void setAdjuvant(String adjuvant) {
        this.adjuvant = adjuvant;
    }

    /**
     * @return the injectionDate
     */
    public Date getInjectionDate() {
        return injectionDate;
    }

    /**
     * @param injectionDate the injectionDate to set
     */
    public void setInjectionDate(Date injectionDate) {
        this.injectionDate = injectionDate;
    }

    /**
     * @return the daysAfterImmune
     */
    public int getDaysAfterImmune() {
        return daysAfterImmune;
    }

    /**
     * @param daysAfterImmune the daysAfterImmune to set
     */
    public void setDaysAfterImmune(int daysAfterImmune) {
        this.daysAfterImmune = daysAfterImmune;
    }

    /**
     * @return the drugAmount
     */
    public double getDrugAmount() {
        return drugAmount;
    }

    /**
     * @param drugAmount the drugAmount to set
     */
    public void setDrugAmount(double drugAmount) {
        this.drugAmount = drugAmount;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the bloodTiter
     */
    public String getBloodTiter() {
        return bloodTiter;
    }

    /**
     * @param bloodTiter the bloodTiter to set
     */
    public void setBloodTiter(String bloodTiter) {
        this.bloodTiter = bloodTiter;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the editedBy
     */
    public String getEditedBy() {
        return editedBy;
    }

    /**
     * @param editedBy the editedBy to set
     */
    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    /**
     * @return the modifiedOn
     */
    public Date getModifiedOn() {
        return modifiedOn;
    }

    /**
     * @param modifiedOn the modifiedOn to set
     */
    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }





}

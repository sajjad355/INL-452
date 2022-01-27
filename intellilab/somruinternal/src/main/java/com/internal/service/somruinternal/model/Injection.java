package com.internal.service.somruinternal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Injection")
@EntityListeners(AuditingEntityListener.class)
public class Injection {

    public Injection() {
        super();
    }

    public Injection(Long dbid, String chickenid, String adjuvant, Date injectdate, String daysafterimmune, String drugamount, String unit, String bloodtiter, String complete, String editby, Date modify) {
        super();
        this.dbid = dbid;
        this.chickenid = chickenid;
        this.adjuvant = adjuvant;
        this.injectdate = injectdate;
        this.daysafterimmune = daysafterimmune;
        this.drugamount = drugamount;
        this.unit = unit;
        this.bloodtiter = bloodtiter;
        this.complete = complete;
        this.editby = editby;
        this.modify = modify;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column(nullable = true)
    private String chickenid;

    @Column(nullable = true)
    private String adjuvant;

    @Column(nullable = true)
    private Date injectdate;

    @Column(nullable = true)
    private String daysafterimmune;

    @Column(nullable = true)
    private String drugamount;

    @Column(nullable = true)
    private String unit;

    @Column(nullable = true)
    private String bloodtiter;

    @Column(nullable = true)
    private String complete;

    @Column(nullable = true)
    private String editby;

    @Column(nullable = true)
    private Date modify;

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getChickenid() {
        return chickenid;
    }

    public void setChickenid(String chickenid) {
        this.chickenid = chickenid;
    }

    public String getAdjuvant() {
        return adjuvant;
    }

    public void setAdjuvant(String adjuvant) {
        this.adjuvant = adjuvant;
    }

    public Date getInjectdate() {
        return injectdate;
    }

    public void setInjectdate(Date injectdate) {
        this.injectdate = injectdate;
    }

    public String getDaysafterimmune() {
        return daysafterimmune;
    }

    public void setDaysafterimmune(String daysafterimmune) {
        this.daysafterimmune = daysafterimmune;
    }

    public String getDrugamount() {
        return drugamount;
    }

    public void setDrugamount(String drugamount) {
        this.drugamount = drugamount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getBloodtiter() {
        return bloodtiter;
    }

    public void setBloodtiter(String bloodtiter) {
        this.bloodtiter = bloodtiter;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getEditby() {
        return editby;
    }

    public void setEditby(String editby) {
        this.editby = editby;
    }

    public Date getModify() {
        return modify;
    }

    public void setModify(Date modify) {
        this.modify = modify;
    }

    @Override
    public String toString() {
        return "Injection [dbid=" + dbid + ", chickenid=" + chickenid + ", adjuvant=" + adjuvant + ", injectdate=" + injectdate + ", daysafterimmune=" + daysafterimmune + ", drugamount=" + drugamount + ", unit=" + unit + ", , bloodtiter=" + bloodtiter + ", complete=" + complete + "]";
    }

}

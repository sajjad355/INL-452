package com.internal.service.somruinternal.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Chicken")
@EntityListeners(AuditingEntityListener.class)
public class Chicken {

    public Chicken() {
        super();
    }

    public Chicken(Long dbid,
                   String chickenid,
                   Date dateofbirth,
                   String immunogen,
                   String projectname,
                   String chickenstatus,
                   String totalegg,
                   String eggused,
                   String eggdiscard,
                   String latesttiter,
                   Date titerdate,
                   String editby,
                   Date modify,
                   double sequence,
                   String immunstatus) {
        super();
        this.dbid = dbid;
        this.chickenid = chickenid;
        this.dateofbirth = dateofbirth;
        this.immunogen = immunogen;
        this.projectname = projectname;
        this.chickenstatus = chickenstatus;
        this.totalegg = totalegg;
        this.eggused = eggused;
        this.eggdiscard = eggdiscard;
        this.latesttiter = latesttiter;
        this.titerdate = titerdate;
        this.editby = editby;
        this.modify = modify;
        this.sequence = sequence;
        this.immunstatus = immunstatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column(nullable = true)
    private String chickenid;

    @Column(nullable = true)
    private Date dateofbirth;

    @Column(nullable = true)
    private String immunogen;

    @Column(nullable = true)
    private String projectname;

    @Column(nullable = true)
    private String chickenstatus;

    @Column(nullable = true)
    private String totalegg;

    @Column(nullable = true)
    private String eggused;

    @Column(nullable = true)
    private String eggdiscard;

    @Column(nullable = true)
    private String latesttiter;

    @Column(nullable = true)
    private Date titerdate;

    @Column(nullable = true)
    private String editby;

    @Column(nullable = true)
    private Date modify;

    @Column(nullable = true)
    private double sequence;

    @Column(nullable = true)
    private String immunstatus;

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

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getImmunogen() {
        return immunogen;
    }

    public void setImmunogen(String immunogen) {
        this.immunogen = immunogen;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public String getChickenstatus() {
        return chickenstatus;
    }

    public void setChickenstatus(String chickenstatus) {
        this.chickenstatus = chickenstatus;
    }

    public String getTotalegg() {
        return totalegg;
    }

    public void setTotalegg(String totalegg) {
        this.totalegg = totalegg;
    }

    public String getEggused() {
        return eggused;
    }

    public void setEggused(String eggused) {
        this.eggused = eggused;
    }

    public String getEggdiscard() {
        return eggdiscard;
    }

    public void setEggdiscard(String eggdiscard) {
        this.eggdiscard = eggdiscard;
    }

    public String getLatesttiter() {
        return latesttiter;
    }

    public void setLatesttiter(String latesttiter) {
        this.latesttiter = latesttiter;
    }

    public Date getTiterdate() {
        return titerdate;
    }

    public void setTiterdate(Date titerdate) {
        this.titerdate = titerdate;
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

    public double getSequence() {
        return sequence;
    }

    public void setSequence(double sequence) {
        this.sequence = sequence;
    }

    public String getImmunstatus() {
        return immunstatus;
    }

    public void setImmunstatus(String immunstatus) {
        this.immunstatus = immunstatus;
    }

    @Override
    public String toString() {
        return "Chicken [dbid=" + dbid + ", chickenid=" + chickenid + ", dateofbirth=" + dateofbirth + ", immunogen=" + immunogen + ", projectname=" + projectname + ",  chickenstatus=" + chickenstatus + ", totalegg=" + totalegg + ", eggused=" + eggused + ", eggdiscard=" + eggdiscard + ",  latesttiter=" + latesttiter + ", editby=" + editby + ", modify=" + modify + ", sequence=" + sequence + ", immunstatus=" + immunstatus + "]";
    }

}

package com.internal.service.somruinternal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "egg")
@EntityListeners(AuditingEntityListener.class)
public class Egg {

    public Egg() {
        super();
    }

    public Egg(Long dbid, String chickenid, String immunogen, String eggpart, Date collectiondate, Date firstinjectdate, String daysafterimmune, Date firstlayeggdate, Date lastlayeggdate, String amount, String location, String sublocation, Date frozendate, String addsucrose, String titer, String editby, Date modify) {
        super();
        this.dbid = dbid;
        this.chickenid = chickenid;
        this.immunogen = immunogen;
        this.eggpart = eggpart;
        this.collectiondate = collectiondate;
        this.firstinjectdate = firstinjectdate;
        this.daysafterimmune = daysafterimmune;
        this.firstlayeggdate = firstlayeggdate;
        this.lastlayeggdate = lastlayeggdate;
        this.amount = amount;
        this.location = location;
        this.sublocation = sublocation;
        this.frozendate = frozendate;
        this.addsucrose = addsucrose;
        this.titer = titer;
        this.editby = editby;
        this.modify = modify;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long dbid;

    @Column(nullable = true)
    private String chickenid;

    @Column(nullable = true)
    private String immunogen;

    @Column(nullable = true)
    private String eggpart;

    @Column(nullable = true)
    private Date collectiondate;

    @Column(nullable = true)
    private Date firstinjectdate;

    @Column(nullable = true)
    private String daysafterimmune;

    @Column(nullable = true)
    private Date firstlayeggdate;

    @Column(nullable = true)
    private Date lastlayeggdate;

    @Column(nullable = true)
    private String amount;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String sublocation;

    @Column(nullable = true)
    private Date frozendate;

    @Column(nullable = true)
    private String addsucrose;

    @Column(nullable = true)
    private String titer;

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

    public String getImmunogen() {
        return immunogen;
    }

    public void setImmunogen(String immunogen) {
        this.immunogen = immunogen;
    }

    public String getEggpart() {
        return eggpart;
    }

    public void setEggpart(String eggpart) {
        this.eggpart = eggpart;
    }

    public Date getCollectiondate() {
        return collectiondate;
    }

    public void setCollectiondate(Date collectiondate) {
        this.collectiondate = collectiondate;
    }

    public Date getFirstinjectdate() {
        return firstinjectdate;
    }

    public void setFirstinjectdate(Date firstinjectdate) {
        this.firstinjectdate = firstinjectdate;
    }

    public String getDaysafterimmune() {
        return daysafterimmune;
    }

    public void setDaysafterimmune(String daysafterimmune) {
        this.daysafterimmune = daysafterimmune;
    }

    public Date getFirstlayeggdate() {
        return firstlayeggdate;
    }

    public void setFirstlayeggdate(Date firstlayeggdate) {
        this.firstlayeggdate = firstlayeggdate;
    }

    public Date getLastlayeggdate() {
        return lastlayeggdate;
    }

    public void setLastlayeggdate(Date lastlayeggdate) {
        this.lastlayeggdate = lastlayeggdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSublocation() {
        return sublocation;
    }

    public void setSublocation(String sublocation) {
        this.sublocation = sublocation;
    }

    public Date getFrozendate() {
        return frozendate;
    }

    public void setFrozendate(Date frozendate) {
        this.frozendate = frozendate;
    }

    public String getAddsucrose() {
        return addsucrose;
    }

    public void setAddsucrose(String addsucrose) {
        this.addsucrose = addsucrose;
    }

    public String getTiter() {
        return titer;
    }

    public void setTiter(String titer) {
        this.titer = titer;
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
        return "Collection [dbid=" + dbid + ", chickenid=" + chickenid + ",  immunogen=" + immunogen + ", eggpart=" + eggpart + ",  collectiondate=" + collectiondate + ", firstinjectdate=" + firstinjectdate + ", daysafterimmune=" + daysafterimmune + ",  firstlayeggdate=" + firstlayeggdate + ", lastlayeggdate=" + lastlayeggdate + ", amount=" + amount + ", location=" + location + ", sublocation=" + sublocation + ", frozendate=" + frozendate + ", addsucrose=" + addsucrose + ", titer=" + titer + ", editby=" + editby + ", modify=" + modify + "]";
    }

}

package com.internal.service.somruinternal.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "itemarchive")
@EntityListeners(AuditingEntityListener.class)
public class ItemArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemarchivedbid;

    @Column(nullable = true)
    private String action;

    @Column(nullable = true)
    private Date createtime;

    @Column(nullable = true)
    private String createperson;

    @Column(nullable = true)
    private long dbid;

    @Column(nullable = true)
    private String category;

    @Column(nullable = true)
    private String cat;

    @Column(nullable = true)
    private String suppliercat;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private boolean active;

    @Column(nullable = true)
    private String clientspecific;

    @Column(nullable = true)
    private String supplier;

    @Column(nullable = true)
    private String manufacturer;

    @Column(nullable = true)
    private String unit;

    @Column(nullable = true)
    private String unitprice;

    @Column(nullable = true)
    private String unitsize;

    @Column(nullable = true)
    private String quantitythreshold;

    @Column(nullable = true)
    private String supplylink;

    @Column(nullable = true)
    private String manufacturerlink;

    @Column(nullable = true)
    private Date lastmodify;

    @Column(nullable = true)
    private String modifyperson;

    @Column(nullable = true, length = 5000)
    private String comment;

    public ItemArchive(
            Long itemarchivedbid,
            String action,
            Date createtime,
            String createperson,
            Long dbid,
            String category,
            String cat,
            String suppliercat,
            String name,
            String type,
            boolean active,
            String clientspecific,
            String supplier,
            String manufacturer,
            String unit,
            String unitprice,
            String unitsize,
            String quantitythreshold,
            String supplylink,
            String manufacturerlink,
            Date lastmodify,
            String modifyperson,
            String comment) {
        super();
        this.itemarchivedbid = itemarchivedbid;
        this.action = action;
        this.createtime = createtime;
        this.createperson = createperson;
        this.dbid = dbid;
        this.category = category;
        this.cat = cat;
        this.suppliercat = suppliercat;
        this.name = name;
        this.type = type;
        this.active = active;
        this.clientspecific = clientspecific;
        this.supplier = supplier;
        this.manufacturer = manufacturer;
        this.unit = unit;
        this.unitprice = unitprice;
        this.unitsize = unitsize;
        this.quantitythreshold = quantitythreshold;
        this.supplylink = supplylink;
        this.manufacturerlink = manufacturerlink;
        this.lastmodify = lastmodify;
        this.modifyperson = modifyperson;
        this.comment = comment;
    }

    public ItemArchive() {
        super();
    }

    public long getItemarchivedbid() {
        return itemarchivedbid;
    }

    public void setItemarchivedbid(long itemarchivedbid) {
        this.itemarchivedbid = itemarchivedbid;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreateperson() {
        return createperson;
    }

    public void setCreateperson(String createperson) {
        this.createperson = createperson;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSuppliercat() {
        return suppliercat;
    }

    public void setSuppliercat(String suppliercat) {
        this.suppliercat = suppliercat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getClientspecific() {
        return clientspecific;
    }

    public void setClientspecific(String clientspecific) {
        this.clientspecific = clientspecific;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getUnitsize() {
        return unitsize;
    }

    public void setUnitsize(String unitsize) {
        this.unitsize = unitsize;
    }

    public String getQuantitythreshold() {
        return quantitythreshold;
    }

    public void setQuantitythreshold(String quantitythreshold) {
        this.quantitythreshold = quantitythreshold;
    }

    public String getSupplylink() {
        return supplylink;
    }

    public void setSupplylink(String supplylink) {
        this.supplylink = supplylink;
    }

    public String getManufacturerlink() {
        return manufacturerlink;
    }

    public void setManufacturerlink(String manufacturerlink) {
        this.manufacturerlink = manufacturerlink;
    }

    public Date getLastmodify() {
        return lastmodify;
    }

    public void setLastmodify(Date lastmodify) {
        this.lastmodify = lastmodify;
    }

    public String getModifyperson() {
        return modifyperson;
    }

    public void setModifyperson(String modifyperson) {
        this.modifyperson = modifyperson;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}

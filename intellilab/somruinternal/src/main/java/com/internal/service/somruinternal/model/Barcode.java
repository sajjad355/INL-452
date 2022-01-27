package com.internal.service.somruinternal.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Barcode")
@EntityListeners(AuditingEntityListener.class)
public class Barcode {

    public Barcode(long dbid, long itemdetaildbid, String barcode, String usestatus, String editby, Date modify) {
        super();
        this.dbid = dbid;
        this.itemdetaildbid = itemdetaildbid;
        this.barcode = barcode;
        this.usestatus = usestatus;
        this.editby = editby;
        this.modify = modify;
    }

    public Barcode() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long dbid;
    @Column(nullable = true)
    private long itemdetaildbid;
    @Column(nullable = true)
    private String barcode;
    @Column(nullable = true)
    private String usestatus;
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

    public long getItemdetaildbid() {
        return itemdetaildbid;
    }

    public void setItemdetaildbid(long itemdetaildbid) {
        this.itemdetaildbid = itemdetaildbid;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getUsestatus() {
        return usestatus;
    }

    public void setUsestatus(String usestatus) {
        this.usestatus = usestatus;
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

}

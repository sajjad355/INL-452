package com.internal.service.somruinternal.model2;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "il_folder_custodian_transfer")
public class FolderCustodianTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbid;

    //transfer notes
    private String note;

    @OneToOne
    @JoinColumn(name = "fk_folder_id")
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "fk_sender_id")
    private UserV2 sender;

    @ManyToOne
    @JoinColumn(name = "fk_receiver_id")
    private UserV2 receiver;

    private String requestStatus;

    @Column(nullable = false)
    @CreationTimestamp
    private Date requestInitiationTime;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date lastModified;

    public Long getDbid() {
        return dbid;
    }

    public void setDbid(Long dbid) {
        this.dbid = dbid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Date getRequestInitiationTime() {
        return requestInitiationTime;
    }

    public void setRequestInitiationTime(Date requestInitiationTime) {
        this.requestInitiationTime = requestInitiationTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    /**
     * @return the sender
     */
    public UserV2 getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(UserV2 sender) {
        this.sender = sender;
    }

    /**
     * @return the receiver
     */
    public UserV2 getReceiver() {
        return receiver;
    }

    /**
     * @param receiver the receiver to set
     */
    public void setReceiver(UserV2 receiver) {
        this.receiver = receiver;
    }
}

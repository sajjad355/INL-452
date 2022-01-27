package com.internal.service.somruinternal.dto;

import com.internal.service.somruinternal.model2.Folder;
import com.internal.service.somruinternal.model2.UserV2;
import org.apache.commons.lang3.builder.*;

public class FolderTransferInput {

    private Long dbid;

    private String note;

    private Folder folder;

    private UserV2 sender;

    private UserV2 receiver;

    private String requestStatus;

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
    
    @Override
    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
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

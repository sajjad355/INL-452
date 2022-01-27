package com.internal.service.somruinternal.dto;


public class EmailDetail {
    
    private String[] emailAddressList;
    private String[] emailCcList;
    private String subject;
    private String emailContent;
    private String mimeType; 

    public EmailDetail() {
        super();
    }

    public EmailDetail(String[] emailAddressList, 
                       String[] emailCcList,
                       String subject, 
                       String emailContent,
                       String mimeType) {
        super();
        this.emailAddressList = emailAddressList;
        this.emailCcList = emailCcList;
        this.subject = subject;
        this.emailContent = emailContent;
        this.mimeType = mimeType;       
    }

    public String[] getEmailAddressList() {
        return emailAddressList;
    }

    public void setEmailAddressList(String[] emailAddressList) {
        this.emailAddressList = emailAddressList;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @param mimeType the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @return the emailCcList
     */
    public String[] getEmailCcList() {
        return emailCcList;
    }

    /**
     * @param emailCcList the emailCcList to set
     */
    public void setEmailCcList(String[] emailCcList) {
        this.emailCcList = emailCcList;
    }

}

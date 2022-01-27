package com.internal.service.somruinternal.model2;


import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Date;
import org.apache.commons.lang3.builder.*;



@Entity
@Table(name = "il_client_contact")
public class ClientContactV2 implements Diffable<ClientContactV2> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientContactId;
    
    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false,length=50)
    private String name;

    @Column(nullable = true,length=50)
    private String role;

    @Column(nullable = true,length=50)
    private String email;

    @Column(nullable = true,length=50)
    private String phone;

    @Column(nullable = true,length=50)
    private String ext;

    @Column(nullable = true,length=50)
    private String fax;

    @Column(nullable = false,length=50)
    private String editedBy;

    @Column(nullable = false)
    private Date modifiedOn;



    public ClientContactV2() {
        super();
    }

  
    /**
     * @return the clientContactId
     */
    public long getClientContactId() {
        return clientContactId;
    }

    /**
     * @param clientContactId the clientContactId to set
     */
    public void setClientContactId(long clientContactId) {
        this.clientContactId = clientContactId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext the ext to set
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
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

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public String toString() {        
        return ReflectionToStringBuilder.toString(this,  ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    @Override
    public DiffResult diff(ClientContactV2 obj) {
        return new DiffBuilder(this, obj, ToStringStyle.SHORT_PREFIX_STYLE, true)
          .append("name", this.getName(), obj.getName() )
          .append("active", this.isActive(), obj.isActive())      
          .append("role", this.getRole(), obj.getRole() )
          .append("email", this.getEmail(), obj.getEmail() )
          .append("phone", this.getPhone(), obj.getPhone() )
          .append("ext", this.getExt(),  obj.getExt() )
          .append("fax", this.getFax(),  obj.getFax() )
          .append("modifiedOn", this.getModifiedOn(), obj.getModifiedOn() )
          .append("editedBy", this.getEditedBy(), obj.getEditedBy() )
          .build();
    }
    
    
    
    

}

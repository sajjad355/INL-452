/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.dto;

import java.util.List;
import java.util.ArrayList;
import com.internal.service.somruinternal.model2.ClientContactV2;
import com.internal.service.somruinternal.model2.ClientCompanyV2;


public class ClientSearchDisplayDTO {

    private long clientCompanyId;
    private String companyName;
    private boolean active;
    private final List<ClientContactDTO> contacts = new ArrayList<ClientContactDTO>();
 
    /**
    @param client - ClientCompanyV2 instance to copy data from - use for search results
    */
    public ClientSearchDisplayDTO(ClientCompanyV2 client) {
       this.clientCompanyId = client.getClientCompanyId();
       this.companyName = client.getCompanyName();
       this.active = client.isActive();
       List<ClientContactV2> contactList = client.getClientContacts();
       contactList.forEach((clientContactV2) -> {
           if ( clientContactV2.isActive())
               this.addContact(clientContactV2);
       });
    }
    
    /**
    @param client - pass in individual fields from ClientCompanyV2 instance 
    but no list of contacts - purpose is for drop down client display
   */
    public ClientSearchDisplayDTO(long clientCompanyId, String companyName, boolean active ) {
       this.clientCompanyId = clientCompanyId;
       this.companyName = companyName;
       this.active = active;
       // empty list
    }

    /**
     * @return the clientCompanyId
     */
    public long getClientCompanyId() {
        return clientCompanyId;
    }

    /**
     * @param clientCompanyId the clientCompanyId to set
     */
    public void setClientCompanyId(long clientCompanyId) {
        this.clientCompanyId = clientCompanyId;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the contacts
     */
    public List<ClientContactDTO> getContacts() {
        return contacts;
    }
    
    /**
     * @param contact - the contact to add
     */
    public void addContact(ClientContactV2 contact) {
        ClientContactDTO contactDTO = new ClientContactDTO( contact );
        this.contacts.add(contactDTO);
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
    
    

}

class ClientContactDTO {

    private String name;
    private String email;
    private String phone;

    public ClientContactDTO( ClientContactV2 contact ) {
        this.name = contact.getName();
        this.email = contact.getEmail();
        this.phone = contact.getPhone();
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
}

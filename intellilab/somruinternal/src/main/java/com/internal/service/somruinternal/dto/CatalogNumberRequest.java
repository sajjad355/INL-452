/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.dto;

/**
 *
 * @author peter
 */
public class CatalogNumberRequest {

    private String clientAttribute;
    private String catalogCategoryTypeAttribute;
    private String speciesAttribute;
    private boolean kit;

    CatalogNumberRequest() {
    }

    /**
     * @return the clientAttribute
     */
    public String getClientAttribute() {
        return clientAttribute;
    }

    /**
     * @param clientAttribute the clientAttribute to set
     */
    public void setClientAttribute(String clientAttribute) {
        this.clientAttribute = clientAttribute;
    }

    /**
     * @return the catalogCategoryTypeAttribute
     */
    public String getCatalogCategoryTypeAttribute() {
        return catalogCategoryTypeAttribute;
    }

    /**
     * @param catalogCategoryTypeAttribute the catalogCategoryTypeAttribute to
     * set
     */
    public void setCatalogCategoryTypeAttribute(String catalogCategoryTypeAttribute) {
        this.catalogCategoryTypeAttribute = catalogCategoryTypeAttribute;
    }

    /**
     * @return the speciesAttribute
     */
    public String getSpeciesAttribute() {
        return speciesAttribute;
    }

    /**
     * @param speciesAttribute the speciesAttribute to set
     */
    public void setSpeciesAttribute(String speciesAttribute) {
        this.speciesAttribute = speciesAttribute;
    }

    /**
     * @return the kit
     */
    public boolean isKit() {
        return kit;
    }

    /**
     * @param kit the kit to set
     */
    public void setKit(boolean kit) {
        this.kit = kit;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.dto;

public class CatalogNumberResponse {

    private String catalogNumber;
    private String catalogNumberType;

    public CatalogNumberResponse() {
    }

    /**
     * @return the catalogNumber
     */
    public String getCatalogNumber() {
        return catalogNumber;
    }

    /**
     * @param catalogNumber the catalogNumber to set
     */
    public void setCatalogNumber(String catalogNumber) {
        this.catalogNumber = catalogNumber;
    }

    /**
     * @return the catalogNumberType
     */
    public String getCatalogNumberType() {
        return catalogNumberType;
    }

    /**
     * @param catalogNumberType the catalogNumberType to set
     */
    public void setCatalogNumberType(String catalogNumberType) {
        this.catalogNumberType = catalogNumberType;
    }

}

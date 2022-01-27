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
public class CatalogNumberDTO {

    private CatalogNumberRequest request;
    private CatalogNumberResponse response;

    public CatalogNumberDTO() {
        request = new CatalogNumberRequest();
        response = new CatalogNumberResponse();
    }

    /**
     * @return the request
     */
    public CatalogNumberRequest getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(CatalogNumberRequest request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public CatalogNumberResponse getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(CatalogNumberResponse response) {
        this.response = response;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (request != null) {
            sb.append("clientAttribute=");
            sb.append(request.getClientAttribute());
            sb.append(",catalogCategoryTypeAttribute=");
            sb.append(request.getCatalogCategoryTypeAttribute());
            sb.append(",speciesAttribute=");
            sb.append(request.getSpeciesAttribute());
            sb.append(",isKit=");
            sb.append(request.isKit());
        }
        if (response != null) {
            sb.append("catalogNumber=");
            sb.append(response.getCatalogNumber());
            sb.append("catalogNumberType=");
            sb.append(response.getCatalogNumberType());
        }
        return sb.toString();
    }

    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.utils;


public interface CatalogNumberGenerator {
    
    /**
     * Generates the next catalog nunber based on passed attributes according to client need
     * @param clientAttribute - an attribute attached to a possible client for whom the kit/product is intended for
     * @param catalogCategoryTypeAttribute - an attribute attached to the category for which the kit/product is intended for
     * @param speciesAttribute - an attribute attached to the species for which the kit/product is intened for
     * @return - A client specific catalog number based on the provided data
     */
    public String generateNextCatalogNumber( String clientAttribute, 
                                             String catalogCategoryTypeAttribute,
                                             String speciesAttribute
                                            );
    
}

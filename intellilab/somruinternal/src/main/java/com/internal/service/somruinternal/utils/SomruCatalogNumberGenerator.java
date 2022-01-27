/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 


/**
 *
 * @author peter
 */
public class SomruCatalogNumberGenerator implements CatalogNumberGenerator {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(SomruCatalogNumberGenerator.class);
    
    
    
    /**
     * Generates the next catalog nunber based on passed attributes according to clients need
     * @param clientAttribute - an attribute attached to a possible client for whom the kit/product is intended for
     * @param catalogCategoryTypeAttribute - an attribute attached to the category for which the kit/product is intended for
     * @param speciesAttribute - an attribute attached to the species for which the kit/product is intended for
     * @return - A client specific catalog number based on the provided data
     */
    public String generateNextCatalogNumber( String clientAttribute, 
                                             String catalogCategoryTypeAttribute,
                                             String speciesAttribute
                                           ) {
        LOGGER.info( String.format(  
          "generateNextCatalogNumber called with clientAttribute = %s, catalogCategoryTypeAttribute = %s, speciesAttribute = %s",
          clientAttribute, catalogCategoryTypeAttribute, speciesAttribute ));
        StringBuffer catalogNumberBuffer = new StringBuffer();
        catalogNumberBuffer.append( "SB-0" );
        if ( clientAttribute != null && clientAttribute.length() > 0 )
            catalogNumberBuffer.append( clientAttribute );
        if ( catalogCategoryTypeAttribute != null && catalogCategoryTypeAttribute.length() > 0 )
            catalogNumberBuffer.append( catalogCategoryTypeAttribute );
        catalogNumberBuffer.append("-");
        if ( speciesAttribute != null && speciesAttribute.length() > 0 )
            catalogNumberBuffer.append(speciesAttribute);
        else 
            catalogNumberBuffer.append("0");
        return catalogNumberBuffer.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internal.service.somruinternal.utils;


import com.internal.service.somruinternal.model2.CatalogSequenceV2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.transaction.annotation.Transactional;
import com.internal.service.somruinternal.repository2.CatalogSequenceRepositoryV2;



/**
 *
 * @author peter
 */
public class CatalogNumberUtil {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CatalogNumberUtil.class);
    
    @Transactional(rollbackFor=Exception.class)
    public String generateNextCatalogNumber( CatalogSequenceRepositoryV2 catRepo,
                                             String clientAttribute, 
                                             String catalogCategoryTypeAttribute,
                                             String speciesAttribute
                                            ) {
        LOGGER.info("generateNextCatalogNumber");
        
        CatalogSequenceV2 aCatNumber = this.getCurrentCatalogNumber(catRepo, "ALL");
        if ( aCatNumber == null ) {
            LOGGER.info( "Catalog # not found - creating new");
            aCatNumber = new CatalogSequenceV2();
            aCatNumber.setCatalogNumber(1);
            aCatNumber.setCatalogNumberType("ALL");
        }
        CatalogNumberGenerator catNoGen = new SomruCatalogNumberGenerator();
        String nextCatNo = catNoGen.generateNextCatalogNumber( 
                clientAttribute, catalogCategoryTypeAttribute, speciesAttribute );
        nextCatNo += Integer.toString( aCatNumber.getCatalogNumber() );
        LOGGER.info(String.format("generateNextCatalogNumber - nextCatNo: ", nextCatNo) );
        aCatNumber.setCatalogNumber(  aCatNumber.getCatalogNumber() + 1 );
        catRepo.save(aCatNumber);
        LOGGER.info("save and incrmeent of catalog number complete");
        return nextCatNo;
    }
    
   
    private CatalogSequenceV2 getCurrentCatalogNumber( 
            CatalogSequenceRepositoryV2 catRepo,
            String catNumberType) {
        LOGGER.info( String.format(  
           "getCurrentCatalogNumber received request for CatalogNumberType %s", catNumberType) );
        CatalogSequenceV2 aCatNumber = null;
        aCatNumber = catRepo.findCatalogNumber(catNumberType);
        LOGGER.info(String.format( "Next Catalog Number is %s", aCatNumber));
        return aCatNumber;
    }
    
}

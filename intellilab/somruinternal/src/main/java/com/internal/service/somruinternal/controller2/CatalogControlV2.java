package com.internal.service.somruinternal.controller2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.internal.service.somruinternal.dto.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.utils.CatalogNumberUtil;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/catalogv2")
public class CatalogControlV2 extends ParentControl {
    
    @Autowired
    CatalogSequenceRepositoryV2 catalogSequenceRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(CatalogControlV2.class);
    
    
    @PostMapping("/request")
    public CatalogNumberDTO requestCatalogNumber(@Valid @RequestBody CatalogNumberDTO catalogNumberDTO) {
        LOGGER.info( String.format(
           "requestCatalogNumber received request with vslurd %s", catalogNumberDTO ));        
        CatalogNumberUtil catNoUtil = new CatalogNumberUtil();
        String nextCatNo = catNoUtil.generateNextCatalogNumber( 
                catalogSequenceRepo, 
                catalogNumberDTO.getRequest().getClientAttribute(),
                catalogNumberDTO.getRequest().getCatalogCategoryTypeAttribute(),
                catalogNumberDTO.getRequest().getSpeciesAttribute() );
                
        LOGGER.info( String.format( "Catalog Number = %s", nextCatNo ));
        CatalogNumberDTO responseCatNoDTO = new CatalogNumberDTO();
        responseCatNoDTO.setResponse(new CatalogNumberResponse() );
        responseCatNoDTO.getResponse().setCatalogNumber(nextCatNo);
        responseCatNoDTO.getResponse().setCatalogNumberType( catalogNumberDTO.getRequest().isKit() ? "Kit" : "Product" );
        return responseCatNoDTO;
    }
    
}

package com.internal.service.somruinternal.controller2;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.utils.SearchUtil;

@RestController
@RequestMapping("/salesitemv2")
public class SalesItemControlV2 extends ParentControl {

   
    private SalesItemRepositoryV2 salesItemRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductControlV2.class);
    
    @Autowired
    public SalesItemControlV2( SalesItemRepositoryV2 salesItemRepo ) {
        super();
        this.salesItemRepo = salesItemRepo;
    }
    
    @GetMapping("/search/{searchKey}")
    public List<SalesItemV2> search(
            @PathVariable(value = "searchKey") String searchKey) {
        LOGGER.info( String.format("search received request with searchKey %s", 
                searchKey));        
        String search = SearchUtil.replaceIllegalSearchCharacters(searchKey);
        List<SalesItemV2> salesItemList = salesItemRepo.search(search.toUpperCase());
        LOGGER.info( String.format("Retrieved %d products", salesItemList.size()));
        return salesItemList;
    }
    
    @GetMapping("/countByCatalogNumber/{catalogNumber}")
    public int countByCatalogNumber(
            @PathVariable(value = "catalogNumber") String catalogNumber) {
        LOGGER.info( String.format("countByCatalogNumber received request with catalogNumber %s", 
                catalogNumber));        
        String search = SearchUtil.replaceIllegalSearchCharacters(catalogNumber);
        List<SalesItemV2> salesItemList = salesItemRepo.searchByCatalogNumber(search);
        LOGGER.info( String.format("Retrieved %d Sales Items", salesItemList.size()));
        return salesItemList.size();
    }
    
}

package com.internal.service.somruinternal.controller2;

import java.util.List;
import java.util.ArrayList;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.hibernate.Hibernate;

import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.dto.*;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.utils.SearchUtil;


@RestController
@RequestMapping("/clientv2")
public class ClientControlV2 extends ParentControl {

    @Autowired
    ClientCompanyRepositoryV2 clientRepo;

    private final static Logger LOGGER = LoggerFactory.getLogger(ClientControlV2.class);
 
    @GetMapping("/all/{active}/{page}")
    public List<ClientSearchDisplayDTO> getAllClientsByPage(
            @PathVariable(value = "active") boolean active,
            @PathVariable(value = "page") int page
    ) {
    	 PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        LOGGER.info(String.format("Now entering getAllActiveClientsByPage(%d)...", page));
        List<ClientCompanyV2> dbClientList;
        if ( active )
            dbClientList = clientRepo.getAllActiveClients(pageable);
        else 
            dbClientList = clientRepo.getAllClients(pageable);
        
        List<ClientSearchDisplayDTO> dtoClientList = new ArrayList<>();
        dbClientList.forEach((clientCompany) -> {
            ClientSearchDisplayDTO dto = new ClientSearchDisplayDTO(clientCompany);
            dtoClientList.add(dto);
        });
        LOGGER.info(String.format("Retrieved %d client rows", dtoClientList.size()));
        return dtoClientList;
    }
    
    @GetMapping("/count/{active}")
    public Long getCount(@PathVariable(value = "active") boolean active) {
        LOGGER.info(String.format("client getCount received request with param %b", active));
        Long numRows;
        if (active)
            numRows = clientRepo.countActiveClients();
        else
            numRows = clientRepo.countAllClients();
        LOGGER.info(String.format("count = %d", numRows));
        return numRows;
    }
    
    @GetMapping("/search/{keyword}/{active}/{page}")
    public List<ClientSearchDisplayDTO> searchPageable(
            @PathVariable(value = "keyword") String keyword,
            @PathVariable(value = "active") boolean active,
            @PathVariable(value = "page") int page) {
        LOGGER.info(String.format( "searchPageable (Client) received request with params %s, %b, %d", 
                keyword, active, page ));

        String sqlKey = "";
        if( keyword != null ) sqlKey = SearchUtil.replaceIllegalSearchCharacters(keyword).toUpperCase();

        PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        LOGGER.info(String.format( "searching database for query '%s'", sqlKey));
        List<ClientCompanyV2> dbClientList;
        if ( active )
            dbClientList = clientRepo.searchActiveClients( sqlKey, pageable);
        else
            dbClientList = clientRepo.searchAllClients( sqlKey, pageable);
        
        List<ClientSearchDisplayDTO> dtoClientList = new ArrayList<>();
        dbClientList.forEach((clientCompany) -> {
            ClientSearchDisplayDTO dto = new ClientSearchDisplayDTO(clientCompany);
            dtoClientList.add(dto);
        });
        LOGGER.info(String.format("Retrieved %d client rows", dtoClientList.size()));
        return dtoClientList;
    }
    
    @GetMapping("/searchCount/{keyword}/{active}")
    public Long searchCount(
            @PathVariable(value = "keyword") String keyword,
            @PathVariable(value = "active") boolean active) {
        LOGGER.info(String.format( "client searchCount received request with params %s, %b", 
                keyword, active ));

        String sqlKey = "";
        if( keyword != null ) sqlKey = SearchUtil.replaceIllegalSearchCharacters(keyword).toUpperCase();
 
        LOGGER.info(String.format( "searching database for query: %s",  sqlKey ));
        Long resultCount;
        if ( active )
            resultCount = clientRepo.countActiveClients( sqlKey );
        else
            resultCount = clientRepo.countAllClients( sqlKey );
        
        LOGGER.info(String.format("Retrieved %d client rows", resultCount));
        return resultCount;
    }
    
    @GetMapping("/search/{keyword}/{active}")
    public List<ClientSearchDisplayDTO> search(
            @PathVariable(value = "keyword") String keyword,
            @PathVariable(value = "active") boolean active) {
        LOGGER.info("search (Client) received request");
        String sqlKey = "";
        if( keyword != null ) sqlKey = SearchUtil.replaceIllegalSearchCharacters(keyword).toUpperCase();

        LOGGER.info("searching database for query '" + sqlKey + "'");
        List<ClientCompanyV2> dbClientList; 
        if ( active ) 
            dbClientList = clientRepo.searchActiveClients(sqlKey );
        else
            dbClientList = clientRepo.searchAllClients(sqlKey );
        
        List<ClientSearchDisplayDTO> dtoClientList = new ArrayList<>();
        dbClientList.forEach((clientCompany) -> {
            ClientSearchDisplayDTO dto = new ClientSearchDisplayDTO(clientCompany);
            dtoClientList.add(dto);
        });
        LOGGER.info(String.format("Retrieved %d client rows", dtoClientList.size()));
        return dtoClientList;
    }
    
    @GetMapping("/searchExactNameMatch/{keyword}")
    public List<ClientCompanyV2> searchExactNameMatch(
            @PathVariable(value = "keyword") String keyword) {
        LOGGER.info(String.format("searchExactNameMatch received request with keyword %s", keyword));
        String sqlKey = "";
        if( keyword != null ) sqlKey = SearchUtil.replaceIllegalSearchCharacters(keyword);
        LOGGER.info(String.format("searching database for query %s", sqlKey ));
        List<ClientCompanyV2> dbClientList = clientRepo.searchExactNameMatch(sqlKey );
        LOGGER.info(String.format("Retrieved %d client rows", dbClientList.size()));
        return dbClientList;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ClientCompanyV2> get(@PathVariable(value = "id") Long clientCompanyId)
        throws EntityNotFoundException {
        LOGGER.info( String.format("get received request with clientCompanyId %d", clientCompanyId));
        if ( clientCompanyId == null ) throw new EntityNotFoundException(ClientCompanyV2.class, "id", "");
        ClientCompanyV2 client = this.loadCompany(clientCompanyId);
        LOGGER.info( String.format( "Loaded client details: %s", client ));
        return ResponseEntity.ok().body(client);
    }
    
    private ClientCompanyV2 loadCompany( Long clientCompanyId ) {
        ClientCompanyV2 client = clientRepo.findByClientCompanyId(clientCompanyId);
        if (client == null) {
            LOGGER.info( "client not found");
            throw new EntityNotFoundException(ClientCompanyV2.class, "id", clientCompanyId.toString());
        }
        Hibernate.initialize(client.getBillingAddress());
        Hibernate.initialize(client.getShippingAddresses());
        Hibernate.initialize(client.getClientContacts());
        return client;
    }

    /**
     *  Save the passed Client and associated objects to the database.
     *  @param client the client to insert or update
     *  @return the client that was saved
     */
    @PostMapping("/save")
    @Transactional(rollbackFor=Exception.class)
    public ClientCompanyV2 saveClient(@Valid @RequestBody ClientCompanyV2 client) {
        LOGGER.info( String.format(
           "saveClient received request - details %s:", client ));
        
        String action;
        String auditTrailRecord;
        if ( client.getClientCompanyId() > 0 ) {
            ClientCompanyV2 previousClientDetails = this.loadCompany(client.getClientCompanyId());
            action = "update client";
            auditTrailRecord = getDiffs(client, previousClientDetails);
        }
        else {
            action = "insert client";
            auditTrailRecord = client.toString();
        }
        
        ClientCompanyV2 savedClient = clientRepo.save(client);
        super.saveUserHistory(client.getEditedBy(), "Client", action, auditTrailRecord);  
        LOGGER.info("Completed saving client");
        return savedClient;
    }
    
    private String getDiffs(ClientCompanyV2 lhs, ClientCompanyV2 rhs ) {
        if ( (lhs == null) || (rhs == null) )
            throw new RuntimeException("Error: Trying to compare two ClientCompany objects and at least one is null");
        String diffs = lhs.diffCompare(rhs);
        LOGGER.info(String.format( "ClientCompanyV2 GET DIFFS RESULT: %s", diffs));
        return diffs;
        
    }

}

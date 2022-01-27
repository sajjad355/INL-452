package com.internal.service.somruinternal.repository2;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.*;

@Repository
public interface ClientCompanyRepositoryV2 extends JpaRepository<ClientCompanyV2, Long>{
    
    
    
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
            "where c.active = TRUE and cc.active = true order by c.modifiedOn desc")
    public List<ClientCompanyV2> getAllActiveClients(Pageable pageable);
    
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
            " where cc.active = true order by c.modifiedOn desc")
    public List<ClientCompanyV2> getAllClients(Pageable pageable);
    
    @Query(value = "SELECT count(c) from ClientCompanyV2 c where c.active = TRUE")
    public Long countActiveClients();
    
    @Query(value = "SELECT count(c) from ClientCompanyV2 c")
    public Long countAllClients();
    
    @Query(value = "SELECT c from ClientCompanyV2 c where c.companyName = :searchKey")
    public List<ClientCompanyV2> searchExactNameMatch(@Param("searchKey") String searchKey);
        
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
                   " where c.active = true and cc.active = true and " + 
                   " ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" +
                   " order by c.modifiedOn desc" )
    public List<ClientCompanyV2> searchActiveClients(
            @Param("searchKey") String searchKey,
            Pageable pageable);
    
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
                   " where c.active = true and cc.active = true and " + 
                   " ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" +
                   " order by c.modifiedOn desc" )
    public List<ClientCompanyV2> searchActiveClients( @Param("searchKey") String searchKey );
    
    @Query(value = "SELECT count( c ) from ClientCompanyV2 c LEFT JOIN c.clientContacts cc " +
                   " where c.active = true and cc.active = true and " + 
                   " ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" )
    public Long countActiveClients( @Param("searchKey") String searchKey );
    
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
                   " where cc.active = true and " +
                   " ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" +
                   " order by c.modifiedOn desc" )
    public List<ClientCompanyV2> searchAllClients(
            @Param("searchKey") String searchKey,
            Pageable pageable);
    
    @Query(value = "SELECT distinct c from ClientCompanyV2 c LEFT JOIN fetch c.clientContacts cc " +
                   " where cc.active = true and " + 
                   " ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" +
                   " order by c.modifiedOn desc" )
    public List<ClientCompanyV2> searchAllClients( @Param("searchKey") String searchKey );
    
    @Query(value = "SELECT count( c ) from ClientCompanyV2 c LEFT JOIN c.clientContacts cc " +
                   " where cc.active = true and ( UPPER(c.companyName) LIKE  %:searchKey% or UPPER(cc.name) LIKE %:searchKey%)" )
    public Long countAllClients( @Param("searchKey") String searchKey );

	public ClientCompanyV2 findByClientCompanyId(Long clientCompanyId);
    
}

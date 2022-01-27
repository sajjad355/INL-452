package com.internal.service.somruinternal.repository2;

import java.util.List;
import java.util.Date;
import com.internal.service.somruinternal.model2.QuoteV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepositoryV2 extends JpaRepository<QuoteV2, Long> {
    
    @Query(value = "Select DISTINCT quote from QuoteV2 quote where "
            + " active = TRUE " + 
            " Order By quote.modifiedOn DESC")
    public List<QuoteV2> findAllActiveByPage(Pageable pageable);
    
    @Query(value = "Select DISTINCT quote from QuoteV2 quote " +
            " Order By quote.modifiedOn DESC")
    public List<QuoteV2> findAllByPage(Pageable pageable);
    
    @Query(value = "Select count(quote) from QuoteV2 quote where active = TRUE")
    public Long findAllActiveCount();
    
    @Query(value = "Select count(quote) from QuoteV2 quote")
    public Long findAllCount();
    
    @Query(value = "Select DISTINCT quote from QuoteV2 quote where " +
            "  quote.quoteNumber LIKE %:searchKey% " + 
            " AND quote.active = true " +
            " Order By quote.modifiedOn DESC")
    public List<QuoteV2> searchActiveQuotes(@Param(value = "searchKey") String searckKey, Pageable pageable);
    

    @Query(value = "Select DISTINCT quote from QuoteV2 quote where " +
            "  quote.quoteNumber LIKE %:searchKey% " +
            " Order By quote.modifiedOn DESC")
    public List<QuoteV2> searchAllQuotes(@Param(value = "searchKey") String searckKey, Pageable pageable);
    
    
    @Query(value = "Select COUNT( quote ) from QuoteV2 quote where "
            + " quote.quoteNumber LIKE %:searchKey% "
            + " AND quote.active = true")
    public Long searchCountActive(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( quote ) from QuoteV2 quote where "
            + " quote.quoteNumber LIKE %:searchKey% ")
    public Long searchCount(@Param(value = "searchKey") String searckKey);
    
    
    @Query(value = "Select DISTINCT quote from QuoteV2 quote where " +
            "  (:expiredSinceDate <= quote.expirationDate AND quote.expirationDate <= CURRENT_TIMESTAMP) OR " +
            "  (:expireAfterDate >= quote.expirationDate AND quote.expirationDate >= CURRENT_TIMESTAMP)"  + 
            " Order By quote.expirationDate ASC" )
    public List<QuoteV2> searchExpiredAndExpiringQuotes(
            @Param(value = "expiredSinceDate") Date expiredSinceDate,
            @Param(value = "expireAfterDate") Date expireAfterDate);

	public QuoteV2 findByQuoteId(Long quoteId);

}

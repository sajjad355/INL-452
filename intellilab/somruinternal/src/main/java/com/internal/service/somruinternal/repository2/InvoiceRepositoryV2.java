package com.internal.service.somruinternal.repository2;

import java.util.List;
import com.internal.service.somruinternal.model2.InvoiceV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepositoryV2 extends JpaRepository<InvoiceV2, Long> {
    
    @Query(value = "Select invoice from InvoiceV2 invoice where "
            + " active = TRUE ORDER By invoice.modifiedOn DESC")
    public List<InvoiceV2> findAllActiveByPage(Pageable pageable);
    
    @Query(value = "Select count(invoice) from InvoiceV2 invoice where active = TRUE")
    public Long findAllActiveCount();
    
    @Query(value = "Select invoice from InvoiceV2 invoice  "
            + "  ORDER By invoice.modifiedOn DESC")
    public List<InvoiceV2> findAllByPage(Pageable pageable);
    
    @Query(value = "Select count(invoice) from InvoiceV2 invoice")
    public Long findAllCount();
    
    @Query(value = "Select max(invoice.invoiceNumber) from InvoiceV2 invoice")
    public Long getCurrentInvoiceNumber();
    
    @Query(value = "Select DISTINCT invoice from InvoiceV2 invoice where "
            + "  invoice.invoiceNumber LIKE %:searchKey% " 
            + " AND invoice.active = true ORDER By invoice.invoiceId DESC")
    public List<InvoiceV2> searchActiveInvoices(@Param(value = "searchKey") String searckKey, Pageable pageable);

    @Query(value = "Select DISTINCT invoice from InvoiceV2 invoice where "
            + "  invoice.invoiceNumber LIKE %:searchKey% " 
            + "  ORDER By invoice.invoiceId DESC")
    public List<InvoiceV2> searchAllInvoices(@Param(value = "searchKey") String searckKey, Pageable pageable);
    
    @Query(value = "Select COUNT( invoice ) from InvoiceV2 invoice where "
            + " invoice.invoiceNumber LIKE %:searchKey% "
            + " AND invoice.active = true")
    public Long searchCountActive(@Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT( invoice ) from InvoiceV2 invoice where "
            + " invoice.invoiceNumber LIKE %:searchKey% ")
    public Long searchCount(@Param(value = "searchKey") String searckKey);

	public InvoiceV2 findByInvoiceId(Long invoiceId);
    
    
}

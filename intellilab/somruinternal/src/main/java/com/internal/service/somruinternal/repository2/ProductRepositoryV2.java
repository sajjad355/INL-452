package com.internal.service.somruinternal.repository2;


import java.util.List;
import com.internal.service.somruinternal.model2.ProductV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepositoryV2 extends JpaRepository<ProductV2, Long> {
    
    
    
    @Query(value = "Select product from ProductV2 product Where " + 
            " active = TRUE ORDER By product.modifiedOn DESC")
    public List<ProductV2> findAllActivePageable(Pageable pageable);
    
    @Query(value = "Select product from ProductV2 product " + 
            " ORDER By product.modifiedOn DESC")
    public List<ProductV2> findAllPageable(Pageable pageable);
    
    
    @Query(value = "Select count(product) from ProductV2 product Where " + 
            " active = TRUE")
    public Long countAllActive();
    
    @Query(value = "Select count(product) from ProductV2 product")
    public Long countAll();
    

    @Query(value = "Select product from ProductV2 product Where " + 
            "(UPPER(product.name) LIKE %:searchKey% OR UPPER(product.catalogNumber) LIKE %:searchKey%)" +
            " and active = TRUE ORDER By product.modifiedOn DESC")
    public List<ProductV2> searchActive(
            @Param(value = "searchKey") String searckKey,
            Pageable pageable);
    
    @Query(value = "Select product from ProductV2 product Where " + 
            "(UPPER(product.name) LIKE %:searchKey% OR UPPER(product.catalogNumber) LIKE %:searchKey%)" +
            " and active = TRUE ORDER By product.modifiedOn DESC")
    public List<ProductV2> searchActive(
            @Param(value = "searchKey") String searckKey);

    @Query(value = "Select COUNT(product) from ProductV2 product Where " +
             "( UPPER(product.name) LIKE %:searchKey% OR UPPER(product.catalogNumber) LIKE %:searchKey%)" +
             " and active = TRUE")
    public Long searchCountActive(
            @Param("searchKey") String searchKey );
    
    @Query(value = "Select product from ProductV2 product Where " + 
            "UPPER(product.name) LIKE %:searchKey% OR UPPER(product.catalogNumber) LIKE %:searchKey% " +
            " ORDER By product.modifiedOn DESC")
    public List<ProductV2> search(
            @Param(value = "searchKey") String searckKey,
            Pageable pageable);

    @Query(value = "Select COUNT(product) from ProductV2 product Where " +
             "UPPER(product.name) LIKE %:searchKey% OR UPPER(product.catalogNumber) LIKE %:searchKey%")
    public Long searchCount(
            @Param("searchKey") String searchKey );
    
    @Query(value = "SELECT distinct p from ProductV2 p LEFT JOIN fetch p.applications a where p.salesItemId = :productId ")
    public ProductV2 getSingleProduct(@Param(value = "productId") Long productId);

	public ProductV2 findBySalesItemId(Long salesitemId);

}

package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.OrderItemV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderItemRepositoryV2 extends JpaRepository<OrderItemV2, Long> {

    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = :status")
    public List<OrderItemV2> loadOrderByStatus(@Param(value = "status") String status);

    @Query(value = "SELECT count(orderitem) FROM OrderItemV2 orderitem WHERE orderitem.status = :status")
    public Long countOrder(@Param(value = "status") String status);

    @Query(value = "SELECT count(orderitem) FROM OrderItemV2 orderitem JOIN orderitem.requestUser user "
            + " WHERE orderitem.status = :status and user.name = :userName")
    public Long countOrder(
            @Param(value = "status") String status,
            @Param(value = "userName") String userName);
    
    @Query(value = "SELECT COUNT(DISTINCT orderitem) FROM OrderItemV2 orderitem WHERE orderitem.status = :status AND orderitem.urgent= TRUE")
    public Long countUrgentOrders(@Param(value = "status") String status);
    
    @Query(value = "SELECT count(orderitem) FROM OrderItemV2 orderitem JOIN orderitem.requestUser user "
            + " WHERE orderitem.status = :status and user.name = :userName AND orderitem.urgent= TRUE")
    public Long countUrgentOrders(
            @Param(value = "status") String status,
            @Param(value = "userName") String userName);

    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = :status")
    public List<OrderItemV2> loadOrder(
            @Param(value = "status") String status, 
            Pageable pageable);

    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem JOIN orderitem.requestUser user"
            + " WHERE orderitem.status = :status "
            + " and user.name = :userName")
    public List<OrderItemV2> loadOrder(
            @Param(value = "status") String status,
            @Param(value = "userName") String userName,
            Pageable pageable);
    
    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem JOIN orderitem.requestUser user"
            + " WHERE orderitem.status = :status "
            + " and user.name = :userName "  
            + " AND orderitem.urgent= TRUE")
    public List<OrderItemV2> loadUrgentOrder(
            @Param(value = "status") String status,
            @Param(value = "userName") String userName,
            Pageable pageable);

    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = :status AND orderitem.urgent= TRUE")
    public List<OrderItemV2> loadUrgentOrder(
            @Param(value = "status") String status, 
            Pageable pageable);

    
    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = 'Requested' AND orderitem.requestTime > :createdSince ORDER BY orderitem.requestTime DESC")
    public List<OrderItemV2> loadRequestedOrderCreatedSince(@Param(value = "createdSince") Date createdSince);

    
    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = 'Ordered' AND orderitem.eta is null ORDER BY orderitem.orderTime DESC")
    public List<OrderItemV2> loadOrderedItemsNoETA();

    @Query(value = "SELECT orderitem FROM OrderItemV2 orderitem WHERE orderitem.status = 'Ordered' AND orderitem.eta < CURDATE() ORDER BY orderitem.orderTime DESC")
    public List<OrderItemV2> loadOrderedItemsPastETA();

    @Query(value = "SELECT DISTINCT orderitem FROM OrderItemV2 orderitem LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status AND "
            + "(orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%) "
            + "ORDER BY orderitem.requestTime DESC")
    public List<OrderItemV2> searchOrder(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey, 
            Pageable pageable);
    
    @Query(value = "SELECT DISTINCT orderitem FROM OrderItemV2 orderitem  " 
            + " INNER JOIN orderitem.requestUser user "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status "
            + "AND user.name = :userName "
            + "AND ((orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%)) "
            + "ORDER BY orderitem.requestTime DESC")
    public List<OrderItemV2> searchOrdersByRequestedUser(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey,
            @Param(value = "userName") String userName,
            Pageable pageable);
    
    @Query(value = "SELECT DISTINCT orderitem FROM OrderItemV2 orderitem "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status AND "
            + "orderitem.urgent= TRUE AND "
            + "(orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%) "
            + "ORDER BY orderitem.requestTime DESC")
    public List<OrderItemV2> searchUrgentOrder(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey, 
            Pageable pageable);
    
    @Query(value = "SELECT DISTINCT orderitem FROM OrderItemV2 orderitem  " 
            + " INNER JOIN orderitem.requestUser user "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status "
            + "AND user.name = :userName "
            + "AND orderitem.urgent= TRUE "
            + "AND ((orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%)) "
            + "ORDER BY orderitem.requestTime DESC")
    public List<OrderItemV2> searchUrgentOrderByRequestedUser(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey,
            @Param(value = "userName") String userName,
            Pageable pageable);

    
    @Query(value = "SELECT COUNT(DISTINCT orderitem) FROM OrderItemV2 orderitem LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status AND "
            + "(orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%)")
    public Long searchOrderCount(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey);

    @Query(value = "SELECT COUNT(DISTINCT orderitem) FROM OrderItemV2 orderitem  " 
            + " INNER JOIN orderitem.requestUser user "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status "
            + "AND user.name = :userName "
            + "AND ((orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%))")
    public Long searchOrderCountByRequestedUser(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey,
            @Param(value = "userName") String userName);

    @Query(value = "SELECT COUNT(DISTINCT orderitem) FROM OrderItemV2 orderitem "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status AND "
            + "orderitem.urgent= TRUE AND "
            + "(orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%) "
            + "ORDER BY orderitem.requestTime DESC")
    public Long searchUrgentOrderCount(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey);

    @Query(value = "SELECT COUNT(DISTINCT orderitem) FROM OrderItemV2 orderitem  " 
            + " INNER JOIN orderitem.requestUser user "
            + " LEFT JOIN orderitem.reserveForClient client "
            + "WHERE orderitem.status = :status "
            + "AND user.name = :userName "
            + "AND orderitem.urgent= TRUE "
            + "AND ((orderitem.name LIKE %:searchKey% OR orderitem.catalogNumber LIKE %:searchKey% OR orderitem.supplierCatalogNumber LIKE %:searchKey% OR orderitem.supplier LIKE %:searchKey% OR orderitem.manufacturer LIKE %:searchKey% "
            + "OR client.companyName LIKE %:searchKey%))")
    
    public Long searchUrgentOrderCountByRequestedUser(
            @Param(value = "status") String status, 
            @Param(value = "searchKey") String searchKey,
            @Param(value = "userName") String userName);

	public OrderItemV2 findByOrderItemId(Long orderItemId);

    
}

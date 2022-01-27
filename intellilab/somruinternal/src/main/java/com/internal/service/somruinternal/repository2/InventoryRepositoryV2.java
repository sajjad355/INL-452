package com.internal.service.somruinternal.repository2;

import com.internal.service.somruinternal.model2.InventoryV2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepositoryV2 extends JpaRepository<InventoryV2, Long>, JpaSpecificationExecutor<InventoryV2> {

    // the following are basically some spring magic: see
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
    Long countDistinctByActiveTrue();

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE item.active = true "
            + " AND item.type = :type ")
    Long countDistinctByActiveTrueAndType(String type);

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE item.type = :type ")
    Long countDistinctByType(String type);

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE item.active = true "
            + " AND item.catalogNumber = :catalogNumber ")
    Long countDistinctByActiveTrueAndCatalogNumber(String catalogNumber);

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE item.catalogNumber = :catalogNumber ")
    Long countDistinctByCatalogNumber(String catalogNumber);

    Long countDistinctByInventoryIdIsNotAndCatalogNumberIs(long inventoryId, String catalogNumber);

    Long countDistinctByInventoryIdIsNotAndCatalogNumberAndSupplierCatalogNumberAndUnitAndContainerSize(long inventoryId, String catalogNumber, String supplierCatalogNumber, String unit, Double containerSize);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> findAllActiveOnly();

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> findAllActiveOnly(Pageable pageable);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true "
            + " AND (catalogNumber like :key or "
            + " supplierCatalogNumber like :key or "
            + " name like :key or "
            + " supplier like :key or "
            + " manufacturer like :key) "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByActiveOnlyAndContainsKey(@Param(value = "key") String key);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true and "
            + " catalogNumber = :catalogNumber and "
            + " name = :name "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByNameAndCatalogNumberExactMatch(
            @Param(value = "name") String name,
            @Param(value = "catalogNumber") String catalogNumber);

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE item.active = true and "
            + " (catalogNumber like :key or "
            + " supplierCatalogNumber like :key or "
            + " name like :key or "
            + " supplier like :key or "
            + " manufacturer like :key)")
    Long countByActiveOnlyAndContainsKey(@Param(value = "key") String key);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true and "
            + " (catalogNumber like :key or "
            + " supplierCatalogNumber like :key or "
            + " name like :key or "
            + " supplier like :key or "
            + " manufacturer like :key) "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByActiveOnlyAndContainsKeyPageable(@Param(value = "key") String key, Pageable pageable);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item WHERE "
            + " (catalogNumber like :key or "
            + " supplierCatalogNumber like :key or "
            + " name like :key or "
            + " supplier like :key or "
            + " manufacturer like :key) "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchAllAndContainsKeyPageable(@Param(value = "key") String key, Pageable pageable);

    @Query(value = "SELECT count(item) FROM InventoryV2 item "
            + " WHERE  "
            + " (catalogNumber like :key or "
            + " supplierCatalogNumber like :key or "
            + " name like :key or "
            + " supplier like :key or "
            + " manufacturer like :key)")
    Long countAllAndContainsKey(@Param(value = "key") String key);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.active = true and item.type = :type"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByTypeActiveOnlyPageable(@Param(value = "type") String type, Pageable pageable);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item "
            + " WHERE item.type = :type"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchAllByTypePageable(@Param(value = "type") String type, Pageable pageable);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " WHERE location.locationId = :locationId"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByLocationIdPageable(@Param(value = "locationId") Long locationId,
            Pageable pageable);

    @Query(value = "SELECT count(item) FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " WHERE location.locationId = :locationId"
            + " ORDER By item.modifiedOn DESC")
    Long countByLocationId(@Param(value = "locationId") Long locationId);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + "  JOIN detail.location location "
            + " WHERE item.active = true and location.locationId = :locationId"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByLocationIdActiveOnlyPageable(@Param(value = "locationId") Long locationId,
            Pageable pageable);

    @Query(value = "SELECT count(item) FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " WHERE location.locationId = :locationId AND item.active = true"
            + " ORDER By item.modifiedOn DESC")
    Long countByLocationIdActiveOnly(@Param(value = "locationId") Long locationId);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " JOIN detail.subLocation subLocation "
            + " WHERE location.locationId = :locationId and subLocation.locationId = :subLocationId "
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByLocationIdAndSublocationIdPageable(
            @Param(value = "locationId") Long locationId,
            @Param(value = "subLocationId") Long subLocationId,
            Pageable pageable);

    @Query(value = "SELECT count(item) FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " JOIN detail.subLocation subLocation "
            + " WHERE location.locationId = :locationId and subLocation.locationId = :subLocationId "
            + " ORDER By item.modifiedOn DESC")
    Long countByLocationIdAndSublocationId(
            @Param(value = "locationId") Long locationId,
            @Param(value = "subLocationId") Long subLocationId);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " JOIN detail.subLocation subLocation "
            + " WHERE location.locationId = :locationId and "
            + " subLocation.locationId = :subLocationId and "
            + " item.active = true"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> searchByLocationIdAndSublocationIdActiveOnly(
            @Param(value = "locationId") Long locationId,
            @Param(value = "subLocationId") Long subLocationId,
            Pageable pageable);

    @Query(value = "SELECT count( item ) FROM InventoryV2 item JOIN item.inventoryDetails detail "
            + " JOIN detail.location location "
            + " JOIN detail.subLocation subLocation "
            + " WHERE location.locationId = :locationId and "
            + " subLocation.locationId = :subLocationId and"
            + " item.active = true"
            + " ORDER By item.modifiedOn DESC")
    Long countByLocationIdAndSublocationIdActiveOnly(
            @Param(value = "locationId") Long locationId,
            @Param(value = "subLocationId") Long subLocationId);

    @Query(value = "SELECT DISTINCT item FROM InventoryV2 item"
            + " WHERE item.amount <= item.quantityThreshold and "
            + " item.active = true"
            + " ORDER By item.modifiedOn DESC")
    List<InventoryV2> loadLowInStock();

    InventoryV2 findByInventoryId(Long id);

}

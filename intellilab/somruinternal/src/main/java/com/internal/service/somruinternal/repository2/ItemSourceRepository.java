package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.ClientCompanyV2;
import com.internal.service.somruinternal.model2.ItemSource;

@Repository
public interface ItemSourceRepository extends JpaRepository<ItemSource, Long> {

	@Query("SELECT itemsource FROM ItemSource itemsource " + "WHERE itemsource.name LIKE %:key% "
			+ "OR itemsource.type LIKE %:key% " + "ORDER BY itemsource.dbid DESC")
	List<ItemSource> searchItemSourecByNameOrType(String key);

	List<ItemSource> findByTypeAndIsActive(String type, boolean status);

	List<ItemSource> findByType(String type);

	List<ItemSource> findByIsActive(boolean status);
	
	@Query("SELECT itemsource FROM ItemSource itemsource " + "WHERE itemsource.name LIKE %:name% "
			+ "AND itemsource.type LIKE %:type% " + "ORDER BY itemsource.dbid DESC")
	List<ItemSource> findByTypeAndName(String name, String type);

	public ItemSource findByDbid(Long dbId);

}

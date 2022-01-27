package com.internal.service.somruinternal.repository;
import java.util.List;


import com.internal.service.somruinternal.model.ItemArchive;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemArchiveRepository extends JpaRepository<ItemArchive, Long>{

	ItemArchive findByItemarchivedbid(Long itemarchiveId);}

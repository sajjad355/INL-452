package com.internal.service.somruinternal.repository;
import com.internal.service.somruinternal.model.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrugRepository extends JpaRepository<Inventory, Long>{

}

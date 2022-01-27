package com.internal.service.somruinternal.repository;
import com.internal.service.somruinternal.model.Itemtype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemtypeRepository extends JpaRepository<Itemtype, Long>{

}

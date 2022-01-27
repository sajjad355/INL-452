package com.internal.service.somruinternal.repository;
import com.internal.service.somruinternal.model.Orderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderitemRepository extends JpaRepository<Orderitem, Long>{

}

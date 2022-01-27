package com.internal.service.somruinternal.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model.Egg;


@Repository
public interface EggRepository  extends JpaRepository<Egg, Long>{

    @Query(value="Select egg From Egg egg Where egg.chickenid = :id AND egg.amount!='0' ORDER BY egg.dbid")
    public List<Egg> loadEggsWChickenId(@Param("id") String id);
    
    @Query(value="Select egg From Egg egg Where egg.chickenid = :id")
    public List<Egg> getAllEggsByChickenId(@Param("id") String id);

	public Egg findByDbid(Long eggDbid);
    
}

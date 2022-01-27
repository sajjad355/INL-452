package com.internal.service.somruinternal.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model.Chicken;
import com.internal.service.somruinternal.model.Egg;

@Repository
public interface ChickenRepository  extends JpaRepository<Chicken, Long>{
	@Query(value="Select chicken From Chicken chicken Where chicken.chickenid LIKE %:searchKey% OR chicken.immunogen LIKE %:searchKey%")
	public List<Chicken> searchChickenByChickenId(@Param(value="searchKey") String searchKey, Pageable pageable);
	
	@Query(value="Select COUNT(DISTINCT chicken) From Chicken chicken Where chicken.chickenid LIKE %:searchKey% OR chicken.immunogen LIKE %:searchKey%")
	public Long searchChickenCount(@Param("searchKey") String searchKey);
	
        @Query(value="Select chicken From Chicken chicken Where chicken.chickenid LIKE %:searchKey% OR chicken.immunogen LIKE %:searchKey%")
	public List<Chicken> searchChickenByChickenId(@Param(value="searchKey") String searchKey );

		public Chicken findByDbid(Long dbid);
	
}

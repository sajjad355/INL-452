package com.internal.service.somruinternal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model.Injection;

@Repository
public interface InjectionRepository  extends JpaRepository<Injection, Long>{

    @Query(value="Select injection From Injection injection Where injection.chickenid = :chickenId")
    public List<Injection> searchInjectionByChickenId(@Param(value="chickenId") String chickenId );

	public Injection findByDbid(Long injectiondbid);
}

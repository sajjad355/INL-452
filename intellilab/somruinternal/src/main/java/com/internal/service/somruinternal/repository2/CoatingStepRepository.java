package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.CoatingStep;

@Repository
public interface CoatingStepRepository extends JpaRepository<CoatingStep, Long> {

	CoatingStep findByDbid(Long dbid);

}

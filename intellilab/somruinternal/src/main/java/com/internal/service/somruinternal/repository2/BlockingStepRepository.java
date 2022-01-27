package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.BlockingStep;

@Repository
public interface BlockingStepRepository extends JpaRepository<BlockingStep, Long> {

	BlockingStep findByDbid(Long dbid);

}

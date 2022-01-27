package com.internal.service.somruinternal.repository2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.DetectionStep;

@Repository
public interface DetectionStepRepository extends JpaRepository<DetectionStep, Long> {

	DetectionStep findByDbid(Long dbid);

}

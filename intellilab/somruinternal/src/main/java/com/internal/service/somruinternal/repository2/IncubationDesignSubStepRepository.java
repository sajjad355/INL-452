package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.BlockingStep;
import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.IncubationDesignSubStep;
import com.internal.service.somruinternal.model2.SubstrateStep;
import com.internal.service.somruinternal.model2.TestStep;

@Repository
public interface IncubationDesignSubStepRepository extends JpaRepository<IncubationDesignSubStep, Long> {

	List<IncubationDesignSubStep> findByBlockingStep(BlockingStep wsStep);

	List<IncubationDesignSubStep> findByCaptureStep(CaptureStep wsStep);

	List<IncubationDesignSubStep> findByCoatingStep(CoatingStep wsStep);

	List<IncubationDesignSubStep> findByDetectionStep(DetectionStep wsStep);

	List<IncubationDesignSubStep> findBySubstrateStep(SubstrateStep wsStep);

	List<IncubationDesignSubStep> findByTestStep(TestStep wsStep);

}

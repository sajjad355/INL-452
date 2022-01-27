package com.internal.service.somruinternal.repository2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.internal.service.somruinternal.model2.BlockingStep;
import com.internal.service.somruinternal.model2.CaptureStep;
import com.internal.service.somruinternal.model2.CoatingStep;
import com.internal.service.somruinternal.model2.DetectionStep;
import com.internal.service.somruinternal.model2.IncubationInstanceSubStep;
import com.internal.service.somruinternal.model2.SubstrateStep;
import com.internal.service.somruinternal.model2.TestStep;

@Repository
public interface IncubationInstanceSubStepRepository extends JpaRepository<IncubationInstanceSubStep, Long> {

	List<IncubationInstanceSubStep> findByBlockingStep(BlockingStep wsStep);

	List<IncubationInstanceSubStep> findByCaptureStep(CaptureStep wsStep);

	List<IncubationInstanceSubStep> findByCoatingStep(CoatingStep wsStep);

	List<IncubationInstanceSubStep> findByDetectionStep(DetectionStep wsStep);

	List<IncubationInstanceSubStep> findBySubstrateStep(SubstrateStep wsStep);

	List<IncubationInstanceSubStep> findByTestStep(TestStep wsStep);

}

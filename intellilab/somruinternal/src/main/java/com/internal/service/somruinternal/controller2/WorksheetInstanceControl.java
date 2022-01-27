package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.dto.*;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.utils.*;

@RestController
@RequestMapping("/worksheetinstance")
public class WorksheetInstanceControl extends ParentControl {

    @Autowired
    WorksheetDesignRepository worksheetDesignRepo;

    @Autowired
    WorksheetInstanceRepository worksheetInstanceRepo;

    @Autowired
    UserRepositoryV2 userRepo;

    @Autowired
    UserHistoryRepositoryV2 userHistoryRepo;

    @Autowired
    StopStepInstanceRepository stopStepInstanceRepo;

    @Autowired
    WashStepInstanceRepository washStepInstanceRepo;

    @Autowired
    CoatingStepInstanceRepository coatingStepInstanceRepo;

    @Autowired
    BlockingStepInstanceRepository blockingStepInstanceRepo;

    @Autowired
    CaptureStepInstanceRepository captureStepInstanceRepo;

    @Autowired
    TestStepInstanceRepository testStepInstanceRepo;

    @Autowired
    DetectionStepInstanceRepository detectionStepInstanceRepo;

    @Autowired
    SubstrateStepInstanceRepository substrateStepInstanceRepo;

    @Autowired
    MaterialInstanceSubStepRepository materialInstanceSubStepRepo;

    @Autowired
    DilutionInstanceSubStepRepository dilutionInstanceSubStepRepo;

    @Autowired
    ResultRepository resultRepo;

    @Autowired
    IncubationInstanceSubStepRepository incubationInstanceSubStepRepo;

    @Autowired
    EquipmentRepositoryV2 equipmentRepository;

    @Autowired
    WorksheetStepRepository wsStepRepository;

    @Autowired
    PlateMapRepository plateMapRepository;

    final double TOLERANCE = 0.10;

    private final static Logger LOGGER = LoggerFactory.getLogger(WorksheetInstanceControl.class);
    

    @Transactional
    @PostMapping("/addworksheetinstance")
    public Map<String, String> addWorksheetInstance(@Valid @RequestBody WorksheetInstancePostInput wsInstanceInput) {

        WorksheetInstance wsInstance;
        boolean isUpdating = false;
        Long inputInstanceId = wsInstanceInput.getWorksheetId();
        if (inputInstanceId == null || inputInstanceId < 1) {
            wsInstance = new WorksheetInstance();
            Integer maxExVersion = worksheetInstanceRepo.findMaxExVersionByDesign(wsInstanceInput.getTemplateId());
            maxExVersion = (maxExVersion == null ? 0 : maxExVersion) + 1;
            wsInstance.setExVersion(maxExVersion);
        } else {
            wsInstance = worksheetInstanceRepo.findByDbid(inputInstanceId);
            isUpdating = true;
        }

        if (wsInstance == null) {
            return Collections.singletonMap("message", "failure: invalid id for ws_instance_worksheet");
        }
        String inputStatus = wsInstanceInput.getStatus();
        String existingStatus = wsInstance.getStatus();


        if (isUpdating) {
            List<Long> inputIdList = new ArrayList<>();

            // Remove steps
            List<WorksheetStep> inputStepsList = wsInstanceInput.getSteps();
            for (WorksheetStep wsInstanceStep : inputStepsList) {
                if (wsInstanceStep.getDbid() != null && wsInstanceStep.getDbid() > 0) {
                    inputIdList.add(wsInstanceStep.getDbid());
                }
            }

            // update files from qc preparation, calibrator preparation, sample dilution and
            // mrd steps
            List<WorksheetStep> persistedStepList = new ArrayList<>();
            persistedStepList = wsStepRepository.findByWorksheet(wsInstance);
            if (persistedStepList != null && persistedStepList.size() > 0) {
                for (WorksheetStep persistedStep : persistedStepList) {
                    WorksheetStep currentInputStep = null;
                    for (WorksheetStep wsInstanceStep : inputStepsList) {
                        if (wsInstanceStep.getDbid() != null && wsInstanceStep.getDbid() == persistedStep.getDbid()) {
                            currentInputStep = wsInstanceStep;
                            break;
                        }
                    }
                }
            }

            persistedStepList = null;
            if (inputIdList.size() > 0) {
                persistedStepList = wsStepRepository.findByWorksheetAndDbidNotIn(wsInstance, inputIdList);
            } else {
                persistedStepList = wsStepRepository.findByWorksheet(wsInstance);
            }

            if (inputIdList.size() <= 0) {// Input don't have any step, Delete all steps from DB
                if (persistedStepList != null && persistedStepList.size() > 0) {
                    for (WorksheetStep wsStep : persistedStepList) {
                        // Delete MaterialDesignSubStep for all templates
                        List<MaterialInstanceSubStep> mdSubSteps = new ArrayList<>();
                        List<IncubationInstanceSubStep> idSubSteps = new ArrayList<>();
                        if (wsStep instanceof BlockingStep) {
                            idSubSteps = incubationInstanceSubStepRepo.findByBlockingStep((BlockingStep) wsStep);
                        } else if (wsStep instanceof CaptureStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                        } else if (wsStep instanceof CoatingStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                        } else if (wsStep instanceof DetectionStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                        } else if (wsStep instanceof SubstrateStep) {
                            idSubSteps = incubationInstanceSubStepRepo.findBySubstrateStep((SubstrateStep) wsStep);
                        } else if (wsStep instanceof TestStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByTestStep((TestStep) wsStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByTestStep((TestStep) wsStep);
                        }

                        if (mdSubSteps != null) {
                            for (MaterialInstanceSubStep mdSubStep : mdSubSteps) {
                                List<DilutionInstanceSubStep> dilutionSubSteps = dilutionInstanceSubStepRepo
                                        .findByMaterialStep(mdSubStep);
                                dilutionInstanceSubStepRepo.deleteAll(dilutionSubSteps);
                            }
                            materialInstanceSubStepRepo.deleteAll(mdSubSteps);
                        }

                        if (idSubSteps != null) {
                            incubationInstanceSubStepRepo.deleteAll(idSubSteps);
                        }
                    }
                    wsStepRepository.deleteAll(persistedStepList);
                }
            } else if (persistedStepList != null && persistedStepList.size() > 0) {// Both input and DB has at least one
                // Step each
                for (WorksheetStep persistedStep : persistedStepList) {
                    if (!inputIdList.contains(persistedStep.getDbid())) {// This Step is in DB but not in input
                        // Delete MaterialDesignSubStep for all templates
                        List<MaterialInstanceSubStep> mdSubSteps = new ArrayList<>();
                        List<IncubationInstanceSubStep> idSubSteps = new ArrayList<>();
                        if (persistedStep instanceof BlockingStep) {
                            idSubSteps = incubationInstanceSubStepRepo.findByBlockingStep((BlockingStep) persistedStep);
                        } else if (persistedStep instanceof CaptureStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                        } else if (persistedStep instanceof CoatingStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                        } else if (persistedStep instanceof DetectionStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByDetectionStep((DetectionStep) persistedStep);
                            idSubSteps = incubationInstanceSubStepRepo
                                    .findByDetectionStep((DetectionStep) persistedStep);
                        } else if (persistedStep instanceof SubstrateStep) {
                            idSubSteps = incubationInstanceSubStepRepo
                                    .findBySubstrateStep((SubstrateStep) persistedStep);
                        } else if (persistedStep instanceof TestStep) {
                            mdSubSteps = materialInstanceSubStepRepo.findByTestStep((TestStep) persistedStep);
                            idSubSteps = incubationInstanceSubStepRepo.findByTestStep((TestStep) persistedStep);
                        }

                        if (mdSubSteps != null) {
                            for (MaterialInstanceSubStep mdSubStep : mdSubSteps) {
                                List<DilutionInstanceSubStep> dilutionSubSteps = dilutionInstanceSubStepRepo
                                        .findByMaterialStep(mdSubStep);
                                dilutionInstanceSubStepRepo.deleteAll(dilutionSubSteps);
                            }
                            materialInstanceSubStepRepo.deleteAll(mdSubSteps);
                        }

                        if (idSubSteps != null) {
                            incubationInstanceSubStepRepo.deleteAll(idSubSteps);
                        }
                        wsStepRepository.delete(persistedStep);
                    } else { // This step is both in DB and in input. So, work with sub-steps and files

                    }

                }
            }

            // Remove results
            inputIdList.clear();
            if (wsInstanceInput.getResults() != null && wsInstanceInput.getResults().length != 0) {
                for (int i = 0; i < wsInstanceInput.getResults().length; i++) {
                    Result result = wsInstanceInput.getResults()[i];
                    inputIdList.add(result.getDbid());
                }
            }
            List<Result> persistedResultList = resultRepo.findByWorksheet(wsInstance);
            if (inputIdList.size() <= 0 && persistedResultList != null) {// Input don't have any file, Delete all files
                // from DB
                for (Result persistedResult : persistedResultList) {
                    if (persistedResult != null) {
                        resultRepo.delete(persistedResult);
                    }
                }
            } else if (inputIdList.size() > 0 && persistedResultList != null) {// Both input and DB has at least one
                // file each
                for (Result persistedResult : persistedResultList) {
                    if (persistedResult != null && !inputIdList.contains(persistedResult.getDbid())) {
                        resultRepo.delete(persistedResult);
                    }
                }
            }
        }

        WorksheetDesign wsDesign;
        UserV2 analyst;
        Long inputDesignId = wsInstanceInput.getTemplateId();
        Long inputAnalystId = wsInstanceInput.getAnalystId();
        if (inputDesignId == null || inputDesignId < 1 || inputAnalystId == null || inputAnalystId < 1) {
            return Collections.singletonMap("message", "failure: invalid id for worksheet design or analyst");
        } else {
            wsDesign = worksheetDesignRepo.findByDbid(inputDesignId);
            analyst = userRepo.findByUserId(inputAnalystId);
        }

        if (wsDesign == null || analyst == null) {
            return Collections.singletonMap("message", "failure: invalid id for worksheet design or analyst");
        }

        UserV2 reviewer = null;
        if (wsInstanceInput.getReviewerId() != null) {
            reviewer = userRepo.findByUserId(wsInstanceInput.getReviewerId());
        }

        wsInstance.setWorksheetDesign(wsDesign);
        wsInstance.setAnalyst(analyst);
        wsInstance.setStartDate(wsInstanceInput.getStartDate());
        wsInstance.setSaveDate(wsInstanceInput.getSaveDate());
        wsInstance.setStatus(wsInstanceInput.getStatus());
        wsInstance.setExType(wsInstanceInput.getExType());
        wsInstance.setExObjective(wsInstanceInput.getExObjective());
        wsInstance.setStudyNum(wsInstanceInput.getStudyNum());
        wsInstance.setCompletionDate(wsInstanceInput.getCompletionDate());
        wsInstance.setExObjective(wsInstanceInput.getExObjective());
        wsInstance.setOverallComment(wsInstanceInput.getOverallComment());
        wsInstance.setReviewDate(wsInstanceInput.getReviewDate());
        wsInstance.setReviewer(reviewer);
        wsInstance.setStartDate(wsInstanceInput.getStartDate());
        wsInstance.setDesignComment(wsInstanceInput.getDesignComment());

        WorksheetInstance wsInstancePersisted = worksheetInstanceRepo.save(wsInstance);
        
        // INL-308 - check if we have to write updates to user history table reflecting that an E-Sig
        // was gathered from the user. Scenarios are as follows:
        // Scenario 1: The analystId will indicate who executes a worksheet instance (executor), 
        // and the analystId will do the status change from "incomplete" to "complete"
        // Scenario 2: The reviewerId will do the status change from "complete" to "approved" or "rejected"
        // Will change this code when we do INL-309
        if ( !isUpdating ) {
            String activityDescription = String.format(
                        "%s executed and saved a worksheet instance - id '%d'",
                        analyst.getName(), wsInstance.getDbid());
                super.saveUserHistory(
                        analyst.getName(), // who
                        "WorkSheetInstance", // what 
                        "addWorksheetInstance", // activity 
                        activityDescription); // description
        }
        else {
            LOGGER.info( String.format( "Instance Input Status = %s", inputStatus ));
            LOGGER.info( String.format( "Saved Instance Status = %s", existingStatus));
            
            if (("complete".equals(inputStatus)) && ("incomplete".equals(existingStatus))) {
                LOGGER.info( "Detect Worksheet completed");
                String activityDescription = String.format(
                        "%s completed a worksheet instance - id '%d'",
                        analyst.getName(), wsInstance.getDbid());
                super.saveUserHistory(
                        analyst.getName(), // who
                        "WorkSheetInstance", // what 
                        "CompleteWorksheetInstance", // activity 
                        activityDescription); // description

            }
            else if (("complete".equals(existingStatus)) && 
                       (("approved".equals(inputStatus)) || ("rejected".equals(inputStatus)))) {
                LOGGER.info( String.format("Detect Worksheet %s", inputStatus));
                String reviewerName = "N/A";
                if (reviewer != null) {
                    reviewerName = reviewer.getName();
                }
                String activityDescription = String.format(
                        "%s %s a worksheet instance - id '%d'",
                        reviewerName, inputStatus, wsInstance.getDbid()  );
                super.saveUserHistory(
                        reviewer.getName(), // who
                        "WorkSheetInstance", // what 
                        String.format( "%s with E-Signature", inputStatus ), // activity
                        activityDescription); // description
            }
            else {
                LOGGER.info( "Update of Worksheet Instance");
                String activityDescription = String.format(
                        "%s updated a worksheet instance - id: '%d'",
                        analyst.getName(), wsInstance.getDbid());
                super.saveUserHistory(
                        analyst.getName(), // who
                        "WorkSheetInstance", // what 
                        "updateWorksheetInstance", // activity
                        activityDescription); // description
            }
        }

        List<WorksheetStep> wsInstanceStepList = wsInstanceInput.getSteps();

        long stepPosition = 0;

        for (WorksheetStep wsInstanceStep : wsInstanceStepList) {
            WorksheetStep persistedStep;
            wsInstanceStep.setWorksheet(wsInstancePersisted);
            wsInstanceStep.setPosition(++stepPosition);
            persistedStep = wsStepRepository.save(wsInstanceStep);
            List<IncubationInstanceSubStep> incubationList = getIncubationList(wsInstanceStep);
            int subStepPosition = 0;
            if (incubationList != null && persistedStep != null) {

                for (IncubationInstanceSubStep incubation : incubationList) {
                    if (wsInstanceStep.getType().equalsIgnoreCase("blocking instance")) {
                        incubation.setBlockingStep((BlockingStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("capture instance")) {
                        incubation.setCaptureStep((CaptureStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("coating instance")) {
                        incubation.setCoatingStep((CoatingStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("detection instance")) {
                        incubation.setDetectionStep((DetectionStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("substrate instance")) {
                        incubation.setSubstrateStep((SubstrateStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("test instance")) {
                        incubation.setTestStep((TestStepInstance) persistedStep);
                        incubation.setPosition(++subStepPosition);
                        incubationInstanceSubStepRepo.save(incubation);
                    }
                }

            }

            List<MaterialInstanceSubStep> substepList = getSubStepList(wsInstanceStep);
            if (substepList != null && persistedStep != null) {
                for (MaterialInstanceSubStep miSubstep : substepList) {
                    MaterialInstanceSubStep persistedMISubStep = null;
                    if (wsInstanceStep.getType().equalsIgnoreCase("capture instance")) {
                        miSubstep.setCaptureStep((CaptureStepInstance) persistedStep);
                        persistedMISubStep = materialInstanceSubStepRepo.save(miSubstep);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("coating instance")) {
                        miSubstep.setCoatingStep((CoatingStepInstance) persistedStep);
                        persistedMISubStep = materialInstanceSubStepRepo.save(miSubstep);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("detection instance")) {
                        miSubstep.setDetectionStep((DetectionStepInstance) persistedStep);
                        persistedMISubStep = materialInstanceSubStepRepo.save(miSubstep);
                    } else if (wsInstanceStep.getType().equalsIgnoreCase("test instance")) {
                        miSubstep.setTestStep((TestStepInstance) persistedStep);
                        persistedMISubStep = materialInstanceSubStepRepo.save(miSubstep);
                    }

                    List<DilutionInstanceSubStep> dilutionInstanceSubStepList = miSubstep.getSubStepArray();
                    if (dilutionInstanceSubStepList != null && persistedMISubStep != null) {
                        for (DilutionInstanceSubStep dilutionInstanceSubStep : dilutionInstanceSubStepList) {
                            dilutionInstanceSubStep.setMaterialStep(persistedMISubStep);
                            dilutionInstanceSubStepRepo.save(dilutionInstanceSubStep);
                        }
                    }
                }
            }
        }

        Result[] inputResultList = wsInstanceInput.getResults();
        if (inputResultList != null) {
            for (Result inputResult : inputResultList) {
                inputResult.setWorksheet(wsInstancePersisted);
                resultRepo.save(inputResult);
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "success");
        response.put("worksheetId", "" + wsInstancePersisted.getDbid());

        return response;
    }

    // TODO
    // This separate end-point is not required, since addWorksheetInstance method is
    // capable to both add and update
    // @PathVariable id is not required since WorksheetInstancePostInput class
    // already has the id
    @Transactional
    @PutMapping("/update/{id}")
    public Map<String, String> updateWorksheetInstance(@PathVariable(value = "id") Long inputInstanceId,
            @Valid @RequestBody WorksheetInstancePostInput wsInstanceInput) {

        if (inputInstanceId == null || inputInstanceId < 1
                || worksheetInstanceRepo.findByDbid(inputInstanceId) == null) {
            return Collections.singletonMap("message", "failure: invalid id for ws_instance_worksheet");
        }

        wsInstanceInput.setWorksheetId(inputInstanceId);

        return addWorksheetInstance(wsInstanceInput);

    }

    @GetMapping("/specificinstance/{id}")
    public WorksheetInstanceStepGetOutput getSpecificWorksheet(@PathVariable(value = "id") Long inputInstanceId) {

        WorksheetInstance wsInstance = worksheetInstanceRepo.findByDbid(inputInstanceId);

        if (wsInstance == null) {
            WorksheetInstanceStepGetOutput output = new WorksheetInstanceStepGetOutput();
            output.setMessage("faliure: invalid id");
            return output;
        }

        Long templateId = null;
        String templateName = "";
        Long analystId = null;
        String analystName = null;
        Long reviewerId = null;
        String reviewerName = null;

        if (wsInstance.getWorksheetDesign() != null) {
            templateId = wsInstance.getWorksheetDesign().getDbid();
            templateName = wsInstance.getWorksheetDesign().getTemplateName();
        }

        if (wsInstance.getAnalyst() != null) {
            analystId = wsInstance.getAnalyst().getUserId();
            analystName = wsInstance.getAnalyst().getName();
        }

        if (wsInstance.getReviewer() != null) {
            reviewerId = wsInstance.getReviewer().getUserId();
            reviewerName = wsInstance.getReviewer().getName();
        }

        // Getting Results
        List<Result> resultList = wsInstance.getResults();
        if (resultList != null) {
            for (Result result : resultList) {
                // Do nothing, this block is required to deserialize JSON
            }
        }

        Set<WorksheetStep> wsStepSet = wsInstance.getSteps();
        List<WorksheetStep> wsStepList = new ArrayList<>(wsStepSet);
        if (wsStepList != null) {
            Collections.sort(wsStepList, new WorksheetStepSort());
        }

        for (WorksheetStep wsStep : wsStepList) {

            // Getting Equipments
            List<EquipmentV2> eqList = wsStep.getEquipments();
            for (EquipmentV2 eq : eqList) {
                // Do nothing, this block is required to deserialize JSON
            }

            List<IncubationInstanceSubStep> incubationList = getIncubationList(wsStep);
            if (incubationList != null) {
                Collections.sort(incubationList, new IncubationInstanceSubStepSort());
            }
            if (incubationList != null) {
                for (IncubationInstanceSubStep incubation : incubationList) {
                    LOGGER.info("At least one incubation found");
                }

            }

            List<MaterialInstanceSubStep> substepList = getSubStepList(wsStep);
            if (substepList != null) {
                Collections.sort(substepList, new MaterialInstanceSubStepSort());
            }
            if (substepList != null) {
                for (MaterialInstanceSubStep substep : substepList) {
                    // Do nothing, this block is required to deserialize JSON
                    List<DilutionInstanceSubStep> dilutionSubStepList = substep.getSubStepArray();
                    if (dilutionSubStepList != null) {
                        Collections.sort(dilutionSubStepList, new DilutionInstanceSubStepSort());
                    }
                    for (DilutionInstanceSubStep dilutionSubStep : dilutionSubStepList) {
                        // Do nothing, this block is required to deserialize JSON
                    }
                }
            }


        }

        List<PlateMap> plateMapList = plateMapRepository.findByWorksheet(wsInstance.getWorksheetDesign());
        if (plateMapList != null) {
            for (PlateMap plateMap : plateMapList) {
                // Do nothing, this block is required to deserialize JSON
            }
        }

        return new WorksheetInstanceStepGetOutput("success", templateName, wsInstance.getDbid(), templateId, analystId,
                analystName, reviewerId, reviewerName, wsInstance.getStartDate(), wsInstance.getSaveDate(),
                wsInstance.getCompletionDate(), wsInstance.getReviewDate(), wsInstance.getStatus(), wsStepList,
                wsInstance.getOverallComment(), wsInstance.getExVersion(), wsInstance.getExType(), wsInstance.getExObjective(),
                wsInstance.getStudyNum(), plateMapList, wsInstance.getResults(), wsInstance.getDesignComment());
    }

    private List<IncubationInstanceSubStep> getIncubationList(WorksheetStep wsStep) {

        List<IncubationInstanceSubStep> incubationList = null;

        if (wsStep == null || wsStep.getType() == null) {
            return null;
        }

        if (wsStep.getType().equalsIgnoreCase("blocking instance")) {
            BlockingStepInstance blocking = (BlockingStepInstance) wsStep;
            incubationList = blocking.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("capture instance")) {
            CaptureStepInstance capture = (CaptureStepInstance) wsStep;
            incubationList = capture.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("coating instance")) {
            CoatingStepInstance coating = (CoatingStepInstance) wsStep;
            incubationList = coating.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("detection instance")) {
            DetectionStepInstance detection = (DetectionStepInstance) wsStep;
            incubationList = detection.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("substrate instance")) {
            SubstrateStepInstance substrate = (SubstrateStepInstance) wsStep;
            incubationList = substrate.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("test instance")) {
            TestStepInstance test = (TestStepInstance) wsStep;
            incubationList = test.getIncubationArray();
        }

        return incubationList;
    }

    private List<MaterialInstanceSubStep> getSubStepList(WorksheetStep wsStep) {

        List<MaterialInstanceSubStep> substepList = null;

        if (wsStep == null || wsStep.getType() == null) {
            return null;
        }
        if (wsStep.getType().equalsIgnoreCase("capture instance")) {
            CaptureStepInstance capture = (CaptureStepInstance) wsStep;
            substepList = capture.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("coating instance")) {
            CoatingStepInstance coating = (CoatingStepInstance) wsStep;
            substepList = coating.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("detection instance")) {
            DetectionStepInstance detection = (DetectionStepInstance) wsStep;
            substepList = detection.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("test instance")) {
            TestStepInstance test = (TestStepInstance) wsStep;
            substepList = test.getSubStepArray();
        }

        return substepList;
    }

    

    @GetMapping("/incomplete_summary")
    public WorksheetInstanceGetOutput getIncompleteWorksheetSummaries() {

        try {
            List<WorksheetInstance> list = worksheetInstanceRepo.findIncompleteWorksheetInstances();

            List<WorksheetInstanceSummary> summaries = new ArrayList<WorksheetInstanceSummary>();

            WorksheetInstanceSummary summary = null;

            for (int i = 0; i < list.size(); i++) {

                WorksheetInstance w = list.get(i);

                if (w.getReviewer() == null) {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            null, null, w.getStartDate(), w.getSaveDate(), w.getCompletionDate(), w.getReviewDate(),
                            w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(), w.getStudyNum());

                } else {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            w.getReviewer().getUserId(), w.getReviewer().getName(), w.getStartDate(), w.getSaveDate(),
                            w.getCompletionDate(), w.getReviewDate(), w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(),
                            w.getStudyNum());

                }

                summaries.add(summary);

            }

            return new WorksheetInstanceGetOutput("success", summaries);

        } catch (Exception e) {
            return new WorksheetInstanceGetOutput("failure", null);

        }

    }

    @GetMapping("/complete_summary")
    public WorksheetInstanceGetOutput getCompleteWorksheetSummaries() {

        try {
            List<WorksheetInstance> list = worksheetInstanceRepo.findCompleteWorksheetInstances();

            List<WorksheetInstanceSummary> summaries = new ArrayList<WorksheetInstanceSummary>();

            WorksheetInstanceSummary summary = null;

            for (int i = 0; i < list.size(); i++) {

                WorksheetInstance w = list.get(i);

                if (w.getReviewer() == null) {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            null, null, w.getStartDate(), w.getSaveDate(), w.getCompletionDate(), w.getReviewDate(),
                            w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(), w.getStudyNum());

                } else {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            w.getReviewer().getUserId(), w.getReviewer().getName(), w.getStartDate(), w.getSaveDate(),
                            w.getCompletionDate(), w.getReviewDate(), w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(),
                            w.getStudyNum());

                }

                summaries.add(summary);

            }

            return new WorksheetInstanceGetOutput("success", summaries);

        } catch (Exception e) {
            return new WorksheetInstanceGetOutput("failure", null);

        }

    }

    @GetMapping("/approved_summary")
    public WorksheetInstanceGetOutput getApprovedWorksheetSummaries() {

        try {
            List<WorksheetInstance> list = worksheetInstanceRepo.findApprovedWorksheetInstances();

            List<WorksheetInstanceSummary> summaries = new ArrayList<WorksheetInstanceSummary>();

            WorksheetInstanceSummary summary = null;

            for (int i = 0; i < list.size(); i++) {

                WorksheetInstance w = list.get(i);

                if (w.getReviewer() == null) {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            null, null, w.getStartDate(), w.getSaveDate(), w.getCompletionDate(), w.getReviewDate(),
                            w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(), w.getStudyNum());

                } else {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            w.getReviewer().getUserId(), w.getReviewer().getName(), w.getStartDate(), w.getSaveDate(),
                            w.getCompletionDate(), w.getReviewDate(), w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(),
                            w.getStudyNum());

                }

                summaries.add(summary);

            }

            return new WorksheetInstanceGetOutput("success", summaries);

        } catch (Exception e) {
            return new WorksheetInstanceGetOutput("failure", null);

        }
    }

    @GetMapping("/rejected_summary")
    public WorksheetInstanceGetOutput getRejectedWorksheetSummaries() {
        try {
            List<WorksheetInstance> list = worksheetInstanceRepo.findRejectedWorksheetInstances();

            List<WorksheetInstanceSummary> summaries = new ArrayList<WorksheetInstanceSummary>();

            WorksheetInstanceSummary summary = null;

            for (int i = 0; i < list.size(); i++) {

                WorksheetInstance w = list.get(i);

                if (w.getReviewer() == null) {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            null, null, w.getStartDate(), w.getSaveDate(), w.getCompletionDate(), w.getReviewDate(),
                            w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(), w.getStudyNum());

                } else {
                    summary = new WorksheetInstanceSummary(w.getWorksheetDesign().getWsNum(),
                            w.getWorksheetDesign().getExeNum(), w.getWorksheetDesign().getTemplateName(), w.getDbid(),
                            w.getWorksheetDesign().getDbid(), w.getAnalyst().getUserId(), w.getAnalyst().getName(),
                            w.getReviewer().getUserId(), w.getReviewer().getName(), w.getStartDate(), w.getSaveDate(),
                            w.getCompletionDate(), w.getReviewDate(), w.getStatus(), w.getExVersion(), w.getExType(), w.getExObjective(),
                            w.getStudyNum());

                }

                summaries.add(summary);

            }

            return new WorksheetInstanceGetOutput("success", summaries);
        } catch (Exception e) {
            return new WorksheetInstanceGetOutput("failure", null);
        }
    }
}



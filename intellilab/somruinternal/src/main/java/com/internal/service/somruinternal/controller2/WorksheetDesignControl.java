package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.internal.service.somruinternal.model2.*;
import com.internal.service.somruinternal.dto.*;
import com.internal.service.somruinternal.repository2.*;
import com.internal.service.somruinternal.utils.*;

@RestController
@RequestMapping("/worksheetdesign")
public class WorksheetDesignControl extends ParentControl {

    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger LOGGER = LoggerFactory.getLogger(WorksheetDesignControl.class);

    @Autowired
    WorksheetDesignRepository worksheetDesignRepo;

    @Autowired
    UserRepositoryV2 userRepo;

    @Autowired
    UserHistoryRepositoryV2 userHistoryRepo;

    @Autowired
    StopStepRepository stopStepRepo;

    @Autowired
    WashStepRepository washStepRepo;

    @Autowired
    CoatingStepRepository coatingStepRepo;

    @Autowired
    BlockingStepRepository blockingStepRepo;

    @Autowired
    CaptureStepRepository captureStepRepo;

    @Autowired
    TestStepRepository testStepRepo;

    @Autowired
    DetectionStepRepository detectionStepRepo;

    @Autowired
    SubstrateStepRepository substrateStepRepo;

    @Autowired
    MaterialDesignSubStepRepository materialDesignSubStepRepo;

    @Autowired
    DilutionDesignSubStepRepository dilutionDesignSubStepRepo;

    @Autowired
    PlateMapRepository plateMapRepo;

    @Autowired
    IncubationDesignSubStepRepository incubationDesignSubStepRepo;

    @Autowired
    AnalystWorksheetDesignRepository analystWorksheetDesignRepo;

    @Autowired
    WorksheetStepRepository wsStepRepository;

    @Transactional
    @DeleteMapping("/delete/{id}")
    public Map<String, String> deleteWorksheetDesign(@PathVariable(value = "id") Long id) {
        WorksheetDesign worksheetDesign = worksheetDesignRepo.findByDbid(id);

        if (worksheetDesign == null || worksheetDesign.getFinalized() == 1) {
            return Collections.singletonMap("message", "failure");
        }

        Set<WorksheetStep> wsSteps = wsStepRepository.findByTemplate(worksheetDesign);
        if (wsSteps != null) {
            for (WorksheetStep wsStep : wsSteps) {
                //Delete MaterialDesignSubStep for all templates 
                List<MaterialDesignSubStep> mdSubSteps = new ArrayList<>();
                List<IncubationDesignSubStep> idSubSteps = new ArrayList<>();
                if (wsStep instanceof BlockingStep) {
                    idSubSteps = incubationDesignSubStepRepo.findByBlockingStep((BlockingStep) wsStep);
                } else if (wsStep instanceof CaptureStep) {
                    mdSubSteps = materialDesignSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                    idSubSteps = incubationDesignSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                } else if (wsStep instanceof CoatingStep) {
                    mdSubSteps = materialDesignSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                    idSubSteps = incubationDesignSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                } else if (wsStep instanceof DetectionStep) {
                    mdSubSteps = materialDesignSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                    idSubSteps = incubationDesignSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                } else if (wsStep instanceof SubstrateStep) {
                    idSubSteps = incubationDesignSubStepRepo.findBySubstrateStep((SubstrateStep) wsStep);
                } else if (wsStep instanceof TestStep) {
                    mdSubSteps = materialDesignSubStepRepo.findByTestStep((TestStep) wsStep);
                    idSubSteps = incubationDesignSubStepRepo.findByTestStep((TestStep) wsStep);
                }

                if (mdSubSteps != null) {
                    for (MaterialDesignSubStep mdSubStep : mdSubSteps) {
                        List<DilutionDesignSubStep> dilutionSubSteps = dilutionDesignSubStepRepo.findByMaterialStep(mdSubStep);
                        dilutionDesignSubStepRepo.deleteAll(dilutionSubSteps);
                    }
                    materialDesignSubStepRepo.deleteAll(mdSubSteps);
                }

                if (idSubSteps != null) {
                    incubationDesignSubStepRepo.deleteAll(idSubSteps);
                }
            }
            wsStepRepository.deleteAll(wsSteps);
        }

        List<PlateMap> plateMaps = worksheetDesign.getPlateMap();
        if (plateMaps != null) {
            plateMapRepo.deleteAll(plateMaps);
        }

        List<AnalystWorksheetDesign> analystWsDesignList = worksheetDesign.getAnalystWorksheetDesignList();
        if (analystWsDesignList != null) {
            analystWorksheetDesignRepo.deleteAll(analystWsDesignList);
        }
        String activityDescription = String.format(
                        "%s deleted a worksheet design id - '%d'"  ,
                        worksheetDesign.getScientist(), worksheetDesign.getDbid());
        super.saveUserHistory(worksheetDesign.getScientist(), "WorkSheetDesign", "deleteWorksheetDesign", activityDescription);
        worksheetDesignRepo.delete(worksheetDesign);
        return Collections.singletonMap("message", "success");
    }

    @Transactional
    @PutMapping("/update/{id}")
    public Map<String, String> updateWorksheet(@PathVariable(value = "id") Long inputDesignId,
            @Valid @RequestBody WorksheetDesignPostInput newWorksheet) {

        if (inputDesignId == null || inputDesignId < 1
                || worksheetDesignRepo.findByDbid(inputDesignId) == null) {
            return Collections.singletonMap("message", "failure: invalid id for ws_design_worksheet");
        }

        newWorksheet.setTemplateId(inputDesignId);

        WorksheetDesignPostOutput output = addWorksheet(newWorksheet);
        if ( newWorksheet.getFinalized() == 1 ) {
            String activityDescription = String.format(
                        "%s finalized worksheet design id - '%d'",
                        newWorksheet.getScientist(), newWorksheet.getTemplateId());
            super.saveUserHistory(newWorksheet.getScientist(), "WorkSheetDesign", "updateWorksheetDesign", activityDescription);    
        }
        else {
            String activityDescription = String.format(
                        "%s updated a worksheet design id - '%d'",
                        newWorksheet.getScientist(), newWorksheet.getTemplateId());
            super.saveUserHistory(newWorksheet.getScientist(), "WorkSheetDesign", "updateWorksheetDesign", activityDescription);    
        }
        
        if (output != null && output.getMessage() != null && output.getMessage().trim().equalsIgnoreCase("success")) {
            return Collections.singletonMap("message", "success");
        } else {
            return Collections.singletonMap("message", "failure");
        }
    }

    @Transactional
    @PostMapping("/addworksheet")
    public WorksheetDesignPostOutput addWorksheet(@Valid @RequestBody WorksheetDesignPostInput inputDesign) {

        LOGGER.info(String.format("addWorksheet received following input: %s", inputDesign));
        WorksheetDesign worksheet;

        boolean isUpdating = false;

        Long inputDesignId = inputDesign.getTemplateId();
        if (inputDesignId == null || inputDesignId < 1) {
            worksheet = new WorksheetDesign();
        } else {
            worksheet = worksheetDesignRepo.findByDbid(inputDesignId);
            isUpdating = true;
        }

        if (worksheet == null) {
            return new WorksheetDesignPostOutput("failure: invalid id for worksheet", null, null);
        }

        if (isUpdating) { //Then remove steps and files which are not present anymore in input
            List<Long> inputIdList = new ArrayList<>();
            
            List<WorksheetStep> inputDesignStepsList = inputDesign.getSteps();
            for (WorksheetStep designStep : inputDesignStepsList) {
                inputIdList.add(designStep.getDbid());
            }
            Set<WorksheetStep> persistedStepList = wsStepRepository.findByTemplate(worksheet);
            if (inputIdList.size() <= 0) {//Input don't have any step, Delete all steps from DB 
                if (persistedStepList != null && persistedStepList.size() > 0) {
                    for (WorksheetStep wsStep : persistedStepList) {
                        //Delete MaterialDesignSubStep for all templates 
                        List<MaterialDesignSubStep> mdSubSteps = new ArrayList<>();
                        List<IncubationDesignSubStep> idSubSteps = new ArrayList<>();
                        if (wsStep instanceof BlockingStep) {
                            idSubSteps = incubationDesignSubStepRepo.findByBlockingStep((BlockingStep) wsStep);
                        } else if (wsStep instanceof CaptureStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                            idSubSteps = incubationDesignSubStepRepo.findByCaptureStep((CaptureStep) wsStep);
                        } else if (wsStep instanceof CoatingStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                            idSubSteps = incubationDesignSubStepRepo.findByCoatingStep((CoatingStep) wsStep);
                        } else if (wsStep instanceof DetectionStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                            idSubSteps = incubationDesignSubStepRepo.findByDetectionStep((DetectionStep) wsStep);
                        } else if (wsStep instanceof SubstrateStep) {
                            idSubSteps = incubationDesignSubStepRepo.findBySubstrateStep((SubstrateStep) wsStep);
                        } else if (wsStep instanceof TestStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByTestStep((TestStep) wsStep);
                            idSubSteps = incubationDesignSubStepRepo.findByTestStep((TestStep) wsStep);
                        }

                        if (mdSubSteps != null) {
                            for (MaterialDesignSubStep mdSubStep : mdSubSteps) {
                                List<DilutionDesignSubStep> dilutionSubSteps = dilutionDesignSubStepRepo.findByMaterialStep(mdSubStep);
                                dilutionDesignSubStepRepo.deleteAll(dilutionSubSteps);
                            }
                            materialDesignSubStepRepo.deleteAll(mdSubSteps);
                        }

                        if (idSubSteps != null) {
                            incubationDesignSubStepRepo.deleteAll(idSubSteps);
                        }
                    }
                    wsStepRepository.deleteAll(persistedStepList);
                }
            } else if (persistedStepList != null && persistedStepList.size() > 0) {//Both input and DB has at least one Step each 
                for (WorksheetStep persistedStep : persistedStepList) {
                    if (!inputIdList.contains(persistedStep.getDbid())) {//This Step is in DB but not in input
                        //Delete MaterialDesignSubStep for all templates 
                        List<MaterialDesignSubStep> mdSubSteps = new ArrayList<>();
                        List<IncubationDesignSubStep> idSubSteps = new ArrayList<>();
                        if (persistedStep instanceof BlockingStep) {
                            idSubSteps = incubationDesignSubStepRepo.findByBlockingStep((BlockingStep) persistedStep);
                        } else if (persistedStep instanceof CaptureStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                            idSubSteps = incubationDesignSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                        } else if (persistedStep instanceof CoatingStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                            idSubSteps = incubationDesignSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                        } else if (persistedStep instanceof DetectionStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByDetectionStep((DetectionStep) persistedStep);
                            idSubSteps = incubationDesignSubStepRepo.findByDetectionStep((DetectionStep) persistedStep);
                        } else if (persistedStep instanceof SubstrateStep) {
                            idSubSteps = incubationDesignSubStepRepo.findBySubstrateStep((SubstrateStep) persistedStep);
                        } else if (persistedStep instanceof TestStep) {
                            mdSubSteps = materialDesignSubStepRepo.findByTestStep((TestStep) persistedStep);
                            idSubSteps = incubationDesignSubStepRepo.findByTestStep((TestStep) persistedStep);
                        }

                        if (mdSubSteps != null) {
                            for (MaterialDesignSubStep mdSubStep : mdSubSteps) {
                                List<DilutionDesignSubStep> dilutionSubSteps = dilutionDesignSubStepRepo.findByMaterialStep(mdSubStep);
                                dilutionDesignSubStepRepo.deleteAll(dilutionSubSteps);
                            }
                            materialDesignSubStepRepo.deleteAll(mdSubSteps);
                        }

                        if (idSubSteps != null) {
                            incubationDesignSubStepRepo.deleteAll(idSubSteps);
                        }
                        wsStepRepository.delete(persistedStep);
                    } else { //This step is present both on input and DB, now take care of the sub-steps
                        WorksheetStep inputDesignStep = null;
                        for (WorksheetStep designStep : inputDesignStepsList) {
                            if (persistedStep.getDbid() == designStep.getDbid()) {
                                inputDesignStep = designStep;
                                break;
                            }
                        }
                        if (inputDesignStep != null) {

                            List<MaterialDesignSubStep> mdSubSteps = new ArrayList<>();
                            List<IncubationDesignSubStep> idSubSteps = new ArrayList<>();

                            List<MaterialDesignSubStep> inputMdSubSteps = new ArrayList<>();
                            List<IncubationDesignSubStep> inputIdSubSteps = new ArrayList<>();

                            if (persistedStep instanceof BlockingStep) {
                                BlockingStep step = (BlockingStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                idSubSteps = incubationDesignSubStepRepo.findByBlockingStep((BlockingStep) persistedStep);
                            } else if (persistedStep instanceof CaptureStep) {
                                CaptureStep step = (CaptureStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                inputMdSubSteps = step.getSubStepArray();
                                mdSubSteps = materialDesignSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                                idSubSteps = incubationDesignSubStepRepo.findByCaptureStep((CaptureStep) persistedStep);
                            } else if (persistedStep instanceof CoatingStep) {
                                CoatingStep step = (CoatingStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                inputMdSubSteps = step.getSubStepArray();
                                mdSubSteps = materialDesignSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                                idSubSteps = incubationDesignSubStepRepo.findByCoatingStep((CoatingStep) persistedStep);
                            } else if (persistedStep instanceof DetectionStep) {
                                DetectionStep step = (DetectionStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                inputMdSubSteps = step.getSubStepArray();
                                mdSubSteps = materialDesignSubStepRepo.findByDetectionStep((DetectionStep) persistedStep);
                                idSubSteps = incubationDesignSubStepRepo.findByDetectionStep((DetectionStep) persistedStep);
                            } else if (persistedStep instanceof SubstrateStep) {
                                SubstrateStep step = (SubstrateStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                idSubSteps = incubationDesignSubStepRepo.findBySubstrateStep((SubstrateStep) persistedStep);
                            } else if (persistedStep instanceof TestStep) {
                                TestStep step = (TestStep) inputDesignStep;
                                inputIdSubSteps = step.getIncubationArray();
                                inputMdSubSteps = step.getSubStepArray();
                                mdSubSteps = materialDesignSubStepRepo.findByTestStep((TestStep) persistedStep);
                                idSubSteps = incubationDesignSubStepRepo.findByTestStep((TestStep) persistedStep);
                            }

                            for (IncubationDesignSubStep idSubStep : idSubSteps) {
                                boolean existsInDB = false;
                                for (IncubationDesignSubStep inputIdSubStep : inputIdSubSteps) {
                                    if (inputIdSubStep.getDbid() == idSubStep.getDbid()) {
                                        existsInDB = true;
                                        break;
                                    }
                                }
                                if (!existsInDB) {
                                    incubationDesignSubStepRepo.delete(idSubStep);
                                }
                            }

                            for (MaterialDesignSubStep mdSubStep : mdSubSteps) {
                                boolean existsInDB = false;
                                for (MaterialDesignSubStep inputMdSubStep : inputMdSubSteps) {
                                    if (inputMdSubStep.getDbid() == mdSubStep.getDbid()) {
                                        existsInDB = true;
                                        List<DilutionDesignSubStep> ddSubStepList = mdSubStep.getSubStepArray();
                                        List<DilutionDesignSubStep> inputDdSubStepList = inputMdSubStep.getSubStepArray();
                                        for (DilutionDesignSubStep ddSubStep : ddSubStepList) {
                                            boolean ddExistsInDb = false;
                                            for (DilutionDesignSubStep inputDdSubStep : inputDdSubStepList) {
                                                if (inputDdSubStep.getDbid() == ddSubStep.getDbid()) {
                                                    ddExistsInDb = true;
                                                    break;
                                                }
                                            }
                                            if (!ddExistsInDb) {
                                                dilutionDesignSubStepRepo.delete(ddSubStep);
                                            }
                                        }
                                        break;
                                    }
                                }
                                if (!existsInDB) {
                                    List<DilutionDesignSubStep> ddSubStepList = mdSubStep.getSubStepArray();
                                    dilutionDesignSubStepRepo.deleteAll(ddSubStepList);
                                    materialDesignSubStepRepo.delete(mdSubStep);
                                }
                            }

                        }
                    }
                }
            }

            //Remove Files
            inputIdList.clear();
            if (inputDesign.getFiles() != null && inputDesign.getFiles().length != 0) {
                for (int i = 0; i < inputDesign.getFiles().length; i++) {
                    PlateMap plateMap = inputDesign.getFiles()[i];
                    inputIdList.add(plateMap.getDbid());
                }
            }
            List<PlateMap> persistedFileList = plateMapRepo.findByWorksheet(worksheet);
            if (inputIdList.size() <= 0 && persistedFileList != null) {//Input don't have any file, Delete all files from DB
                for (PlateMap persistedFile : persistedFileList) {
                    if (persistedFile != null) {
                        plateMapRepo.delete(persistedFile);
                    }
                }
            } else if (inputIdList.size() > 0 && persistedFileList != null) {//Both input and DB has at least one file each
                for (PlateMap persistedFile : persistedFileList) {
                    if (persistedFile != null && !inputIdList.contains(persistedFile.getDbid())) {
                        plateMapRepo.delete(persistedFile);
                    }
                }
            }
        }

        worksheet.setWsNum(inputDesign.getWsNum());
        worksheet.setExeNum(inputDesign.getExeNum());
        worksheet.setDateCreated(inputDesign.getCreationDate());
        worksheet.setDateFinalized(inputDesign.getFinalizationDate());
        worksheet.setDateSaved(inputDesign.getSaveDate());
        worksheet.setExObjective(inputDesign.getExObjective());
        worksheet.setExType(inputDesign.getExType());
        worksheet.setFinalized(inputDesign.getFinalized());
        worksheet.setScientist(inputDesign.getScientist());
        worksheet.setStudyNum(inputDesign.getStudyNum());
        worksheet.setTemplateName(inputDesign.getTemplateName());
        worksheet.setUser(inputDesign.getUserId() != null ? userRepo.findByUserId(inputDesign.getUserId()) : null);
        worksheet.setVersion(inputDesign.getVersion() >= 0 ? (inputDesign.getVersion() + 1) : 1);
        worksheet.setDesignComment(inputDesign.getDesignComment());

        //Saving WS Design
        worksheetDesignRepo.save(worksheet);

        List<WorksheetStep> inputDesignStepList = inputDesign.getSteps();

        long stepPosition = 0;

        for (WorksheetStep inputDesignStepStep : inputDesignStepList) {//Save all steps after setting WS design to those steps

            WorksheetStep persistedStep;
            //Setting saved design to this step
            inputDesignStepStep.setTemplate(worksheet);
            inputDesignStepStep.setPosition(++stepPosition);
            //Saving Step
            persistedStep = wsStepRepository.save(inputDesignStepStep);
            List<IncubationDesignSubStep> incubationList = getIncubationList(inputDesignStepStep);
            int subStepPosition = 0;
            if (incubationList != null && persistedStep != null) {
                for (IncubationDesignSubStep incubation : incubationList) {//Save all incubation sub-steps after setting Step to them
                    if (inputDesignStepStep.getType().equalsIgnoreCase("blocking")) {
                        incubation.setBlockingStep(blockingStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("capture")) {
                        incubation.setCaptureStep(captureStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("coating")) {
                        incubation.setCoatingStep(coatingStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("detection")) {
                        incubation.setDetectionStep(detectionStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("substrate")) {
                        incubation.setSubstrateStep(substrateStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("test")) {
                        incubation.setTestStep(testStepRepo.findByDbid(persistedStep.getDbid()));
                        incubation.setPosition(++subStepPosition);
                        incubationDesignSubStepRepo.save(incubation);
                    }
                }

            }

            List<MaterialDesignSubStep> mdSubStepList = getSubStepList(inputDesignStepStep);
            subStepPosition = 0;
            if (mdSubStepList != null && persistedStep != null) {
                for (MaterialDesignSubStep mdSubStep : mdSubStepList) {//Save all material sub-steps after setting Step to them
                    MaterialDesignSubStep persistedMDSubStep = null;
                    if (inputDesignStepStep.getType().equalsIgnoreCase("capture")) {
                        mdSubStep.setCaptureStep(captureStepRepo.findByDbid(persistedStep.getDbid()));
                        mdSubStep.setPosition(++subStepPosition);
                        persistedMDSubStep = materialDesignSubStepRepo.save(mdSubStep);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("coating")) {
                        mdSubStep.setCoatingStep(coatingStepRepo.findByDbid(persistedStep.getDbid()));
                        mdSubStep.setPosition(++subStepPosition);
                        persistedMDSubStep = materialDesignSubStepRepo.save(mdSubStep);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("detection")) {
                        mdSubStep.setDetectionStep(detectionStepRepo.findByDbid(persistedStep.getDbid()));
                        mdSubStep.setPosition(++subStepPosition);
                        persistedMDSubStep = materialDesignSubStepRepo.save(mdSubStep);
                    } else if (inputDesignStepStep.getType().equalsIgnoreCase("test")) {
                        mdSubStep.setTestStep(testStepRepo.findByDbid(persistedStep.getDbid()));
                        mdSubStep.setPosition(++subStepPosition);
                        persistedMDSubStep = materialDesignSubStepRepo.save(mdSubStep);
                    }

                    List<DilutionDesignSubStep> dilutionDesignSubStepist = mdSubStep.getSubStepArray();
                    subStepPosition = 0;
                    if (dilutionDesignSubStepist != null && persistedMDSubStep != null) {
                        for (DilutionDesignSubStep dilutionDesignSubStep : dilutionDesignSubStepist) {
                            dilutionDesignSubStep.setMaterialStep(persistedMDSubStep);
                            dilutionDesignSubStep.setPosition(++subStepPosition);
                            dilutionDesignSubStepRepo.save(dilutionDesignSubStep);
                        }
                    }
                }
            }

        }

        //TODO Add Plate Map files:
        if (inputDesign.getFiles() != null && inputDesign.getFiles().length != 0) {
            for (int i = 0; i < inputDesign.getFiles().length; i++) {
                PlateMap plateMap = inputDesign.getFiles()[i];

                plateMap.setWorksheet(worksheet);

                plateMapRepo.save(plateMap);
            }

        }

        if ( worksheet.getFinalized() == 0 ) {
            String activityDescription = String.format(
                        "%s saved a new worksheet design id - %d",
                        worksheet.getScientist(), worksheet.getDbid());
            super.saveUserHistory(worksheet.getScientist(), "WorkSheetDesign", "addWorksheetDesign", activityDescription);
        }
        return new WorksheetDesignPostOutput("success", worksheet.getDbid(), worksheet.getVersion());

//		*/
    }

    @GetMapping("/specificworksheet/{id}")
    public WorksheetDesignStepGetOutput getSpecificWorksheet(@PathVariable(value = "id") Long inputDesignId) {

        WorksheetDesign worksheet = worksheetDesignRepo.findByDbid(inputDesignId);

        WorksheetDesignStepGetOutput output = new WorksheetDesignStepGetOutput();
        if (worksheet == null) {
            output.setMessage("failure: invalid id");
            return output;
        }

        Set<WorksheetStep> wsStepSet = worksheet.getWorksheetSteps();

        List<WorksheetStep> wsStepList = new ArrayList<>(wsStepSet);

        if (wsStepList != null) {
            Collections.sort(wsStepList, new WorksheetStepSort());
        }

        for (WorksheetStep wsStep : wsStepList) {

            //Getting Equipments
            List<EquipmentV2> eqList = wsStep.getEquipments();
            for (EquipmentV2 eq : eqList) {
                //Do nothing, this block is required to deserialize JSON
            }

            List<IncubationDesignSubStep> incubationList = getIncubationList(wsStep);
            if (incubationList != null) {
                Collections.sort(incubationList, new IncubationDesignSubStepSort());
            }
            if (incubationList != null) {
                for (IncubationDesignSubStep incubation : incubationList) {
                    System.out.println("At least one incubation found");
                    //Do nothing, this block is required to deserialize JSON
                }

            }

            List<MaterialDesignSubStep> substepList = getSubStepList(wsStep);
            if (substepList != null) {
                Collections.sort(substepList, new MaterialDesignSubStepSort());
            }
            if (substepList != null) {
                for (MaterialDesignSubStep substep : substepList) {
                    //Do nothing, this block is required to deserialize JSON
                    List<DilutionDesignSubStep> dilutionSubStepList = substep.getSubStepArray();
                    if (dilutionSubStepList != null) {
                        Collections.sort(dilutionSubStepList, new DilutionDesignSubStepSort());
                    }
                    for (DilutionDesignSubStep dilutionSubStep : dilutionSubStepList) {
                        //Do nothing, this block is required to deserialize JSON
                    }
                }
            }

        }

        // Getting Plate Maps
        List<PlateMap> plateMapList = worksheet.getPlateMap();
        if (plateMapList != null) {
            for (PlateMap plateMap : plateMapList) {
                // Do nothing, this block is required to deserialize JSON
            }
        }

        output.setWsNum(worksheet.getWsNum());
        output.setExeNum(worksheet.getExeNum());
        output.setCreationDate(worksheet.getDateCreated());
        output.setExObjective(worksheet.getExObjective());
        output.setExType(worksheet.getExType());
        output.setFiles(plateMapList);
        output.setFinalizationDate(worksheet.getDateFinalized());
        output.setFinalized(worksheet.getFinalized());
        output.setMessage("success");
        output.setParentId(worksheet.getWorksheetParent() != null ? worksheet.getWorksheetParent().getDbid() : null);
        output.setSaveDate(worksheet.getDateSaved());
        output.setScientist(worksheet.getScientist());
        output.setSteps(wsStepList);
        output.setStudyNum(worksheet.getStudyNum());
        output.setTemplateName(worksheet.getTemplateName());
        output.setUserId(worksheet.getUser() != null ? worksheet.getUser().getUserId() : null);
        output.setVersion(worksheet.getVersion());
        output.setDesignComment(worksheet.getDesignComment());

        return output;
    }

    private List<IncubationDesignSubStep> getIncubationList(WorksheetStep wsStep) {

        List<IncubationDesignSubStep> incubationList = null;

        if (wsStep == null || wsStep.getType() == null) {
            return null;
        }

        if (wsStep.getType().equalsIgnoreCase("blocking")) {
            BlockingStep blocking = (BlockingStep) wsStep;
            incubationList = blocking.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("capture")) {
            CaptureStep capture = (CaptureStep) wsStep;
            incubationList = capture.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("coating")) {
            CoatingStep coating = (CoatingStep) wsStep;
            incubationList = coating.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("detection")) {
            DetectionStep detection = (DetectionStep) wsStep;
            incubationList = detection.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("substrate")) {
            SubstrateStep substrate = (SubstrateStep) wsStep;
            incubationList = substrate.getIncubationArray();
        } else if (wsStep.getType().equalsIgnoreCase("test")) {
            TestStep test = (TestStep) wsStep;
            incubationList = test.getIncubationArray();
        }

        return incubationList;
    }

    private List<MaterialDesignSubStep> getSubStepList(WorksheetStep wsStep) {

        List<MaterialDesignSubStep> substepList = null;

        if (wsStep == null || wsStep.getType() == null) {
            return null;
        }
        if (wsStep.getType().equalsIgnoreCase("capture")) {
            CaptureStep capture = (CaptureStep) wsStep;
            substepList = capture.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("coating")) {
            CoatingStep coating = (CoatingStep) wsStep;
            substepList = coating.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("detection")) {
            DetectionStep detection = (DetectionStep) wsStep;
            substepList = detection.getSubStepArray();
        } else if (wsStep.getType().equalsIgnoreCase("test")) {
            TestStep test = (TestStep) wsStep;
            substepList = test.getSubStepArray();
        }

        return substepList;
    }

    @GetMapping("/summary")
    public WorksheetDesignGetOutput getWorksheetSummaries() {

        try {
            List<WorksheetSummary> summaries = worksheetDesignRepo.findWorksheetSummaries();
            return new WorksheetDesignGetOutput("success", summaries);

        } catch (Exception e) {
            return new WorksheetDesignGetOutput("failure", null);

        }

    }

    @Transactional
    @PostMapping("/assignAnalyst")
    public boolean assignAnalyst(@RequestBody AssignAnalystInput assignAnalystInput) {

        LOGGER.info(String.format("assignAnalyst received following input: %s", assignAnalystInput));

        if (assignAnalystInput == null) {
            return false;
        }

        List<UserV2> userList = userRepo.findAllByUserIdIn(assignAnalystInput.getAnalysts());

        LOGGER.info(String.format("analyst lists from users table: %s", userList));

        WorksheetDesign worksheetDesign = worksheetDesignRepo.findByDbid(assignAnalystInput.getTemplateId());

        LOGGER.info(String.format("template lists from worksheetDesign table: %s", worksheetDesign));
        StringBuilder analystText = new StringBuilder("");
        for (UserV2 user : userList) {
            analystText.append(user.getName()).append(",");
            AnalystWorksheetDesign analystWorksheetDesign = new AnalystWorksheetDesign();
            analystWorksheetDesign.setAnalyst(user);
            analystWorksheetDesign.setWorksheetDesign(worksheetDesign);
            analystWorksheetDesignRepo.save(analystWorksheetDesign);
        }
        // remove the final comma in the list of users i.e. peter,wayne,nim,
        if ( analystText.length() > 0 ) {
            analystText.deleteCharAt(analystText.length() - 1); 
        }
        LOGGER.info("analystId and templateId saved successfully to the datbase");
        String activityDescription = String.format(
                        "%s assigned a worksheet design - id: %d to - %s",
                        worksheetDesign.getScientist(), worksheetDesign.getDbid(), analystText.toString());
        super.saveUserHistory(worksheetDesign.getScientist(), "WorkSheet", "assignAnalyst",activityDescription);
        return true;

    }

    @GetMapping("/getAssignedTemplateDesigns")
    public AssignAnalystOutput getAssignedTempaleDesigns(@RequestParam long analystId) {

        LOGGER.info(String.format("getAssignedTemplateDesigns received analystId: %s", analystId));

        AssignAnalystOutput assignAnalystOutput = new AssignAnalystOutput();

        List<Assignment> assignmentList = new ArrayList<Assignment>();

        UserV2 user = userRepo.findByUserId(analystId);

        LOGGER.info(String.format("get user by analystId: %s", user));

        List<AnalystWorksheetDesign> analystWorksheetDesignList = analystWorksheetDesignRepo.findByAnalyst(user);

        LOGGER.info(String.format("List of assigned templates: %s", user));

        for (AnalystWorksheetDesign analystWorksheetDesign : analystWorksheetDesignList) {

            Assignment assignment = new Assignment();

            WorksheetDesign worksheetDesign = worksheetDesignRepo.findByDbid(analystWorksheetDesign.getWorksheetDesign().getDbid());

            assignment.setWsNum(worksheetDesign.getWsNum());
            assignment.setExeNum(worksheetDesign.getExeNum());
            assignment.setTemplateId(worksheetDesign.getDbid());
            assignment.setTemplateName(worksheetDesign.getTemplateName());
            assignment.setStudyNum(worksheetDesign.getStudyNum());
            assignment.setScientist(worksheetDesign.getScientist());
            assignment.setExType(worksheetDesign.getExType());
            assignment.setExObjective(worksheetDesign.getExObjective());
            assignment.setAssignDate(analystWorksheetDesign.getAssignDate().getTime());

            assignmentList.add(assignment);
        }

        assignAnalystOutput.setMessage("Success");
        assignAnalystOutput.setAssignments(assignmentList);

        LOGGER.info(String.format("All assignments: %s", assignAnalystOutput));

        return assignAnalystOutput;

    }

    @Transactional
    @PutMapping("/exeNum/update/{id}")
    public Map<String, String> updateWorksheetExeNum(@PathVariable(value = "id") Long inputDesignId,
            @Valid @RequestBody WorksheetDesignExeNumPostInput exeNumInput) {

        if (inputDesignId == null || inputDesignId < 1) {
            return Collections.singletonMap("message", "failure: invalid id for ws_design_worksheet");
        }

        WorksheetDesign wsDesign = worksheetDesignRepo.findByDbid(inputDesignId);

        if (wsDesign == null) {
            return Collections.singletonMap("message", "failure: invalid id for ws_design_worksheet");
        }

        try {
            wsDesign.setExeNum(exeNumInput.getExeNum());
            worksheetDesignRepo.save(wsDesign);
        } catch (Exception ex) {
            return Collections.singletonMap("message", "failure: could not save provided value of exeNum");
        }

        return Collections.singletonMap("message", "success");
    }
    
    

}

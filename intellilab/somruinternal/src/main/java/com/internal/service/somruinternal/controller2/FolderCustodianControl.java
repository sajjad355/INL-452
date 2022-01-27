package com.internal.service.somruinternal.controller2;

import java.util.ArrayList;

import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.internal.service.somruinternal.dto.FolderInput;
import com.internal.service.somruinternal.dto.FolderLocationInput;
import com.internal.service.somruinternal.dto.FolderTransferInput;
import com.internal.service.somruinternal.error.EntityNotFoundException;
import com.internal.service.somruinternal.model2.ClientCompanyV2;
import com.internal.service.somruinternal.model2.Folder;
import com.internal.service.somruinternal.model2.FolderCustodianTransfer;
import com.internal.service.somruinternal.model2.FolderLocation;
import com.internal.service.somruinternal.model2.UserV2;
import com.internal.service.somruinternal.repository2.FolderCustodianTransferRepository;
import com.internal.service.somruinternal.repository2.FolderLocationRepository;
import com.internal.service.somruinternal.repository2.FolderLocationSpecification;
import com.internal.service.somruinternal.repository2.FolderRepository;
import com.internal.service.somruinternal.repository2.FolderSpecification;
import com.internal.service.somruinternal.repository2.UserRepositoryV2;
import com.internal.service.somruinternal.utils.SendEmailUtil;
import com.internal.service.somruinternal.dto.EmailDetail;


@RestController
@RequestMapping("/foldercustodian")
public class FolderCustodianControl extends ParentControl  {

    private final static Logger LOGGER = LoggerFactory.getLogger(FolderCustodianControl.class);

    @Autowired
    FolderLocationRepository folderLocationRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    UserRepositoryV2 userRepository;

    @Autowired
    FolderCustodianTransferRepository transferRepository;
    
    @Autowired
    SendEmailUtil sendEmailUtil;
    
    
    private FolderLocation loadFolderLocation( Long id ) {
    	FolderLocation folderLocation = folderLocationRepository.findByDbid(id);
        if (folderLocation == null) {
            LOGGER.info( "folderLocation not found");
            throw new EntityNotFoundException(ClientCompanyV2.class, "id", id.toString());
        }
        Hibernate.initialize(folderLocation.getEmployee());
        Hibernate.initialize(folderLocation.getProjectManager());
        Hibernate.initialize(folderLocation.getFolderList());
        return folderLocation;
    }

    @PostMapping("/addFolderLocation")
    public FolderLocation addFolderLocation(@RequestBody FolderLocation folderLocation) {
        LOGGER.info(String.format( "addFolderLocation recevied request - details: %s", folderLocation));
//        FolderLocation location;
//        if (locationInput.getDbid() != null && locationInput.getDbid() > 0) {
//            LOGGER.info( "Upcate case for existing folder");
//            location = folderLocationRepository.findByDbid(locationInput.getDbid());
//        } else {
//            LOGGER.info( "Inser case for new folder");
//            location = new FolderLocation();
//        }
//
//        location.setTitle(locationInput.getTitle());
//        location.setActive(locationInput.isActive());
//
//        UserV2 employee = null;
//        if (locationInput.getEmployee() != null) {
//            employee = userRepository.findByUserId(locationInput.getEmployee().getUserId());
//        }
//        location.setEmployee(employee);
//
//        UserV2 projectManager = null;
//        if (locationInput.getProjectManager() != null) {
//            projectManager = userRepository.findByUserId(locationInput.getProjectManager().getUserId());
//        }
//        location.setProjectManager(projectManager);
        String action;
        String auditTrailRecord;
        
        if ( folderLocation.getDbid() > 0 ) {
        	FolderLocation previousFolderLocationDetails = this.loadFolderLocation(folderLocation.getDbid());
            action = "update Folder Location";
            auditTrailRecord = getDiffs(folderLocation, previousFolderLocationDetails);
        }
        else {
            action = "insert Folder Location";
            auditTrailRecord = folderLocation.toString();
        }
        
        folderLocation = folderLocationRepository.save(folderLocation);
        super.saveUserHistory(folderLocation.getTitle(), "Folder Location", action, auditTrailRecord);  
        LOGGER.info( "Succesfully saved folder location");
        return folderLocation;
    }
    
    private String getDiffs(FolderLocation lhs, FolderLocation rhs ) {
        if ( (lhs == null) || (rhs == null) )
            throw new RuntimeException("Error: Trying to compare two FolderLocation objects and at least one is null");
        String diffs = lhs.diffCompare(rhs);
        LOGGER.info(String.format( "FolderLocation GET DIFFS RESULT: %s", diffs));
        return diffs;
        
    }

    

    @PostMapping("/addFolder")
    public List<Folder> addFolder(@RequestBody FolderInput folderInput) {
        LOGGER.info(String.format( "addFolder recevied request - details: %s", folderInput));
        UserV2 custodian = null;
        UserV2 projectManager = null;
        UserV2 creator = null;
        FolderLocation folderLocation = null;

        if (folderInput.getCustodian() != null) {
            custodian = userRepository.findByUserId(folderInput.getCustodian().getUserId());
        }
        if (folderInput.getProjectManager() != null) {
            projectManager = userRepository.findByUserId(folderInput.getProjectManager().getUserId());
        }
        if (folderInput.getCreatedBy() != null) {
            creator = userRepository.findByUserId(folderInput.getCreatedBy().getUserId());
        }
        if (folderInput.getLocation() != null) {
            folderLocation = folderLocationRepository.findByDbid(folderInput.getLocation().getDbid());
        }

        List<Folder> folderList = new ArrayList<>();

        int numOfFolders = 1;
        if (folderInput.getNumberOfFolder() != null && folderInput.getNumberOfFolder() > 1) {
            numOfFolders = folderInput.getNumberOfFolder();
        }

        for (int i = 0; i < numOfFolders; i++) {
            Folder folder = null;
            if (folderInput.getDbid() != null && folderInput.getDbid() > 0) {
                folder = folderRepository.findByDbid(folderInput.getDbid());
            } else {
                folder = new Folder();
            }
            folder.setCustodian(custodian);
            folder.setLocation(folderLocation);
            folder.setProjectManager(projectManager);
            folder.setCreatedBy(creator);
            folder.setStudyNumber(folderInput.getStudyNumber());
            folder.setActive(folderInput.isActive());
            folder.setRemarks(folderInput.getRemarks());
            if (numOfFolders <= 1) {
                folder.setDbid(folderInput.getDbid());
                folder.setTitle(folderInput.getTitle());
                folderList.add(folderRepository.save(folder));
            } else {
                folder.setTitle(folderInput.getTitle() + " - " + (i + 1));
                folderList.add(folderRepository.save(folder));
            }

        }
        LOGGER.info( "Folder add successfully");
        return folderList;
    }


    @GetMapping("/getFolderLocationList")
    public List<FolderLocation> getFolderLocationList() {
        LOGGER.info("getFolderLocationList received request");
        List<FolderLocation> folderList = folderLocationRepository.findAll();
        LOGGER.info(String.format("return %d folders", folderList.size()));
        return folderList;
    }

    @GetMapping("/getFolderList/{page}")
    public List<Folder> getFolderList(@PathVariable(value = "page") int page, HttpServletResponse response) {
        LOGGER.info("getFolderList received request");
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("modificationTime").descending());
        Page<Folder> folderList = folderRepository.findAll(pageable);
        response.addHeader("numberOfPages", String.valueOf(folderList.getTotalPages()));
        return folderList.getContent();
    }

    @GetMapping("/getFolderCount")
    public Long getFolderCount() {
        LOGGER.info("getFolderCount received request");
        Long folderCount = folderRepository.count();
        LOGGER.info( String.format( "getFolderCount # of folders %d", folderCount ));
        return folderCount;
    }

    @GetMapping("/openSearchFolder")
    public List<Folder> searchFolder(@RequestParam String searchKey) {
        LOGGER.info(String.format("searchFolder received request with searchKey:%s", searchKey));
        List<Folder> folderList = new ArrayList<>();
        folderList.addAll(folderRepository.findByTitleContaining(searchKey));
        List<UserV2> users = userRepository.searchByNameOrEmail(searchKey);
        if (!users.isEmpty()) {
            folderList.addAll(folderRepository.findByCustodianIn(users));
            folderList.addAll(folderRepository.findByProjectManagerIn(users));
        }
        List<FolderLocation> folderLocationList = folderLocationRepository.findByTitleContaining(searchKey);
        if (!folderLocationList.isEmpty()) {
            folderList.addAll(folderRepository.findByLocationIn(folderLocationList));
        }
        LOGGER.info(String.format("searchFolder number results :%d", folderList.size()));
        return folderList;
    }

    @GetMapping("/searchFolderByParam")
    public List<Folder> searchFolderByParam(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean isActive, 
            @RequestParam(required = false) String studyNumber,
            @RequestParam(required = false) String title, 
            @RequestParam(required = false) Long custodianId,
            @RequestParam(required = false) String barcode) {
        
        String logEntry = "searchFolderByParam received request with params: id = %d, isActive = %b, studyNumber = %s, title = %s, custodianId = %d, barcode = %s";
        LOGGER.info(String.format(logEntry, id, isActive, studyNumber, title, custodianId, barcode));
        Specification<Folder> folderSearchSpec = FolderSpecification.getSpecification(id, isActive, studyNumber, title,
                custodianId, barcode);
        List<Folder> folderList = folderRepository.findAll(folderSearchSpec);
        LOGGER.info( "searchFolderByParam returned number of results: %d", folderList.size() );
        return folderList;
    }

    @GetMapping("/searchFolderLocationByParam")
    public List<FolderLocation> searchFolderLocationByParam(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Boolean isActive, 
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String barcode) {
        
        String logEntry = "searchFolderLocationByParam received request with params: id = %d, isActive = %b, title = %s, barcode = %s";
        LOGGER.info(String.format(logEntry, id, isActive, title,  barcode));
        Specification<FolderLocation> folderSearchSpec = FolderLocationSpecification.getSpecification(id, isActive,
                title, barcode);
        List<FolderLocation> folderLocationList = folderLocationRepository.findAll(folderSearchSpec);
        LOGGER.info( "searchFolderLocationByParam returned %d number of results", folderLocationList.size());
        return folderLocationList;
    }

    @PostMapping("/findFolderBy") // End-point not used
    public List<Folder> findFolders(@RequestBody FolderInput folderInput) {
        LOGGER.info( String.format( "findFolders received request with folderInput = %s", folderInput));
        UserV2 custodian = null;
        UserV2 projectManager = null;
        FolderLocation folderLocation = null;

        if (folderInput.getCustodian() != null) {
            custodian = userRepository.findByUserId(folderInput.getCustodian().getUserId());
        }
        if (folderInput.getProjectManager() != null) {
            projectManager = userRepository.findByUserId(folderInput.getProjectManager().getUserId());
        }
        if (folderInput.getLocation() != null) {
            folderLocation = folderLocationRepository.findByDbid(folderInput.getLocation().getDbid());
        }

        Folder folder = new Folder();
        folder.setTitle(folderInput.getTitle());
        folder.setCustodian(custodian);
        folder.setDbid(folderInput.getDbid());
        folder.setLocation(folderLocation);
        folder.setProjectManager(projectManager);
        folder.setStudyNumber(folderInput.getStudyNumber());

        return folderRepository.findAll(Example.of(folder));
    }

    @PostMapping("/requestTransfer")
    public boolean transferFolder(@RequestBody FolderTransferInput transferInput) {

        
        LOGGER.info( String.format( "transferFolder received a request with following input: %s", transferInput));
        FolderCustodianTransfer transferRecord = null;
        if (transferInput.getDbid() != null && transferInput.getDbid() > 0) {
            transferRecord = transferRepository.findByDbid(transferInput.getDbid());
        }
        if (transferRecord == null) {
            transferRecord = new FolderCustodianTransfer();
        }

        Folder folder = folderRepository.findByDbid(transferInput.getFolder().getDbid());
        UserV2 receiver = userRepository.findByUserId(transferInput.getReceiver().getUserId());
        UserV2 sender = userRepository.findByUserId(transferInput.getSender().getUserId());
        if (transferInput.getRequestStatus() != null
                && transferInput.getRequestStatus().trim().equalsIgnoreCase("acknowledged")) {
            folder.setCustodian(sender);
            folderRepository.save(folder);
        }
        
        transferRecord.setDbid(transferInput.getDbid());
        transferRecord.setFolder(folder);
        transferRecord.setNote(transferInput.getNote());
        transferRecord.setReceiver(receiver);
        transferRecord.setRequestStatus(transferInput.getRequestStatus());
        transferRecord.setSender(sender);
        transferRepository.save(transferRecord);

        sendTransferEmail( folder.getTitle(), sender, receiver, transferInput.getNote(), transferInput.getRequestStatus() ); 
       
        return true;
    }
    
    private void sendTransferEmail(
             String folderName, 
             UserV2 sender, 
             UserV2 receiver, 
             String transferNote,
             String requestStatus ) {
        
        if ( requestStatus == null ) return;
        String[] emailDistList = { receiver.getEmail(), sender.getEmail() };
        String[] ccDistList = {}; // empty
        
        
        String emailSubject = "";
        String emailBody = "";
        if (requestStatus.trim().equalsIgnoreCase("acknowledged")) {
            emailSubject = String.format( "'%s' has acknowledged receiving folder '%s' from '%s'", 
              sender.getName(), folderName, receiver.getName() ); 
            emailBody = String.format( "User '%s' has acknowledged the transfer of folder '%s' from '%s'", 
              sender.getName(), folderName, receiver.getName() ); 
        }
        else if (requestStatus.trim().equalsIgnoreCase("requested")) {
            emailSubject = String.format( "Request to transfer folder '%s' by '%s'", 
                     folderName, sender.getName() ); 
            emailBody = String.format( "User '%s' has requested a transfer of folder '%s' with the following reason '%s'", 
                     sender.getName(), folderName, transferNote);  
        }
        else if (requestStatus.trim().equalsIgnoreCase("accepted")) {
            emailSubject = String.format( "Request to transfer folder '%s' by '%s' has been accepted", 
              folderName, sender.getName()); 
            emailBody = String.format( "User '%s' has accepted the transfer of folder '%s' as requested", 
              sender.getName(), folderName); 
        }
        else if (requestStatus.trim().equalsIgnoreCase("rejected")) {
            emailSubject = String.format( "Request to transfer folder '%s' rejected by '%s'", 
                folderName, sender.getName()); 
            emailBody = String.format( "User '%s' has rejected the request to transfer folder '%s'", 
                sender.getName(), folderName );  
        }
        
        LOGGER.info( emailBody );

        EmailDetail ed = new EmailDetail(
                 emailDistList,
                 ccDistList, 
                 emailSubject,
                 emailBody,
                 "text/html"
            );
        try {
            sendEmailUtil.sendEmail(ed);
        }
        catch ( Exception e) {
            LOGGER.error( "Folder transfer request email sending failed", e);
        }
    }
    
    @GetMapping("/getTransferList/{userid}")
    public List<FolderCustodianTransfer> getTransferList(
            @PathVariable(value = "userid") long userid,
            HttpServletResponse response) {
        LOGGER.info(String.format( "getTransferList received request for userId: %s", userid ));
        List<FolderCustodianTransfer> transferList =  
            transferRepository.getFolderTransfersBySender(userid);
        LOGGER.info( String.format( "Returned %d rows for getTransferList", transferList.size() ));
        return transferList;
    }
    
    
    @GetMapping("/getTransferListPageable/{userid}/{requestStatus}/{page}")
    public List<FolderCustodianTransfer> getTransferListPageable(
            @PathVariable(value = "userid") long userid,
            @PathVariable(value = "requestStatus") String requestStatus,
            @PathVariable(value = "page") int page, 
            HttpServletResponse response) {
        LOGGER.info(String.format( "getTransferListPageable received request for userId: %s and requestStatus %s", 
                userid, requestStatus ));
        List<FolderCustodianTransfer> transferList = new ArrayList();
        PageRequest pageable = PageRequest.of(page, 10, Sort.by("lastModified").descending());
        if ( "accepted".equalsIgnoreCase(requestStatus) ) {
            transferList = transferRepository.getMyAcceptedFolderTransfersPageable(userid, pageable);
        }
        else if ( "requested".equalsIgnoreCase(requestStatus)) {
            transferList = transferRepository.getMyRequestedFolderTransfersPageable(userid, pageable);
        }
        response.addHeader("numberOfPages", String.valueOf(transferList.size()));
        return transferList;
    }
    
    
    @GetMapping("/getTransferListAll/{userid}/{requestStatus}")
    public List<FolderCustodianTransfer> getTransferListAll(
            @PathVariable(value = "userid") long userid,
            @PathVariable(value = "requestStatus") String requestStatus,
            HttpServletResponse response) {
        LOGGER.info(String.format( "getTransferListAll received request for userId: %s and requestStatus %s", 
                userid, requestStatus ));
        List<FolderCustodianTransfer> transferList = new ArrayList();
        if ( "accepted".equalsIgnoreCase(requestStatus) ) {
            transferList = transferRepository.getMyAcceptedFolderTransfersAll(userid);
        }
        else if ( "requested".equalsIgnoreCase(requestStatus)) {
            transferList = transferRepository.getMyRequestedFolderTransfersAll(userid);
        }
        return transferList;
    }
}

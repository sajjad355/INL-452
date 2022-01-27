package com.internal.service.somruinternal.controller;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import static org.springframework.data.domain.ExampleMatcher.matching;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.internal.service.somruinternal.model.*;
import com.internal.service.somruinternal.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/egg")
public class EggControl {

    @Autowired
    private EggRepository eggRepo;
    
//    @Autowired
//    private UserHistoryRepository userHistoryRepo;
//    
    @Autowired
    private ChickenRepository chickenRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(EggControl.class);
    

    @GetMapping("/all")
    public List<Egg> getAllEggs() {
        LOGGER.info( "getAllEggs received request");
        List<Egg> eggList = eggRepo.findAll();
        LOGGER.info( String.format( "retrieve %d eggs", eggList.size()));
        return eggList;
    }

    @PostMapping("/add")
    public Egg createEgg(@Valid @RequestBody Egg egg) {
        LOGGER.info( String.format( "createEgg received request - egg details: %s",  egg));
        Egg savedEgg =  eggRepo.save(egg);
//        UserHistory userHistory =new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                egg.getEditby(), 
//                "Egg Inventory", 
//                "Add Eggs to a Chicken", 
//                savedEgg.toString()
//        );
//        userHistoryRepo.save(userHistory);
        LOGGER.info( "Completed createEgg");
        return savedEgg;
    }

    @GetMapping("/getIEggByChickenId/{id}")
    public List<Egg> getEggByChickenId(@PathVariable(value = "id") String chickenId) {
        LOGGER.info( String.format( "getEggByChickenId received request for chikenId: %s",  chickenId));
        Example<Egg> example = Example.of(new Egg((long) -1, chickenId, "", "", null, null, "", null, null, "", "", "", null, "", "", "", null), matching(). //
                withIgnorePaths("dbid", "immunogen", "eggpart", "collectiondate", "firstinjectdate", "daysafterimmune", "firstlayeggdate", "lastlayeggdate", "amount", "location", "sublocation", "frozendate", "addsucrose", "titer", "editby", "modify"));
        List<Egg> eggList = eggRepo.findAll(example);
        LOGGER.info( String.format( "retrieve %d eggs", eggList.size()));
        return eggList;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Egg> getEggBydbid(
            @PathVariable(value = "id") Long eggDbid) throws EntityNotFoundException {
        LOGGER.info( String.format("getEggBydbid received request with eggDbid %d", eggDbid)); 
        if ( eggDbid == null ) throw new EntityNotFoundException(EggControl.class, "id", "");
        Egg egg = eggRepo.findByDbid(eggDbid);
        if (egg == null) {
            LOGGER.info( "egg not found");
            throw new EntityNotFoundException(EggControl.class, "id", eggDbid.toString());
        }
        LOGGER.info( String.format( "Found egg - details: %s", egg) );
        return ResponseEntity.ok().body(egg);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Egg> updateEgg(
            @PathVariable(value = "id") Long eggdbid,
            @Valid @RequestBody Egg eggDetail) throws EntityNotFoundException  {
        LOGGER.info( String.format("updateEgg received request with eggdbid %d and Egg %s", eggdbid, eggDetail));        
        
        Egg egg = eggRepo.findByDbid(eggdbid);
        if (egg == null) {
            LOGGER.info("egg not found!");
            throw new EntityNotFoundException(EggControl.class, "id", eggdbid.toString());
        }

        egg.setDbid(eggDetail.getDbid());
        egg.setChickenid(eggDetail.getChickenid());
        egg.setImmunogen(eggDetail.getImmunogen());
        egg.setEggpart(eggDetail.getEggpart());
        egg.setCollectiondate(eggDetail.getCollectiondate());
        egg.setFirstinjectdate(eggDetail.getFirstinjectdate());
        egg.setDaysafterimmune(eggDetail.getDaysafterimmune());
        egg.setFirstlayeggdate(eggDetail.getFirstlayeggdate());
        egg.setLastlayeggdate(eggDetail.getLastlayeggdate());
        egg.setAmount(eggDetail.getAmount());
        egg.setLocation(eggDetail.getLocation());
        egg.setSublocation(eggDetail.getSublocation());
        egg.setFrozendate(eggDetail.getFrozendate());
        egg.setAddsucrose(eggDetail.getAddsucrose());
        egg.setTiter(eggDetail.getTiter());
        egg.setEditby(eggDetail.getEditby());
        egg.setModify(eggDetail.getModify());

        Egg updateEgg = eggRepo.save(egg);
//        UserHistory userHistory =new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                egg.getEditby(), 
//                "Egg Inventory", 
//                "Update Egg", 
//                updateEgg.toString()
//        );
//        userHistoryRepo.save(userHistory);
        LOGGER.info("Successfully update egg");
        return ResponseEntity.ok(updateEgg);
    }

    @DeleteMapping("/delete/{id}/{username}")
    public ResponseEntity<Egg> deleteEgg(
            @PathVariable(value = "id") Long eggDbid,
            @PathVariable(value = "username") String username) throws EntityNotFoundException {
        LOGGER.info( String.format("deleteEgg received request with eggDbid %d ", eggDbid)); 
        if ( eggDbid == null ) throw new EntityNotFoundException(EggControl.class, "id", "");
        Egg egg = eggRepo.findByDbid(eggDbid);
        if (egg == null) {
            LOGGER.info( "egg not found!");
            throw new EntityNotFoundException(EggControl.class, "id", eggDbid.toString());
        }
        List<Chicken> chickenList = chickenRepo.searchChickenByChickenId( egg.getChickenid()  );
        if ( chickenList.size() == 1 ) {
            Chicken eggsParent = chickenList.get(0);
            String totalEggs = eggsParent.getTotalegg();
            if ( totalEggs != null && egg.getAmount() != null ) {
                int remainingEggs = Integer.parseInt(totalEggs) - Integer.parseInt(egg.getAmount());
                eggsParent.setTotalegg( String.valueOf(remainingEggs));
                chickenRepo.save(eggsParent);
                LOGGER.info( String.format( "Updated chicken so remaining # of eggs = %d", remainingEggs ));
            }
        }
        else {
            LOGGER.info("No parent chicken was found in order to reduce # remaining eggs");
        }
        
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                username, 
//                "Egg Inventory", 
//                "Delete Egg", 
//                egg.toString()
//        );
//        userHistoryRepo.save(userHistory);
        eggRepo.delete(egg);
        LOGGER.info("Successfully deleted egg");
        return ResponseEntity.ok().build();
    }
}

package com.internal.service.somruinternal.controller;

import com.internal.service.somruinternal.error.EntityNotFoundException;
import static org.springframework.data.domain.ExampleMatcher.matching;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import com.internal.service.somruinternal.dto.ChickenWEggs;
import com.internal.service.somruinternal.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/chicken")
public class ChickenControl {

    @Autowired
    ChickenRepository chickenRepo;
    
    @Autowired
    EggRepository eggRepo;
    
    @Autowired
    InjectionRepository injectionRepo;
    
//    @Autowired 
//    UserHistoryRepository userHistoryRepo;
    
    private final static Logger LOGGER = LoggerFactory.getLogger(ChickenControl.class);
    

    @GetMapping("/all")
    public List<Chicken> getAllChickens() {
        return chickenRepo.findAll();
    }

    @GetMapping("/count")
    public int getCount() {
        return chickenRepo.findAll().size();
    }

    @GetMapping("/allByPage/{page}")
    public List<Chicken> getAllChickens(@PathVariable(value = "page") int page) {
    	PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return chickenRepo.findAll(pageable).getContent();
    }

    @GetMapping("/allWEggsByPage/{page}")
    public List<ChickenWEggs> getAllChickenWEgg(@PathVariable(value = "page") int page) {
    	
    	PageRequest pageable = PageRequest.of(page, 10, Sort.by("dbid").descending());

    	//TODO need to check with the split logic of old code
//        List<Chicken> chicken = chickenRepo.findAll(new PageRequest(page, 10, Sort.Direction.DESC, "sequence".split("-")[0])).getContent();
        List<Chicken> chicken = chickenRepo.findAll(pageable).getContent();

        List<ChickenWEggs> result = new ArrayList();
        int i = 0;
        for (i = 0; i < chicken.size(); i++) {
            String chickenid = chicken.get(i).getChickenid();
            result.add(new ChickenWEggs(chicken.get(i), eggRepo.loadEggsWChickenId(chickenid)));
        }

        return result;
    }

    @GetMapping("/get/{dbid}")
    public ResponseEntity<Chicken> getChickenByDbid(@PathVariable(value = "dbid") Long dbid) {
        Chicken chicken = chickenRepo.findByDbid(dbid);
        if (chicken == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(chicken);
    }

    @GetMapping("/getChickenByChickenId/{id}")
    public Chicken getChickenByChickenId(@PathVariable(value = "id") String chickenid) {
        Example<Chicken> example = Example.of(new Chicken((long) -1, chickenid, new Date(), "", "", "", "", "", "", "", new Date(), "", new Date(), (double) 1.1, ""), matching(). //
                withIgnorePaths("dbid", "dateofbirth", "immunogen", "projectname", "chickenstatus", "totalegg", "eggused", "eggdiscard", "latesttiter", "titerdate", "editby", "modify", "sequence", "immunstatus"));
        return chickenRepo.findAll(example, Sort.by("dbid").descending()).get(0);
    }

    @GetMapping("/getChickenByImmunogen/{immunogen}")
    public List<Chicken> getChickenByImmunogen(@PathVariable(value = "immunogen") String immunogen) {
        Example<Chicken> example = Example.of(new Chicken((long) -1, "", new Date(), immunogen, "", "", "", "", "", "", new Date(), "", new Date(), (double) 1.1, ""), matching(). //
                withIgnorePaths("dbid", "chickenid", "dateofbirth", "projectname", "chickenstatus", "totalegg", "eggused", "eggdiscard", "latesttiter", "titerdate", "editby", "modify", "sequence", "immunstatus"));
        return chickenRepo.findAll(example);
    }

    @GetMapping("/searchChickenById/{searchKey}/{page}")
    public List<ChickenWEggs> searchChickenById(@PathVariable(value = "searchKey") String searchKey, @PathVariable(value = "page") int page) {
    	PageRequest pageable = PageRequest.of(page, 10, Sort.by("id").descending());

    	List<Chicken> chicken = chickenRepo.searchChickenByChickenId(searchKey, pageable);

        List<ChickenWEggs> result = new ArrayList();
        int i = 0;
        for (i = 0; i < chicken.size(); i++) {
            String chickenid = chicken.get(i).getChickenid();
            result.add(new ChickenWEggs(chicken.get(i), eggRepo.loadEggsWChickenId(chickenid)));
        }
        return result;
    }

    @GetMapping("/searchChickenByIdCount/{searchkey}/")
    public Long searchChickenByIdCount(@PathVariable(value = "searchkey") String searchkey) {
        return chickenRepo.searchChickenCount(searchkey);
    }

    @PostMapping("/add")
    public Chicken createChicken(@Valid @RequestBody Chicken chicken) {
        LOGGER.info( String.format( "createChicken received request with following data: %s", chicken));
        Chicken savedChicken = chickenRepo.save(chicken);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                savedChicken.getEditby(), 
//                "Chicken", 
//                "Create Chicken", 
//                savedChicken.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return chicken;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Chicken> updateChicken(@PathVariable(value = "id") Long Chickendbid,
            @Valid @RequestBody Chicken chickenDetail) {
        Chicken chicken = chickenRepo.findByDbid(Chickendbid);
        if (chicken == null) {
            return ResponseEntity.notFound().build();
        }

        chicken.setDbid(chickenDetail.getDbid());
        chicken.setChickenid(chickenDetail.getChickenid());
        chicken.setDateofbirth(chickenDetail.getDateofbirth());
        chicken.setImmunogen(chickenDetail.getImmunogen());
        chicken.setProjectname(chickenDetail.getProjectname());
        chicken.setChickenstatus(chickenDetail.getChickenstatus());
        chicken.setTotalegg(chickenDetail.getTotalegg());
        chicken.setEggused(chickenDetail.getEggused());
        chicken.setEggdiscard(chickenDetail.getEggdiscard());
        chicken.setLatesttiter(chickenDetail.getLatesttiter());
        chicken.setTiterdate(chickenDetail.getTiterdate());
        chicken.setEditby(chickenDetail.getEditby());
        chicken.setModify(chickenDetail.getModify());
        chicken.setSequence(chickenDetail.getSequence());
        chicken.setImmunstatus(chickenDetail.getImmunstatus());

        Chicken updateChicken = chickenRepo.save(chicken);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                updateChicken.getEditby(), 
//                "Chicken", 
//                "Update Chicken", 
//                updateChicken.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return ResponseEntity.ok(updateChicken);
    }

    @DeleteMapping("/delete/{id}/{username}")
    public ResponseEntity<Chicken> deleteChicken(
            @PathVariable(value = "id") Long chickenDbid,
            @PathVariable(value = "username") String username) throws EntityNotFoundException {
        LOGGER.info( String.format("deleteChicken received request with eggDbid %d ", chickenDbid)); 
        if ( chickenDbid == null ) throw new EntityNotFoundException(ChickenControl.class, "id", "");
        Chicken chicken = chickenRepo.findByDbid(chickenDbid);
        if (chicken == null) {
            throw new EntityNotFoundException(ChickenControl.class, "id", chickenDbid.toString());
        }
        
        List<Injection> injectionList = injectionRepo.searchInjectionByChickenId(chicken.getChickenid());
        LOGGER.info( String.format( "Retrieved %d injections for Chicken to be deleted", injectionList.size()));
        injectionList.forEach((injection) -> {
            injectionRepo.delete(injection);
        });
        
        List<Egg> eggList = eggRepo.getAllEggsByChickenId(chicken.getChickenid());
        LOGGER.info( String.format( "Retrieved %d eggs for Chicken to be deleted", eggList.size()));
        eggList.forEach((egg) -> {
            eggRepo.delete(egg);
        });
        
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                username, 
//                "Chicken", 
//                "Delete Chicken and related eggs/injections", 
//                chicken.toString()
//        );
//        userHistoryRepo.save(userHistory);

        chickenRepo.delete(chicken);
        return ResponseEntity.ok().build();
    }
}

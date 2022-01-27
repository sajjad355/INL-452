package com.internal.service.somruinternal.controller;

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

@RestController
@RequestMapping("/injection")
public class InjectionControl {

    @Autowired
    private InjectionRepository injectionRepo;
    
//    @Autowired
//    private UserHistoryRepository userHistoryRepo;

    @GetMapping("/all")
    public List<Injection> getAllInjections() {
        return injectionRepo.findAll();
    }

    @PostMapping("/add")
    public Injection createInjection(@Valid @RequestBody Injection injection) {
        Injection savedInjection =  injectionRepo.save(injection);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                savedInjection.getEditby(), 
//                "Injection", 
//                "Create Injection", 
//                savedInjection.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return savedInjection;
    }

    @GetMapping("/getInjectionByChickenId/{id}")
    public List<Injection> getInjectionByChickenId(@PathVariable(value = "id") String chickenid) {
        Example<Injection> example = Example.of(new Injection((long) -1, chickenid, "", null, "", "", "", "", "", "", null), matching(). //
                withIgnorePaths("dbid", "adjuvant", "drugamount", "injectdate", "daysafterimmune", "unit", "bloodtiter", "complete", "editby", "modify"));
        return injectionRepo.findAll(example);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Injection> updateInjection(@PathVariable(value = "id") Long injectiondbid,
            @Valid @RequestBody Injection injectionDetail) {
        Injection injection = injectionRepo.findByDbid(injectiondbid);
        if (injection == null) {
            return ResponseEntity.notFound().build();
        }

        injection.setDbid(injectionDetail.getDbid());
        injection.setChickenid(injectionDetail.getChickenid());
        injection.setAdjuvant(injectionDetail.getAdjuvant());
        injection.setInjectdate(injectionDetail.getInjectdate());
        injection.setDaysafterimmune(injectionDetail.getDaysafterimmune());
        injection.setDrugamount(injectionDetail.getDrugamount());
        injection.setUnit(injectionDetail.getUnit());
        injection.setBloodtiter(injectionDetail.getBloodtiter());
        injection.setComplete(injectionDetail.getComplete());
        injection.setEditby(injectionDetail.getEditby());
        injection.setModify(injectionDetail.getModify());

        Injection updateInjection = injectionRepo.save(injection);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                updateInjection.getEditby(), 
//                "Injection", 
//                "Update Injection", 
//                updateInjection.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return ResponseEntity.ok(updateInjection);
    }

    @DeleteMapping("/delete/{id}/{username}/")
    public ResponseEntity<Injection> deleteInjection(
            @PathVariable(value = "id") Long injectionid,
            @PathVariable(value = "username") String username
            ) {
        Injection injection = injectionRepo.findByDbid(injectionid);
        if (injection == null) {
            return ResponseEntity.notFound().build();
        }
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                username, 
//                "Injection", 
//                "Delete Injection", 
//                injection.toString()
//        );
//        userHistoryRepo.save(userHistory);
        injectionRepo.delete(injection);
        return ResponseEntity.ok().build();
    }
}

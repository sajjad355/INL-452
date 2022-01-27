package com.internal.service.somruinternal.controller;

import static org.springframework.data.domain.ExampleMatcher.matching;

import java.util.Date;
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
@RequestMapping("/eggtransfer")
public class EggtransferControl {

    @Autowired
    EggtransferRepository eggtransferRepository;
    
//    @Autowired
//    UserHistoryRepository userHistoryRepo;

    @GetMapping("/all")
    public List<Eggtransfer> getAlleggtransfers() {
        return eggtransferRepository.findAll();
    }

    @GetMapping("/count")
    public int getCount() {
        return eggtransferRepository.findAll().size();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Eggtransfer> getEggtransferByDbid(@PathVariable(value = "id") Long eggtransferdbid) {
        Eggtransfer eggtransfer = eggtransferRepository.findByDbid(eggtransferdbid);
        if (eggtransfer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(eggtransfer);
    }

    @GetMapping("/getEggtransfersByChickenId/{id}")
    public List<Eggtransfer> getEggtransfersByChickenId(@PathVariable(value = "id") String chickenid) {
        Example<Eggtransfer> example = Example.of(new Eggtransfer((long) -1, chickenid, "", "", "", "", "", "", new Date()), matching(). //
                withIgnorePaths("dbid", "eggdbid", "action", "egguseamount", "destinationtable", "destinationdbid", "editby", "modify"));
        return eggtransferRepository.findAll(example);
    }

    @PostMapping("/add")
    public Eggtransfer createEggtransfer(@Valid @RequestBody Eggtransfer eggtransfer) {
        Eggtransfer transferredEgg =  eggtransferRepository.save(eggtransfer);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                transferredEgg.getEditby(), 
//                "Egg", 
//                "Create Transfer Egg", 
//                transferredEgg.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return transferredEgg;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Eggtransfer> updateEggtransfer(@PathVariable(value = "id") Long eggtransferdbid,
            @Valid @RequestBody Eggtransfer eggtransferDetail) {
        Eggtransfer eggtransfer = eggtransferRepository.findByDbid(eggtransferdbid);
        if (eggtransfer == null) {
            return ResponseEntity.notFound().build();
        }

        eggtransfer.setDbid(eggtransferDetail.getDbid());
        eggtransfer.setChickenid(eggtransferDetail.getChickenid());
        eggtransfer.setEggdbid(eggtransferDetail.getEggdbid());
        eggtransfer.setAction(eggtransferDetail.getAction());
        eggtransfer.setEgguseamount(eggtransferDetail.getEgguseamount());
        eggtransfer.setDestinationtable(eggtransferDetail.getDestinationtable());
        eggtransfer.setDestinationdbid(eggtransferDetail.getDestinationdbid());
        eggtransfer.setEditby(eggtransferDetail.getEditby());
        eggtransfer.setModify(eggtransferDetail.getModify());

        Eggtransfer updateEggtransfer = eggtransferRepository.save(eggtransfer);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                updateEggtransfer.getEditby(), 
//                "Egg", 
//                "Update Transfer Egg", 
//                updateEggtransfer.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return ResponseEntity.ok(updateEggtransfer);
    }

    @DeleteMapping("/delete/{id}/{username}")
    public ResponseEntity<Eggtransfer> deleteEggtransfer(
          @PathVariable(value = "id") Long Eggtransferdbid,
          @PathVariable(value="username") String username  ) {
        Eggtransfer egg = eggtransferRepository.findByDbid(Eggtransferdbid);
        if (egg == null) {
            return ResponseEntity.notFound().build();
        }
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                username, 
//                "Egg", 
//                "Delete Transfer Egg", 
//                egg.toString()
//        );
//        userHistoryRepo.save(userHistory);
        eggtransferRepository.delete(egg);
        return ResponseEntity.ok().build();
    }
}

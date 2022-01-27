package com.internal.service.somruinternal.controller;

import static org.springframework.data.domain.ExampleMatcher.matching;

import com.internal.service.somruinternal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import com.internal.service.somruinternal.repository.*;

@RestController
@RequestMapping("/itemarchive")
public class ItemarchiveControl {

    @Autowired
    ItemArchiveRepository itemarchiverepo;
    
//    @Autowired
//    UserHistoryRepository userHistoryRepo;

    @GetMapping("/all")
    public List<ItemArchive> getAllItemArchive() {
        return itemarchiverepo.findAll();
    }

    @PostMapping("/add")
    public ItemArchive createItemArchive(@Valid @RequestBody ItemArchive itemarchive) {
        ItemArchive savedItemArchive = itemarchiverepo.save(itemarchive);
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                savedItemArchive.getModifyperson(), 
//                "ItemArchive", 
//                "Create ItemArchive", 
//                savedItemArchive.toString()
//        );
//        userHistoryRepo.save(userHistory);
        return savedItemArchive;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ItemArchive> getItemArchiveById(@PathVariable(value = "id") Long itemarchiveId) {
        ItemArchive itemarchive = itemarchiverepo.findByItemarchivedbid(itemarchiveId);
        if (itemarchive == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(itemarchive);
    }

    @GetMapping("/getItemarchiveByItemdbid/{id}")
    public List<ItemArchive> getItemarchiveByItemdbid(@PathVariable(value = "id") Long itemdbid) {
        Example<ItemArchive> example = Example.of(new ItemArchive((long) -1, "", new Date(), "", itemdbid, "", "", "", "", "", true, "",
                "", "", "", "", "", "", "", "", new Date(), "", ""), matching(). //
                withIgnorePaths("itemarchivedbid", "action", "createtime", "createperson", "category", "cat", "suppliercat", "name", "type", "active",
                        "clientspecific", "supplier", "manufacturer", "unit", "unitprice", "unitsize", "quantitythreshold",
                        "supplylink", "manufacturerlink", "lastmodify", "modifyperson", "comment"));
        return itemarchiverepo.findAll(example);
    }

    @DeleteMapping("/delete/{id}/{username}/")
    public ResponseEntity<ItemArchive> deleteItemArchive(
            @PathVariable(value = "id") Long itemarchiveId,
            @PathVariable(value = "username") String username) {
        ItemArchive itemArchive = itemarchiverepo.findByItemarchivedbid(itemarchiveId);
        if (itemArchive == null) {
            return ResponseEntity.notFound().build();
        }
//        UserHistory userHistory = new UserHistory(
//                -1, 
//                new java.util.Date(System.currentTimeMillis()), 
//                username, 
//                "ItemArchive", 
//                "Delete ItemArchive", 
//                itemArchive.toString()
//        );
//        userHistoryRepo.save(userHistory);
        itemarchiverepo.delete(itemArchive);
        return ResponseEntity.ok().build();
    }
}

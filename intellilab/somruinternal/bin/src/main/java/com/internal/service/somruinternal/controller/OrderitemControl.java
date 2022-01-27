package com.internal.service.somruinternal.controller;

import com.internal.service.somruinternal.model.Orderitem;
import com.internal.service.somruinternal.repository.OrderitemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/orderitem")
public class OrderitemControl {
    @Autowired
    OrderitemRepository OrderitemRepo;
    
    @GetMapping("/all")
    public List<Orderitem> getAllDrugs() {
        return OrderitemRepo.findAll();
    }
    
    @PostMapping("/add")
    public Orderitem createDrug(@Valid @RequestBody Orderitem orderitem) {
    	return OrderitemRepo.save(orderitem);
    }
    
    @GetMapping("/get/{id}")
    public ResponseEntity<Orderitem> getDrugById(@PathVariable(value = "id") Long orderitemId) {
    	Orderitem orderitem = OrderitemRepo.findOne(orderitemId);
        if(orderitem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(orderitem);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Orderitem> updateDrug(@PathVariable(value = "id") Long  orderitemId, 
                                           @Valid @RequestBody Orderitem orderitemDetail) {
        Orderitem orderitem= OrderitemRepo.findOne(orderitemId);
        if(orderitem == null) {
            return ResponseEntity.notFound().build();
        }
        
        orderitem.setDbid(orderitemDetail.getDbid());
        orderitem.setCat(orderitemDetail.getCat());
        orderitem.setName(orderitemDetail.getName());
        orderitem.setVendor(orderitemDetail.getVendor());
        orderitem.setAmount(orderitemDetail.getAmount());       
        orderitem.setUnit(orderitemDetail.getUnit());        
        orderitem.setGrantid(orderitemDetail.getGrantid()); 
        orderitem.setStatus(orderitemDetail.getStatus()); 
        orderitem.setRequesttime(orderitemDetail.getRequesttime());
        orderitem.setOrdertime(orderitemDetail.getOrdertime());
        orderitem.setReceivetime(orderitemDetail.getReceivetime());
        orderitem.setUrgent(orderitemDetail.getUrgent());

        
        Orderitem UpdateEquip = OrderitemRepo.save(orderitem);
        return ResponseEntity.ok(UpdateEquip);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Orderitem> deleteDrug(@PathVariable(value = "id") Long orderitemId) {
    	Orderitem orderitem = OrderitemRepo.findOne(orderitemId);
        if(orderitem == null) {
            return ResponseEntity.notFound().build();
        }

        OrderitemRepo.delete(orderitem);
        return ResponseEntity.ok().build();
    }
}

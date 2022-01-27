package com.internal.service.somruinternal.controller;

import com.internal.service.somruinternal.model.Itemtype;
import com.internal.service.somruinternal.repository.ItemtypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itemtype")
public class ItemtypeControl {

    @Autowired
    ItemtypeRepository itemtype;
    
    
    @GetMapping("/all")
    public List<Itemtype> getAllItemtypes() {
        return itemtype.findAll();
    }
}

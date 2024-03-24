package com.salesmanagement.management.controller;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.ItemsId;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.service.InwardService;
import com.salesmanagement.management.service.ItemService;
import com.salesmanagement.management.service.OutwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private InwardService inwardService;

    @Autowired
    private OutwardService outwardService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@RequestBody Items items) {
        itemService.addNewItem(items);
    }

    @GetMapping("/getItemsList")
    public ResponseEntity<List<Items>> getItemsList()
    {
       List<Items> SearchQ = itemService.getAllItemsList();
       return ResponseEntity.ok(SearchQ);
    }

    @PostMapping("/addInward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> addInward(@RequestBody Inward inward) {
        if(inwardService.isInwardAlreadyContainsItem(inward)) return ResponseEntity.ok(0);
        inwardService.addInward(inward);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/addOutward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> addOutward(@RequestBody Outward outward){
        if(outwardService.isOutwardAlreadyContainsItem(outward)) return ResponseEntity.ok(0);
        outwardService.addOutward(outward);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/searchInwardItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Inward>> searchInwardItem(@RequestBody Items items) {
        List<Inward> searchQ = inwardService.searchInwardByTypeAndSize(items.getItemsId().getItemType(), items.getItemsId().getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchOutwardItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Outward>> searchOutwardItem(@RequestBody Items items) {
        List<Outward> searchQ = outwardService.searchOutwardByTypeAndSize(items.getItemsId().getItemType(),items.getItemsId().getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchAllItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Items>> searchAllItem(@RequestBody Items items){
        List<Items> searchQ = itemService.searchItemsByTypeAndSize(items.getItemsId().getItemType(),items.getItemsId().getItemSize());
        return ResponseEntity.ok(searchQ);
    }
}
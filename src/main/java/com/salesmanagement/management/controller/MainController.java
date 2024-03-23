package com.salesmanagement.management.controller;

import com.salesmanagement.management.entity.Inward;
import com.salesmanagement.management.entity.Items;
import com.salesmanagement.management.entity.Outward;
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
    public void addItem(@RequestParam("itemSize") String itemSize,
                        @RequestParam("itemType") String itemType) {
        Items item = new Items();
        item.setItemSize(Integer.parseInt(itemSize));
        item.setItemType(itemType);
        itemService.addNewItem(item);
    }

    @PostMapping("/addInward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInward(@RequestBody Inward inward) {
        inwardService.addInward(inward);
    }

    @PostMapping("/addOutward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addOutward(@RequestBody Outward outward){
        outwardService.addOutward(outward);
    }

    @PostMapping("/searchInwardItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Inward>> searchInwardItem(@RequestBody Items items) {
        List<Inward> searchQ = inwardService.searchInwardByTypeAndSize(items.getItemType(),items.getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchOutwardItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Outward>> searchOutwardItem(@RequestBody Items items) {
        List<Outward> searchQ = outwardService.searchOutwardByTypeAndSize(items.getItemType(),items.getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchAllItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Items>> searchAllItem(@RequestBody Items items){
        List<Items> searchQ = itemService.searchItemsByTypeAndSize(items.getItemType(),items.getItemSize());
        return ResponseEntity.ok(searchQ);
    }
}
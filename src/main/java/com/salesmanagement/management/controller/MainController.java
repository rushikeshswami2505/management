package com.salesmanagement.management.controller;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.ItemsId;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.sales.Sales;
import com.salesmanagement.management.service.InwardService;
import com.salesmanagement.management.service.ItemService;
import com.salesmanagement.management.service.OutwardService;
import com.salesmanagement.management.service.SalesService;
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

    @Autowired
    private SalesService salesService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> addItem(@RequestBody Items items) {
        if (items.getItemsId() == null) {
            return ResponseEntity.ok(0);
        }
        itemService.addNewItem(items);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/deleteItem")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteItem(@RequestBody Items item) {
        System.out.println("delete called for item"+item);
        if (item.getItemsId() == null) {
            return ResponseEntity.ok(0);
        }
        itemService.deleteItem(item);

        return ResponseEntity.ok(1);
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
        if (inward.getInwardId() == null) {
            return ResponseEntity.ok(0); // Handle the case where InwardId is not set
        }

        // Check if the inward already exists
        if (inwardService.isInwardAlreadyContainsItem(inward)) {
            return ResponseEntity.ok(0);
        }

        System.out.println("delete inward: "+inward.toString());
        // Save the inward
        inwardService.addInward(inward);
        return ResponseEntity.ok(1);
    }
    @PostMapping("/updateInward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> updateInward(@RequestBody Inward inward) {
        if (inward.getInwardId() == null) {
            return ResponseEntity.ok(0);
        }
        inwardService.updateInward(inward);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/deleteInward")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteInward(@RequestBody Inward inward) {
        if (inward.getInwardId() == null) {
            return ResponseEntity.ok(0);
        }
        inwardService.deleteInward(inward);
        System.out.println("delete inward: "+inward);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/addOutward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> addOutward(@RequestBody Outward outward) {
        if (outward.getOutwardId() == null) {
            return ResponseEntity.ok(0); // Handle the case where OutwardId is not set
        }

        // Check if the outward already exists
        if (outwardService.isOutwardAlreadyContainsItem(outward)) {
            return ResponseEntity.ok(0);
        }
        System.out.println("add outward: "+outward.toString());
        // Save the outward
        outwardService.addOutward(outward);
        return ResponseEntity.ok(1);
    }

    @PostMapping("/deleteOutward")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> deleteOutward(@RequestBody Outward outward) {
        if (outward.getOutwardId() == null) {
            return ResponseEntity.ok(0);
        }
        outwardService.deleteOutward(outward);
        System.out.println("delete outward: "+outward);
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

    @PostMapping("/searchSalesItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Sales>> searchSalesItem(@RequestBody Items items){
        List<Sales> searchQ = salesService.searchSalesByTypeAndSize(items.getItemsId().getItemType(),items.getItemsId().getItemSize());
        return ResponseEntity.ok(searchQ);
    }
}
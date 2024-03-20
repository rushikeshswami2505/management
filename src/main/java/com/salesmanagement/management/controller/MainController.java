package com.salesmanagement.management.controller;

import com.salesmanagement.management.entity.Inward;
import com.salesmanagement.management.entity.Items;
import com.salesmanagement.management.service.InwardService;
import com.salesmanagement.management.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private InwardService inwardService;

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


//    @PostMapping("/addInward")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void  addInward(@RequestParam("inwardMemoNumber") String inwardMemoNumber,
//                           @RequestParam("inwardItemType") String inwardItemType,
//                           @RequestParam("inwardItemSize") String inwardItemSize,
//                           @RequestParam("inwardDate") String inwardDate,
//                           @RequestParam("inwardDozen") String inwardDozen,
//                           @RequestParam("inwardPiece") String inwardPiece)
//    {
//        Inward inward = new Inward();
//        inward.setInwardMemoNumber(Integer.parseInt(inwardMemoNumber));
//        inward.setInwardItemType(inwardItemType);
//        inward.setInwardItemSize(Integer.parseInt(inwardItemSize));
//        inward.setInwardDate(inwardDate);
//        inward.setInwardDozen(Float.parseFloat(inwardDozen));
//        inward.setInwardPiece(Integer.parseInt(inwardPiece));
//        inwardService.addInward(inward);
//    }
}


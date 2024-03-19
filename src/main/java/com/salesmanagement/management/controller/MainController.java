package com.salesmanagement.management.controller;

import com.salesmanagement.management.entity.Items;
import com.salesmanagement.management.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MainController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/addItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@RequestParam("itemSize") String itemSize, @RequestParam("itemType") String itemType) {
        System.out.println("itemSize: " + itemSize);
        System.out.println("itemType: " + itemType);
        Items item = new Items();
        item.setItemSize(Integer.parseInt(itemSize));
        item.setItemType(itemType);
        itemService.addNewItem(item);
    }
}


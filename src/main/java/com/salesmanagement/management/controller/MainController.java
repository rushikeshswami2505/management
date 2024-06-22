package com.salesmanagement.management.controller;

import com.salesmanagement.management.download.ExcelGenerator;
import com.salesmanagement.management.entity.UpdateEntity;
import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.SearchItems;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.sales.Sales;
import com.salesmanagement.management.service.InwardService;
import com.salesmanagement.management.service.ItemService;
import com.salesmanagement.management.service.OutwardService;
import com.salesmanagement.management.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
        return "login";
    }
    @GetMapping("/home.html")
    public String homePage() {
        return "home"; // Assuming "home.html" is located in "resources/templates/home.html"
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
    public ResponseEntity<Integer> updateInward(@RequestBody UpdateEntity updateEntity) {
        inwardService.updateInward(updateEntity.getItemsOld().getItemsId(),updateEntity.getItemsNew().getItemsId());
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

    @PostMapping("/updateOutward")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> updateOutward(@RequestBody UpdateEntity updateEntity) {
        outwardService.updateOutward(updateEntity.getItemsOld().getItemsId(),updateEntity.getItemsNew().getItemsId());
        return ResponseEntity.ok(2);
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
    public ResponseEntity<List<Inward>> searchInwardItem(@RequestBody SearchItems searchItems) {
        List<Inward> searchQ = inwardService.searchInwardByTypeAndSizeAndMemoNumber(searchItems.getItemType(), searchItems.getItemSize(), searchItems.getMemoNumber());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchOutwardItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Outward>> searchOutwardItem(@RequestBody SearchItems searchItems) {
        List<Outward> searchQ = outwardService.searchOutwardByTypeAndSizeAndBailNumber(searchItems.getItemType(), searchItems.getItemSize(), searchItems.getBailNumber());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/searchAllItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Items>> searchAllItem(@RequestBody SearchItems searchItems){
        List<Items> searchQ = itemService.searchItemsByTypeAndSize(searchItems.getItemType(), searchItems.getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/updateSales")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Integer> updateSales(@RequestBody UpdateEntity updateEntity) {
        salesService.updateSales(updateEntity.getItemsOld().getItemsId(),updateEntity.getItemsNew().getItemsId());
        System.out.println("update controller");
        return ResponseEntity.ok(3);
    }

    @PostMapping("/searchSalesItem")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<List<Sales>> searchSalesItem(@RequestBody SearchItems searchItems){
        List<Sales> searchQ = salesService.searchSalesByTypeAndSize(searchItems.getItemType(), searchItems.getItemSize());
        return ResponseEntity.ok(searchQ);
    }

    @PostMapping("/downloadOutwardExcel")
    public ResponseEntity<ByteArrayResource> downloadOutwardExcel(@RequestBody SearchItems searchItems) throws IOException {
        List<Outward> outwardList = outwardService.searchOutwardByTypeAndSizeAndBailNumber(searchItems.getItemType(), searchItems.getItemSize(), searchItems.getBailNumber());

        ByteArrayOutputStream out = ExcelGenerator.generateOutwardExcel(outwardList);

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=outward.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PostMapping("/downloadInwardExcel")
    public ResponseEntity<ByteArrayResource> downloadInwardExcel(@RequestBody SearchItems searchItems) throws IOException {
        List<Inward> inwardList = inwardService.searchInwardByTypeAndSizeAndMemoNumber(searchItems.getItemType(), searchItems.getItemSize(), searchItems.getMemoNumber());

        ByteArrayOutputStream out = ExcelGenerator.generateInwardExcel(inwardList);

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=inward.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }

    @PostMapping("/downloadStockExcel")
    public ResponseEntity<ByteArrayResource> downloadStockExcel(@RequestBody SearchItems searchItems) throws IOException {
        List<Sales> salesList = salesService.searchSalesByTypeAndSize(searchItems.getItemType(), searchItems.getItemSize());

        ByteArrayOutputStream out = ExcelGenerator.generateStockExcel(salesList);

        ByteArrayResource resource = new ByteArrayResource(out.toByteArray());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=stock.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(resource.contentLength())
                .body(resource);
    }
}
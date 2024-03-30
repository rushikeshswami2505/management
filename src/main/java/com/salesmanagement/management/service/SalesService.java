package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.sales.Sales;
import com.salesmanagement.management.entity.sales.SalesId;
import com.salesmanagement.management.repository.SalesRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesService {

    private final SalesRepository salesRepository;

    @Autowired
    public SalesService(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @Transactional
    public void updateSalesOnInwardAddition(Inward inward) {
        updateSales(inward.getInwardId().getInwardItemType(), inward.getInwardId().getInwardItemSize(), inward.getInwardDozen(), inward.getInwardPiece());
    }
    @Transactional
    public void updateSalesOnOutwardAddition(Outward outward) {
        updateSales(outward.getOutwardId().getOutwardItemType(), outward.getOutwardId().getOutwardItemSize(), -outward.getOutwardDozen(), -outward.getOutwardPiece());
    }

    @Transactional
    public void updateSalesOnOutwardDeletion(Outward outward) {
        updateSales(outward.getOutwardId().getOutwardItemType(),outward.getOutwardId().getOutwardItemSize(),outward.getOutwardDozen(),outward.getOutwardPiece());
    }

    @Transactional
    public void updateSalesOnInwardDeletion(Inward inward) {
        updateSales(inward.getInwardId().getInwardItemType(),inward.getInwardId().getInwardItemSize(),-inward.getInwardDozen(),-inward.getInwardPiece());
    }

    private void updateSales(String itemType, int itemSize, float dozenChange, int pieceChange) {
        SalesId salesId = new SalesId(itemSize, itemType);
        Sales existingSales = salesRepository.findById(salesId).orElse(null);

        if (existingSales != null) {
            existingSales.setSalesDozen(existingSales.getSalesDozen() + dozenChange);
            existingSales.setSalesPiece(existingSales.getSalesPiece() + pieceChange);
        } else {
            existingSales = new Sales(salesId, dozenChange, pieceChange);
        }

        salesRepository.save(existingSales);
    }


    public List<Sales> searchSalesByTypeAndSize(String itemType, int itemSize) {
        if ((itemType == null || StringUtils.isEmpty(itemType)) && itemSize == 0) return salesRepository.findAll();
        else if (itemType == null || StringUtils.isEmpty(itemType)) return salesRepository.getSalesByItemSize(itemSize);
        else if (itemSize == 0) return salesRepository.getSalesByItemType(itemType);
        else return salesRepository.getSalesByItemTypeAndItemSize(itemType, itemSize);
    }
}

//public void calculateAndSaveAvailableItems() {
//    List<Inward> inwardList = inwardRepository.findAll();
//    List<Outward> outwardList = outwardRepository.findAll();
//
//    Map<SalesId, Sales> availableItemsMap = new HashMap<>();
//
//    // Subtract outward from inward
//    for (Inward inward : inwardList) {
//        SalesId salesId = new SalesId(inward.getInwardId().getInwardItemSize(), inward.getInwardId().getInwardItemType());
//        Sales availableItem = availableItemsMap.getOrDefault(salesId, new Sales());
//        availableItem.setSalesId(salesId);
//        availableItem.setSalesDozen(availableItem.getSalesDozen() + inward.getInwardDozen());
//        availableItem.setSalesPiece(availableItem.getSalesPiece() + inward.getInwardPiece());
//        availableItemsMap.put(salesId, availableItem);
//    }
//
//    for (Outward outward : outwardList) {
//        SalesId salesId = new SalesId(outward.getOutwardId().getOutwardItemSize(), outward.getOutwardId().getOutwardItemType());
//        Sales availableItem = availableItemsMap.getOrDefault(salesId, new Sales());
//        availableItem.setSalesId(salesId);
//        availableItem.setSalesDozen(availableItem.getSalesDozen() - outward.getOutwardDozen());
//        availableItem.setSalesPiece(availableItem.getSalesPiece() - outward.getOutwardPiece());
//        availableItemsMap.put(salesId, availableItem);
//    }
//
//    // Save available items to database
//    for (Sales availableItem : availableItemsMap.values()) {
//        salesRepository.save(availableItem);
//    }
//}

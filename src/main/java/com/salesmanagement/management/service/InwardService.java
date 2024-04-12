package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.ItemsId;
import com.salesmanagement.management.repository.InwardRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InwardService {
    private final InwardRepository inwardRepository;

    @Autowired
    private SalesService salesService;

    @Autowired
    public InwardService(InwardRepository inwardRepository) {
        this.inwardRepository = inwardRepository;
    }
    public void addInward(Inward inward){
        inwardRepository.save(inward);
        salesService.updateSalesOnInwardAddition(inward);
    }

    public List<Inward> searchInwardByTypeAndSize(String itemType, int itemSize) {
        if ((itemType == null || StringUtils.isEmpty(itemType)) && itemSize == 0) return inwardRepository.findAll();
        else if (itemType == null || StringUtils.isEmpty(itemType)) return inwardRepository.getInwardByItemSize(itemSize);
        else if (itemSize == 0) return inwardRepository.getInwardByItemType(itemType);
        else return inwardRepository.getInwardByItemTypeAndItemSize(itemType, itemSize);
    }

    public boolean isInwardAlreadyContainsItem(Inward inward) {
        Inward getInward = inwardRepository.findByInwardId(inward.getInwardId());
        return getInward!=null;
    }

    @Transactional
    public void updateInward(ItemsId itemOldId, ItemsId itemNewId) {
        inwardRepository.updateInward(itemOldId.getItemSize(),itemOldId.getItemType(),
                                      itemNewId.getItemSize(),itemNewId.getItemType());
    }
    @Transactional
    public void deleteInward(Inward inward) {
        Inward getInward = inwardRepository.findByInwardId(inward.getInwardId());
        System.out.println("inward SERVICE: "+inward.getInwardId());
        if (getInward != null) {
            salesService.updateSalesOnInwardDeletion(getInward);
            inwardRepository.delete(getInward);
        }
    }
}

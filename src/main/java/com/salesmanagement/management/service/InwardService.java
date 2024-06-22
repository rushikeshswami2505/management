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

    public List<Inward> searchInwardByTypeAndSizeAndMemoNumber(String itemType, int itemSize, String memoNumber) {
        if ((StringUtils.isEmpty(itemType)) && itemSize == 0 && StringUtils.isEmpty(memoNumber)) return inwardRepository.findAll();
        else if (itemSize==0 && StringUtils.isEmpty(itemType)) return inwardRepository.getInwardByMemoNumber(memoNumber);
        else if (StringUtils.isEmpty(itemType) && StringUtils.isEmpty(memoNumber)) return inwardRepository.getInwardByItemSize(itemSize);
        else if (itemSize==0 && StringUtils.isEmpty(memoNumber)) return inwardRepository.getInwardByItemType(itemType);
        else if (itemSize==0) return inwardRepository.getInwardByItemTypeAndMemoNumber(itemType,memoNumber);
        else if (StringUtils.isEmpty(itemType)) return inwardRepository.getInwardByItemSizeAndMemoNumber(itemSize,memoNumber);
        else if (StringUtils.isEmpty(memoNumber)) return inwardRepository.getInwardByItemTypeAndItemSize(itemType, itemSize);
        else return inwardRepository.getInwardByItemTypeAndItemSizeAndMemoNumber(itemType, itemSize,memoNumber);
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

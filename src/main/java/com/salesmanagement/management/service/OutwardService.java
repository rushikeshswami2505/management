package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.items.ItemsId;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.outward.OutwardId;
import com.salesmanagement.management.repository.OutwardRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutwardService {
    private final OutwardRepository outwardRepository;
    @Autowired
    private SalesService salesService;
    @Autowired
    public OutwardService(OutwardRepository outwardRepository) {
        this.outwardRepository = outwardRepository;
    }
    public void addOutward(Outward outward){
        outwardRepository.save(outward);
        salesService.updateSalesOnOutwardAddition(outward);
    }

    public List<Outward> searchOutwardByTypeAndSizeAndBailNumber(String itemType, int itemSize, String bailNumber) {
        if ((StringUtils.isEmpty(itemType)) && itemSize == 0 && StringUtils.isEmpty(bailNumber)) return outwardRepository.findAll();
        else if (itemSize==0 && StringUtils.isEmpty(itemType)) return outwardRepository.getOutwardByBailNumber(bailNumber);
        else if (StringUtils.isEmpty(itemType) && StringUtils.isEmpty(bailNumber)) return outwardRepository.getOutwardByItemSize(itemSize);
        else if (itemSize==0 && StringUtils.isEmpty(bailNumber)) return outwardRepository.getOutwardByItemType(itemType);
        else if (itemSize==0) return outwardRepository.getOutwardByItemTypeAndBailNumber(itemType,bailNumber);
        else if (StringUtils.isEmpty(itemType)) return outwardRepository.getOutwardByItemSizeAndBailNumber(itemSize,bailNumber);
        else if (StringUtils.isEmpty(bailNumber)) return outwardRepository.getOutwardByItemTypeAndItemSize(itemType,itemSize);
        else return outwardRepository.getOutwardByItemTypeAndItemSizeAndBailNumber(itemType, itemSize,bailNumber);
    }

    public boolean isOutwardAlreadyContainsItem(Outward outward) {
        Outward getOutward = outwardRepository.findByOutwardId(outward.getOutwardId());
        return getOutward!=null;
    }

    @Transactional
    public void updateOutward(ItemsId itemOldId,ItemsId itemNewId) {
        outwardRepository.updateOutward(itemOldId.getItemSize(),itemOldId.getItemType(),
                                        itemNewId.getItemSize(),itemNewId.getItemType());
    }


    @Transactional
    public void deleteOutward(Outward outward) {
        Outward getOutward = outwardRepository.findByOutwardId(outward.getOutwardId());
        System.out.println("outward SERVICE: "+outward.getOutwardId());
        if (getOutward != null) {
            salesService.updateSalesOnOutwardDeletion(getOutward);
            outwardRepository.delete(getOutward);
        }
    }
}

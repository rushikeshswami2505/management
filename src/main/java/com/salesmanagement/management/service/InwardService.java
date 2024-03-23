package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.Inward;
import com.salesmanagement.management.entity.Items;
import com.salesmanagement.management.repository.InwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class InwardService {
    private final InwardRepository inwardRepository;

    @Autowired
    public InwardService(InwardRepository inwardRepository) {
        this.inwardRepository = inwardRepository;
    }
    public void addInward(Inward inward){
        inwardRepository.save(inward);
    }

    public List<Inward> searchInwardByTypeAndSize(String itemType, int itemSize) {
        if (itemType.isEmpty() && itemSize == 0) return inwardRepository.findAll();
        else if(itemType.isEmpty()) return inwardRepository.getInwardByItemSize(itemSize);
        else if(itemSize == 0) return inwardRepository.getInwardByItemType(itemType);
        else return inwardRepository.getInwardByItemTypeAndItemSize(itemType,itemSize);
    }
}

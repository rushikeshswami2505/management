package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.Outward;
import com.salesmanagement.management.repository.OutwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutwardService {
    private final OutwardRepository outwardRepository;

    @Autowired
    public OutwardService(OutwardRepository outwardRepository) {
        this.outwardRepository = outwardRepository;
    }
    public void addOutward(Outward outward){
        outwardRepository.save(outward);
    }

    public List<Outward> searchOutwardByTypeAndSize(String itemType, int itemSize) {
        if (itemType.isEmpty() && itemSize == 0) return outwardRepository.findAll();
        else if(itemType.isEmpty()) return outwardRepository.getOutwardByItemSize(itemSize);
        else if(itemSize == 0) return outwardRepository.getOutwardByItemType(itemType);
        else return outwardRepository.getOutwardByItemTypeAndItemSize(itemType,itemSize);
    }
}

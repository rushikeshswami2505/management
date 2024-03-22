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

    public List<Outward> searchOutwardByTypeAndName(String itemType, int itemSize) {
        return outwardRepository.getOutwardByItemTypeAndItemSize(itemType,itemSize);
    }
}

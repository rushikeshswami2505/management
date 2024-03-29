package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.repository.InwardRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        if ((itemType == null || StringUtils.isEmpty(itemType)) && itemSize == 0) return inwardRepository.findAll();
        else if (itemType == null || StringUtils.isEmpty(itemType)) return inwardRepository.getInwardByItemSize(itemSize);
        else if (itemSize == 0) return inwardRepository.getInwardByItemType(itemType);
        else return inwardRepository.getInwardByItemTypeAndItemSize(itemType, itemSize);
    }

    public boolean isInwardAlreadyContainsItem(Inward inward) {
        InwardId inwardId = inward.getInwardId();
        String inwardDate = inward.getInwardDate();
        float inwardDozen = inward.getInwardDozen();
        int inwardPiece = inward.getInwardPiece();
        Inward existingInward = inwardRepository.findByInwardIdAndInwardDateAndInwardDozenAndInwardPiece(
                inwardId, inwardDate, inwardDozen, inwardPiece);

        return existingInward != null;
    }
}

package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
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
//        InwardId inwardId = inward.getInwardId();
//        String inwardDate = inward.getInwardDate();
//        float inwardDozen = inward.getInwardDozen();
//        int inwardPiece = inward.getInwardPiece();
//        Inward existingInward = inwardRepository.findByInwardIdAndInwardDateAndInwardDozenAndInwardPiece(
//                inwardId, inwardDate, inwardDozen, inwardPiece);
//        return existingInward != null;
    }

    @Transactional
    public void updateInward(Inward inward) {
        // Retrieve the existing inward entity from the database
        Inward existingInward = inwardRepository.findByInwardId(inward.getInwardId());

        // Check if the existing inward entity exists
        if (existingInward != null) {
            // Update the properties of the existing inward entity with the values from the provided inward entity
            existingInward.setInwardDate(inward.getInwardDate());
            existingInward.setInwardDozen(inward.getInwardDozen());
            existingInward.setInwardPiece(inward.getInwardPiece());

            // Save the updated inward entity to the database
            inwardRepository.save(existingInward);
        }
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

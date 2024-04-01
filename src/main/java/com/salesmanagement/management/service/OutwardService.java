package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.inward.InwardId;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.entity.outward.OutwardId;
import com.salesmanagement.management.repository.OutwardRepository;
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

    public List<Outward> searchOutwardByTypeAndSize(String itemType, int itemSize) {
        if (itemType.isEmpty() && itemSize == 0) return outwardRepository.findAll();
        else if(itemType.isEmpty()) return outwardRepository.getOutwardByItemSize(itemSize);
        else if(itemSize == 0) return outwardRepository.getOutwardByItemType(itemType);
        else return outwardRepository.getOutwardByItemTypeAndItemSize(itemType,itemSize);
    }

    public boolean isOutwardAlreadyContainsItem(Outward outward) {
        Outward getOutward = outwardRepository.findByOutwardId(outward.getOutwardId());
        return getOutward!=null;
//        OutwardId outwardId = outward.getOutwardId();
//        String outwardDate = outward.getOutwardDate();
//        float outwardDozen = outward.getOutwardDozen();
//        int outwardPiece = outward.getOutwardPiece();
//        Outward existingOutward = outwardRepository.findByOutwardIdAndOutwardDateAndOutwardDozenAndOutwardPiece(
//                outwardId, outwardDate, outwardDozen, outwardPiece);
//        return existingOutward != null;
    }

    @Transactional
    public void updateOutward(Outward outward) {
        // Retrieve the existing inward entity from the database
        Outward existingOutward = outwardRepository.findByOutwardId(outward.getOutwardId());

        // Check if the existing inward entity exists
        if (existingOutward != null) {
            // Update the properties of the existing inward entity with the values from the provided inward entity
            existingOutward.setOutwardDate(outward.getOutwardDate());
            existingOutward.setOutwardDozen(outward.getOutwardDozen());
            existingOutward.setOutwardPiece(outward.getOutwardPiece());

            // Save the updated inward entity to the database
            outwardRepository.save(existingOutward);
        }
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

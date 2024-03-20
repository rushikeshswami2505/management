package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.Inward;
import com.salesmanagement.management.repository.InwardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

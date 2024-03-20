package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.Items;
import com.salesmanagement.management.repository.ItemsRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }
    public void addNewItem(Items item) {
        itemsRepository.save(item);
    }
}
package com.salesmanagement.management.service;

import com.salesmanagement.management.entity.inward.Inward;
import com.salesmanagement.management.entity.items.Items;
import com.salesmanagement.management.entity.outward.Outward;
import com.salesmanagement.management.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Items> searchItemsByTypeAndSize(String itemType, int itemSize) {
        if (itemType.isEmpty() && itemSize == 0) return itemsRepository.findAll();
        else if(itemType.isEmpty()) return itemsRepository.getItemsByItemSize(itemSize);
        else if(itemSize == 0) return itemsRepository.getItemsByItemType(itemType);
        else return itemsRepository.getItemsByItemTypeAndItemSize(itemType,itemSize);
    }
    public List<Items> getAllItemsList() {
        return itemsRepository.findAll();
    }

    @Transactional
    public void deleteItem(Items item) {
        Items getItems = itemsRepository.findByItemsId(item.getItemsId());
        if (getItems != null) {
            itemsRepository.delete(getItems);
        }
    }
}